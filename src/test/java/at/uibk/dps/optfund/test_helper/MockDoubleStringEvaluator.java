package at.uibk.dps.optfund.test_helper;

import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

import java.util.Random;

public class MockDoubleStringEvaluator implements Evaluator<DoubleString> {
    private final Random rnd = new Random();

    @Override
    public Objectives evaluate(DoubleString phenotype) {
        Objectives o = new Objectives();
        o.add("rnd", Objective.Sign.MIN, rnd.nextInt());
        return o;
    }
}
