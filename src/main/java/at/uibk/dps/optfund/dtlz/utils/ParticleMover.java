package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;
import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Individual;

/**
 * Service for moving particles
 * @author Daniel Eberharter
 */
public interface ParticleMover {

    /**
     * Mutation method for position of particle
     * @param firefly the particle to move
     * @param reference the reference particle (does not move)
     * @return the new position for particle
     * @author Daniel Eberharter
     */
    DoubleString move(Firefly firefly, Firefly reference);
}
