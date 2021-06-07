package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;

import java.util.List;

/**
 * Greedy Selector -> take element with highest weight
 */
public class GreedySelector implements Selector {

    /**
     * @param elements the elements to select from
     * @param weights the weight for each element
     * @return the element with the highest weight
     */
    @Override
    public AbstractAntEdge select(List<AbstractAntEdge> elements, List<Double> weights) {
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
        AbstractAntEdge element = null;

        for(int i=0; i < weights.size(); i++) {
            if(weights.get(i) > maxWeight) {
                maxWeight = weights.get(i);
                element = elements.get(i);
            }
        }
        return element;
    }
}
