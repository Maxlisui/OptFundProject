package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.*;
import at.uibk.dps.optfund.ant_colony.stepper.AntStepper;

import java.util.*;

/**
 * Represents a ant in an ant colony
 * @param <T> The type of data inside the node
 * @author Maximilian Suitner
 */
public class Ant<T> {

    private final int antIndex;
    private final int numberOfNodes;
    private final AntNode<T> startNode;
    private final Set<AntEdge<T>> usedEdges = new HashSet<>();
    private final Set<AntNode<T>> seenNodes = new HashSet<>();
    private final AntStepper stepper;

    /**
     * Creates a new instance of an ant
     * @param antIndex An ant index, which is used for comparing ants
     * @param startNode The ants start node
     * @param numberOfNodes The number of total nodes
     * @param stepper The stepping interface of ant which selects the next node
     */
    public Ant(int antIndex, AntNode<T> startNode, int numberOfNodes, AntStepper stepper) {
        this.antIndex = antIndex;
        this.startNode = startNode;
        this.numberOfNodes = numberOfNodes;
        this.stepper = stepper;
    }

    /**
     * Computes the path which the ant will take of the current state of the graph
     * @param alpha Alpha value used for computing the probability which edge to take
     * @param beta Beta value used for computing the probability which edge to take
     * @return An instance of an {@link AntPath}
     */
    public AntPath<T> getPath(double alpha, double beta) {
        // First reset the ant and initialize the results
        reset();
        AntNode<T> currentNode = startNode;
        List<AntNode<T>> nodes = new ArrayList<>();
        List<AntEdge<T>> edges = new ArrayList<>();

        // First we add the currentNode (startNode) to the seen nodes and the result nodes.
        nodes.add(currentNode);
        seenNodes.add(currentNode);

        // As long as the ant is not on the start node we let the ant move through the graph.
        // Further implementation will check to not hit a node multiple times
        do {
            // Get the next edge and get the destination
            // Set the destination as current node and add it to the result
            AntEdge<T> edge = step(currentNode, alpha, beta);

            currentNode = currentNode.getDestination(edge);
            edges.add(edge);
            nodes.add(currentNode);

        } while (!currentNode.equals(startNode));

        // Create a new ant path and return it
        return new AntPath<>(edges, nodes);
    }

    /**
     * Returns whether the ant used the given edge on its path
     * @param edge The edge to check
     * @return Whether the ant has seen this edge
     */
    public boolean hasUsedEdge(AntEdge<T> edge) {
        return usedEdges.contains(edge);
    }

    /**
     * Determines whether this ant is equal to another ant
     * @param o The other ant
     * @return Whether this ant is equal to the given ant
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ant<T> ant = (Ant<T>) o;
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

    private AntEdge<T> step(AntNode<T> currentNode, double alpha, double beta) {
        // First get the next node, based on the ACO algorithm
        // And add it to the other side to the seen nodes and the used edges
        AntEdge<T> edge = stepper.getNextEdge(currentNode, startNode, seenNodes, alpha, beta, numberOfNodes);

        usedEdges.add(edge);
        currentNode = currentNode.getDestination(edge);
        seenNodes.add(currentNode);
        return edge;
    }
}
