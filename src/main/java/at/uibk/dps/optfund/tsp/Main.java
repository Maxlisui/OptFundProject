package at.uibk.dps.optfund.tsp;

import at.uibk.dps.optfund.ant_colony.AntColonyModule;
import at.uibk.dps.optfund.ant_colony.optimizer.AntOptimizerModule;
import at.uibk.dps.optfund.tsp.visualization.SalesmanWidgetModule;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.start.Opt4JTask;
import org.opt4j.viewer.ViewerModule;

public class Main {

    public static void main(String[] args) {
        int numberOfCities = 20;

        AntColonyModule ants = new AntColonyModule();
        ants.setAlpha(1);
        ants.setBeta(5);
        ants.setRo(0.5);
        ants.setQ(500);
        ants.setNumberOfAnts((int)(numberOfCities * 0.8));

        AntOptimizerModule ea = new AntOptimizerModule();
        ea.setIterations(500);
        ea.setPopulationSize(10);

        SalesmanModule salesman = new SalesmanModule();
        salesman.setSize(numberOfCities);

        SalesmanWidgetModule widget = new SalesmanWidgetModule();
        ViewerModule viewer = new ViewerModule();
        viewer.setCloseOnStop(false);

        Opt4JTask task = new Opt4JTask(false);
        task.init(ea, salesman, viewer, widget, ants);

        try {
            task.execute();
            Archive archive = task.getInstance(Archive.class);
            for (Individual individual : archive) {
                // obtain the phenotype and objective, etc. of each individual
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            task.close();
        }
    }

}
