package at.uibk.dps.optfund.ant_colony.stepper;

import at.uibk.dps.optfund.ant_colony.AntColonyImpl;
import at.uibk.dps.optfund.ant_colony.AntConstants;
import at.uibk.dps.optfund.ant_colony.filter.EdgeFilter;
import at.uibk.dps.optfund.ant_colony.model.AntEdge;
import at.uibk.dps.optfund.ant_colony.model.AntNode;
import at.uibk.dps.optfund.ant_colony.selector.Selector;
import com.google.inject.Inject;
import org.opt4j.core.start.Constant;

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
    private final int consideredEdges;

    /**
     * Constructor of the stepper
     * @param filter The edge filter, which filters the edges available to the ant
     * @param edgeSelector A selector which selects the edge the ant should take
     */
    @Inject
    public AntStepperImpl(EdgeFilter filter,
                          Selector edgeSelector,
                          @Constant(value = AntConstants.CONSIDERED_EDGES_CONSTANT, namespace = AntColonyImpl.class) int consideredEdges) {
        this.filter = filter;
        this.edgeSelector = edgeSelector;
        this.consideredEdges = consideredEdges;
    }


    /**
     * @param currentNode   The ants current node
     * @param startNode     The ants start node
     * @param seenNodes     All nodes which the ant already saw
     * @param numberOfNodes Total number of nodes in the graph
     * @return the next edge to travel
     * @author Daniel Eberharter
     */
    @Override
    public <T> AntEdge<T> getNextEdge(AntNode<T> currentNode, AntNode<T> startNode, Collection<AntNode<T>> seenNodes, int numberOfNodes) {
        // Here we calculate the next edge the ant will take based on the ACO algorithm

        // If the ant has seen the same number of nodes as there are nodes in the graph,
        // the ant has to go to the end/start node
        // This is just an optimization, the rest of the algorithm would yield the same edge
        if(seenNodes.size() == numberOfNodes) {
            return currentNode.getNeighbours().get(startNode);
        }

        // get best N edges
        // if all of those lead to already visited destinations take next N
        // this is done so we do not need to consider each node during each iteration
        List<AntEdge<T>> possible;
        int skip = 0;
        do {
            List<AntEdge<T>> bestEdges = currentNode.getBestNeighbourEdges(skip, consideredEdges);
            if(bestEdges.size() == 0) {
                throw new IllegalArgumentException("bestEdges must not be empty");
            }

            possible = filter.getPossibleEdges(currentNode,
                    bestEdges,
                    seenNodes,
                    startNode);
            skip += consideredEdges;
        }
        while(possible.size() == 0);

        List<Double> weights = new ArrayList<>(possible.size());
        for (AntEdge<T> e : possible) {
            // add probability to list of probabilities
            weights.add(e.getEdgeWeight());
        }

        // probabilistic selection of next edge based on der weight
        AntEdge<T> bestEdge = edgeSelector.select(possible, weights);
        if(bestEdge == null) {
            throw new IllegalArgumentException("best edge must not be null");
        }
        return bestEdge;
    }
}
