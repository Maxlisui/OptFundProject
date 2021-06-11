package at.uibk.dps.optfund.ant_colony.stepper;

import at.uibk.dps.optfund.ant_colony.model.AntEdge;
import at.uibk.dps.optfund.ant_colony.model.AntNode;

import java.util.Collection;

/**
 * Stepper which performs one step for a ant
 * @author Maximilian Suitner
 */
public interface AntStepper {

    /**
     * Returns the next node the ant should take on its path
     * @param currentNode The ants current node
     * @param startNode The ants start node
     * @param seenNodes All nodes which the ant already saw
     * @param alpha Alpha value for ACO
     * @param beta Beta value for ACO
     * @param numberOfNodes Total number of nodes in the graph
     * @param <T> The type of data inside the node
     * @return The next node the ant should take on its path
     */
    <T> AntEdge<T> getNextEdge(AntNode<T> currentNode, AntNode<T> startNode, Collection<AntNode<T>> seenNodes,
                               double alpha, double beta, int numberOfNodes);
}
