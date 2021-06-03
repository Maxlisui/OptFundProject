package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import at.uibk.dps.optfund.ant_colony.model.AntPath;

import java.util.*;

public class Ant {

    private final int antIndex;
    private final int numberOfCities;
    private final AbstractAntNode startNode;
    private final Set<AbstractAntEdge> usedEdges = new HashSet<>();
    private final Set<AbstractAntNode> seenNodes = new HashSet<>();
    private final Random rnd = new Random(0);

    public Ant(int antIndex, AbstractAntNode startNode, int numberOfCities) {
        this.antIndex = antIndex;
        this.startNode = startNode;
        this.numberOfCities = numberOfCities;
    }

    public AntPath getPath(double alpha, double beta) {
        reset();
        AbstractAntNode currentNode = startNode;
        List<AbstractAntNode> nodes = new ArrayList<>();
        List<AbstractAntEdge> edges = new ArrayList<>();

        nodes.add(currentNode);
        seenNodes.add(currentNode);

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
        AbstractAntEdge edge = getNextEdge(currentNode, alpha, beta);

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

    private AbstractAntEdge getNextEdge(AbstractAntNode currentNode, double alpha, double beta) {
        if(seenNodes.size() == numberOfCities) {
            return currentNode.getNeighbours().get(startNode);
        }

        List<AbstractAntEdge> possible = getPossibleEdges(currentNode.getNeighbours().values().asList());

        double minProp = 0.0;
        AbstractAntEdge bestEdge = null;

        for(AbstractAntEdge e : possible) {
            double sumFactorBot = 0.0;
            boolean ignore = false;

            for(AbstractAntEdge poss : possible) {
                if (poss.equals(e)) {
                    ignore = true;
                    break;
                }
                sumFactorBot += getFactor(poss.getPheromone(), poss.getDistance(), alpha, beta);
            }

            if(ignore) {
                continue;
            }

            double factorTop = getFactor(e.getPheromone(), e.getDistance(), alpha, beta);

            double prop = factorTop / sumFactorBot;
            if(prop > minProp) {
                minProp = prop;
                bestEdge = e;
            }
        }

        if (bestEdge == null) {
            do {
                bestEdge = possible.get(rnd.nextInt(possible.size()));
            } while (bestEdge.getB().equals(startNode));
        }

        return bestEdge;
    }

    private List<AbstractAntEdge> getPossibleEdges(List<AbstractAntEdge> possible) {
        if(seenNodes.isEmpty()) {
            return possible;
        }

        List<AbstractAntEdge> edges = new ArrayList<>(possible.size() - 1);


        //noinspection ForLoopReplaceableByForEach
        for(int i = 0; i < possible.size(); i++) {
            AbstractAntEdge e = possible.get(i);
            if(seenNodes.contains(e.getB())) {
                continue;
            }
            edges.add(e);
        }
        return edges;
    }

    private double getFactor(double pheromone, double distance, double alpha, double beta) {
        double tau = Math.pow(pheromone, alpha);
        double nu = Math.pow(distance, beta);
        return tau * nu;
    }

}
