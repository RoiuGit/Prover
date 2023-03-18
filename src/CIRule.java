import java.util.List;

public class CIRule extends Rule{

    CIRule(){
        numPremises = 2;
        ruleName = "CI";
    }
    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        if (premises.size() != numPremises) return null;
        else{
            Formula conjunct1 = premises.get(0);
            Formula conjunct2 = premises.get(1);
            return new Formula("(%s&%s)".formatted(conjunct1, conjunct2));
        }
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
