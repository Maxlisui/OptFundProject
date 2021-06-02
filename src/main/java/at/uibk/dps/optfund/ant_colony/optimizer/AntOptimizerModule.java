package at.uibk.dps.optfund.ant_colony.optimizer;

import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

public class AntOptimizerModule extends OptimizerModule {

    @MaxIterations
    protected int iterations = 1000;
    @Constant(value = "populationSize")
    protected int populationSize = 100;
    @Constant(value = "offSize")
    protected int offSize = 25;

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getOffSize() {
        return offSize;
    }

    public void setOffSize(int offSize) {
        this.offSize = offSize;
    }

    @Override
    protected void config() {
        bindIterativeOptimizer(AntOptimizer.class);
    }
}
