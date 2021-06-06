package at.uibk.dps.optfund.ant_colony.optimizer;

import at.uibk.dps.optfund.ant_colony.AntColony;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import com.google.inject.Inject;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.start.Constant;
import org.opt4j.optimizers.ea.Selector;

import java.util.Collection;

/**
 * The implementation of the ant optimizer for {@link IterativeOptimizer}
 */
public class AntOptimizer implements IterativeOptimizer {

    private final IndividualFactory individualFactory;
    private final Selector selector;
    private final Population population;
    private final AntColony antColony;
    private final int populationSize;
    private final int offSize;

    /**
     * Creates a new instance of the ant optimizer
     * @param individualFactory An factory to create new {@link Individual}
     * @param selector Selector which selects {@link Individual} to consider
     * @param population The population of the optimizer
     * @param antColony The ant colony implementation
     * @param populationSize The maximal population size
     * @param offSize The offsize, of {@link Individual} to remove
     */
    @Inject
    public AntOptimizer(IndividualFactory individualFactory,
                        Selector selector,
                        Population population,
                        AntColony antColony,
                        @Constant(value = "populationSize") int populationSize,
                        @Constant(value = "offSize") int offSize) {
        this.individualFactory = individualFactory;
        this.selector = selector;
        this.population = population;
        this.antColony = antColony;
        this.populationSize = populationSize;
        this.offSize = offSize;
    }

    /**
     * Initializes the optimizer.
     * Initializes the selector and the ant colony
     */
    @SuppressWarnings("unchecked")
    @Override
    public void initialize() {
        selector.init(offSize + populationSize);

        // The ant colony needs to know the start node, so we create a new genotype and get their first element.
        // This node will be used as the start/end node for all future calculations
        PermutationGenotype<AbstractAntNode> genotype = (PermutationGenotype<AbstractAntNode>) individualFactory.create().getGenotype();
        antColony.init(genotype.get(0));
    }

    /**
     * On step of the optimizer
     * Each step creates a new {@link PermutationGenotype<AbstractAntNode>} which will be added to the node.
     */
    @Override
    public void next() {
        // Initialize the population if empty
        if(population.isEmpty()){
            for(int i = 0; i < populationSize; i++) {
                population.add(individualFactory.create());
            }
        } else {
            // reduce population size
            // Remove lame elements from the population
            if (population.size() > populationSize) {
                Collection<Individual> lames = selector.getLames(population.size() - populationSize, population);
                population.removeAll(lames);
            }

            // Let the ant colony create a new genotype and add it to the population
            PermutationGenotype<AbstractAntNode> genotype = new PermutationGenotype<>(antColony.next());
            Individual child = individualFactory.create(genotype);
            population.add(child);
        }
    }
}
