package at.uibk.dps.optfund.test_helper;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.IndividualStateListener;
import org.opt4j.core.problem.Creator;

public class MockIndividualFactory<T extends Genotype> implements IndividualFactory {

    private final Creator<T> creator;

    public MockIndividualFactory(Creator<T> creator) {
        this.creator = creator;
    }

    @Override
    public Individual create() {
        Individual i = new MockIndividual();
        i.setGenotype(creator.create());
        return i;
    }

    @Override
    public Individual create(Genotype genotype) {
        Individual i = new MockIndividual();
        i.setGenotype(genotype);
        return i;
    }

    @Override
    public void addIndividualStateListener(IndividualStateListener listener) {}

    @Override
    public void removeIndividualStateListener(IndividualStateListener listener) {}
}
