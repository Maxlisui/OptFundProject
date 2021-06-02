package at.uibk.dps.optfund.tsp;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Category;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.core.start.Constant;

@Category
@Icon(Icons.HELP)
public class SalesmanModule extends ProblemModule {

    @SuppressWarnings("BindingAnnotationWithoutInject")
    @Constant(value = "size")
    protected int size = 100;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    protected void config() {
        bindProblem(SalesmanCreator.class, SalesmanDecoder.class, SalesmanEvaluator.class);

    }
}
