import java.util.ArrayList;
import java.util.List;
public class ProofStep {
    private final int index;
    private List<Integer> premisesIndexes;
    private final Formula formula;
    private Rule ruleApplied;
    private boolean isPremise;
    private boolean isAssumption;
    ProofStep(int index, List<Integer> premisesIndexes, Formula formula, Rule ruleApplied){
        this.index = index;
        this.premisesIndexes = new ArrayList<>();
        this.premisesIndexes.addAll(premisesIndexes);
        this.formula = formula;
        this.ruleApplied = ruleApplied;
    }
    ProofStep(int index, Formula formula, char mode){
        this.index = index;
        this.formula = formula;
        if (mode == 'p') isPremise = true;
        if (mode == 'a') isAssumption = true;
    }
    public String getAnnotation(){
        StringBuilder annotation = new StringBuilder();
        if (!isPremise && !isAssumption) {
            annotation.append(" (").append(ruleApplied.getRuleName()).append(":");
            for (int i = 0; i < premisesIndexes.size() - 1; i++) {
                annotation.append(premisesIndexes.get(i)).append(", ");
            }
            annotation.append(premisesIndexes.get(premisesIndexes.size() - 1));
            annotation.append(")");
        }
        else if (isPremise) annotation.append(" (premise)");
        else annotation.append(" (assumption)");
        return annotation.toString();
    }

    public Formula getFormula(){
        return formula;
    }
}
