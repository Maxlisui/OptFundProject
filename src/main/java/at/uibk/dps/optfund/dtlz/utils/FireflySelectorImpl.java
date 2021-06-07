package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;

import java.util.Collection;

/**
 * Selector for choosing the best firefly in the population
 */
public class FireflySelectorImpl implements FireflySelector {

    /**
     * Returns the best firefly (highest fitness -> highest light intensity)
     * @param fireflies the population
     * @return the fittest firefly
     * @author Daniel Eberharter
     */
    @Override
    public Firefly getFittestFirefly(Collection<Firefly> fireflies) {
        double highestFitness = Double.MIN_VALUE;
        Firefly best = null;

        // iterate over population and search individual with highest light intensity
        for (Firefly i: fireflies) {
            if(i.getFitness() > highestFitness) {
                best = i;
                highestFitness = i.getFitness();
            }
        }
        return best;
    }
}
