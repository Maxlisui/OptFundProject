package at.uibk.dps.optfund.dtlz.utils;

import org.opt4j.core.Individual;

import java.util.Arrays;

/**
 * Calculates the fitness for a given individual
 * @author Daniel Eberharter
 */
public class InverseProportionalFitnessCalculator extends AbstractFitnessCalculator {

    /**
     * Get fitness of individual
     * @param objectiveValues the objective values for some individual
     * @return the fitness
     * @author Daniel Eberharter
     */
    protected double getFitness(double[] objectiveValues) {
        return 1 / Arrays.stream(objectiveValues).sum();
    }
}
