import java.util.List;

public abstract class Rule {

    protected int numPremises;
    public abstract Formula applyRule(List<Formula> premises);
    public abstract int getNumPremises();
}