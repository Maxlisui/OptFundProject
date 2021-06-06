package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;

import java.util.List;

public class GreedySelector implements Selector {

    @Override
    public AbstractAntEdge select(List<AbstractAntEdge> elements, List<Double> weights) {
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
