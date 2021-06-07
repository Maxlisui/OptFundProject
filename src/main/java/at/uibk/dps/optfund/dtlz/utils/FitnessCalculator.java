package at.uibk.dps.optfund.dtlz.utils;

import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Population;

/**
 * Calculates the fitness for a given individual
 * @author Daniel Eberharter
 */
public interface FitnessCalculator {

    /**
     * Get fitness of individual
     * @param i some individual
     * @return the fitness
     * @author Daniel Eberharter
     */
    double getFitness(Individual i);


    /**
     * Returns the fittest individual in the population
     * @param population the current population
     * @return the fittest individual
     */
    Individual getFittestIndividual(Population population);
}
