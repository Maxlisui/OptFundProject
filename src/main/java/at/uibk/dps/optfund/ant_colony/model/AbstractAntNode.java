package at.uibk.dps.optfund.ant_colony.model;

import com.google.common.collect.ImmutableMap;

/**
 * Represents a node in a weighted graph used for an ant colony optimizer
 */
public abstract class AbstractAntNode {

    /**
     * Gets all neighbouring nodes with their respective edges
     * @return An {@link ImmutableMap} with key {@link AbstractAntNode} and value {@link AbstractAntEdge}
     */
    public abstract ImmutableMap<AbstractAntNode, AbstractAntEdge> getNeighbours();
}
