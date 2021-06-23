package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;

import java.util.Collection;
import java.util.Optional;

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
        // iterate over population and search individual with highest light intensity
        return fireflies.stream().max(this::compareFireflies).orElse(null);
    }

    /**
     * compares two fireflies base on their fitness
     * @param f1 the first firefly
     * @param f2 the second firefly
     * @return 1 iff f1 is fitter than f2, -1 iff f2 is fitter than f1, 0 otherwise
     * @author Daniel Eberharter
     */
    private int compareFireflies(Firefly f1, Firefly f2) {
        return Double.compare(f1.getFitness(), f2.getFitness());
    }
}
