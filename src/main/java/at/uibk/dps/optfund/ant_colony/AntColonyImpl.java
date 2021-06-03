package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import at.uibk.dps.optfund.ant_colony.model.AntPath;
import com.google.inject.Inject;
import org.opt4j.core.start.Constant;

import java.util.*;

/**
 * An implementation for an ant colony
 */
public class AntColonyImpl implements AntColony {

    private final int numberOfAnts;
    private final double alpha;
    private final double beta;
    private final double ro;
    private final double q;
    private final ArrayList<Ant> ants = new ArrayList<>();
    private final Object lock = new Object();

    @Inject
    public AntColonyImpl(@Constant(value = "numberOfAnts") int numberOfAnts,
                         @Constant(value = "alpha") double alpha,
                         @Constant(value = "beta") double beta,
                         @Constant(value = "ro") double ro,
                         @Constant(value = "q") double q) {
        this.numberOfAnts = numberOfAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.ro = ro;
        this.q = q;
    }

    @Override
    public void init(AbstractAntNode startNode) {
        int numberOfCities = startNode.getNeighbours().values().size() + 1;
        for(int i = 0; i < numberOfAnts; i++) {
            ants.add(new Ant(i, startNode, numberOfCities));
        }
    }

    @Override
    public Collection<AbstractAntNode> next() {
        Map<Ant, AntPath> pathsPerAnt = new HashMap<>();

        ants.parallelStream().forEach(x -> {
            AntPath path = x.getPath(alpha, beta);

            synchronized (lock) {
                pathsPerAnt.put(x, path);
            }
        });

        updatePheromone(pathsPerAnt);

        return getBestPath(pathsPerAnt.values());
    }

    private void updatePheromone(Map<Ant, AntPath> paths) {
        Set<AbstractAntEdge> alreadyDone = new HashSet<>();
        final double factor = 1 - ro;

        for(AntPath p : paths.values()) {
            for(AbstractAntEdge edge : p.getEdges()) {
                if(alreadyDone.contains(edge)) {
                    continue;
                }

                double newPheromone = factor * edge.getPheromone();

                for(Ant ant : paths.keySet()) {
                    newPheromone += ant.hasUsedEdge(edge)
                            ? q / paths.get(ant).getCost()
                            : 0;
                }

                edge.setPheromone(newPheromone);

                alreadyDone.add(edge);
            }
        }
    }

    private List<AbstractAntNode> getBestPath(Collection<AntPath> paths) {
        return paths.stream()
                .min(Comparator.comparingDouble(AntPath::getCost)).orElseThrow().getNodes();
    }
}
