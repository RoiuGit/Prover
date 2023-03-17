import java.util.List;
import java.util.Objects;

public class CE2Rule extends Rule {

    CE2Rule() {
        numPremises = 1;
        ruleName = "CE2";
    }

    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        if (premises.size() != numPremises) return null;
        Formula conjunction = premises.get(0);
        if (Objects.equals(conjunction.getSign(), "&")) return conjunction.getConsequent();
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
