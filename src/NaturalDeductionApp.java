import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class NaturalDeductionApp {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        NaturalDeduction nd = new NaturalDeduction();
        nd.getRuleList().stream()
                .map(ruleInstance -> ruleInstance.getRuleName() + ":\n" + ruleInstance.getSchema() + "\n")
                .forEach(System.out::println);
        List<Formula> premises = new ArrayList<>();
        int numPremises;
        while (true) {
            try {
                // Prompt user to enter number of initial premises.
                System.out.print("Enter number of premises: ");
                numPremises = Integer.parseInt(scn.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
        //
        for (int i = 0; i < numPremises; i++) {
            try {
                // Prompt user to enter a premise numPremises-number of times.
                System.out.print("Enter premise " + (i + 1) + ": ");
                premises.add(new Formula(scn.nextLine()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid formula. Please try again.");
            }
        }
        Proof proof = new Proof(premises);
        String rule;
        List<Integer> premisesIndexes = new ArrayList<>();
        while (true) { // Main loop.
            premisesIndexes.clear();
            System.out.println(proof);
            System.out.print("Enter the rule you want to apply, enter \"ASSUME\" to add an assumption, or enter \"END\" to end proof: ");
            rule = scn.nextLine();
            // If user finishes proof, print the proof again and print the result ([premises list] => last formula)
            if (rule.equalsIgnoreCase("END")) {
                System.out.println(proof);
                String result = proof.getResult();
                if (result != null) {
                    System.out.println(result);
                    break;
                }
                else {
                    System.out.println("Could not end the result. All of the subproofs must be closed.");
                    continue;
                }
            }

            if (rule.equalsIgnoreCase("ASSUME")) {
                System.out.print("Enter the assumption: ");
                proof.assume(new Formula(scn.nextLine()));
                continue;
            }
            int numPremisesForRule;
            try {
                numPremisesForRule = nd.getNumPremises(rule);
            } catch (Exception e) {
                System.out.println("Invalid rule or input. Please try again.");
                continue;
            }
            for (int i = 0; i < numPremisesForRule; i++) {
                System.out.print("Enter the index of premise " + (i + 1) + ": ");
                int lineIndex;
                try {
                    lineIndex = Integer.parseInt(scn.nextLine());
                    if (!proof.belongsToClosedProof(lineIndex)) premisesIndexes.add(lineIndex);
                    else {
                        System.out.println("Can't use formula in a closed proof.");
                        i--;
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Must enter a valid number.");
                    i--;
                }
            }
            try {
                nd.applyRule(rule, premisesIndexes, proof);
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to apply rule " + rule + ". Please try again.");
            }
        }
    }
}