import java.util.List;
import java.util.Objects;

public class CE1Rule extends Rule {

    CE1Rule() {
        numPremises = 1;
        ruleName = "CE1";
    }

    @Override
    public Formula applyRule(List<Formula> premises, Proof proof) {
        Formula result = null;
        if (premises.size() == numPremises) {
            Formula conjunction = premises.get(0);
            if (Objects.equals(conjunction.getSign(), "&")) result = conjunction.getAntecedent();
        }
        return result;
    }
}
