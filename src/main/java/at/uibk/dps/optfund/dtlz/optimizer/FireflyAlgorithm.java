package at.uibk.dps.optfund.dtlz.optimizer;

import com.google.inject.Inject;
import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.start.Constant;
import org.opt4j.optimizers.ea.Selector;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Concrete implementation for firefly optimization
 * @author Daniel Eberharter
 */
public class FireflyAlgorithm implements IterativeOptimizer {

    protected final IndividualFactory individualFactory;
    protected final Population population;
    protected final Selector selector;
    protected final int numberOfFireflies;
    protected final double randomWalkCoefficient;
    protected final double attractivenessCoefficient;
    protected final double lightAbsorptionCoefficient;
    protected final Random rnd = new Random(0);

    @Inject
    public FireflyAlgorithm(IndividualFactory individualFactory,
                            Population population,
                            Selector selector,
                            @Constant(value = "numberOfFireflies", namespace = FireflyAlgorithm.class) int numberOfFireflies,
                            @Constant(value = "randomWalkCoefficient", namespace = FireflyAlgorithm.class) double randomWalkCoefficient,
                            @Constant(value = "attractivenessCoefficient", namespace = FireflyAlgorithm.class) double attractivenessCoefficient,
                            @Constant(value = "lightAbsorptionCoefficient", namespace = FireflyAlgorithm.class) double lightAbsorptionCoefficient) {

        this.individualFactory = individualFactory;
        this.population = population;
        this.selector = selector;
        this.numberOfFireflies = numberOfFireflies;
        this.randomWalkCoefficient = randomWalkCoefficient;
        this.attractivenessCoefficient = attractivenessCoefficient;
        this.lightAbsorptionCoefficient = lightAbsorptionCoefficient;
    }

    /**
     * initializes the algorithm
     * @author Daniel Eberharter
     */
    @Override
    public void initialize() {
        selector.init(numberOfFireflies);
    }

    /**
     * Describes one iteration of the algorithm
     * @author Daniel Eberharter
     */
    @Override
    public void next() {

        // first iteration -> initialize population -> one individual per firefly
        if(population.isEmpty()) {
            for(int i = 0; i < numberOfFireflies; i++) {
                population.add(individualFactory.create());
            }
        } else {

            // pick the best individual -> firefly with highest brightness
            final Individual bestIndividual = getBestIndividual(population);

            // move all fireflies towards the firefly with the highest brightness
            List<Individual> newPopulation = population.parallelStream().map(i -> {

                // bestIndividual does not move
                if(i == bestIndividual) {
                    return bestIndividual;
                }

                // move and create individual from new position
                DoubleString newGenotype = move(i , bestIndividual);
                return this.individualFactory.create(newGenotype);
            }).collect(Collectors.toList());

            // update population
            population.clear();
            population.addAll(newPopulation);
        }
    }

    /**
     * @param population the current population
     * @return the fittest individual (highest brightness)
     * @author Daniel Eberharter
     */
    private Individual getBestIndividual(Population population) {
        double highestIntensity = Double.MIN_VALUE;
        Individual brightestIndividual = null;

        // iterate over population and search individual with highest light intensity
        for (Individual i: population) {
            double intensity = getLightIntensity(i);
            if(intensity > highestIntensity) {
                brightestIndividual = i;
                highestIntensity = intensity;
            }
        }
        return brightestIndividual;
    }

    /**
     * Get light intensitiy for firefly
     * @param i some firefly
     * @return the light intensity
     * @author Daniel Eberharter
     */
    protected double getLightIntensity(Individual i) {
        if(!i.isEvaluated()) {
            throw new IllegalArgumentException("individual must be evaluated");
        }
        return getLightIntensity(i.getObjectives().array());
    }

    /**
     * Get light intensitiy for firefly
     * @param objectiveValues the objective values for some firefly
     * @return the light intensity
     * @author Daniel Eberharter
     */
    protected double getLightIntensity(double[] objectiveValues) {
        return 1 / Arrays.stream(objectiveValues).sum();
    }

    /**
     * Mutation method for position of firefly f1
     * @param f1 the firefly to move
     * @param f2 the reference firefly (does not move)
     * @return the new position for f1
     * @author Daniel Eberharter
     */
    protected DoubleString move(Individual f1, Individual f2) {

        DoubleString positionF1 = ((DoubleString)f1.getGenotype());
        DoubleString positionF2 = ((DoubleString)f2.getGenotype());

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
