import java.util.ArrayList;
import java.util.List;

public class Proof {
    private final int assumptionDepth;
    private final int startingIndex;
    List<ProofStep> proofSteps;
    List<Formula> premises;
    final List<Proof> subProofList = new ArrayList<>();
    Proof subProof;
    private int endingIndex;
    private boolean isClosed;

    Proof(List<Formula> proofSteps) {
        premises = new ArrayList<>();
        premises.addAll(proofSteps);
        this.proofSteps = new ArrayList<>();
        this.proofSteps = toSteps(proofSteps);
        this.startingIndex = 1;
        endingIndex = 1 + proofSteps.size() - 1;
        this.assumptionDepth = 0;
        isClosed = false;
    }

    Proof(int assumptionDepth, int startingIndex) {
        proofSteps = new ArrayList<>();
        this.startingIndex = startingIndex;
        endingIndex = startingIndex - 1;
        this.assumptionDepth = assumptionDepth;
        isClosed = false;
    }

    public void append(ProofStep proofStep) {
        proofSteps.add(proofStep);
        endingIndex++;
        if (subProof != null && subProof.isNotClosed()) {
            subProof.append(proofStep);
        }
    }

    public Formula getFormula(int index) {
        return proofSteps.get(index).getFormula();
    }

    public void assume(Formula assumption) {
        addSubProof();
        append(new ProofStep(endingIndex + 1, assumption, 'a'));
    }

    public String getResult() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < premises.size() - 1; i++) {
            result.append(premises.get(i).toString()).append(", ");
        }
        if (!premises.isEmpty()) result.append(premises.get(premises.size() - 1).toString()).append(" ");
        result.append("=> ");
        result.append(proofSteps.get(proofSteps.size() - 1).getFormula());
        return result.toString();
    }

    private int getLineAssumptionDepth(int lineNumber) {
        int depth = 0;
        for (Proof sp : subProofList) {
            if (lineNumber >= sp.startingIndex && lineNumber <= sp.endingIndex) {
                depth++;
                depth += sp.getLineAssumptionDepth(lineNumber);
            }
        }
        return depth;
    }

    public int getAssumptionDepth() {
        return assumptionDepth;
    }

    public boolean belongsToClosedProof(int lineNumber) {
        for (Proof subProof : subProofList) {
            if (lineNumber >= subProof.getStartingIndex() && lineNumber <= subProof.getEndingIndex()) {
                if (subProof.isClosed) {
                    return true;
                } else {
                    return subProof.belongsToClosedProof(lineNumber);
                }
            }
        }
        return false;
    }

    public Proof getSubProof() {
        return subProof;
    }

    public void addSubProof() {
        if (subProof != null && subProof.isNotClosed()) subProof.addSubProof();
        else {
            subProof = new Proof(assumptionDepth + 1, endingIndex + 1);
            subProofList.add(subProof);
        }
    }

    public void close() {
        isClosed = true;
    }

    public boolean isNotClosed() {
        return !isClosed;
    }

    public int getStartingIndex() {
        return startingIndex;
    }

    public int getEndingIndex() {
        return endingIndex;
    }

    public int size() {
        return proofSteps.size();
    }

    private List<ProofStep> toSteps(List<Formula> formulas){
        List<ProofStep> proofStepList = new ArrayList<>();
        for (int i = 0; i < formulas.size(); i++){
            proofStepList.add(new ProofStep(i + 1, formulas.get(i), 'p'));
        }
        return proofStepList;
    }
    public String toString() {
        StringBuilder proof = new StringBuilder();
        int lineNumber = 1;
        for (ProofStep proofStep : proofSteps) {
            proof.append(lineNumber).append(".");
            int depth = getLineAssumptionDepth(lineNumber);
            proof.append("|".repeat(Math.max(0, depth)));
            proof.append(" ");
            proof.append(proofStep.getFormula()).append(proofStep.getAnnotation()).append("\n");
            lineNumber++;
        }

        return proof.toString();
    }
}
