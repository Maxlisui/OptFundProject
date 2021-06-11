package at.uibk.dps.optfund.ant_colony.filter;

import at.uibk.dps.optfund.ant_colony.model.AntEdge;
import at.uibk.dps.optfund.ant_colony.model.AntNode;

import java.util.Collection;
import java.util.List;

/**
 * Interface for filtering a collection of edges base on possible edges, already seen nodes and the start node
 * @author Maximilian Suitner
 */
public interface EdgeFilter {

    /**
     * Returns a collection of possible edges the ant can take
     * @param currentNode The ants current node
     * @param possible All possible edges the ant can take
     * @param seenNodes All nodes which the ant already saw
     * @param startNode The ants start node
     * @param <T> The type of data inside the node
     * @return A list of nodes which the ant is able to take
     */
    <T> List<AntEdge<T>> getPossibleEdges(AntNode<T> currentNode, List<AntEdge<T>> possible,
                                          Collection<AntNode<T>> seenNodes, AntNode<T> startNode);

}
