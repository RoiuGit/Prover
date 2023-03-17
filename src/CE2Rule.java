import java.util.List;
import java.util.Objects;

public class CE2Rule extends Rule{

    CE2Rule(){
        this.numPremises = 1;
    }
    @Override
    public Formula applyRule(List<Formula> premises) {
        if (premises.size() != numPremises) return null;
        Formula conjunction = premises.get(0);
        if (Objects.equals(conjunction.getSign(), "&"))
            return conjunction.getConsequent();
        return null;
    }

    @Override
    public int getNumPremises() {
        return numPremises;
    }
}
