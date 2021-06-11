package at.uibk.dps.optfund.test_helper;

import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.problem.Creator;

import java.util.Random;

public class MockDoubleStringCreator implements Creator<DoubleString> {
    private final Random rnd = new Random(0);
    private final int n;

    public MockDoubleStringCreator(int n) {
        this.n = n;
    }

    @Override
    public DoubleString create() {
        DoubleString ds = new DoubleString();
        ds.init(rnd, n);
        return ds;
    }
}
