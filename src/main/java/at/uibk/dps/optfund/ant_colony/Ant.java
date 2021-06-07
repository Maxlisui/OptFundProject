package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import at.uibk.dps.optfund.ant_colony.model.AntPath;
import at.uibk.dps.optfund.ant_colony.selector.Selector;

import java.util.*;

/**
 * Represents a ant in an ant colony
 */
public class Ant {

    private final int antIndex;
    private final int numberOfNodes;
    private final AbstractAntNode startNode;
    private final Set<AbstractAntEdge> usedEdges = new HashSet<>();
    private final Set<AbstractAntNode> seenNodes = new HashSet<>();
    private final Selector edgeSelector;

    /**
     * Creates a new instance of an ant
     * @param antIndex An ant index, which is used for comparing ants
     * @param startNode The ants start node
     * @param numberOfNodes The number of total nodes
     * @param edgeSelector the selection algorithm for picking an edge
     */
    public Ant(int antIndex, AbstractAntNode startNode, int numberOfNodes, Selector edgeSelector) {
        this.antIndex = antIndex;
        this.startNode = startNode;
        this.numberOfNodes = numberOfNodes;
        this.edgeSelector = edgeSelector;
    }

    /**
     * Computes the path which the ant will take of the current state of the graph
     * @param alpha Alpha value used for computing the probability which edge to take
     * @param beta Beta value used for computing the probability which edge to take
     * @return An instance of an {@link AntPath}
     */
    public AntPath getPath(double alpha, double beta) {
        // First reset the ant and initialize the results
        reset();
        AbstractAntNode currentNode = startNode;
        List<AbstractAntNode> nodes = new ArrayList<>();
        List<AbstractAntEdge> edges = new ArrayList<>();

        // First we add the currentNode (startNode) to the seen nodes and the result nodes.
        nodes.add(currentNode);
        seenNodes.add(currentNode);

        // As long as the ant is not on the start node we let the ant move through the graph.
        // Further implementation will check to not hit a node multiple times
        do {
            // Get the next edge and add it to the result
            // The ant is now on the other side of the edge
            AbstractAntEdge edge = step(currentNode, alpha, beta);

            currentNode = edge.getB();
            edges.add(edge);
            nodes.add(currentNode);

        } while (!currentNode.equals(startNode));

        // Create a new ant path and return it
        return new AntPath(edges, nodes);
    }

    /**
     * Returns whether the ant used the given edge on its path
     * @param edge The edge to check
     * @return Whether the ant has seen this edge
     */
    public boolean hasUsedEdge(AbstractAntEdge edge) {
        return usedEdges.contains(edge);
    }

    /**
     * Determines whether this ant is equal to another ant
     * @param o The other ant
     * @return Whether this ant is equal to the given ant
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ant ant = (Ant) o;
        return antIndex == ant.antIndex;
    }

    /**
     * Returns the hashcode for this ant
     * @return The hashcode for this ant
     */
    @Override
    public int hashCode() {
        return antIndex;
    }

    private void reset() {
        // Clear all used edges and seen node for a new calculation
        usedEdges.clear();
        seenNodes.clear();
    }

    private AbstractAntEdge step(AbstractAntNode currentNode, double alpha, double beta) {
        // First get the next node, based on the ACO algorithm
        // And add it to the other side to the seen nodes and the used edges
        AbstractAntEdge edge = getNextEdge(currentNode, alpha, beta);

        usedEdges.add(edge);
        currentNode = edge.getB();
        seenNodes.add(currentNode);
        return edge;
    }

    private AbstractAntEdge getNextEdge(AbstractAntNode currentNode, double alpha, double beta) {
        // Here we calculate the next edge the ant will take based on the ACO algorithm

        // If the ant has seen the same number of nodes as there are nodes in the graph,
        // the ant has to go to the end/start node
        // This is just an optimization, the rest of the algorithm would yield the same edge
        if(seenNodes.size() == numberOfNodes) {
            return currentNode.getNeighbours().get(startNode);
        }

        // Get all edges which can be used by the ant
        List<AbstractAntEdge> possible = getPossibleEdges(currentNode.getNeighbours().values().asList());
        if (possible.size() == 0) {
            throw new IllegalArgumentException("list of possible edges must not be empty");
        }

        // Now do the actual ACO calculation
        HashMap<AbstractAntEdge, Double> factors = new HashMap<>(possible.size() + 1);
        double sumFactorBot = 0.0;

        // calculate the factor for each possible edge
        for(AbstractAntEdge poss : possible) {
            double factor = getFactor(poss.getPheromone(), poss.getDistance(), alpha, beta);
            sumFactorBot += factor;
            factors.put(poss, factor);
        }

        List<Double> props = new ArrayList<>(possible.size());
        for(AbstractAntEdge e : possible) {

            if(sumFactorBot == 0.0) {
                props.add(0.0);
                continue;
            }

            // Algorithm
            // p = factorTop / sumFactorBot
            // where sumFactorBot = sum(z -> z.pheromone^alpha * (1/z.distance)^beta, possible)
            // and factorTop = e.pheromone^alpha * (1/e.distance)^beta

            // add probability to list of probabilities
            props.add(factors.get(e) / sumFactorBot);
        }

        AbstractAntEdge bestEdge = this.edgeSelector.select(possible, props);
        if(bestEdge == null) {
            throw new IllegalArgumentException("best edge must not be null");
        }
        return bestEdge;
    }

    private List<AbstractAntEdge> getPossibleEdges(List<AbstractAntEdge> possible) {
        // The ant has not seen any node yet, just return all edges.
        if(seenNodes.isEmpty()) {
            return possible;
        }

        List<AbstractAntEdge> edges = new ArrayList<>(possible.size());


        // A for loop is faster in this case
        //noinspection ForLoopReplaceableByForEach
        for(int i = 0; i < possible.size(); i++) {
            AbstractAntEdge e = possible.get(i);

            if(e.getB().equals(startNode)) {
                continue;
            }

            // Ignore nodes which the ant already visited
            if(seenNodes.contains(e.getB())) {
                continue;
            }
            edges.add(e);
        }
        return edges;
    }

    private double getFactor(double pheromone, double distance, double alpha, double beta) {
        // Calculate the factor for the ACO
        double tau = Math.pow(pheromone, alpha);
        double ita = Math.pow(1 / distance, beta);
        return tau * ita;
    }

}
