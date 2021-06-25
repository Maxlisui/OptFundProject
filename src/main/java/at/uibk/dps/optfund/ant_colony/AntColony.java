package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AntNode;

import java.util.Collection;
import java.util.List;

/**
 * Interface which defines methods used for an ant optimizer
 * @param <T> The type of data inside the node
 * @author Maximilian Suitner
 */
public interface AntColony<T> {

    /**
     * Initializes the ant colony with the given start node
     * @param startNode The start node where all ants start
     * @param nodes all nodes in the problem
     */
    void init(AntNode<T> startNode, List<AntNode<T>> nodes);

    /**
     * Next function returns a collection of nodes which each ant takes
     * @return A collection of a collection of {@link AntNode}
     */
    Collection<Collection<AntNode<T>>> next();


}
