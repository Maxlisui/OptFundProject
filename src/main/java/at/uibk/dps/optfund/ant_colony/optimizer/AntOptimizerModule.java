package at.uibk.dps.optfund.ant_colony.optimizer;

import at.uibk.dps.optfund.ant_colony.factory.AntNodeFactory;
import at.uibk.dps.optfund.ant_colony.factory.AntNodeFactoryImpl;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;

/**
 * The ant optimization module.
 * @author Maximilian Suitner
 */
public class AntOptimizerModule extends OptimizerModule {

    @Info("The number of iterations.")
    @Order(3)
    @MaxIterations
    protected int iterations = 1000;

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getIterations() { return iterations; }

    /**
     * Configures the module with {@link AntOptimizer}
     */
    @Override
    protected void config() {
        bind(AntNodeFactory.class).to(AntNodeFactoryImpl.class).in(SINGLETON);
        bindIterativeOptimizer(AntOptimizer.class);
    }
}
