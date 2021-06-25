package at.uibk.dps.optfund.test_helper;

import at.uibk.dps.optfund.ant_colony.AntConstants;
import at.uibk.dps.optfund.ant_colony.model.AntEdge;
import at.uibk.dps.optfund.ant_colony.model.AntNode;
import org.opt4j.tutorial.salesman.SalesmanProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TSPProvider {

    private static final Random rnd = new Random(0);
    private static final SalesmanProblem problem = new SalesmanProblem(0);

    public static AntNode<SalesmanProblem.City> getRandomNode() {
        SalesmanProblem.City city = problem.new City(rnd.nextDouble(), rnd.nextDouble());
        return new AntNode<>(city, city.getX(), city.getY());
    }

    public static AntEdge<SalesmanProblem.City> getRandomEdge() {
        return new AntEdge<>(getRandomNode(), getRandomNode(), AntProvider.ALPHA, AntProvider.BETA);
    }

    public static List<AntNode<SalesmanProblem.City>> setupExampleGraph() {
        /*
        Setup a Graph like this where the numbers notate the weights
              A -----2----- B
              |       _>2__/|
              2   ___/      2
              | /           |
              D -----2----- C
         */

        List<AntNode<SalesmanProblem.City>> result = new ArrayList<>();

        AntNode<SalesmanProblem.City> a = new AntNode<>(problem.new City(0.0, 0.0), 0.0, 0.0);
        AntNode<SalesmanProblem.City> b = new AntNode<>(problem.new City(2.0, 0.0), 2.0, 0.0);
        AntNode<SalesmanProblem.City> c = new AntNode<>(problem.new City(2.0, 2.0), 2.0, 2.0);
        AntNode<SalesmanProblem.City> d = new AntNode<>(problem.new City(0.0, 2.0), 0.0, 2.0);

        result.add(a);
        result.add(b);
        result.add(c);
        result.add(d);

        final double defaultGoodPheromone = 10;
        final double defaultBadPheromone = 1;

        AntEdge<SalesmanProblem.City> ab = new AntEdge<>(a, b, AntProvider.ALPHA, AntProvider.BETA);
        ab.setPheromone(defaultGoodPheromone);
        AntEdge<SalesmanProblem.City> ac = new AntEdge<>(a, c, AntProvider.ALPHA, AntProvider.BETA);
        ac.setPheromone(defaultBadPheromone);
        AntEdge<SalesmanProblem.City> ad = new AntEdge<>(a, d, AntProvider.ALPHA, AntProvider.BETA);
        ad.setPheromone(defaultGoodPheromone);
        AntEdge<SalesmanProblem.City> bc = new AntEdge<>(b, c, AntProvider.ALPHA, AntProvider.BETA);
        bc.setPheromone(defaultGoodPheromone);
        AntEdge<SalesmanProblem.City> bd = new AntEdge<>(b, d, AntProvider.ALPHA, AntProvider.BETA);
        bd.setPheromone(defaultBadPheromone);
        AntEdge<SalesmanProblem.City> cd = new AntEdge<>(c, d, AntProvider.ALPHA, AntProvider.BETA);
        cd.setPheromone(defaultGoodPheromone);

        a.addNeighbour(b, ab);
        a.addNeighbour(c, ac);
        a.addNeighbour(d, ad);

        b.addNeighbour(a, ab);
        b.addNeighbour(c, bc);
        b.addNeighbour(d, bd);

        c.addNeighbour(a, ac);
        c.addNeighbour(b, bc);
        c.addNeighbour(d, cd);

        d.addNeighbour(a, ad);
        d.addNeighbour(b, bd);
        d.addNeighbour(c, cd);

        return result;
    }

}
