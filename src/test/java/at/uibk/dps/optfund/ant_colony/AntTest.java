package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import at.uibk.dps.optfund.ant_colony.model.AntPath;
import at.uibk.dps.optfund.ant_colony.selector.GreedySelector;
import at.uibk.dps.optfund.test_helper.AntProvider;
import at.uibk.dps.optfund.test_helper.TSPProvider;
import at.uibk.dps.optfund.tsp.model.City;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AntTest {


    @Test
    public void ant_GetPath_ReturnsValidPath() {
        int numberOfNodes = 4;

        Ant a = new Ant(0, TSPProvider.setupExampleGraph().get(0), numberOfNodes, new GreedySelector());

        AntPath path = a.getPath(AntProvider.ALPHA, AntProvider.BETA);

        Assert.assertEquals(numberOfNodes + 1, path.getNodes().size());
    }

    @Test
    public void ant_HasUsedEdge_UsedEdge() {
        int numberOfNodes = 4;
        List<City> nodes = TSPProvider.setupExampleGraph();
        AbstractAntNode startNode = nodes.get(0);
        AbstractAntNode b = nodes.get(1);

        Ant a = new Ant(0, startNode, numberOfNodes, new GreedySelector());

        a.getPath(AntProvider.ALPHA, AntProvider.BETA);

        Assert.assertTrue(a.hasUsedEdge(startNode.getNeighbours().get(b)));
    }

    @Test
    public void ant_HasUsedEdge_CorrectPathTaken() {
        int numberOfNodes = 4;
        List<City> nodes = TSPProvider.setupExampleGraph();
        AbstractAntNode startNode = nodes.get(0);
        AbstractAntNode b = nodes.get(1);
        AbstractAntNode c = nodes.get(2);
        AbstractAntNode d = nodes.get(3);

        Ant a = new Ant(0, startNode, numberOfNodes, new GreedySelector());

        a.getPath(AntProvider.ALPHA, AntProvider.BETA);

        Assert.assertTrue(a.hasUsedEdge(startNode.getNeighbours().get(b)));
        Assert.assertTrue(a.hasUsedEdge(b.getNeighbours().get(c)));
        Assert.assertTrue(a.hasUsedEdge(c.getNeighbours().get(d)));
        Assert.assertTrue(a.hasUsedEdge(d.getNeighbours().get(startNode)));

        Assert.assertFalse(a.hasUsedEdge(startNode.getNeighbours().get(c)));
        Assert.assertFalse(a.hasUsedEdge(b.getNeighbours().get(d)));
    }

    @Test
    public void ant_GetPathNoPathAvailable_ExceptionThrown() {
        int numberOfNodes = 2;

        Ant a = new Ant(0, new City(0, 0), numberOfNodes, new GreedySelector());

        Assert.assertThrows(IllegalArgumentException.class, () -> a.getPath(AntProvider.ALPHA, AntProvider.BETA));
    }

}
