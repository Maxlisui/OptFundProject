package at.uibk.dps.optfund.tsp;

import at.uibk.dps.optfund.ant_colony.AntColonyModule;
import at.uibk.dps.optfund.ant_colony.optimizer.AntOptimizerModule;
import at.uibk.dps.optfund.tsp.visualization.SalesmanWidgetModule;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.optimizer.OptimizerStateListener;
import org.opt4j.core.start.Opt4JTask;
import org.opt4j.viewer.ViewerModule;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static class Runner implements OptimizerIterationListener, OptimizerStateListener {

        private Instant startTime;
        private Instant iterationStartTime;
        private final int iterations = 500;
        private final List<Long> iterationTimes = new ArrayList<>(iterations);

        public void run() {
            int numberOfCities = 100;

            AntColonyModule ants = new AntColonyModule();
            ants.setAlpha(1);
            ants.setBeta(5);
            ants.setRo(0.5);
            ants.setQ(500);
            ants.setNumberOfAnts((int)(numberOfCities * 0.8));

            AntOptimizerModule ea = new AntOptimizerModule();
            ea.setIterations(iterations);
            ea.setPopulationSize(10);
            ea.setOffSize(2);

            SalesmanModule salesman = new SalesmanModule();
            salesman.setSize(numberOfCities);

            SalesmanWidgetModule widget = new SalesmanWidgetModule();
            ViewerModule viewer = new ViewerModule();
            viewer.setCloseOnStop(false);

            Opt4JTask task = new Opt4JTask(false);
            task.init(ea, salesman, viewer, widget, ants);
            task.open();

            Optimizer antOptimizer = task.getInstance(Optimizer.class);
            antOptimizer.addOptimizerIterationListener(this);
            antOptimizer.addOptimizerStateListener(this);

            try {
                task.execute();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                task.close();
            }
        }

        @Override
        public void iterationComplete(int iteration) {
            Instant finish = Instant.now();
            long elapsed = Duration.between(iterationStartTime, finish).toMillis();
            iterationTimes.add(elapsed);

            System.out.println("Iteration " + iteration + " took " + elapsed+ "ms");

            iterationStartTime = Instant.now();
        }

        @Override
        public void optimizationStarted(Optimizer optimizer) {
            startTime = Instant.now();
            iterationStartTime = Instant.now();
        }

        @Override
        public void optimizationStopped(Optimizer optimizer) {
            Instant finish = Instant.now();
            long elapsed = Duration.between(startTime, finish).toMillis();
            System.out.println("Optimization took " + elapsed+ "ms");

            double averageIterationTimes = iterationTimes.stream().mapToLong(a -> a).average().orElseThrow();
            System.out.println("Average Iteration Time was " + averageIterationTimes + "ms");
        }
    }

    public static void main(String[] args) {
        Runner r = new Runner();
        r.run();
    }
}
