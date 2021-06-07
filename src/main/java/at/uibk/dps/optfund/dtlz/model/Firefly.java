package at.uibk.dps.optfund.dtlz.model;

import org.opt4j.benchmarks.DoubleString;

/**
 * Represents a single firefly
 * @author Daniel Eberharter
 */
public class Firefly {

    private final DoubleString position;
    private final double fitness;

    /**
     * @param position the current position
     * @param fitness the fitness (light intensity)
     * @author Daniel Eberharter
     */
    public Firefly(DoubleString position, double fitness) {
        this.position = position;
        this.fitness = fitness;
    }

    /**
     * @return the fitness
     * @author Daniel Eberharter
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @return the position
     * @author Daniel Eberharter
     */
    public DoubleString getPosition() {
        return position;
    }
}
