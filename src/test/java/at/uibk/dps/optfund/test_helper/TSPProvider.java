package at.uibk.dps.optfund.test_helper;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import at.uibk.dps.optfund.tsp.model.City;
import at.uibk.dps.optfund.tsp.model.Street;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TSPProvider {

    private static final Random rnd = new Random(0);

    public static City getRandomCity() {
        return new City(rnd.nextDouble(), rnd.nextDouble());
    }

    public static Street getRandomStreet() {
        return new Street(getRandomCity(), getRandomCity());
    }

    public static List<City> setupExampleGraph() {
        /*
        Setup a Graph like this where the numbers notate the weights
              A -----2----- B
              |       _>2__/|
              2   ___/      2
              | /           |
              D -----2----- C
         */

        List<City> result = new ArrayList<>();

        City a = new City(0.0, 0.0);
        City b = new City(2.0, 0.0);
        City c = new City(2.0, 2.0);
        City d = new City(0.0, 2.0);

        result.add(a);
        result.add(b);
        result.add(c);
        result.add(d);

        final double defaultGoodPheromone = 10;
        final double defaultBadPheromone = 1;

        Street ab = new Street(a, b);
        ab.setPheromone(defaultGoodPheromone);
        Street ac = new Street(a, c);
        ac.setPheromone(defaultBadPheromone);
        Street ad = new Street(a, d);
        ad.setPheromone(defaultGoodPheromone);
        Street bc = new Street(b, c);
        bc.setPheromone(defaultGoodPheromone);
        Street bd = new Street(b, d);
        bd.setPheromone(defaultBadPheromone);
        Street cd = new Street(c, d);
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
