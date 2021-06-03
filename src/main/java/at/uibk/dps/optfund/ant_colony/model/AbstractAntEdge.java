package at.uibk.dps.optfund.ant_colony.model;

/**
 * Represents an edge in a weighted graph used for an ant colony optimizer
 */
public abstract class AbstractAntEdge {

    private double pheromone = 0.0;

    /**
     * Gets the origin node from the edge
     * @return An instance of an {@link AbstractAntNode}
     */
    public abstract AbstractAntNode getA();

    /**
     * Gets the destination node from the edge
     * @return An instance of an {@link AbstractAntNode}
     */
    public abstract AbstractAntNode getB();

    /**
     * Gets the distance of the edge.
     * @return Returns the distance between {@link AbstractAntEdge#getA()} and {@link AbstractAntEdge#getB()}
     */
    public abstract double getDistance();

    /**
     * Gets the current pheromone level on the edge
     * @return The current pheromone level
     */
    public double getPheromone() {
        return pheromone;
    }

    /**
     * Sets the current pheromone level on the edge
     * @param pheromone The new pheromone level
     */
    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }
}
