package at.uibk.dps.optfund.tsp.modules;

import at.uibk.dps.optfund.tsp.SalesPersonProblemSeed;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.tutorial.salesman.SalesmanProblem;

/**
 * Configures the usage of the seed-based TSP problem.
 * 
 * @author Fedor Smirnov
 *
 */
public class TspSeedModule extends ProblemModule{

	@Override
	protected void config() {
		bind(SalesmanProblem.class).to(SalesPersonProblemSeed.class);
	}
}
