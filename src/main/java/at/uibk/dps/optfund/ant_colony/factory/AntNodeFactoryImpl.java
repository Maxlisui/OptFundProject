package at.uibk.dps.optfund.ant_colony.factory;

import at.uibk.dps.optfund.ant_colony.model.AntEdge;
import at.uibk.dps.optfund.ant_colony.model.AntNode;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.tutorial.salesman.SalesmanProblem;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the mapping factory for ant nodes to genotypes an vise-versa
 * @author Maximilian Suitner
 */
public class AntNodeFactoryImpl implements AntNodeFactory {

    @Override
    public List<AntNode<SalesmanProblem.City>> mapGenotypes(PermutationGenotype<SalesmanProblem.City> genotypes) {
        List<AntNode<SalesmanProblem.City>> result = genotypes.stream()
                .map(x -> new AntNode<>(x, x.getX(), x.getY()))
                .collect(Collectors.toList());

        for(int i = 0; i < result.size(); i++) {
            for(int j = i + 1; j < result.size(); j++) {
                AntNode<SalesmanProblem.City> nodeA = result.get(i);
                AntNode<SalesmanProblem.City> nodeB = result.get(j);
                AntEdge<SalesmanProblem.City> edge = new AntEdge<>(nodeA, nodeB);
                nodeA.addNeighbour(nodeB, edge);
                nodeB.addNeighbour(nodeA, edge);
            }
        }
        return result;
    }

    @Override
    public PermutationGenotype<SalesmanProblem.City> mapNodes(Collection<AntNode<SalesmanProblem.City>> cities) {
        return new PermutationGenotype<>(cities.stream().map(AntNode::getInternalObject).collect(Collectors.toList()));
    }
}
