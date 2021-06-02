package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;

import java.util.Collection;

public interface AntColony {

    void init(AbstractAntNode startNode);
    Collection<AbstractAntNode> next();


}
