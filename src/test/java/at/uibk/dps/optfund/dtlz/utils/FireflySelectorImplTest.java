package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.opt4j.benchmarks.DoubleString;

import java.util.ArrayList;
import java.util.List;

public class FireflySelectorImplTest {

    private final FireflySelectorImpl fireflySelector = new FireflySelectorImpl();

    @Test
    public void testGetFittestFirefly() {
        List<Firefly> fireflies = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            fireflies.add(new Firefly(new DoubleString(), i == 5 ? 2.0 : 1.0));
        }

        Firefly fittest = fireflySelector.getFittestFirefly(fireflies);
        Assert.assertNotNull(fittest);
        Assert.assertEquals(fireflies.get(5), fittest);
    }
}