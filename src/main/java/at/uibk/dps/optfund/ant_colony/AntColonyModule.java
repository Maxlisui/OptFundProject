package at.uibk.dps.optfund.ant_colony;

import org.opt4j.core.start.Constant;
import org.opt4j.core.start.Opt4JModule;

public class AntColonyModule extends Opt4JModule {

    @Constant(value = "numberOfAnts")
    protected int numberOfAnts;
    @Constant(value = "alpha")
    protected double alpha;
    @Constant(value = "beta")
    protected double beta;
    @Constant(value = "ro")
    protected double ro;

    public int getNumberOfAnts() {
        return numberOfAnts;
    }

    public void setNumberOfAnts(int numberOfAnts) {
        this.numberOfAnts = numberOfAnts;
    }

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

    @Override
    protected void config() {
        bind(AntColony.class).to(AntColonyImpl.class);
    }
}
