package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import com.google.inject.Inject;
import org.opt4j.core.start.Constant;

import java.util.*;
import java.util.stream.Collectors;

public class AntColonyImpl implements AntColony {

    private final int numberOfAnts;
    private final double alpha;
    private final double beta;
    private final double ro;
    private final ArrayList<Ant> ants = new ArrayList<>();

    @Inject
    public AntColonyImpl(@Constant(value = "numberOfAnts") int numberOfAnts,
                         @Constant(value = "alpha") double alpha,
                         @Constant(value = "beta") double beta,
                         @Constant(value = "ro") double ro) {
        this.numberOfAnts = numberOfAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.ro = ro;
    }

    @Override
    public void init(AbstractAntNode startNode) {
        for(int i = 0; i < numberOfAnts; i++) {
            ants.add(new Ant(startNode));
        }
    }

    @Override
    public Collection<AbstractAntNode> next(Collection<AbstractAntNode> nodes) {
        Map<AbstractAntNode, Integer> usedNodesAmount = new HashMap<>();

        for (Ant a : ants) {
            AbstractAntNode usedNode = a.step(alpha, beta);

            if(usedNodesAmount.containsKey(usedNode)) {
                usedNodesAmount.computeIfPresent(usedNode, (k, v) -> v + 1);
            } else {
                usedNodesAmount.put(usedNode, 1);
            }
        }

        List<AbstractAntNode> result = usedNodesAmount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());



        return result;
    }
}
