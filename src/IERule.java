import java.util.List;

public class IERule extends Rule {

    IERule(){
        numPremises = 2;
        ruleName = "IE";
    }
    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        if (premises.size() != numPremises) return null;

        Formula implication = premises.get(0);
        Formula antecedent = premises.get(1);

        if (implication.getSign().equals("->") && implication.getAntecedent().toString().equals(antecedent.toString())) {
            return implication.getConsequent();
        }

        return null;
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
