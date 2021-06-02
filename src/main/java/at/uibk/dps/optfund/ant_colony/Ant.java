package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import at.uibk.dps.optfund.ant_colony.model.AntPath;

import java.util.*;

public class Ant {

    private final int antIndex;
    private final AbstractAntNode startNode;
    private final Set<AbstractAntEdge> usedEdges = new HashSet<>();
    private final Set<AbstractAntNode> seenNodes = new HashSet<>();
    private final Random rnd = new Random(0);

    public Ant(int antIndex, AbstractAntNode startNode) {
        this.antIndex = antIndex;
        this.startNode = startNode;
    }

    public AntPath getPath(double alpha, double beta) {
        reset();
        AbstractAntNode currentNode = startNode;
        List<AbstractAntNode> nodes = new ArrayList<>();
        List<AbstractAntEdge> edges = new ArrayList<>();

        do {
            AbstractAntEdge edge = step(currentNode, alpha, beta);

            currentNode = edge.getB();
            edges.add(edge);
            nodes.add(currentNode);

        } while (!currentNode.equals(startNode));

        return new AntPath(edges, nodes);
    }

    private void reset() {
        usedEdges.clear();
        seenNodes.clear();
    }

    private AbstractAntEdge step(AbstractAntNode currentNode, double alpha, double beta) {
        AbstractAntEdge edge = getNextEdge(currentNode.getNeighbours().values().asList(), alpha, beta);

        usedEdges.add(edge);
        currentNode = edge.getB();
        seenNodes.add(currentNode);
        return edge;
    }

    public boolean hasUsedEdge(AbstractAntEdge edge) {
        return usedEdges.contains(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ant ant = (Ant) o;
        return antIndex == ant.antIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(antIndex);
    }

    private AbstractAntEdge getNextEdge(List<AbstractAntEdge> edges, double alpha, double beta) {
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

        if (bestEdge == null) {
            bestEdge = edges.get(rnd.nextInt(edges.size()));
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
