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
        List<AntEdge<T>> possible = filter.getPossibleEdges(currentNode,
                currentNode.getNeighbours().values().asList(),
                seenNodes,
                startNode);

        if (possible.size() == 0) {
            throw new IllegalArgumentException("list of possible edges must not be empty");
        }

        List<Double> props = new ArrayList<>(possible.size());
        for (AntEdge<T> e : possible) {
            // add probability to list of probabilities
            props.add(e.getEdgeWeight());
        }

        AntEdge<T> bestEdge = edgeSelector.select(possible, props);
        if(bestEdge == null) {
            throw new IllegalArgumentException("best edge must not be null");
        }
        return bestEdge;
    }
}
