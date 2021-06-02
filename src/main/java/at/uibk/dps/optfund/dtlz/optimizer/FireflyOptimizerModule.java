package at.uibk.dps.optfund.dtlz.optimizer;

import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

public class FireflyOptimizerModule extends OptimizerModule {

    @MaxIterations
    protected int iterations = 1000;
    @Constant(value = "populationSize")
    protected int populationSize = 100;
    @Constant(value = "offSize")
    protected int offSize = 25;
    @Constant(value = "mutationRate")
    protected double mutationRate = 0.1;

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

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    protected void config() {
        bindIterativeOptimizer(FireflyOptimizer.class);
    }
}
