package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import at.uibk.dps.optfund.ant_colony.model.AntPath;
import at.uibk.dps.optfund.ant_colony.selector.Selector;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import org.opt4j.core.start.Constant;

import java.util.*;

/**
 * An implementation for an ant colony
 */
public class AntColonyImpl implements AntColony {

    private final Selector edgeSelector;
    private final int numberOfAnts;
    private final double alpha;
    private final double beta;
    private final double pheromoneFactor;
    private final double q;
    private final ArrayList<Ant> ants = new ArrayList<>();
    private final Object lock = new Object();

    private AbstractAntNode startNode = null;

    /**
     * @param edgeSelector the selector which decides which path should be taken
     * @param numberOfAnts the number of ants
     * @param alpha the pheromone weight
     * @param beta the path length weight
     * @param ro the pheromone decay rate
     * @param q the pheromone level constant
     */
    @Inject
    public AntColonyImpl(Selector edgeSelector,
                         @Constant(value = "numberOfAnts", namespace = AntColonyImpl.class) int numberOfAnts,
                         @Constant(value = "alpha", namespace = AntColonyImpl.class) double alpha,
                         @Constant(value = "beta", namespace = AntColonyImpl.class) double beta,
                         @Constant(value = "ro", namespace = AntColonyImpl.class) double ro,
                         @Constant(value = "q", namespace = AntColonyImpl.class) double q) {
        this.edgeSelector = edgeSelector;
        this.numberOfAnts = numberOfAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.pheromoneFactor = 1 - ro;
        this.q = q;
    }

    /**
     * @param startNode The start node where all ants start
     */
    @Override
    public void init(AbstractAntNode startNode) {
        this.startNode = startNode;
        int numberOfCities = startNode.getNeighbours().values().size();
        for(int i = 0; i < numberOfAnts; i++) {
            ants.add(new Ant(i, startNode, numberOfCities, this.edgeSelector));
        }
    }

    /**
     * @return the best path of this iteration
     */
    @Override
    public Collection<AbstractAntNode> next() {
        Map<Ant, AntPath> pathsPerAnt = new HashMap<>();

        ants.parallelStream().forEach(x -> {
            AntPath path = x.getPath(alpha, beta);

            synchronized (lock) {
                pathsPerAnt.put(x, path);
            }
        });

        updatePheromone(startNode, new HashSet<>(), pathsPerAnt);

        return getBestPath(pathsPerAnt.values());
    }

    /**
     * @param node the current node to update
     * @param alreadyDone a set which contains all already visited edges
     * @param paths the paths the ants traveled
     */
    private void updatePheromone(AbstractAntNode node, Set<AbstractAntEdge> alreadyDone, Map<Ant, AntPath> paths) {

        ImmutableMap<AbstractAntNode, AbstractAntEdge> neighbors = node.getNeighbours();
        for(AbstractAntNode neighborNode : node.getNeighbours().keySet()) {

            AbstractAntEdge edge = neighbors.get(neighborNode);

            // check if edge has been processed
            if(alreadyDone.contains(edge)) {
                continue;
            }
            alreadyDone.add(edge);

            double newPheromone = pheromoneFactor * edge.getPheromone();

            for(Ant ant : ants) {
                newPheromone += ant.hasUsedEdge(edge)
                        ? q / paths.get(ant).getCost()
                        : 0;
            }

            edge.setPheromone(newPheromone);
            updatePheromone(neighborNode, alreadyDone, paths);
        }
    }

    /**
     * @param paths the paths for each ant of this iteration
     * @return the path with the lowest cost
     */
    private List<AbstractAntNode> getBestPath(Collection<AntPath> paths) {
        return paths
                .stream()
                .min(Comparator.comparingDouble(AntPath::getCost))
                .orElseThrow()
                .getNodes();
    }
}
