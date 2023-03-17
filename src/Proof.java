import java.util.ArrayList;
import java.util.List;

public class Proof {
    List<Formula> formulaList;
    private final int assumptionDepth;
    List<Formula> premises;
    private final int startingIndex;
    private int endingIndex;
    private boolean isClosed;
    List<Proof> subProofList = new ArrayList<>();
    Proof subProof;

    Proof(List<Formula> formulaList, int assumptionDepth, int startingIndex){
        premises = new ArrayList<>();
        premises.addAll(formulaList);
        this.formulaList = new ArrayList<>();
        this.formulaList.addAll(formulaList);
        this.startingIndex = startingIndex;
        endingIndex = startingIndex + formulaList.size() - 1;
        this.assumptionDepth = assumptionDepth;
        isClosed = false;
    }
    Proof(int assumptionDepth, int startingIndex){
        formulaList = new ArrayList<>();
        this.startingIndex = startingIndex;
        endingIndex = startingIndex - 1;
        this.assumptionDepth = assumptionDepth;
        isClosed = false;
    }
    public void append(Formula formula){
        formulaList.add(formula);
        endingIndex++;
        if (subProof != null && subProof.isNotClosed()){
            subProof.append(formula);
        }
    }
    public Formula getFormula(int index){
        return formulaList.get(index);
    }

    public void assume(Formula assumption){
        setSubProof();
        append(assumption);
    }

    public String getResult(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < premises.size() - 1; i++){
            result.append(premises.get(i).toString()).append(", ");
        }
        if (!premises.isEmpty()) result.append(premises.get(premises.size() - 1).toString()).append(" ");
        result.append("=> ");
        result.append(formulaList.get(formulaList.size() - 1).toString());
        return result.toString();
    }

    protected int getLineAssumptionDepth(int lineNumber) {
        int depth = 0;
        for (Proof sp : subProofList) {
            if (lineNumber >= sp.startingIndex && lineNumber <= sp.endingIndex) {
                depth++;
                depth += sp.getLineAssumptionDepth(lineNumber);
            }
        }
        return depth;
    }

    public int getAssumptionDepth(){
        return assumptionDepth;
    }
    public boolean isEmpty(){
        return formulaList.isEmpty();
    }

    public Proof getSubProof(){
        return subProof;
    }
    public void setSubProof(){
        if (subProof != null && subProof.isNotClosed()) subProof.setSubProof();
        else {
            subProof = new Proof(assumptionDepth + 1, endingIndex + 1);
            subProofList.add(subProof);
        }
    }
    public void closeSubProof(){
        isClosed = true;
    }

    public boolean isNotClosed(){
        return !isClosed;
    }
    public int getStartingIndex(){
        return startingIndex;
    }
    public int getEndingIndex(){
        return endingIndex;
    }
    public int size(){ return formulaList.size();}

    public String toString() {
        StringBuilder proof = new StringBuilder();
        int lineNumber = 1;
        for (Formula formula : formulaList) {
            proof.append(lineNumber).append(".");
            int depth = getLineAssumptionDepth(lineNumber);
            proof.append("|".repeat(Math.max(0, depth)));
            proof.append(" ");
            proof.append(formula).append("\n");
            lineNumber++;
        }

        return proof.toString();
    }
}
