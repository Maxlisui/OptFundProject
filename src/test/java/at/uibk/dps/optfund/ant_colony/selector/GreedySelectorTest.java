package at.uibk.dps.optfund.ant_colony.selector;

import at.uibk.dps.optfund.ant_colony.model.AntEdge;
import at.uibk.dps.optfund.test_helper.TSPProvider;
import org.junit.Assert;
import org.junit.Test;
import org.opt4j.tutorial.salesman.SalesmanProblem;

import java.util.ArrayList;
import java.util.List;

public class GreedySelectorTest {

    @Test
    public void greedySelector_OneHighestWeight_ChooseWithHighestWeight() {
        List<AntEdge<SalesmanProblem.City>> edges = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            edges.add(TSPProvider.getRandomEdge());
            weights.add((double)i);
        }

        GreedySelector selector = new GreedySelector();
        AntEdge<SalesmanProblem.City> chosen = selector.select(edges, weights);

        Assert.assertEquals(chosen, edges.get(4));
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
        List<AntEdge<SalesmanProblem.City>> edges = new ArrayList<>();
        edges.add(TSPProvider.getRandomEdge());

        Assert.assertNull(selector.select(edges, null));
    }

    @Test
    public void greedySelector_EmptyWeights_ReturnsNull() {
        GreedySelector selector = new GreedySelector();
        List<AntEdge<SalesmanProblem.City>> edges = new ArrayList<>();
        edges.add(TSPProvider.getRandomEdge());

        Assert.assertNull(selector.select(edges, new ArrayList<>()));
    }

    @Test
    public void greedySelector_EdgesAndWeightsDifferentSize_Throws() {
        GreedySelector selector = new GreedySelector();
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
