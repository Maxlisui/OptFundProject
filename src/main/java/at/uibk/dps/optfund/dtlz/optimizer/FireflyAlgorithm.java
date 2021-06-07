package at.uibk.dps.optfund.dtlz.optimizer;

import at.uibk.dps.optfund.dtlz.utils.FitnessCalculator;
import at.uibk.dps.optfund.dtlz.utils.ParticleMover;
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
    private final FitnessCalculator fitnessCalculator;
    private final ParticleMover particleMover;
    protected final int numberOfFireflies;

    @Inject
    public FireflyAlgorithm(IndividualFactory individualFactory,
                            Population population,
                            Selector selector,
                            FitnessCalculator fitnessCalculator,
                            ParticleMover particleMover,
                            @Constant(value = "numberOfFireflies", namespace = FireflyAlgorithmModule.class) int numberOfFireflies) {

        this.individualFactory = individualFactory;
        this.population = population;
        this.selector = selector;
        this.fitnessCalculator = fitnessCalculator;
        this.particleMover = particleMover;
        this.numberOfFireflies = numberOfFireflies;
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
            final Individual bestIndividual = fitnessCalculator.getFittestIndividual(population);

            // move all fireflies towards the firefly with the highest brightness
            List<Individual> newPopulation = population.parallelStream().map(i -> {

                // bestIndividual does not move
                if(i == bestIndividual) {
                    return bestIndividual;
                }

                // move and create individual from new position
                DoubleString newGenotype = particleMover.move(i , bestIndividual);
                return this.individualFactory.create(newGenotype);

            }).collect(Collectors.toList());

            // update population
            population.clear();
            population.addAll(newPopulation);
        }
    }
}
