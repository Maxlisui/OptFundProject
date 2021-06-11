package at.uibk.dps.optfund.ant_colony.model;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

/**
 * Represents a path taken by an ant.
 * Contains the all nodes and edges taken by the ant during its path.
 * @param <T> The type of data inside the node
 * @author Maximilian Suitner
 */
public class AntPath<T> {

    private final ImmutableList<AntNode<T>> nodes;
    private final double cost;

    /**
     * Creates a new instance of an ant path
     * @param edges The edges taken by the ant
     * @param nodes The nodes taken by the ant
     */
    public AntPath(Collection<AntEdge<T>> edges, Collection<AntNode<T>> nodes) {
        this.nodes = ImmutableList.copyOf(nodes);
        this.cost = this.calculateCost(edges);
    }

    /**
     * Returns all nodes taken by the ant
     * @return An instance of an {@link ImmutableList} containing {@link AntNode}
     */
    public ImmutableList<AntNode<T>> getNodes() {
        return nodes;
    }

    /**
     * Returns the cost of the path, calculated by summing {@link AntEdge#getDistance()}
     * @return The cost of the path taken by the ant
     */
    public double getCost() {
        return this.cost;
    }

    private double calculateCost(Collection<AntEdge<T>> edges) {
        return edges.stream().mapToDouble(AntEdge::getDistance).sum();
    }
}
