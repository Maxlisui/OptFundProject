package at.uibk.dps.optfund.tsp;

import at.uibk.dps.optfund.tsp.model.City;
import at.uibk.dps.optfund.tsp.model.Street;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

public class SalesmanEvaluator implements Evaluator<SalesmanRoute> {

    @Override
    public Objectives evaluate(SalesmanRoute route) {
        double dist = 0.0;
        for(int i = 0; i < route.size(); i++) {
            City one = route.get(i);
            City two = route.get((i + 1) % route.size());
            if(one == two) {
                continue;
            }
            Street street = one.getStreet(two);
            dist += street.getDistance();
        }
        Objectives objectives = new Objectives();
        objectives.add("distance", Objective.Sign.MIN, dist);
        return objectives;
    }
}
