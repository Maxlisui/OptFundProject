package at.uibk.dps.optfund.dtlz.model;

import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Individual;

/**
 * Represents a single firefly
 * @author Daniel Eberharter
 */
public class Firefly {

    private final Individual individual;
    private double fitness = 0;
    private double[] pendingGenotypeUpdate = null;

    /**
     * @param individual the underlying individual
     * @author Daniel Eberharter
     */
    public Firefly(Individual individual) {
        this.individual = individual;
    }

    /**
     * @return the wrapped individual
     * @author Daniel Eberharter
     */
    public Individual getIndividual() {
        return individual;
    }

    /**
     * @return the fitness
     * @author Daniel Eberharter
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness
     * @author Daniel Eberharter
     */
    public void setFitness(double fitness) { this.fitness = fitness; }

    /**
     * @return the position
     * @author Daniel Eberharter
     */
    public DoubleString getPosition() {
        return (DoubleString) individual.getGenotype();
    }

    public double[] getPendingGenotypeUpdate() {
        return pendingGenotypeUpdate;
    }

    public void setPendingGenotypeUpdate(double[] pendingGenotypeUpdate) {
        this.pendingGenotypeUpdate = pendingGenotypeUpdate;
    }
}
