package at.uibk.dps.optfund.tsp;

import at.uibk.dps.optfund.tsp.model.City;
import com.google.inject.Inject;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.problem.Creator;

import java.util.Collections;

public class SalesmanCreator implements Creator<PermutationGenotype<City>> {
    protected final SalesmanProblem problem;

    @Inject
    public SalesmanCreator(SalesmanProblem problem) {
        this.problem = problem;
    }

    @Override
    public PermutationGenotype<City> create() {
       PermutationGenotype<City> genotype = new PermutationGenotype<>();
       genotype.addAll(problem.getCities());
       Collections.shuffle(genotype);
       return genotype;
    }
}
