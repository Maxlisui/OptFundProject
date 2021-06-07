package at.uibk.dps.optfund.dtlz.utils;

import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Population;

import java.util.Collection;

/**
 * Calculates the fitness for a given individual
 * @author Daniel Eberharter
 */
public interface FitnessCalculator {

    /**
     * Get fitness of individual
     * @param objectives some individuals objectives
     * @return the fitness
     * @author Daniel Eberharter
     */
    double calculateFitness(double[] objectives);
}
