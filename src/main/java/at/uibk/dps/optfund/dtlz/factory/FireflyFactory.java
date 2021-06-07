package at.uibk.dps.optfund.dtlz.factory;

import at.uibk.dps.optfund.dtlz.model.Firefly;
import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Population;

import java.util.List;

public interface FireflyFactory {

    /**
     * @param position the current position
     * @param objectives the evaluated objective values
     * @return the created firefly
     * @author Daniel Eberharter
     */
    Firefly createFirefly(DoubleString position, double[] objectives);

    /**
     * @param individual a individual in the search space
     * @return the created firefly
     * @author Daniel Eberharter
     */
    Firefly createFirefly(Individual individual);

    /**
     * @param population the current population
     * @return a list of fireflies (one firefly per individual in the population)
     * @author Daniel Eberharter
     */
    List<Firefly> createFireflies(Population population);
}
