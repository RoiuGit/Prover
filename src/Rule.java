import java.util.List;

public abstract class Rule {
    protected String ruleName;
    protected String schema;
    protected int numPremises;

    public abstract Formula applyRule(List<Formula> premises, Proof proof);

    public int getNumPremises() {
        return numPremises;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getSchema() {
        return schema;
    }
}