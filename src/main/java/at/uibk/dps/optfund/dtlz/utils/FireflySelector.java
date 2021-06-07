package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;

import java.util.Collection;

/**
 * Selector for choosing the best firefly in the population
 * @author Daniel Eberharter
 */
public interface FireflySelector {

    /**
     * Returns the best firefly (highest fitness -> highest light intensity)
     * @param fireflies the population
     * @return the fittest firefly
     * @author Daniel Eberharter
     */
    Firefly getFittestFirefly(Collection<Firefly> fireflies);
}
