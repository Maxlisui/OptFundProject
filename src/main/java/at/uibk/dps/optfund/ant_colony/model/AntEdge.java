package at.uibk.dps.optfund.ant_colony.model;

import java.util.Objects;

/**
 * Represents an edge within the graph used for ant optimization
 * @param <T> Type which each node on either end holds
 * @author Maximilian Suitner
 */
public class AntEdge<T> {

    private final AntNode<T> nodeA;
    private final AntNode<T> nodeB;
    private final double distance;
    private final int hashValue;

    // initialize each edge with some amount of pheromone
    // this prevents initial problems with zero-probabilities
    private double pheromone = 1.0;

    public AntEdge(AntNode<T> nodeA, AntNode<T> nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        hashValue = Objects.hash(nodeA, nodeB);
        final double x = nodeA.getX() - nodeB.getX();
        final double y = nodeA.getY() - nodeB.getY();
        this.distance = Math.sqrt(x * x + y * y);
    }

    /**
     * Returns the origin node A
     * @return Origin node A
     */
    public AntNode<T> getNodeA() {
        return nodeA;
    }

    /**
     * Returns the destination node B
     * @return Destination node B
     */
    public AntNode<T> getNodeB() {
        return nodeB;
    }

    /**
     * Returns the distance between both nodes
     * @return The distance between both nodes
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Returns the current pheromone amount on the edge
     * @return the current pheromone amount on the edge
     */
    public double getPheromone() {
        return pheromone;
    }

    /**
     * Sets the current pheromone amount on the edge
     * @param pheromone the new pheromone amount on the edge
     */
    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntEdge<T> edge = (AntEdge<T>) o;
        return hashValue == edge.hashValue;
    }

    @Override
    public int hashCode() {
        return hashValue;
    }
}
