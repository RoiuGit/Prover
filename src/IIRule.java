import java.util.List;

public class IIRule extends Rule{

    IIRule(){
        numPremises = 1;
        ruleName = "II";
    }
    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        if (premises.size() != numPremises) return null;
        Formula result;
        if (proof.getSubProof() == null && proof.getAssumptionDepth() > 0){
            result = new Formula("(%s->%s)".formatted(proof.getFormula(0).toString(), proof.getFormula(proof.size() - 1).toString()));
            proof.closeSubProof();
        }
        else result = applyRule(premises, proof.getSubProof());
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
