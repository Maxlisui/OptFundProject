package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.opt4j.benchmarks.DoubleString;

import java.util.Random;

public class ParticleMoverImplTest {

    private final ParticleMoverImpl mover = new ParticleMoverImpl(0, 1, 0.01);
    private final Random rnd = new Random(0);

    @Test(expected = IllegalArgumentException.class)
    public void testMove_arg1null_throws() {
        Firefly f = new Firefly(new DoubleString(), 0);
        mover.move(f, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMove_arg2null_throws() {
        Firefly f = new Firefly(new DoubleString(), 0);
        mover.move(null, f);
    }

    @Test
    public void testCalculateDistance_samePoint_distanceZero() {
        DoubleString d1 = new DoubleString();
        d1.init(rnd, 2);
        d1.set(0, 1.0);
        d1.set(1, 2.0);

        DoubleString d2 = new DoubleString();
        d2.init(rnd, 2);
        d2.set(0, 1.0);
        d2.set(1, 2.0);

        double res = mover.calculateDistance(d1, d2);
        Assert.assertEquals(0, res, res);
    }

    @Test
    public void testCalculateDistance_differentPoint_distanceNotZero() {
        DoubleString d1 = new DoubleString();
        d1.init(rnd, 2);
        d1.set(0, 2.0);
        d1.set(1, 2.0);

        DoubleString d2 = new DoubleString();
        d2.init(rnd, 2);
        d2.set(0, 4.0);
        d2.set(1, 4.0);

        double res = mover.calculateDistance(d1, d2);
        Assert.assertEquals(2.8284271247461903, res, 0);
    }

    @Test
    public void testCalculateNewPosition() {
        double p1 = 10;
        double p2 = 50;

        double res = mover.calculateNewPosition(p1, p2, Math.abs(p2-p1));
        Assert.assertTrue(res > p1);
        Assert.assertTrue(res <= p2);
    }
}