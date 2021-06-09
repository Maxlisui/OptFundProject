package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.test_helper.MockFitnessCalculator;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class FitnessCalculatorImplTest {

    private final FitnessCalculator fitnessCalculator = new FitnessCalculatorImpl();

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateFitness_null_throws() {
        fitnessCalculator.calculateFitness(null);
    }

    @Test
    public void testCalculateFitness_empty_returnsZero() {
        double res = fitnessCalculator.calculateFitness(new double[0]);
        Assert.assertEquals(0, res, 0);
    }

    @Test
    public void testCalculateFitness_validValues_works() {
        double res = fitnessCalculator.calculateFitness(new double[] { 1.0, 12.0, 6.66 });
        Assert.assertNotEquals(0, res, 0);
    }
}