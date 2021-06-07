package at.uibk.dps.optfund.tsp;

import at.uibk.dps.optfund.test_helper.TSPProvider;
import at.uibk.dps.optfund.tsp.model.City;
import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Value;
import org.opt4j.core.genotype.PermutationGenotype;

public class SalesmanTest {

    @Test
    public void salesmanProblem_CreateProblem_CorrectSize() {
        int size = 10;
        SalesmanProblem problem = new SalesmanProblem(size);
        Assert.assertEquals(size, problem.getCities().size());
        Assert.assertTrue(problem.getCities().stream().allMatch(x -> x.getNeighbours().values().size() == size - 1));
    }

    @Test
    public void salesmanCreator_GivenProblem_CorrectGenotypes() {
        int size = 10;
        SalesmanProblem problem = new SalesmanProblem(size);
        SalesmanCreator creator = new SalesmanCreator(problem);

        PermutationGenotype<City> genotypes = creator.create();

        Assert.assertArrayEquals(problem.getCities().toArray(), genotypes.toArray());
    }

    @Test
    public void salesmanDecoder_GivenGenotypes_CorrectRoute() {
        int size = 10;
        SalesmanProblem problem = new SalesmanProblem(size);
        SalesmanCreator creator = new SalesmanCreator(problem);
        PermutationGenotype<City> genotypes = creator.create();
        SalesmanDecoder decoder = new SalesmanDecoder();

        SalesmanRoute path = decoder.decode(genotypes);
        Assert.assertArrayEquals(genotypes.toArray(), path.toArray());
    }

    @Test
    public void salesmanEvaluator_GivenRoute_CorrectResult() {
        SalesmanRoute path = new SalesmanRoute();
        path.addAll(TSPProvider.setupExampleGraph());

        SalesmanEvaluator evaluator = new SalesmanEvaluator();
        Objectives result = evaluator.evaluate(path);

        Objective objective = new Objective("distance", Objective.Sign.MIN);
        Assert.assertEquals(1, result.size());

        Value<?> value = result.get(objective);
        double doubleVal = value.getDouble();
        double expected = 8.0;
        Assert.assertEquals(expected, doubleVal, 0.001);
    }
}
