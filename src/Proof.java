import java.util.ArrayList;
import java.util.List;

public class Proof {
    List<Formula> formulaList;
    protected int assumptionDepth;
    final protected int numPremises;
    protected int startingIndex;
    protected int endingIndex;
    protected boolean isClosed;
    List<SubProof> subProofList = new ArrayList<>();

    SubProof subProof;

    Proof(List<Formula> formulaList){
        numPremises = formulaList.size();
        this.formulaList = new ArrayList<>();
        this.formulaList.addAll(formulaList);
        startingIndex = 1;
        endingIndex = formulaList.size();
        assumptionDepth = 0;
        isClosed = false;
    }

    public Proof() {
        formulaList = new ArrayList<>();
        startingIndex = 0;
        endingIndex = 0;
        numPremises = 0;
        assumptionDepth = 0;
        isClosed = false;
    }


    public void append(Formula formula){
        formulaList.add(formula);
        endingIndex++;
        if (subProof != null && !subProof.isClosed()){
            subProof.append(formula);
        }
    }
    public Formula getFormula(int index){
        return formulaList.get(index);
    }

    public void assume(Formula assumption){
        setSubProof(assumption);
        append(assumption);
    }

    public String getResult(int numPremises){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numPremises - 1; i++){
            result.append(formulaList.get(i).toString()).append(", ");
        }
        if (numPremises > 0) result.append(formulaList.get(numPremises - 1).toString()).append(" ");
        result.append("=> ");
        result.append(formulaList.get(formulaList.size() - 1).toString());
        return result.toString();
    }

    protected int getLineAssumptionDepth(int lineNumber) {
        int depth = 0;
        for (SubProof sp : subProofList) {
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

    public SubProof getSubProof(){
        return subProof;
    }
    public void setSubProof(Formula assumption){
        if (subProof != null && !subProof.isClosed()) subProof.setSubProof(assumption);
        else {
            subProof = new SubProof(endingIndex + 1, assumption, assumptionDepth + 1);
            subProofList.add(subProof);
        }
    }
    public void closeSubProof(){
        isClosed = true;
    }

    public boolean isClosed(){
        return isClosed;
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
