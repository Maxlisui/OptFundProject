package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;

import java.util.List;

public interface Selector {
    AbstractAntEdge select(List<AbstractAntEdge> elements, List<Double> weights);
}
