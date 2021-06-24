package at.uibk.dps.optfund.dtlz.utils;

import at.uibk.dps.optfund.dtlz.model.Firefly;

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
    double[] move(Firefly firefly, Firefly reference);
}
