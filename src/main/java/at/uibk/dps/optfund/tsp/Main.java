package at.uibk.dps.optfund.tsp;

import at.uibk.dps.optfund.ant_colony.AntColonyModule;
import at.uibk.dps.optfund.ant_colony.optimizer.AntOptimizerModule;
import org.opt4j.core.start.Opt4JTask;
import org.opt4j.tutorial.salesman.SalesmanModule;
import org.opt4j.tutorial.salesman.SalesmanWidgetModule;
import org.opt4j.viewer.ViewerModule;

public class Main {

    public static void main(String[] args) {
        int numberOfCities = 1000;
        int iterations = 500;

        AntColonyModule ants = new AntColonyModule();
        ants.setAlpha(1);
        ants.setBeta(5);
        ants.setRo(0.5);
        ants.setQ(100);
        ants.setNumberOfAnts(20);
        ants.setConsideredEdges(10);

        AntOptimizerModule ea = new AntOptimizerModule();
        ea.setIterations(iterations);

        SalesmanModule salesman = new SalesmanModule();
        salesman.setSize(numberOfCities);

        SalesmanWidgetModule widget = new SalesmanWidgetModule();
        ViewerModule viewer = new ViewerModule();
        viewer.setCloseOnStop(false);

        Opt4JTask task = new Opt4JTask(false);
        task.init(ea, salesman, viewer, widget, ants);
        task.open();

        try {
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            task.close();
        }
    }
}
