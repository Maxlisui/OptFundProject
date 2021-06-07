package at.uibk.dps.optfund.ant_colony.optimizer;

import at.uibk.dps.optfund.ant_colony.AntColony;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import com.google.inject.Inject;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;

import java.util.Collection;

/**
 * The implementation of the ant optimizer for {@link IterativeOptimizer}
 */
public class AntOptimizer implements IterativeOptimizer {

    private final IndividualFactory individualFactory;
    private final Population population;
    private final AntColony antColony;

    /**
     * Creates a new instance of the ant optimizer
     * @param individualFactory An factory to create new {@link Individual}
     * @param population The population of the optimizer
     * @param antColony The ant colony implementation
     */
    @Inject
    public AntOptimizer(IndividualFactory individualFactory,
                        Population population,
                        AntColony antColony) {
        this.individualFactory = individualFactory;
        this.population = population;
        this.antColony = antColony;
    }

    /**
     * Initializes the ant colony
     */
    @SuppressWarnings("unchecked")
    @Override
    public void initialize() {
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
        population.clear();

        // Let the ant colony create a new genotype for each ant
        Collection<Collection<AbstractAntNode>> antNodes = antColony.next();

        // Create a new genotype for each path and add it to the population
        for(Collection<AbstractAntNode> path : antNodes) {
            PermutationGenotype<AbstractAntNode> genotype = new PermutationGenotype<>(path);
            Individual child = individualFactory.create(genotype);
            population.add(child);
        }
    }
}
