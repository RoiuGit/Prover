import java.util.List;
import java.util.Objects;

public class NERule extends Rule {
    NERule() {
        numPremises = 1;
        ruleName = "NE";
    }

    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        Formula result = null;
        if (premises.size() == numPremises) {
            Formula doubleNegation = premises.get(0);
            Formula negation = premises.get(0).getAntecedent();
            if (Objects.equals(doubleNegation.getSign(), "~") && Objects.equals(negation.getSign(), "~"))
                result = negation.getAntecedent();
        }
        return result;
    }
}
