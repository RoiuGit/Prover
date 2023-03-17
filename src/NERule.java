import java.util.List;
import java.util.Objects;

public class NERule extends Rule {
    NERule() {
        numPremises = 1;
        ruleName = "NE";
    }

    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        if (premises.size() != numPremises) return null;
        Formula doubleNegation = premises.get(0);
        Formula negation = premises.get(0).getAntecedent();
        if (Objects.equals(doubleNegation.getSign(), "~") && Objects.equals(negation.getSign(), "~"))
            return negation.getAntecedent();
        else return null;

    }

    @Override
    public int getNumPremises() {
        return numPremises;
    }

    @Override
    public String getRuleName() {
        return ruleName;
    }
}
