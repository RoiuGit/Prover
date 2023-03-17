import java.util.List;

public class IERule extends Rule {

    IERule(){
        this.numPremises = 2;
    }
    @Override
    public Formula applyRule(List<Formula> premises) {
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
}
