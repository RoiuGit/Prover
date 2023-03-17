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
    }

    private void putRule(Rule rule){
        this.ruleMap.put(rule.getRuleName(), rule);
    }
    public Formula applyRule(String rule, List<Formula> premises, Proof proof) {
        Rule ruleInstance = ruleMap.get(rule.toUpperCase());
        Formula result = ruleInstance.applyRule(premises, proof);
        if (result != null) {
            return result;
        } else {
            System.out.println("Couldn't apply rule " + rule);
            return null;
        }
    }
    public int getNumPremises(String rule){
        Rule ruleInstance = ruleMap.get(rule.toUpperCase());
        return ruleInstance.numPremises;
    }
}