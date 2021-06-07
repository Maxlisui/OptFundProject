package at.uibk.dps.optfund.ant_colony.optimizer;

import at.uibk.dps.optfund.test_helper.AntProvider;
import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.optimizer.Population;

public class AntOptimizerTests {

    @Test
    public void antOptimizer_NewlyCreated_PopulationEmpty() {
        Population population = new Population();

        AntOptimizer optimizer = AntProvider.createAntOptimizer(population);

        optimizer.initialize();

        Assert.assertEquals(0, population.size());
    }


    @Test
    public void antOptimizer_OneIteration_PopulationSizeEqualsAntSize() {
        Population population = new Population();

        AntOptimizer optimizer = AntProvider.createAntOptimizer(population);

        optimizer.initialize();
        optimizer.next();

        Assert.assertEquals(AntProvider.NUMBER_OF_ANTS, population.size());
    }

    @Test
    public void antOptimizer_MultipleIteration_PopulationSizeEqualsAntSize() {
        Population population = new Population();

        AntOptimizer optimizer = AntProvider.createAntOptimizer(population);

        optimizer.initialize();
        for(int i = 0; i < 5; i++) {
            optimizer.next();
        }

        Assert.assertEquals(AntProvider.NUMBER_OF_ANTS, population.size());
    }
}
