package at.uibk.dps.optfund.ant_colony;

import at.uibk.dps.optfund.ant_colony.model.AntNode;
import at.uibk.dps.optfund.ant_colony.model.AntPath;
import at.uibk.dps.optfund.test_helper.AntProvider;
import at.uibk.dps.optfund.test_helper.TSPProvider;
import org.junit.Assert;
import org.junit.Test;
import org.opt4j.tutorial.salesman.SalesmanProblem;

import java.util.List;

public class AntTest {


    @Test
    public void ant_GetPath_ReturnsValidPath() {
        int numberOfNodes = 4;

        Ant<SalesmanProblem.City> a = new Ant<>(0, TSPProvider.setupExampleGraph().get(0), numberOfNodes, AntProvider.createAntStepper());

        AntPath<SalesmanProblem.City> path = a.getPath(AntProvider.ALPHA, AntProvider.BETA);

        Assert.assertEquals(numberOfNodes + 1, path.getNodes().size());
    }

    @Test
    public void ant_HasUsedEdge_UsedEdge() {
        int numberOfNodes = 4;
        List<AntNode<SalesmanProblem.City>> nodes = TSPProvider.setupExampleGraph();
        AntNode<SalesmanProblem.City> startNode = nodes.get(0);
        AntNode<SalesmanProblem.City> b = nodes.get(1);

        Ant<SalesmanProblem.City> a = new Ant<>(0, startNode, numberOfNodes, AntProvider.createAntStepper());

        a.getPath(AntProvider.ALPHA, AntProvider.BETA);

        Assert.assertTrue(a.hasUsedEdge(startNode.getNeighbours().get(b)));
    }

    @Test
    public void ant_HasUsedEdge_CorrectPathTaken() {
        int numberOfNodes = 4;
        List<AntNode<SalesmanProblem.City>> nodes = TSPProvider.setupExampleGraph();
        AntNode<SalesmanProblem.City> startNode = nodes.get(0);
        AntNode<SalesmanProblem.City> b = nodes.get(1);
        AntNode<SalesmanProblem.City> c = nodes.get(2);
        AntNode<SalesmanProblem.City> d = nodes.get(3);

        Ant<SalesmanProblem.City> a = new Ant<>(0, startNode, numberOfNodes, AntProvider.createAntStepper());

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
        SalesmanProblem problem = new SalesmanProblem(0);
        Ant<SalesmanProblem.City> a = new Ant<>(0, new AntNode<>(problem.new City(0.0, 0.0), 0.0, 0.0), numberOfNodes, AntProvider.createAntStepper());

        Assert.assertThrows(IllegalArgumentException.class, () -> a.getPath(AntProvider.ALPHA, AntProvider.BETA));
    }

}
