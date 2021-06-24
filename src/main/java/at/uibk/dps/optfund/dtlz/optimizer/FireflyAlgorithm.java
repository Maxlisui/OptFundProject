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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private final FitnessCalculator fitnessCalculator;
    private final List<Firefly> fireflies;
    protected final int numberOfFireflies;


    @Inject
    public FireflyAlgorithm(IndividualFactory individualFactory,
                            Population population,
                            Selector selector,
                            ParticleMover particleMover,
                            FireflyFactory factory,
                            FireflySelector fireflySelector,
                            FitnessCalculator fitnessCalculator,
                            @Constant(value = "numberOfFireflies", namespace = FireflyAlgorithmModule.class) int numberOfFireflies) {

        this.individualFactory = individualFactory;
        this.population = population;
        this.selector = selector;
        this.particleMover = particleMover;
        this.factory = factory;
        this.fireflySelector = fireflySelector;
        this.fitnessCalculator = fitnessCalculator;
        this.numberOfFireflies = numberOfFireflies;
        this.fireflies = new ArrayList<>(numberOfFireflies);
    }

    /**
     * initializes the algorithm
     * @author Daniel Eberharter
     */
    @Override
    public void initialize() {
        selector.init(numberOfFireflies);

        // initialize the list of fireflies (population)
        for(int i=0; i < numberOfFireflies; i++) {
            Individual individual = individualFactory.create();
            Firefly firefly = factory.createFirefly(individual);
            population.add(individual);
            fireflies.add(firefly);
        }
    }

    /**
     * Describes one iteration of the algorithm
     * @author Daniel Eberharter
     */
    @Override
    public void next() {

        // update fitness of fireflies
        fireflies
            .parallelStream()
            .forEach(f -> f.setFitness(this.fitnessCalculator.calculateFitness(f)));

        // pick the best individual -> firefly with highest brightness
        final Firefly bestIndividual = fireflySelector.getFittestFirefly(fireflies);

        // move all fireflies towards the firefly with the highest brightness
        fireflies.parallelStream().forEach(i -> {
            // bestIndividual does not move
            if(i != bestIndividual) {
                // move and create individual from new position
                i.setPendingGenotypeUpdate(particleMover.move(i , bestIndividual));
            } else {
                i.setPendingGenotypeUpdate(null);
            }
        });

        // update new position in genotype
        fireflies
            .parallelStream()
            .forEach(f -> {
                DoubleString genotype = (DoubleString)f.getIndividual().getGenotype();
                double[] pending = f.getPendingGenotypeUpdate();
                // pending update is null for best individual
                if(pending != null) {
                    for(int i=0; i < genotype.size(); i++) {
                        genotype.set(i, pending[i]);
                    }
                }
                // set state to genotyped so opt4j does phenotyping/evaluation again
                f.getIndividual().setState(Individual.State.GENOTYPED);
            });
    }
}
