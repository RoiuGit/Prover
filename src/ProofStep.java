import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProofStep {
    private final int index;
    private final Formula formula;
    private Rule ruleApplied;
    private Map<Integer, Formula> premises;
    private boolean isPremise;
    private boolean isAssumption;

    ProofStep(int index, List<Integer> premisesIndexes, List<Formula> premises, Formula formula, Rule ruleApplied) {
        this.index = index;
        this.premises = new HashMap<>();
        for (int i = 0; i < premises.size(); i++){
            this.premises.put(premisesIndexes.get(i), premises.get(i));
        }
        this.formula = formula;
        this.ruleApplied = ruleApplied;
    }

    ProofStep(int index, Formula formula, char mode) {
        this.index = index;
        this.formula = formula;
        if (mode == 'p') isPremise = true;
        if (mode == 'a') isAssumption = true;
    }

    public static List<ProofStep> toSteps(List<Formula> formulas) {
        List<ProofStep> proofStepList = new ArrayList<>();
        for (int i = 0; i < formulas.size(); i++) {
            proofStepList.add(new ProofStep(i + 1, formulas.get(i), 'p'));
        }
        return proofStepList;
    }

    public String getAnnotation() {
        StringBuilder annotation = new StringBuilder();
        if (!isPremise && !isAssumption) {
            annotation.append(" (").append(ruleApplied.getRuleName()).append(":");
            for (int i = 0; i < premisesIndexes.size() - 1; i++) {
                annotation.append(premisesIndexes.get(i)).append(", ");
            }
            annotation.append(premisesIndexes.get(premisesIndexes.size() - 1));
            annotation.append(")");
        } else if (isPremise) annotation.append(" (premise)");
        else annotation.append(" (assumption)");
        return annotation.toString();
    }

    public Formula getFormula() {
        return formula;
    }

    public int getIndex() {
        return index;
    }
}
