import java.util.List;

public class IIRule extends Rule {

    IIRule() {
        numPremises = 1;
        ruleName = "II";
    }

    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        Formula result = null;
        if (premises.size() == numPremises) {
            if (proof.getSubProof() == null && proof.getAssumptionDepth() > 0 || !proof.getSubProof().isNotClosed()) {
                Formula antecedent = premises.get(0);
                result = new Formula("(%s->%s)".formatted(proof.getFormula(0), antecedent));
                proof.close();
            } else result = applyRule(premises, proof.getSubProof());
        }
        return result;
    }
}
