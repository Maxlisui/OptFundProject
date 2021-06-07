package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.test_helper.TSPProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GreedySelectorTest {

    @Test
    public void greedySelector_OneHighestWeight_ChooseWithHighestWeight() {
        List<AbstractAntEdge> streets = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            streets.add(TSPProvider.getRandomStreet());
            weights.add((double)i);
        }

        GreedySelector selector = new GreedySelector();
        AbstractAntEdge chosen = selector.select(streets, weights);

        Assert.assertEquals(chosen, streets.get(4));
    }

    @Test
    public void greedySelector_NullEdges_ReturnsNull() {
        GreedySelector selector = new GreedySelector();

        Assert.assertNull(selector.select(null, new ArrayList<>()));
    }

    @Test
    public void greedySelector_EmptyEdges_ReturnsNull() {
        GreedySelector selector = new GreedySelector();

        Assert.assertNull(selector.select(new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    public void greedySelector_NullWeights_ReturnsNull() {
        GreedySelector selector = new GreedySelector();
        List<AbstractAntEdge> streets = new ArrayList<>();
        streets.add(TSPProvider.getRandomStreet());

        Assert.assertNull(selector.select(streets, null));
    }

    @Test
    public void greedySelector_EmptyWeights_ReturnsNull() {
        GreedySelector selector = new GreedySelector();
        List<AbstractAntEdge> streets = new ArrayList<>();
        streets.add(TSPProvider.getRandomStreet());

        Assert.assertNull(selector.select(streets, new ArrayList<>()));
    }

    @Test
    public void greedySelector_EdgesAndWeightsDifferentSize_Throws() {
        GreedySelector selector = new GreedySelector();
        List<AbstractAntEdge> streets = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            streets.add(TSPProvider.getRandomStreet());
            weights.add((double)i);
        }
        weights.add((double)1);

        Assert.assertThrows(IllegalArgumentException.class, () -> selector.select(streets, weights));
    }
}
