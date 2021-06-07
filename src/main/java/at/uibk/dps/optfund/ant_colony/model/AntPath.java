package at.uibk.dps.optfund.ant_colony.model;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

/**
 * Represents a path taken by an ant.
 * Contains the all nodes and edges taken by the ant during its path.
 */
public class AntPath {

    private final ImmutableList<AbstractAntNode> nodes;
    private final double cost;

    /**
     * Creates a new instance of an ant path
     * @param edges The edges taken by the ant
     * @param nodes The nodes taken by the ant
     */
    public AntPath(Collection<AbstractAntEdge> edges, Collection<AbstractAntNode> nodes) {
        this.nodes = ImmutableList.copyOf(nodes);
        this.cost = this.calculateCost(edges);
    }

    /**
     * Returns all nodes taken by the ant
     * @return An instance of an {@link ImmutableList} containing {@link AbstractAntNode}
     */
    public ImmutableList<AbstractAntNode> getNodes() {
        return nodes;
    }

    /**
     * Returns the cost of the path, calculated by summing {@link AbstractAntEdge#getDistance()}
     * @return The cost of the path taken by the ant
     */
    public double getCost() {
        return this.cost;
    }

    private double calculateCost(Collection<AbstractAntEdge> edges) {
        return edges.stream().mapToDouble(AbstractAntEdge::getDistance).sum();
    }
}
