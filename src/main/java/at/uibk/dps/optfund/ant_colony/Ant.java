package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Ant {

    private final AbstractAntNode startNode;
    private AbstractAntNode currentNode;
    private final Set<AbstractAntEdge> usedEdges = new HashSet<>();
    private final Set<AbstractAntNode> seenNodes = new HashSet<>();

    public Ant(AbstractAntNode startNode) {
        this.currentNode = startNode;
        this.startNode = startNode;
    }

    public AbstractAntNode step(double alpha, double beta) {
        AbstractAntEdge edge = getNextEdge(currentNode.getNeighbours().values(), alpha, beta);

        usedEdges.add(edge);
        currentNode = edge.getB();
        seenNodes.add(currentNode);
        return currentNode;
    }

    public boolean hasUsedEdge(AbstractAntEdge edge) {
        return usedEdges.contains(edge);
    }

    private AbstractAntEdge getNextEdge(Collection<AbstractAntEdge> edges, double alpha, double beta) {
        double minProp = 0.0;
        AbstractAntEdge bestEdge = null;

        for(AbstractAntEdge e : edges) {
            double factorTop = getFactor(e.getPheromone(), e.getDistance(), alpha, beta);
            double sumFactorBot = getAllEdgesExcept(e, edges).stream().mapToDouble(x -> getFactor(x.getPheromone(), x.getDistance(), alpha, beta)).sum();

            double prop = factorTop / sumFactorBot;
            if(prop > minProp) {
                minProp = prop;
                bestEdge = e;
            }
        }
        return bestEdge;
    }

    private Collection<AbstractAntEdge> getAllEdgesExcept(AbstractAntEdge except, Collection<AbstractAntEdge> possible) {
        Collection<AbstractAntEdge> edges = new ArrayList<>(possible.size() - 1);

        for(AbstractAntEdge e : possible) {
            if(!except.equals(e) && !seenNodes.contains(e.getB())) {
                edges.add(e);
            }
        }
        return edges;
    }

    private double getFactor(double pheromone, double distance, double alpha, double beta) {
        double tauXY = Math.pow(pheromone, alpha);
        double nuXZ = Math.pow(distance, beta);
        return tauXY * nuXZ;
    }

}
