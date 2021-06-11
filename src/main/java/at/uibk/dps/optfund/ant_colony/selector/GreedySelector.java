package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AntEdge;

import java.util.List;

/**
 * Greedy Selector -> take element with highest weight
 * @author Daniel Eberharter
 */
public class GreedySelector implements Selector {

    @Override
    public <T> AntEdge<T> select(List<AntEdge<T>> elements, List<Double> weights) {
        if(elements == null || elements.size() == 0) {
            return null;
        }

        if(weights == null || weights.size() == 0) {
            return null;
        }

        if(weights.size() != elements.size()) {
            throw new IllegalArgumentException("count of weights does not match count of elements");
        }

        double maxWeight = Double.MIN_VALUE;
        AntEdge<T> element = null;

        for(int i=0; i < weights.size(); i++) {
            if(weights.get(i) > maxWeight) {
                maxWeight = weights.get(i);
                element = elements.get(i);
            }
        }
        return element;
    }
}
