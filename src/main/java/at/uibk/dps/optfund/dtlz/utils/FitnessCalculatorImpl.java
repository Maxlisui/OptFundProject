package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;
import org.opt4j.core.Individual;

import java.util.Arrays;

/**
 * Calculates the fitness for a given individual
 * @author Daniel Eberharter
 */
public class FitnessCalculatorImpl implements FitnessCalculator {

    /**
     * Get fitness of individual
     * @param objectives the objective values for some individual
     * @return the fitness
     * @author Daniel Eberharter
     */
    @Override
    public double calculateFitness(double[] objectives) {
        if(objectives == null) {
            throw new IllegalArgumentException("objectives");
        }
        if(objectives.length == 0) {
            return 0.0;
        }
        return 1 / Arrays.stream(objectives).sum();
    }

    /**
     * @param firefly the firefly for fitness calculation
     * @return the fitness value
     * @author Daniel Eberharter
     */
    @Override
    public double calculateFitness(Firefly firefly) {
        Individual individual = firefly.getIndividual();
        // if the firefly is not evaluated yet fitness is zero
        if(!individual.isEvaluated()) {
            return 0;
        }
        return calculateFitness(individual.getObjectives().array());
    }
}
