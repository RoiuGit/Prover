import java.util.List;

public abstract class Rule {

    int numPremises;
    public abstract Formula applyRule(List<Formula> premises);
}