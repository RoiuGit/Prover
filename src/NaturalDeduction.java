import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NaturalDeduction {
    private final Map<String, Rule> ruleMap;

    NaturalDeduction() {
        ruleMap = new HashMap<>();
        putRule(new IERule());
        putRule(new IIRule());
        putRule(new CE1Rule());
        putRule(new CE2Rule());
        putRule(new CIRule());
        putRule(new NERule());
        putRule(new NIRule());
        putRule(new DERule());
        putRule(new DI1Rule());
        putRule(new DI2Rule());
    }

    private void putRule(Rule rule) {
        this.ruleMap.put(rule.getRuleName(), rule);
    }

    public void applyRule(String rule, List<Integer> premisesIndexes, Proof proof) {
        List<Formula> premises = new ArrayList<>();
        Rule ruleInstance = ruleMap.get(rule.toUpperCase());
        for (Integer premisesIndex : premisesIndexes) {
            premises.add(proof.getFormula(premisesIndex - proof.getStartingIndex()));
        }
        Formula result = ruleInstance.applyRule(premises, proof);
        if (result != null) {
            proof.append(new ProofStep(proof.getEndingIndex() + 1, premisesIndexes, premises, result, ruleInstance));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getNumPremises(String rule) {
        Rule ruleInstance = ruleMap.get(rule.toUpperCase());
        return ruleInstance.getNumPremises();
    }

    public List<Rule> getRuleList(){ //returns list of rules, sorted by name
        return ruleMap.keySet()
                .stream().sorted()
                .map(ruleMap::get).collect(Collectors.toList());
    }
}