import java.util.ArrayList;
import java.util.List;

public class Proof {
    List<Formula> formulaList;

    Proof(List<Formula> formulaList){
        this.formulaList = new ArrayList<>();
        this.formulaList.addAll(formulaList);
    }



    public void append(Formula formula){
        formulaList.add(formula);
    }
    public void appendAll(List<Formula> formulas){
        formulaList.addAll(formulas);
    }
    public Formula getFormula(int index){
        return formulaList.get(index - 1);
    }

    public String getResult(int numPremises){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numPremises - 1; i++){
            result.append(formulaList.get(i).toString()).append(", ");
        }
        result.append(formulaList.get(numPremises - 1).toString()).append(" => ");
        result.append(formulaList.get(formulaList.size() - 1).toString());
        return result.toString();
    }

    @Override
    public String toString(){
        StringBuilder proof = new StringBuilder();
        for (int i = 0; i < formulaList.size(); i++){
            proof.append(i + 1).append(". ").append(formulaList.get(i).toString()).append("\n");
        }
        return proof.toString();
    }
}
