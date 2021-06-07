package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.test_helper.TSPProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RouletteWheelSelectorTest {

    @Test
    public void rouletteWheelSelector_OneHighestWeight_RandomEdgeButNotFirst() {
        List<AbstractAntEdge> streets = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            streets.add(TSPProvider.getRandomStreet());
            weights.add((double)i);
        }

        RouletteWheelSelector selector = new RouletteWheelSelector();
        AbstractAntEdge chosen = selector.select(streets, weights);

        Assert.assertNotEquals(chosen, streets.get(0));
    }

    @Test
    public void rouletteWheelSelector_NullEdges_ReturnsNull() {
        RouletteWheelSelector selector = new RouletteWheelSelector();

        Assert.assertNull(selector.select(null, new ArrayList<>()));
    }

    @Test
    public void rouletteWheelSelector_EmptyEdges_ReturnsNull() {
        RouletteWheelSelector selector = new RouletteWheelSelector();

        Assert.assertNull(selector.select(new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    public void rouletteWheelSelector_NullWeights_ReturnsNull() {
        RouletteWheelSelector selector = new RouletteWheelSelector();
        List<AbstractAntEdge> streets = new ArrayList<>();
        streets.add(TSPProvider.getRandomStreet());

        Assert.assertNull(selector.select(streets, null));
    }

    @Test
    public void rouletteWheelSelector_EmptyWeights_ReturnsNull() {
        RouletteWheelSelector selector = new RouletteWheelSelector();
        List<AbstractAntEdge> streets = new ArrayList<>();
        streets.add(TSPProvider.getRandomStreet());

        Assert.assertNull(selector.select(streets, new ArrayList<>()));
    }

    @Test
    public void rouletteWheelSelector_EdgesAndWeightsDifferentSize_Throws() {
        RouletteWheelSelector selector = new RouletteWheelSelector();
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
