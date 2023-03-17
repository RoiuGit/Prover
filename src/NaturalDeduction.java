import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaturalDeduction {
    private final Map<String, Rule> ruleMap;

    NaturalDeduction() {
        ruleMap = new HashMap<>();
        ruleMap.put("IE", new IERule());
        ruleMap.put("CE1", new CE1Rule());
        ruleMap.put("CE2", new CE2Rule());
    }

    public Formula applyRule(String rule, List<Formula> premises) {
        Rule ruleInstance = ruleMap.get(rule.toUpperCase());
        Formula result = ruleInstance.applyRule(premises);
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