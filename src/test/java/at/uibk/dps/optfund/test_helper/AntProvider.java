package at.uibk.dps.optfund.test_helper;

import at.uibk.dps.optfund.ant_colony.AntColony;
import at.uibk.dps.optfund.ant_colony.AntColonyImpl;
import at.uibk.dps.optfund.ant_colony.factory.AntNodeFactory;
import at.uibk.dps.optfund.ant_colony.factory.AntNodeFactoryImpl;
import at.uibk.dps.optfund.ant_colony.filter.EdgeFilter;
import at.uibk.dps.optfund.ant_colony.filter.EdgeFilterImpl;
import at.uibk.dps.optfund.ant_colony.optimizer.AntOptimizer;
import at.uibk.dps.optfund.ant_colony.selector.RouletteWheelSelector;
import at.uibk.dps.optfund.ant_colony.selector.Selector;
import at.uibk.dps.optfund.ant_colony.stepper.AntStepper;
import at.uibk.dps.optfund.ant_colony.stepper.AntStepperImpl;
import org.opt4j.core.optimizer.Population;
import org.opt4j.tutorial.salesman.SalesmanCreator;
import org.opt4j.tutorial.salesman.SalesmanProblem;

public class AntProvider {

    public static final int NUMBER_OF_ANTS = 2;
    public static final double ALPHA = 1.0;
    public static final double BETA = 5;
    public static final double RO = 0.5;
    public static final double Q = 100.0;
    public static final int NUMBER_OF_CITIES = 10;

    public static AntStepper createAntStepper() {
        return new AntStepperImpl(new EdgeFilterImpl(), new RouletteWheelSelector(), 0);
    }

    public static AntColony<SalesmanProblem.City> createAntColony() {
        return createAntColony(createAntStepper());
    }

    public static AntColony<SalesmanProblem.City> createAntColony(AntStepper stepper) {
        return createAntColony(stepper, NUMBER_OF_ANTS);
    }

    public static AntColony<SalesmanProblem.City> createAntColony(AntStepper stepper, int numberOfAnts) {
        return createAntColony(stepper, numberOfAnts, ALPHA);
    }

    public static AntColony<SalesmanProblem.City> createAntColony(AntStepper stepper, int numberOfAnts, double alpha) {
        return createAntColony(stepper, numberOfAnts, alpha, BETA);
    }

    public static AntColony<SalesmanProblem.City> createAntColony(AntStepper stepper, int numberOfAnts, double alpha, double beta) {
        return createAntColony(stepper, numberOfAnts, alpha, beta, RO);
    }

    public static AntColony<SalesmanProblem.City> createAntColony(AntStepper stepper, int numberOfAnts, double alpha, double beta, double ro) {
        return createAntColony(stepper, numberOfAnts, alpha, beta, ro, Q);
    }

    public static AntColony<SalesmanProblem.City> createAntColony(AntStepper stepper, int numberOfAnts, double alpha, double beta, double ro, double q) {
        return new AntColonyImpl<>(stepper, numberOfAnts, alpha, beta, ro, q);
    }

    public static AntOptimizer createAntOptimizer(Population population) {
        return createAntOptimizer(population, createAntColony());
    }

    public static AntOptimizer createAntOptimizer(Population population, AntColony<SalesmanProblem.City> colony) {
        return createAntOptimizer(population, colony, new SalesmanProblem(NUMBER_OF_CITIES));
    }

    public static AntOptimizer createAntOptimizer(Population population, AntColony<SalesmanProblem.City> colony, SalesmanProblem problem) {
        return createAntOptimizer(population, colony, new SalesmanCreator(problem));
    }

    public static AntOptimizer createAntOptimizer(Population population, AntColony<SalesmanProblem.City> colony, SalesmanCreator creator) {
        return createAntOptimizer(population, colony, creator, new AntNodeFactoryImpl(AntProvider.ALPHA, AntProvider.BETA));
    }

    public static AntOptimizer createAntOptimizer(Population population, AntColony<SalesmanProblem.City> colony, SalesmanCreator creator, AntNodeFactory antNodeFactory) {
        return new AntOptimizer(new MockIndividualFactory<>(creator), population, colony, antNodeFactory);
    }

}
