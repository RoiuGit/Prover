import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class NaturalDeductionApp {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        NaturalDeduction nd = new NaturalDeduction();
        List<Formula> premises = new ArrayList<>();
        int numPremises;
        while (true) {
            try {
                System.out.print("Enter number of premises: ");
                numPremises = Integer.parseInt(scn.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
        for (int i = 0; i < numPremises; i++) {
            try {
                System.out.print("Enter premise " + (i + 1) + ": ");
                premises.add(new Formula(scn.nextLine()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid formula. Please try again.");
            }
        }
        Proof proof = new Proof(premises);
        String rule;
        List<Integer> premisesIndexes = new ArrayList<>();
        while (true) {
            premisesIndexes.clear();
            System.out.println(proof);
            System.out.print("Enter the rule you want to apply, enter \"ASSUME\" to add an assumption, or enter \"END\" to end proof: ");
            rule = scn.nextLine();
            if (rule.equalsIgnoreCase("END")) {
                System.out.println(proof);
                System.out.println(proof.getResult());
                break;
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
                    if (!proof.belongsToClosedProof(lineIndex))
                        premisesIndexes.add(lineIndex);
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