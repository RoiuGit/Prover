import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NaturalDeduction {
    private final Map<String, Rule> ruleMap = new HashMap<>();
    private Proof mainProof;

    NaturalDeduction() {
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

    public void setMainProof(List<Formula> premises) {
        this.mainProof = new Proof(premises);
    }

    private void putRule(Rule rule) {
        this.ruleMap.put(rule.getRuleName(), rule);
    }

    public void applyRule(String rule, List<Integer> premisesIndexes) {
        Rule ruleInstance = ruleMap.get(rule);
        List<Formula> premises = readPremises(premisesIndexes);
        Formula result = ruleInstance.applyRule(premises, mainProof);
        if (result != null) {
            mainProof.append(new ProofStep(mainProof.getEndingIndex() + 1, premisesIndexes, premises, result, ruleInstance));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void toFile(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(mainProof);
        try {
            FileWriter output = new FileWriter(filename + ".proof");
            output.write(json);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fromFile(String filename) {
        Gson gson = new Gson();
        try {
            FileReader input = new FileReader(filename + ".proof");
            mainProof = gson.fromJson(input, Proof.class);
            input.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private List<Formula> readPremises(List<Integer> indexes) {
        return indexes.stream().map(index -> mainProof.getFormula(index - mainProof.getStartingIndex())).collect(Collectors.toList());
    }

    public int getNumPremises(String rule) {
        Rule ruleInstance = ruleMap.get(rule.toUpperCase());
        return ruleInstance.getNumPremises();
    }

    public void assume(Formula assumption){
        mainProof.assume(assumption);
    }
    public boolean belongsToClosedProof(int linenumber){
        return mainProof.belongsToClosedProof(linenumber);
    }
    public String getResult(){
        return mainProof.getResult();
    }
    public List<Rule> getRuleList() { //returns list of rules, sorted by name
        return ruleMap.keySet()
                .stream().sorted()
                .map(ruleMap::get).collect(Collectors.toList());
    }
    public void displayRules() {
        getRuleList().stream().map(ruleInstance -> ruleInstance.getRuleName() + ":\n" + ruleInstance.getSchema() + "\n").forEach(System.out::println);
    }
    public void printProof(){
        System.out.println(mainProof);
    }
}