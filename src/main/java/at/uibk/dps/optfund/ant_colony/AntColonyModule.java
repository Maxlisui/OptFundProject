package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.filter.EdgeFilter;
import at.uibk.dps.optfund.ant_colony.filter.EdgeFilterImpl;
import at.uibk.dps.optfund.ant_colony.selector.RouletteWheelSelector;
import at.uibk.dps.optfund.ant_colony.selector.Selector;
import at.uibk.dps.optfund.ant_colony.stepper.AntStepper;
import at.uibk.dps.optfund.ant_colony.stepper.AntStepperImpl;
import com.google.inject.TypeLiteral;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;
import org.opt4j.tutorial.salesman.SalesmanProblem;

@SuppressWarnings("ALL")
public class AntColonyModule extends OptimizerModule {

    @Info("The number of ants.")
    @Order(0)
    @Constant(value = AntConstants.NUMBER_OF_ANTS_CONSTANT, namespace = AntColonyImpl.class)
    protected int numberOfAnts = 100;

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

    @Info("Limit how many edges will be considered for selection by each ant at a junction. The edges will be considered based on their fitness. 0 means that all edges will be considered.")
    @Order(5)
    @Constant(value = AntConstants.CONSIDERED_EDGES_CONSTANT, namespace = AntColonyImpl.class)
    protected int consideredEdges = 0;

    public int getNumberOfAnts() { return numberOfAnts; }

    public void setNumberOfAnts(int numberOfAnts) { this.numberOfAnts = numberOfAnts; }

    public int getConsideredEdges() { return consideredEdges; }

    public void setConsideredEdges(int consideredEdges) { this.consideredEdges = consideredEdges; }

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
        bind(Selector.class).to(RouletteWheelSelector.class);
        bind(EdgeFilter.class).to(EdgeFilterImpl.class);
        bind(AntStepper.class).to(AntStepperImpl.class);
        bind(new TypeLiteral<AntColony<SalesmanProblem.City>>(){}).to(new TypeLiteral<AntColonyImpl<SalesmanProblem.City>>() {});
    }
}
