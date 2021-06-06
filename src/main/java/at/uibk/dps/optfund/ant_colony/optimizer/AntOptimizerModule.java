package at.uibk.dps.optfund.ant_colony.optimizer;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

/**
 * The ant optimization module.
 * MaxIterations, PopulationSize and Offsize can be configured.
 */
public class AntOptimizerModule extends OptimizerModule {

    @Constant(value = "populationSize")
    protected int populationSize = 100;
    @Constant(value = "offSize")
    protected int offSize = 25;

    @Info("The number of iterations.")
    @Order(3)
    @MaxIterations
    protected int iterations = 1000;

    /**
     * Gets the population size
     * @return The population size
     */
    public int getPopulationSize() {
        return populationSize;
    }

    /**
     * Sets the population size
     * @param populationSize The new population size
     */
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    /**
     * Gets the off size
     * @return The off size
     */
    public int getOffSize() {
        return offSize;
    }

    /**
     * Sets the off size
     * @param offSize The new off size
     */
    public void setOffSize(int offSize) {
        this.offSize = offSize;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getIterations() { return iterations; }

    /**
     * Configures the module with {@link AntOptimizer}
     */
    @Override
    protected void config() {
        bindIterativeOptimizer(AntOptimizer.class);
    }
}
