package at.uibk.dps.optfund.dtlz.optimizer;

import at.uibk.dps.optfund.dtlz.factory.FireflyFactory;
import at.uibk.dps.optfund.dtlz.model.Firefly;
import at.uibk.dps.optfund.dtlz.utils.FireflySelector;
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
    private final ParticleMover particleMover;
    private final FireflyFactory factory;
    private final FireflySelector fireflySelector;
    protected final int numberOfFireflies;

    @Inject
    public FireflyAlgorithm(IndividualFactory individualFactory,
                            Population population,
                            Selector selector,
                            ParticleMover particleMover,
                            FireflyFactory factory,
                            FireflySelector fireflySelector,
                            @Constant(value = "numberOfFireflies", namespace = FireflyAlgorithmModule.class) int numberOfFireflies) {

        this.individualFactory = individualFactory;
        this.population = population;
        this.selector = selector;
        this.particleMover = particleMover;
        this.factory = factory;
        this.fireflySelector = fireflySelector;
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

            final List<Firefly> fireflies = factory.createFireflies(population);

            // pick the best individual -> firefly with highest brightness
            final Firefly bestIndividual = fireflySelector.getFittestFirefly(fireflies);

            // move all fireflies towards the firefly with the highest brightness
            List<Individual> newPopulation = fireflies.parallelStream().map(i -> {

                // bestIndividual does not move
                if(i == bestIndividual) {
                    return this.individualFactory.create(bestIndividual.getPosition());
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
