import java.util.List;
import java.util.Objects;

public class NIRule extends Rule {

    NIRule() {
        numPremises = 2;
        ruleName = "NI";
    }

    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        if (premises.size() != numPremises) return null;
        Formula result = null;
        Formula affirmation = premises.get(0);
        Formula negation = premises.get(1);
        if (!Objects.equals(affirmation.getSign(), "~") && Objects.equals(negation.getSign(), "~") && affirmation.equals(negation.getAntecedent())) {
            if (proof.getSubProof() == null && proof.getAssumptionDepth() > 0 || !proof.getSubProof().isNotClosed()) {
                result = new Formula("~" + proof.getFormula(proof.size() - 1));
                proof.close();
            } else result = applyRule(premises, proof.getSubProof());
        }
        return result;
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
