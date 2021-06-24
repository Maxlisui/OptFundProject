package at.uibk.dps.optfund.dtlz;

import at.uibk.dps.optfund.dtlz.optimizer.FireflyAlgorithmModule;
import org.opt4j.benchmarks.dtlz.DTLZModule;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.start.Opt4JTask;
import org.opt4j.viewer.ViewerModule;

public class Main {

    public static void main(String[] args) {
        FireflyAlgorithmModule ea = new FireflyAlgorithmModule();
        ea.setIterations(1000);
        ea.setAttractivenessCoefficient(6.0);
        ea.setLightAbsorptionCoefficient(0.01);
        ea.setRandomWalkCoefficient(0.02);
        ea.setNumberOfFireflies(50);

        DTLZModule dtlz = new DTLZModule();
        dtlz.setFunction(DTLZModule.Function.DTLZ1);
        dtlz.setAlpha(100);
        dtlz.setM(1);
        dtlz.setN(1000);
        dtlz.setK(1000);
        dtlz.setEncoding(DTLZModule.Encoding.DOUBLE);
        dtlz.setBits(30);

        ViewerModule viewer = new ViewerModule();
        viewer.setCloseOnStop(false);

        Opt4JTask task = new Opt4JTask(false);
        task.init(ea,dtlz,viewer);
        try {
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            task.close();
        }
    }

}
