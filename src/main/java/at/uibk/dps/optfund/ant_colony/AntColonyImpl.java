package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.*;
import at.uibk.dps.optfund.ant_colony.stepper.AntStepper;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import org.opt4j.core.start.Constant;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation for an ant colony
 * @param <T> The type of data inside the node
 * @author Maximilian Suitner
 */
public class AntColonyImpl<T> implements AntColony<T> {

    private final AntStepper stepper;
    private final int numberOfAnts;
    private final double alpha;
    private final double beta;
    private final double pheromoneFactor;
    private final double q;
    private final ArrayList<Ant<T>> ants = new ArrayList<>();
    private final Object lock = new Object();

    private Set<AntEdge<T>> edges = null;
    private List<AntNode<T>> nodes;

    /**
     * @param stepper The stepping interface of ant which selects the next node
     * @param numberOfAnts the number of ants
     * @param alpha the pheromone weight
     * @param beta the path length weight
     * @param ro the pheromone decay rate
     * @param q the pheromone level constant
     */
    @Inject
    public AntColonyImpl(AntStepper stepper,
                         @Constant(value = AntConstants.NUMBER_OF_ANTS_CONSTANT, namespace = AntColonyImpl.class) int numberOfAnts,
                         @Constant(value = AntConstants.ALPHA_CONSTANT, namespace = AntColonyImpl.class) double alpha,
                         @Constant(value = AntConstants.BETA_CONSTANT, namespace = AntColonyImpl.class) double beta,
                         @Constant(value = AntConstants.RO_CONSTANT, namespace = AntColonyImpl.class) double ro,
                         @Constant(value = AntConstants.Q_CONSTANT, namespace = AntColonyImpl.class) double q) {
        this.stepper = stepper;
        this.numberOfAnts = numberOfAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.pheromoneFactor = 1 - ro;
        this.q = q;
    }

    /**
     * @param startNode The start node where all ants start
     * @param nodes all nodes in the problem
     */
    @Override
    public void init(AntNode<T> startNode, List<AntNode<T>> nodes) {
        this.edges = collectEdges(startNode, new HashSet<>(), new HashSet<>());
        this.nodes = nodes;

        int numberOfCities = startNode.getNeighbours().values().size();
        for(int i = 0; i < numberOfAnts; i++) {
            ants.add(new Ant<>(i, startNode, numberOfCities, stepper));
        }

        // sort edges based on their weights
        this.nodes.parallelStream().forEach(AntNode::sortNeighbourEdges);
    }

    /**
     * @param node the current node to process
     * @param alreadyDoneNodes already processed nodes
     * @param accumulator accumulator for result
     * @return a set of all edges for the current node and all its neighbors
     */
    private Set<AntEdge<T>> collectEdges(AntNode<T> node, Set<AntNode<T>> alreadyDoneNodes, Set<AntEdge<T>> accumulator) {

        if(alreadyDoneNodes.contains(node)) {
            return accumulator;
        }
        alreadyDoneNodes.add(node);

        ImmutableMap<AntNode<T>, AntEdge<T>> neighbors = node.getNeighbours();
        accumulator.addAll(node.getNeighbours().values());
        neighbors.keySet().forEach(x -> collectEdges(x, alreadyDoneNodes, accumulator));
        return accumulator;
    }

    /**
     * @return the best path of this iteration
     */
    @Override
    public Collection<Collection<AntNode<T>>> next() {
        List<AntPath<T>> paths = ants
                .parallelStream()
                .map(x -> x.getPath(alpha, beta))
                .collect(Collectors.toList());

        updatePheromone(paths);

        return paths.stream().map(AntPath::getNodes).collect(Collectors.toList());
    }

    /**
     * @param paths the paths the ants traveled
     * @author Daniel Eberharter
     */
    private void updatePheromone(List<AntPath<T>> paths) {

        // pheromone decay
        this.edges.parallelStream().forEach(x -> x.setPheromone(pheromoneFactor * x.getPheromone()));

        // apply new pheromones
        for (AntPath<T> p: paths) {
            final double factor = q / p.getCost();
            for(AntEdge<T> e: p.getEdges()) {
                e.setPheromone(e.getPheromone() + factor);
            }
        }

        // update edge weights and sort them corresponding to their weight
        this.nodes.parallelStream().forEach(x -> {
            // recalculate the weight of each edge (pheromones have changed)
            x.getNeighbourEdges().forEach(AntEdge::updateEdgeWeight);
            // resort edges based on their weights
            x.sortNeighbourEdges();
        });
    }
}
