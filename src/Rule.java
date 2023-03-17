import java.util.List;

public abstract class Rule {
    protected String ruleName;
    protected int numPremises;

    public abstract Formula applyRule(List<Formula> premises, Proof proof);

    public abstract int getNumPremises();

    public abstract String getRuleName();
}