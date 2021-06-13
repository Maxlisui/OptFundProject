package at.uibk.dps.optfund.ant_colony.viz;

import org.opt4j.viewer.VisualizationModule;

public class AntPathModule extends VisualizationModule {

    @Override
    protected void config() {
        addIndividualMouseListener(AntPathService.class);
    }
}