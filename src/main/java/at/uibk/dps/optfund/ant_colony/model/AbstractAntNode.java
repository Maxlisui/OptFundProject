package at.uibk.dps.optfund.ant_colony.model;

import com.google.common.collect.ImmutableMap;


public abstract class AbstractAntNode {

    public abstract ImmutableMap<AbstractAntNode, AbstractAntEdge> getNeighbours();

    public double getDistanceTo(AbstractAntNode node) {
        return getNeighbours().get(node).getDistance();
    }
}
