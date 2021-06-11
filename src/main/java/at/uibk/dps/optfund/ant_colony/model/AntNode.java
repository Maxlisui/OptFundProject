package at.uibk.dps.optfund.ant_colony.model;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a node in the graph used for ant optimization
 * @param <T> The type of data inside the node
 * @author Maximilian Suitner
 */
public class AntNode<T> {

    private final int hashValue;
    private final Map<AntNode<T>, AntEdge<T>> neighbours = new HashMap<>();
    private final double x;
    private final double y;
    private final T internalObject;
    private ImmutableMap<AntNode<T>, AntEdge<T>> neighboursImmutable;

    public AntNode(T internalObject, double x, double y) {
        this.x = x;
        this.y = y;
        this.hashValue = Objects.hash(x, y);
        this.internalObject = internalObject;
    }

    /**
     * Returns the x-coordinate of the node
     * @return The x-coordinate of the node
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the node
     * @return The y-coordinate of the node
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the internal data of the node
     * @return The internal data of the node
     */
    public T getInternalObject() {
        return this.internalObject;
    }

    /**
     * Ads the given node as a neighbour using the given edge
     * @param node The neighbour node
     * @param edge The edge which connects this node and the other
     */
    public void addNeighbour(AntNode<T> node, AntEdge<T> edge) {
        neighbours.put(node, edge);
    }

    /**
     * Returns all neighbours of this node and the respective edge
     * @return All neighbours of this node and the respective edge
     */
    public ImmutableMap<AntNode<T>, AntEdge<T>> getNeighbours() {
        if(neighboursImmutable == null) {
            neighboursImmutable = ImmutableMap.copyOf(neighbours);
        }
        return neighboursImmutable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntNode<T> node = (AntNode<T>) o;
        return this.x == node.x && this.y == node.y;
    }

    @Override
    public int hashCode() {
        return this.hashValue;
    }
}
