package at.uibk.dps.optfund.ant_colony.model;

public abstract class AbstractAntEdge {

    private double pheromone = 0.0;

    public abstract AbstractAntNode getA();
    public abstract AbstractAntNode getB();
    public abstract double getDistance();

    public double getPheromone() {
        return pheromone;
    }

    public void updatePheromone(double p, int numberOfAnts) {

    }

}
