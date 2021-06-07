package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;

import java.util.Collection;

/**
 * Interface which defines methods used for an ant optimizer
 */
public interface AntColony {

    /**
     * Initializes the ant colony with the given start node
     * @param startNode The start node where all ants start
     */
    void init(AbstractAntNode startNode);

    /**
     * Next function returns a collection of nodes which each ant takes
     * @return A collection of a collection of {@link AbstractAntNode}
     */
    Collection<Collection<AbstractAntNode>> next();


}
