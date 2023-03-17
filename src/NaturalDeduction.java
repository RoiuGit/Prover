import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaturalDeduction {
    private final Map<String, Rule> ruleMap;

    NaturalDeduction() {
        ruleMap = new HashMap<>();
        putRule(new IERule());
        putRule(new CE1Rule());
        putRule(new CE2Rule());
        putRule(new IIRule());
        putRule(new NERule());
        putRule(new NIRule());
    }

    private void putRule(Rule rule) {
        this.ruleMap.put(rule.getRuleName(), rule);
    }

    public void applyRule(String rule, List<Formula> premises, Proof proof) {
        Rule ruleInstance = ruleMap.get(rule.toUpperCase());
        Formula result = ruleInstance.applyRule(premises, proof);
        if (result != null) {
            proof.append(result);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getNumPremises(String rule) {
        Rule ruleInstance = ruleMap.get(rule.toUpperCase());
        return ruleInstance.getNumPremises();
    }
}