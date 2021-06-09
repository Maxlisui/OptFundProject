package at.uibk.dps.optfund.dtlz.factory;

import at.uibk.dps.optfund.dtlz.model.Firefly;
import at.uibk.dps.optfund.test_helper.MockFitnessCalculator;
import at.uibk.dps.optfund.test_helper.MockDoubleStringCreator;
import at.uibk.dps.optfund.test_helper.MockIndividualFactory;
import org.junit.Assert;
import org.junit.Test;
import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Population;

import java.util.List;

public class FireflyFactoryImplTest {

    private final MockDoubleStringCreator creator = new MockDoubleStringCreator(10);
    private final FireflyFactory fireflyFactory = new FireflyFactoryImpl(new MockFitnessCalculator());
    private final MockIndividualFactory<DoubleString> individualFactory = new MockIndividualFactory<>(creator);

    @Test
    public void testCreateFirefly_validArguments_works() {
        Firefly firefly = fireflyFactory.createFirefly(creator.create(), new double[] { 0 });
        Assert.assertNotNull(firefly);
        Assert.assertNotNull(firefly.getPosition());
        Assert.assertEquals(Double.MAX_VALUE, firefly.getFitness(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFirefly_invalidArguments_throws() {
        fireflyFactory.createFirefly(null, new double[] { 0 });
    }

    @Test
    public void testCreateFireflies_validArguments_works() {

        int n = 10;
        Population population = new Population();
        for(int i=0; i < n; i++) {
            population.add(individualFactory.create());
        }

        List<Firefly> fireflies = fireflyFactory.createFireflies(population);
        Assert.assertNotNull(fireflies);
        Assert.assertEquals(n, fireflies.size());

        for(int i=0; i < n; i++) {
            Assert.assertNotNull(fireflies.get(i));
            Assert.assertNotNull(fireflies.get(i).getPosition());
            Assert.assertEquals(Double.MAX_VALUE, fireflies.get(i).getFitness(), 0);
        }
    }

    @Test
    public void testCreateFirefly_fromIndividual_works() {

        Individual i = individualFactory.create();
        Firefly firefly = fireflyFactory.createFirefly(i);
        Assert.assertNotNull(firefly);
        Assert.assertNotNull(firefly.getPosition());
        Assert.assertEquals(Double.MAX_VALUE, firefly.getFitness(), 0);
    }
}