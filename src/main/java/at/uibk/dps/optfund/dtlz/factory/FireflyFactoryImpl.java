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

    private final FitnessCalculator fitnessCalculator;

    /**
     * Constructor
     * @param fitnessCalculator service for calculating fitness
     * @author Daniel Eberharter
     */
    @Inject
    public FireflyFactoryImpl(FitnessCalculator fitnessCalculator) {
        this.fitnessCalculator = fitnessCalculator;
    }

    /**
     * @param position the current position
     * @param objectives the evaluated objective values
     * @return the created firefly
     * @author Daniel Eberharter
     */
    @Override
    public Firefly createFirefly(DoubleString position, double[] objectives) {
        return new Firefly(position, fitnessCalculator.calculateFitness(objectives));
    }

    /**
     * @param individual a individual in the search space
     * @return the created firefly
     * @author Daniel Eberharter
     */
    @Override
    public Firefly createFirefly(Individual individual) {
        return createFirefly((DoubleString) individual.getGenotype(), individual.getObjectives().array());
    }

    /**
     * @param population the current population
     * @return a list of fireflies (one firefly per individual in the population)
     * @author Daniel Eberharter
     */
    @Override
    public List<Firefly> createFireflies(Population population) {
        return population.stream().map(this::createFirefly).collect(Collectors.toList());
    }
}
