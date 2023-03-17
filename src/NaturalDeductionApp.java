import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class NaturalDeductionApp {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        NaturalDeduction nd = new NaturalDeduction();
        List<Formula> premises = new ArrayList<>();
        System.out.print("Enter number of premises: ");
        int numPremises = Integer.parseInt(scn.nextLine());

        for (int i = 0; i < numPremises; i++){
            System.out.print("Enter premise " + (i + 1) + ": ");
            premises.add(new Formula(scn.nextLine()));
        }
        Proof proof = new Proof(premises, 0, 1);
        Formula result;
        String rule;
        while(true) {
            premises.clear();
            System.out.println(proof);
            System.out.print("Enter the rule you want to apply, enter \"ASSUME\" to add an assumption, or enter \"END\" to end proof: ");
            rule = scn.nextLine();

            if (rule.equalsIgnoreCase("END")){
                System.out.println(proof);
                System.out.println(proof.getResult());
                break;
            }

            if (rule.equalsIgnoreCase("ASSUME")){
                System.out.print("Enter the assumption: ");
                proof.assume(new Formula(scn.nextLine()));
                continue;
            }
            for (int i = 0; i < nd.getNumPremises(rule); i++) {
                System.out.print("Enter the index of premise " + (i + 1) + ": ");
                premises.add(proof.getFormula(Integer.parseInt(scn.nextLine()) - proof.getStartingIndex()));
            }

            result = nd.applyRule(rule, premises, proof);
            proof.append(result);
        }
    }
}