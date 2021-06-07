package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.optimizer.FireflyAlgorithm;
import at.uibk.dps.optfund.dtlz.optimizer.FireflyAlgorithmModule;
import com.google.inject.Inject;
import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Individual;
import org.opt4j.core.start.Constant;

import java.util.Random;

/**
 * Service for moving particles
 * @author Daniel Eberharter
 */
public class FireflyParticleMover implements ParticleMover {

    protected final Random rnd = new Random(0);
    private final double randomWalkCoefficient;
    private final double attractivenessCoefficient;
    private final double lightAbsorptionCoefficient;

    @Inject
    public FireflyParticleMover(@Constant(value = "randomWalkCoefficient", namespace = FireflyAlgorithmModule.class) double randomWalkCoefficient,
                                @Constant(value = "attractivenessCoefficient", namespace = FireflyAlgorithmModule.class) double attractivenessCoefficient,
                                @Constant(value = "lightAbsorptionCoefficient", namespace = FireflyAlgorithmModule.class) double lightAbsorptionCoefficient) {
        this.randomWalkCoefficient = randomWalkCoefficient;
        this.attractivenessCoefficient = attractivenessCoefficient;
        this.lightAbsorptionCoefficient = lightAbsorptionCoefficient;
    }

    /**
     * Mutation method for position of particle
     * @param particle the particle to move
     * @param reference the reference particle (does not move)
     * @return the new position for particle
     * @author Daniel Eberharter
     */
    @Override
    public DoubleString move(Individual particle, Individual reference) {
        DoubleString positionF1 = ((DoubleString)particle.getGenotype());
        DoubleString positionF2 = ((DoubleString)reference.getGenotype());

        final double distance = calculateDistance(positionF1, positionF2);
        for(int d = 0; d < positionF1.size(); d++) {
            positionF1.set(d, calculateNewPosition(positionF1.get(d), positionF2.get(d), distance));
        }
        return positionF1;
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
    protected double calculateNewPosition(double p1, double p2, double distance) {
        // x_i = x_i + beta*e^(delta*distance^2) + alpha(rand - 0.5)
        return p1
                + attractivenessCoefficient * Math.exp(-lightAbsorptionCoefficient * Math.pow(distance, 2.0)) * (p2 - p1)
                + randomWalkCoefficient * (rnd.nextDouble() - 0.5);
    }
}
