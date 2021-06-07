package at.uibk.dps.optfund.dtlz.utils;

import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Population;

public abstract class AbstractFitnessCalculator implements FitnessCalculator {

    /**
     * Get fitness of individual
     * @param i some individual
     * @return the fitness
     * @author Daniel Eberharter
     */
    @Override
    public double getFitness(Individual i) {
        if(!i.isEvaluated()) {
            throw new IllegalArgumentException("individual must be evaluated");
        }
        return getFitness(i.getObjectives().array());
    }

    /**
     * Returns the fittest individual in the population
     * @param population the current population
     * @return the fittest individual
     */
    @Override
    public Individual getFittestIndividual(Population population) {
        double highestFittness = Double.MIN_VALUE;
        Individual best = null;

        // iterate over population and search individual with highest light intensity
        for (Individual i: population) {
            double intensity = getFitness(i);
            if(intensity > highestFittness) {
                best = i;
                highestFittness = intensity;
            }
        }
        return best;
    }

    /**
     * Get fitness of individual
     * @param objectiveValues the objective values for some individual
     * @return the fitness
     * @author Daniel Eberharter
     */
    protected abstract double getFitness(double[] objectiveValues);
}
