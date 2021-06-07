package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.selector.RouletteWheelSelector;
import at.uibk.dps.optfund.ant_colony.selector.Selector;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

public class AntColonyModule extends OptimizerModule {

    @Info("The number of ants.")
    @Order(0)
    @Constant(value = AntConstants.NUMBER_OF_ANTS_CONSTANT, namespace = AntColonyImpl.class)
    protected int numberOfAnts = 1000;

    @Info("The alpha value (weight of pheromones).")
    @Order(1)
    @Constant(value = AntConstants.ALPHA_CONSTANT, namespace = AntColonyImpl.class)
    protected double alpha = 1.0;

    @Info("The beta value (weight of paths).")
    @Order(2)
    @Constant(value = AntConstants.BETA_CONSTANT, namespace = AntColonyImpl.class)
    protected double beta = 1.0;

    @Info("The pheromone decay rate.")
    @Order(3)
    @Constant(value = AntConstants.RO_CONSTANT, namespace = AntColonyImpl.class)
    protected double ro = 0.5;

    @Info("The pheromone value.")
    @Order(4)
    @Constant(value = AntConstants.Q_CONSTANT, namespace = AntColonyImpl.class)
    protected double q = 500;

    public int getNumberOfAnts() { return numberOfAnts; }

    public void setNumberOfAnts(int numberOfAnts) { this.numberOfAnts = numberOfAnts; }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getRo() {
        return ro;
    }

    public void setRo(double ro) {
        this.ro = ro;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    @Override
    protected void config() {
        bind(AntColony.class).to(AntColonyImpl.class);
        bind(Selector.class).to(RouletteWheelSelector.class);
    }
}
