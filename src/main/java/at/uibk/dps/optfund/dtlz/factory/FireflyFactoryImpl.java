package at.uibk.dps.optfund.dtlz.factory;

import at.uibk.dps.optfund.dtlz.model.Firefly;
import at.uibk.dps.optfund.dtlz.utils.FitnessCalculator;
import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Population;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory class for creating fireflies from opt4j population
 * @author Daniel Eberharter
 */
public class FireflyFactoryImpl implements FireflyFactory {

    /**
     * Constructor
     * @author Daniel Eberharter
     */
    @Inject
    public FireflyFactoryImpl() {
    }

    /**
     * @param individual a individual in the search space
     * @return the created firefly
     * @author Daniel Eberharter
     */
    @Override
    public Firefly createFirefly(Individual individual) {
        return new Firefly(individual);
    }

    /**
     * @param population the current population
     * @return a list of fireflies (one firefly per individual in the population)
     * @author Daniel Eberharter
     */
    @Override
    public List<Firefly> createFireflies(Population population) {
        return population.parallelStream().map(this::createFirefly).collect(Collectors.toList());
    }
}
