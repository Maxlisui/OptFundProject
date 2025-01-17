package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;
import at.uibk.dps.optfund.dtlz.optimizer.FireflyAlgorithmModule;
import com.google.inject.Inject;
import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.start.Constant;

import java.util.SplittableRandom;

/**
 * Service for moving particles
 * @author Daniel Eberharter
 */
public class ParticleMoverImpl implements ParticleMover {

    // SplittableRandom is several times faster than Random
    protected final SplittableRandom rnd = new SplittableRandom(0);
    private final double randomWalkCoefficient;
    private final double attractivenessCoefficient;
    private final double lightAbsorptionCoefficient;

    /**
     * Constructor
     * @param randomWalkCoefficient coefficient for random movement
     * @param attractivenessCoefficient coefficient for attractiveness between fireflies
     * @param lightAbsorptionCoefficient coefficient for light absorption due to distance
     * @author Daniel Eberharter
     */
    @Inject
    public ParticleMoverImpl(@Constant(value = "randomWalkCoefficient", namespace = FireflyAlgorithmModule.class) double randomWalkCoefficient,
                             @Constant(value = "attractivenessCoefficient", namespace = FireflyAlgorithmModule.class) double attractivenessCoefficient,
                             @Constant(value = "lightAbsorptionCoefficient", namespace = FireflyAlgorithmModule.class) double lightAbsorptionCoefficient) {
        this.randomWalkCoefficient = randomWalkCoefficient;
        this.attractivenessCoefficient = attractivenessCoefficient;
        this.lightAbsorptionCoefficient = lightAbsorptionCoefficient;
    }

    /**
     * Mutation method for position of particle
     * @param firefly the particle to move
     * @param reference the reference particle (does not move)
     * @return the new position for particle
     * @author Daniel Eberharter
     */
    @Override
    public double[] move(Firefly firefly, Firefly reference) {
        if(firefly == null) {
            throw new IllegalArgumentException("firefly");
        }
        if(reference == null) {
            throw new IllegalArgumentException("reference");
        }

        var rnd = new SplittableRandom();
        final double distance = calculateDistance(firefly.getPosition(), reference.getPosition());
        final DoubleString position = firefly.getPosition();
        final DoubleString refPosition = reference.getPosition();
        // create array for updated position
        // -> don't directly write back into firefly position because of race conditions
        final double[] updatedPosition = new double[position.size()];

        for(int d = 0; d < position.size(); d++) {
            double randomWalk = randomWalkCoefficient * (rnd.nextDouble() - 0.5);
            updatedPosition[d] = calculateNewPosition(position.get(d), refPosition.get(d), distance, randomWalk);
        }

        return updatedPosition;
    }

    /**
     * Calculate the distance between two fireflies
     * @param p1 position of firefly 1
     * @param p2 position of firefly 2
     * @return the distance between firefly 1 and 2
     * @author Daniel Eberharter
     */
    protected double calculateDistance(DoubleString p1, DoubleString p2) {
        double distance = 0.0;
        for(int d = 0; d < p1.size(); d++) {
            distance += Math.pow(p1.get(d) - p2.get(d), 2.0);
        }
        return Math.sqrt(distance);
    }

    /**
     * Calculates the new position for firefly 1
     * @param p1 the position of firefly 1
     * @param p2 the position of reference firefly 2
     * @param distance the distance between firefly 1 and 2
     * @return the new position for firefly 1 and 2
     * @author Daniel Eberharter
     */
    protected double calculateNewPosition(double p1, double p2, double distance, double randomWalk) {

        // x_i = x_i + beta*e^(delta*distance^2) + alpha * rand
        return p1
                + attractivenessCoefficient * Math.exp(-lightAbsorptionCoefficient * Math.pow(distance, 2.0)) * (p2 - p1)
                + randomWalk;
    }
}
