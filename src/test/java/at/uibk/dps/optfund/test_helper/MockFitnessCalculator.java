package at.uibk.dps.optfund.test_helper;

import at.uibk.dps.optfund.dtlz.model.Firefly;
import at.uibk.dps.optfund.dtlz.utils.FitnessCalculator;

public class MockFitnessCalculator implements FitnessCalculator {

    @Override
    public double calculateFitness(double[] objectives) {
        return Double.MAX_VALUE;
    }

    @Override
    public double calculateFitness(Firefly firefly) {
        return Double.MAX_VALUE;
    }
}
