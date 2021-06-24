package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;
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

    /**
     * @param firefly the firefly for fitness calculation
     * @return the fitness value
     * @author Daniel Eberharter
     */
    double calculateFitness(Firefly firefly);
}
