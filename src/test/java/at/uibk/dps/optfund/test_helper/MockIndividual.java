package at.uibk.dps.optfund.test_helper;

import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Individual;

public class MockIndividual extends Individual {
    public MockIndividual() {
        this.genotype = new DoubleString();
    }
}
