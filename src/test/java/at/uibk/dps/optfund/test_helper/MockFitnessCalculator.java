package at.uibk.dps.optfund.test_helper;

import at.uibk.dps.optfund.dtlz.utils.FitnessCalculator;

public class MockFitnessCalculator implements FitnessCalculator {
    @Override
    public double calculateFitness(double[] objectives) {
        return Double.MAX_VALUE;
    }
}
