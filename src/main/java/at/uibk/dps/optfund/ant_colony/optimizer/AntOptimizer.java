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
import org.opt4j.operators.copy.Copy;
import org.opt4j.optimizers.ea.Selector;

import java.util.Collection;

public class AntOptimizer implements IterativeOptimizer {

    private final IndividualFactory individualFactory;
    private final Copy<PermutationGenotype<AbstractAntNode>> copy;
    private final Selector selector;
    private final Population population;
    private final AntColony antColony;
    private final int populationSize;
    private final int offSize;

    @Inject
    public AntOptimizer(IndividualFactory individualFactory, Copy<PermutationGenotype<AbstractAntNode>> copy,
                        Selector selector, Population population, AntColony antColony,
                        @Constant(value = "populationSize") int populationSize,
                        @Constant(value = "offSize") int offSize) {
        this.individualFactory = individualFactory;
        this.copy = copy;
        this.selector = selector;
        this.population = population;
        this.antColony = antColony;
        this.populationSize = populationSize;
        this.offSize = offSize;
    }

    @Override
    public void initialize() {
        selector.init(offSize + populationSize);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void next() {
        if(population.isEmpty()){
            for(int i = 0; i < populationSize; i++) {
                population.add(individualFactory.create());
            }
        } else {
            if (population.size() > populationSize) {
                Collection<Individual> lames = selector.getLames(population.size() - populationSize, population);
                population.removeAll(lames);
            }

            Collection<Individual> parents = selector.getParents(populationSize, population);
            for (Individual parent : parents) {
                PermutationGenotype<AbstractAntNode> parentGenotype = (PermutationGenotype<AbstractAntNode>) parent.getGenotype();
                PermutationGenotype<AbstractAntNode> genotype = copy.copy(parentGenotype);



                Individual child = individualFactory.create(genotype);
                population.add(child);
            }
        }
    }
}
