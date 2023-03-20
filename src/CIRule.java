import java.util.List;

public class CIRule extends Rule {

    CIRule() {
        numPremises = 2;
        ruleName = "CI";
    }

    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        Formula result = null;
        if (premises.size() == numPremises) {
            Formula conjunct1 = premises.get(0);
            Formula conjunct2 = premises.get(1);
            result = new Formula("(%s&%s)".formatted(conjunct1, conjunct2));
        }
        return result;
    }
}
