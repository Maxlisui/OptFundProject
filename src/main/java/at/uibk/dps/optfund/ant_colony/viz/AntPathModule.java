package at.uibk.dps.optfund.ant_colony.viz;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.viewer.VisualizationModule;

/**
 * The implementation of the ant visualisation module.
 * @author Christoph Haas
 */
public class AntPathModule extends VisualizationModule {

    @Info("Visualize on optimization start.")
    @Order(0)
    protected boolean vizOnStart = true;

    @Info("Visualization delay in ms, for debugging only.")
    @Order(1)
    protected int vizDelay = 0;

    public boolean getVizOnStart() {
        return vizOnStart;
    }

    public void setVizOnStart(boolean vizOnStart) {
        this.vizOnStart = vizOnStart;
    }

    public int getVizDelay() {
        return vizDelay;
    }

    public void setVizDelay(int vizDelay) {
        this.vizDelay = vizDelay;
    }

    @Override
    protected void config() {
        bind(boolean.class).toInstance(vizOnStart);
        bind(int.class).toInstance(vizDelay);
        addIndividualMouseListener(AntPathService.class);
        addOptimizerIterationListener(AntPathService.class);
        addOptimizerStateListener(AntPathService.class);
    }
}