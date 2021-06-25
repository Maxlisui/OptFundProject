package at.uibk.dps.optfund.ant_colony.model;

import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a node in the graph used for ant optimization
 * @param <T> The type of data inside the node
 * @author Maximilian Suitner
 */
public class AntNode<T> {

    private final UUID id = UUID.randomUUID();
    private final Map<AntNode<T>, AntEdge<T>> neighbours = new HashMap<>();
    private final List<AntEdge<T>> neighbourEdges = new ArrayList<>();
    private final double x;
    private final double y;
    private final T internalObject;
    private ImmutableMap<AntNode<T>, AntEdge<T>> neighboursImmutable;

    public AntNode(T internalObject, double x, double y) {
        this.x = x;
        this.y = y;
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
        neighbourEdges.add(edge);
    }

    /**
     * Returns the destination node of the given edge
     * @param edge The edge
     * @return The destination node
     */
    public AntNode<T> getDestination(AntEdge<T> edge) {
        return edge.getNodeB().id != this.id
                ? edge.getNodeB()
                : edge.getNodeA();
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

    /**
     * @return the nodes edges
     */
    public List<AntEdge<T>> getNeighbourEdges() {
        return neighbourEdges;
    }

    /**
     * @param skip how many edges to skip
     * @param limit how many edges to take
     * @return the best N edges
     */
    public List<AntEdge<T>> getBestNeighbourEdges(int skip, int limit) {
        if (limit <= 0) {
            return neighbourEdges.stream().skip(skip).collect(Collectors.toList());
        }
        return neighbourEdges.stream().skip(skip).limit(limit).collect(Collectors.toList());
    }

    /**
     * updates the edge order based on their weight
     */
    public void sortNeighbourEdges() {
        this.neighbourEdges.sort((x,y) -> Double.compare(x.getEdgeWeight(), y.getEdgeWeight()) * -1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntNode<T> node = (AntNode<T>) o;
        return this.id == node.id;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
