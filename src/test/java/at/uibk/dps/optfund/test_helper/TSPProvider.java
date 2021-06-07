package at.uibk.dps.optfund.test_helper;

import at.uibk.dps.optfund.tsp.model.City;
import at.uibk.dps.optfund.tsp.model.Street;

import java.util.Random;

public class TSPProvider {

    private static final Random rnd = new Random(0);

    public static City getRandomCity() {
        return new City(rnd.nextDouble(), rnd.nextDouble());
    }

    public static Street getRandomStreet() {
        return new Street(getRandomCity(), getRandomCity());
    }

}
