package at.uibk.dps.optfund.tsp.visualization;

import org.opt4j.viewer.VisualizationModule;

public class SalesmanWidgetModule extends VisualizationModule {

    @Override
    protected void config() {
        addIndividualMouseListener(SalesmanWidgetService.class);
    }
}
