package at.uibk.dps.optfund.ant_colony.stepper;

import at.uibk.dps.optfund.ant_colony.filter.EdgeFilter;
import at.uibk.dps.optfund.ant_colony.model.AntEdge;
import at.uibk.dps.optfund.ant_colony.model.AntNode;
import at.uibk.dps.optfund.ant_colony.selector.Selector;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of the ant stepper interface
 * Determines the next node based on ACO
 * @author Maximilian Suitner
 */
public class AntStepperImpl implements AntStepper {

    private final EdgeFilter filter;
    private final Selector edgeSelector;

    /**
     * Constructor of the stepper
     * @param filter The edge filter, which filters the edges available to the ant
     * @param edgeSelector A selector which selects the edge the ant should take
     */
    @Inject
    public AntStepperImpl(EdgeFilter filter, Selector edgeSelector) {
        this.filter = filter;
        this.edgeSelector = edgeSelector;
    }


    @Override
    public <T> AntEdge<T> getNextEdge(AntNode<T> currentNode, AntNode<T> startNode, Collection<AntNode<T>> seenNodes,
                                      double alpha, double beta, int numberOfNodes) {
        // Here we calculate the next edge the ant will take based on the ACO algorithm

        // If the ant has seen the same number of nodes as there are nodes in the graph,
        // the ant has to go to the end/start node
        // This is just an optimization, the rest of the algorithm would yield the same edge
        if(seenNodes.size() == numberOfNodes) {
            return currentNode.getNeighbours().get(startNode);
        }

        // Get all edges which can be used by the ant
        List<AntEdge<T>> possible = filter.getPossibleEdges(currentNode, currentNode.getNeighbours().values().asList(),
                seenNodes, startNode);
        if (possible.size() == 0) {
            throw new IllegalArgumentException("list of possible edges must not be empty");
        }

        // Now do the actual ACO calculation
        HashMap<AntEdge<T>, Double> factors = new HashMap<>(possible.size() + 1);
        double sumFactorBot = 0.0;

        // calculate the factor for each possible edge
        for(AntEdge<T> poss : possible) {
            double factor = getFactor(poss.getPheromone(), poss.getDistance(), alpha, beta);
            sumFactorBot += factor;
            factors.put(poss, factor);
        }

        List<Double> props = new ArrayList<>(possible.size());
        for(AntEdge<T> e : possible) {

            if(sumFactorBot == 0.0) {
                props.add(0.0);
                continue;
            }

            // Algorithm
            // p = factorTop / sumFactorBot
            // where sumFactorBot = sum(z -> z.pheromone^alpha * (1/z.distance)^beta, possible)
            // and factorTop = e.pheromone^alpha * (1/e.distance)^beta

            // add probability to list of probabilities
            props.add(factors.get(e) / sumFactorBot);
        }

        AntEdge<T> bestEdge = edgeSelector.select(possible, props);
        if(bestEdge == null) {
            throw new IllegalArgumentException("best edge must not be null");
        }
        return bestEdge;
    }

    private double getFactor(double pheromone, double distance, double alpha, double beta) {
        // Calculate the factor for the ACO
        double tau = Math.pow(pheromone, alpha);
        double ita = Math.pow(1 / distance, beta);
        return tau * ita;
    }
}
