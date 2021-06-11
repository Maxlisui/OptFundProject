package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AntEdge;
import at.uibk.dps.optfund.test_helper.TSPProvider;
import org.junit.Assert;
import org.junit.Test;
import org.opt4j.tutorial.salesman.SalesmanProblem;

import java.util.ArrayList;
import java.util.List;

public class RouletteWheelSelectorTest {

    @Test
    public void rouletteWheelSelector_OneHighestWeight_OneChosen() {
        List<AntEdge<SalesmanProblem.City>> edges = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            edges.add(TSPProvider.getRandomEdge());
            weights.add((double)i / 10.0);
        }

        RouletteWheelSelector selector = new RouletteWheelSelector();
        AntEdge<SalesmanProblem.City> chosen = selector.select(edges, weights);

        Assert.assertNotNull(chosen);
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
        List<AntEdge<SalesmanProblem.City>> edges = new ArrayList<>();
        edges.add(TSPProvider.getRandomEdge());

        Assert.assertNull(selector.select(edges, null));
    }

    @Test
    public void rouletteWheelSelector_EmptyWeights_ReturnsNull() {
        RouletteWheelSelector selector = new RouletteWheelSelector();
        List<AntEdge<SalesmanProblem.City>> edges = new ArrayList<>();
        edges.add(TSPProvider.getRandomEdge());

        Assert.assertNull(selector.select(edges, new ArrayList<>()));
    }

    @Test
    public void rouletteWheelSelector_EdgesAndWeightsDifferentSize_Throws() {
        RouletteWheelSelector selector = new RouletteWheelSelector();
        List<AntEdge<SalesmanProblem.City>> edges = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            edges.add(TSPProvider.getRandomEdge());
            weights.add((double)i);
        }
        weights.add((double)1);

        Assert.assertThrows(IllegalArgumentException.class, () -> selector.select(edges, weights));
    }
    
}
