package at.uibk.dps.optfund.tsp.model;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;

import java.util.Objects;

public class Street extends AbstractAntEdge {

    protected final City cityA;
    protected final City cityB;
    private final double distance;
    private int javasSlowFuckingHash = -1;

    public Street(City cityA, City cityB) {
        this.cityA = cityA;
        this.cityB = cityB;

        final double x = cityA.getX() - cityB.getX();
        final double y = cityA.getY() - cityB.getY();
        this.distance = Math.sqrt(x * x + y * y);
    }

    @Override
    public AbstractAntNode getA() {
        return cityA;
    }

    @Override
    public AbstractAntNode getB() {
        return cityB;
    }

    public double getDistance() { return distance; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return javasSlowFuckingHash == street.javasSlowFuckingHash;
    }

    @Override
    public int hashCode() {
        if(javasSlowFuckingHash == -1) {
            javasSlowFuckingHash = Objects.hash(cityA, cityB);
        }
        return javasSlowFuckingHash;
    }
}
