import java.util.List;
import java.util.Objects;

public class CE1Rule extends Rule{

    CE1Rule(){
        this.numPremises = 1;
    }
    @Override
    public Formula applyRule(List<Formula> premises) {
        if (premises.size() != numPremises) return null;
        Formula conjunction = premises.get(0);
        if (Objects.equals(conjunction.getSign(), "&"))
            return conjunction.getAntecedent();
        return null;
    }

    @Override
    public int getNumPremises() {
        return numPremises;
    }
}
