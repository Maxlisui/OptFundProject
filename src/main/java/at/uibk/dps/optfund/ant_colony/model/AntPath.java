package at.uibk.dps.optfund.ant_colony.model;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

public class AntPath {

    private final ImmutableList<AbstractAntEdge> edges;
    private final ImmutableList<AbstractAntNode> nodes;
    private final double cost;

    public AntPath(Collection<AbstractAntEdge> edges, Collection<AbstractAntNode> nodes) {
        this.edges = ImmutableList.copyOf(edges);
        this.nodes = ImmutableList.copyOf(nodes);
        this.cost = this.calculateCost();
    }

    public ImmutableList<AbstractAntEdge> getEdges() {
        return edges;
    }

    public ImmutableList<AbstractAntNode> getNodes() {
        return nodes;
    }

    public double getCost() {
        return this.cost;
    }

    private double calculateCost() {
        return edges.stream().mapToDouble(AbstractAntEdge::getDistance).sum();
    }
}
