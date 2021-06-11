package at.uibk.dps.optfund.ant_colony.factory;

import at.uibk.dps.optfund.ant_colony.model.AntNode;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.tutorial.salesman.SalesmanProblem;

import java.util.Collection;
import java.util.List;

/**
 * Factory which maps between {@link org.opt4j.tutorial.salesman.SalesmanProblem.City} and {@link AntNode}
 * @author Maximilian Suitner
 */
public interface AntNodeFactory {

    /**
     * Creates a new list of {@link AntNode} for the genotypes
     * @param genotypes Instance of {@link PermutationGenotype}
     * @return A list mapped ant nodes
     */
    List<AntNode<SalesmanProblem.City>> mapGenotypes(PermutationGenotype<SalesmanProblem.City> genotypes);

    /**
     * Create a new {@link PermutationGenotype} based on the given cities
     * @param cities A collection of {@link AntNode}
     * @return A mapped genotype
     */
    PermutationGenotype<SalesmanProblem.City> mapNodes(Collection<AntNode<SalesmanProblem.City>> cities);

}
