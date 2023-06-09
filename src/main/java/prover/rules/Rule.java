package prover.rules;

import prover.formula.Formula;
import prover.proof.Proof;

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

    @Override
    public String toString() {
        return ruleName;
    }
}