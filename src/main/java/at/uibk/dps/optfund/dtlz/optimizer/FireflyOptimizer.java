package at.uibk.dps.optfund.dtlz.optimizer;

import com.google.inject.Inject;
import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.copy.Copy;
import org.opt4j.operators.mutate.Mutate;
import org.opt4j.optimizers.ea.Selector;

import java.util.Collection;

public class FireflyOptimizer implements IterativeOptimizer {

    protected final IndividualFactory individualFactory;
    protected final Mutate<Genotype> mutate;
    protected final Copy<Genotype> copy;
    protected final Selector selector;
    private final Population population;
    private final int populationSize;
    private final int offSize;
    private final double mutationRate;

    private Individual currentBest = null;

    @Inject
    public FireflyOptimizer(IndividualFactory individualFactory, Mutate<Genotype> mutate,
                            Copy<Genotype> copy, Selector selector, Population population,
                            @Constant(value = "populationSize") int populationSize,
                            @Constant(value = "offSize") int offSize,
                            @Constant(value = "mutationRate") double mutationRate) {
        this.individualFactory = individualFactory;
        this.mutate = mutate;
        this.copy = copy;
        this.selector = selector;
        this.population = population;
        this.populationSize = populationSize;
        this.offSize = offSize;
        this.mutationRate = mutationRate;
    }

    @Override
    public void initialize() {
        selector.init(offSize + populationSize);
        if(population.isEmpty()) {
            for(int i = 0; i < populationSize; i++) {
                population.add(individualFactory.create());
            }
        }
    }

    @Override
    public void next() {
        if (population.size() > populationSize) {
            Collection<Individual> lames = selector.getLames(population.size() - populationSize, population);
            population.removeAll(lames);
        }

        Collection<Individual> parents = selector.getParents(offSize, population);
        for (Individual parent : parents) {
            Genotype genotype = copy.copy(parent.getGenotype());
            mutate.mutate(genotype, mutationRate);
            Individual child = individualFactory.create(genotype);
            population.add(child);
        }
    }
}
