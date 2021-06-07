package at.uibk.dps.optfund.dtlz.optimizer;

import at.uibk.dps.optfund.dtlz.factory.FireflyFactory;
import at.uibk.dps.optfund.dtlz.factory.FireflyFactoryImpl;
import at.uibk.dps.optfund.dtlz.utils.*;
import com.google.inject.TypeLiteral;
import org.opt4j.core.Individual;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

/**
 * Module for firefly optimization
 * @author Daniel Eberharter
 */
public class FireflyAlgorithmModule extends OptimizerModule {

    @MaxIterations
    protected int iterations = 1000;

    @Constant(value = "numberOfFireflies", namespace = FireflyAlgorithmModule.class)
    @Info("The number of fireflies.")
    protected int numberOfFireflies = 100;

    @Constant(value = "randomWalkCoefficient", namespace = FireflyAlgorithmModule.class)
    @Info("The random walk parameter. (alpha)")
    protected double randomWalkCoefficient = 1;

    @Constant(value = "attractivenessCoefficient", namespace = FireflyAlgorithmModule.class)
    @Info("The attractiveness (weight) parameter. (beta)")
    protected double attractivenessCoefficient = 1;

    @Constant(value = "lightAbsorptionCoefficient", namespace = FireflyAlgorithmModule.class)
    @Info("The light absorption coefficient. (delta)")
    protected double lightAbsorptionCoefficient = 0.5;

    /**
     * @return the number of iterations
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * @param iterations the number of iterations
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    /**
     * @return the number of fireflies
     */
    public int getNumberOfFireflies() {
        return numberOfFireflies;
    }

    /**
     * @param numberOfFireflies the number of fireflies
     */
    public void setNumberOfFireflies(int numberOfFireflies) {
        this.numberOfFireflies = numberOfFireflies;
    }

    /**
     * @return the attractiveness coefficient
     */
    public double getAttractivenessCoefficient() {
        return attractivenessCoefficient;
    }

    /**
     * @param attractivenessCoefficient the attractiveness coefficient
     */
    public void setAttractivenessCoefficient(double attractivenessCoefficient) { this.attractivenessCoefficient = attractivenessCoefficient; }

    /**
     * @return the random walk coefficient
     */
    public double getRandomWalkCoefficient() { return randomWalkCoefficient; }

    /**
     * @param randomWalkCoefficient the random walk coefficient
     */
    public void setRandomWalkCoefficient(double randomWalkCoefficient) { this.randomWalkCoefficient = randomWalkCoefficient; }

    /**
     * @return the light absorption coefficient
     */
    public double getLightAbsorptionCoefficient() { return lightAbsorptionCoefficient; }

    /**
     * @param lightAbsorptionCoefficient the light absorption coefficient
     */
    public void setLightAbsorptionCoefficient(double lightAbsorptionCoefficient) { this.lightAbsorptionCoefficient = lightAbsorptionCoefficient; }

    /**
     * bind concrete implementations for dependency injection
     */
    @Override
    protected void config() {
        bind(FireflySelector.class).to(FireflySelectorImpl.class);
        bind(FitnessCalculator.class).to(FitnessCalculatorImpl.class);
        bind(FireflyFactory.class).to(FireflyFactoryImpl.class);
        bind(ParticleMover.class).to(ParticleMoverImpl.class);
        bindIterativeOptimizer(FireflyAlgorithm.class);
    }
}
