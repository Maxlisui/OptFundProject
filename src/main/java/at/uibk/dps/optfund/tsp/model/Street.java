package at.uibk.dps.optfund.tsp.model;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;

import java.util.Objects;

public class Street extends AbstractAntEdge {

    protected final City cityA;
    protected final City cityB;
    private double distance = -1;

    public Street(City cityA, City cityB) {
        this.cityA = cityA;
        this.cityB = cityB;
    }

    @Override
    public AbstractAntNode getA() {
        return cityA;
    }

    @Override
    public AbstractAntNode getB() {
        return cityB;
    }

    public double getDistance() {
        if (distance < 0) {
            final double x = cityA.getX() - cityB.getX();
            final double y = cityA.getY() - cityB.getY();
            distance = Math.sqrt(x * x + y * y);
        }
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return Objects.equals(cityA, street.cityA) && Objects.equals(cityB, street.cityB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityA, cityB);
    }
}
