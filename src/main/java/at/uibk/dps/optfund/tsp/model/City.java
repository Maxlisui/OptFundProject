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
    private final Map<City, Street> neighbours = new HashMap<>();
    private ImmutableMap<AbstractAntNode, AbstractAntEdge> neighboursImmutable = null;
    private int javasSlowFuckingHash = -1;

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
        return this.javasSlowFuckingHash == city.javasSlowFuckingHash;
    }

    @Override
    public int hashCode() {
        if (javasSlowFuckingHash == -1) {
            javasSlowFuckingHash = Objects.hash(x, y);
        }
        return javasSlowFuckingHash;
    }

    @Override
    public ImmutableMap<AbstractAntNode, AbstractAntEdge> getNeighbours() {
        if(neighboursImmutable == null) {
            neighboursImmutable = ImmutableMap.copyOf(neighbours);
        }
        return neighboursImmutable;
    }
}
