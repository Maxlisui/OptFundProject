package at.uibk.dps.optfund.dtlz;

import at.uibk.dps.optfund.dtlz.optimizer.FireflyOptimizerModule;
import org.opt4j.benchmarks.dtlz.DTLZModule;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.start.Opt4JTask;
import org.opt4j.viewer.ViewerModule;

public class Main {

    public static void main(String[] args) {
        FireflyOptimizerModule ea = new FireflyOptimizerModule();
        ea.setIterations(500);
        ea.setMutationRate(0.1);
        DTLZModule dtlz = new DTLZModule();
        dtlz.setFunction(DTLZModule.Function.DTLZ1);
        ViewerModule viewer = new ViewerModule();
        viewer.setCloseOnStop(false);
        Opt4JTask task = new Opt4JTask(false);
        task.init(ea,dtlz,viewer);
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
