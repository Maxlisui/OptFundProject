package at.uibk.dps.optfund.tsp;

import at.uibk.dps.optfund.tsp.model.City;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.problem.Decoder;

public class SalesmanDecoder implements Decoder<PermutationGenotype<City>, SalesmanRoute> {

    @Override
    public SalesmanRoute decode(PermutationGenotype<City> genotype) {
        SalesmanRoute route = new SalesmanRoute();
        route.addAll(genotype);
        return route;
    }
}
