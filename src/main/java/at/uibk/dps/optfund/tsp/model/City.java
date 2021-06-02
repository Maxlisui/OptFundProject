package at.uibk.dps.optfund.tsp.model;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class City extends AbstractAntNode {

    private final double x;
    private final double y;
    private Map<City, Street> neighbours = new HashMap<>();

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void addNeighbour(City city) {
        neighbours.put(city, new Street(this, city));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Double.compare(city.x, x) == 0 && Double.compare(city.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public ImmutableMap<AbstractAntNode, AbstractAntEdge> getNeighbours() {
        return ImmutableMap.copyOf(neighbours);
    }
}
