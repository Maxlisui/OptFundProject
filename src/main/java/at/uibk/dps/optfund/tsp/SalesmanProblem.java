package at.uibk.dps.optfund.tsp;

import at.uibk.dps.optfund.tsp.model.City;
import at.uibk.dps.optfund.tsp.model.Street;
import com.google.inject.Inject;
import org.opt4j.core.start.Constant;

import java.util.*;

public class SalesmanProblem {

    protected Set<City> cities = null;

    @Inject
    public SalesmanProblem(@Constant(value = "size") int size) {

        List<City> cities = new ArrayList<>(size);

        Random random = new Random(0);
        for (int i = 0; i < size; i++) {
            final double x = random.nextDouble() * 100;
            final double y = random.nextDouble() * 100;
            final City city = new City(x, y);
            cities.add(city);
        }

        for(int i = 0; i < cities.size(); i++) {
            for(int j = i+1; j < cities.size(); j++) {
                Street s = new Street(cities.get(i), cities.get(j));
                cities.get(i).addNeighbour(cities.get(j), s);
                cities.get(j).addNeighbour(cities.get(i), s);
            }
        }
        this.cities = new HashSet<>(cities);
    }
    public Set<City> getCities() {
        return cities;
    }
}
