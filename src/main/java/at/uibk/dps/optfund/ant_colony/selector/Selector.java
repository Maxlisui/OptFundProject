package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AntEdge;

import java.util.List;

public interface Selector {

    /**
     * Selects the best edge best on the given weights and
     * @param elements All available edges
     * @param weights The respective weights
     * @param <T> The type of data inside the node
     * @return The selected edge
     */
    <T> AntEdge<T> select(List<AntEdge<T>> elements, List<Double> weights);
}
