import java.util.List;

public class IERule extends Rule {

    IERule() {
        numPremises = 2;
        ruleName = "IE";
    }

    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        Formula result = null;
        if (premises.size() == numPremises) {
            Formula implication = premises.get(0);
            Formula antecedent = premises.get(1);
            if (implication.getSign().equals("->") && implication.getAntecedent().toString().equals(antecedent.toString())) {
                result = implication.getConsequent();
            }
        }
        return result;
    }
}
