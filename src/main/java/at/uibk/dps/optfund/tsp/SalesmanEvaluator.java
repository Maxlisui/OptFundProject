package at.uibk.dps.optfund.tsp;

import at.uibk.dps.optfund.tsp.model.City;
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
            dist += getDistance(one, two);
        }
        Objectives objectives = new Objectives();
        objectives.add("distance", Objective.Sign.MIN, dist);
        return objectives;
    }

    private double getDistance(final City one, final  City two) {
        final double x = one.getX() - two.getX();
        final double y = one.getY() - two.getY();

        return Math.sqrt(x * x + y * y);
    }
}
