package at.uibk.dps.optfund.ant_colony.filter;

import at.uibk.dps.optfund.ant_colony.model.AntEdge;
import at.uibk.dps.optfund.ant_colony.model.AntNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the edge filter for TSP
 * @author Maximilian Suitner
 */
public class EdgeFilterImpl implements EdgeFilter {

    @Override
    public <T> List<AntEdge<T>> getPossibleEdges(AntNode<T> currentNode, List<AntEdge<T>> possible,
                                                 Collection<AntNode<T>> seenNodes, AntNode<T> startNode) {
        // The ant has not seen any node yet, just return all edges.
        if(seenNodes.isEmpty()) {
            return possible;
        }

        List<AntEdge<T>> edges = new ArrayList<>(possible.size());


        // A for loop is faster in this case
        //noinspection ForLoopReplaceableByForEach
        for(int i = 0; i < possible.size(); i++) {
            AntEdge<T> e = possible.get(i);
            AntNode<T> dest = currentNode.getDestination(e);

            if(dest.equals(startNode)) {
                continue;
            }

            // Ignore nodes which the ant already visited
            if(seenNodes.contains(dest)) {
                continue;
            }
            edges.add(e);
        }
        return edges;
    }
}
