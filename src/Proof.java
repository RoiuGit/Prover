import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Proof {
    final List<Proof> subProofList = new ArrayList<>();
    private final int assumptionDepth;
    private final int startingIndex;
    List<ProofStep> proofSteps;
    List<Formula> premises;
    Proof subProof;
    private int endingIndex;
    private boolean isClosed;

    Proof(List<Formula> proofSteps) {
        premises = new ArrayList<>();
        premises.addAll(proofSteps);
        this.proofSteps = new ArrayList<>();
        this.proofSteps = ProofStep.toSteps(proofSteps);
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
        if (subProof != null && subProof.isNotClosed()) return null;
        StringBuilder result = new StringBuilder();
        String premisesStr = premises.stream()
                .map(Formula::toString)
                .collect(Collectors.joining(", "));
        result.append(premisesStr);
        result.append(" => ");
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

    public String toString() {
        StringBuilder proof = new StringBuilder();
        for (ProofStep proofStep : proofSteps) {
            proof.append(proofStep.getIndex()).append(".");
            int depth = getLineAssumptionDepth(proofStep.getIndex());
            proof.append("|".repeat(Math.max(0, depth)));
            proof.append(" ");
            proof.append(proofStep.getFormula()).append(proofStep.getAnnotation()).append("\n");
        }

        return proof.toString();
    }
}
