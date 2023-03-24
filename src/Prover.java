import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Prover implements Runnable {

    private final Scanner scn = new Scanner(System.in);
    private final NaturalDeduction nd = new NaturalDeduction();

    private List<Formula> promptForPremises() {
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
                i--;
            }
        }
        return premises;
    }

    private void promptForAssumption(){
        while (true) {
            System.out.print("Enter the assumption: ");
            try {
                nd.assume(new Formula(scn.nextLine()));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid formula. Please try again.");
            }
        }
    }
    private void promptForSave(){
        while (true) {
            System.out.print("Enter the filename: ");
            try {
                nd.toFile(scn.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Error creating the proof file. Try again.");
            }
        }
    }
    private void endProofSession(){
        nd.printProof();
        String result = nd.getResult();
        if (result != null) {
            System.out.println(result);
            while (true) {
                System.out.print("Save the proof? (Y/N): ");
                String input = scn.nextLine();
                if (input.equalsIgnoreCase("Y")) promptForSave();
                else if (input.equalsIgnoreCase("N")) break;
            }
        } else {
            System.out.println("Could not end the proof. All of the subproofs must be closed.");
        }
    }
    private String promptForRule(){
        System.out.print("""
                    Enter the rule you want to apply,
                    -or enter "ASSUME" to add an assumption,
                    -or enter "END" to end proof,
                    -or enter "SAVE" to save the proof:
                    """);
        return scn.nextLine().toUpperCase();
    }
    private void applyRule(String rule){
        List<Integer> premisesIndexes = new ArrayList<>();
        int numPremisesForRule;
        try {
            numPremisesForRule = nd.getNumPremises(rule);
        } catch (Exception e) {
            System.out.println("Invalid rule or input. Please try again.");
            return;
        }
        for (int i = 0; i < numPremisesForRule; i++) {
            System.out.print("Enter the index of premise " + (i + 1) + ": ");
            int lineIndex;
            try {
                lineIndex = Integer.parseInt(scn.nextLine());
                if (!nd.belongsToClosedProof(lineIndex)) premisesIndexes.add(lineIndex);
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
            nd.applyRule(rule, premisesIndexes);
        } catch (Exception e) {
            System.out.println("Failed to apply rule " + rule + ". Please try again.");
        }
    }
    private void runProofSession() {
        String rule;
        mainLoop:
        while (true) { // Main loop.
            nd.printProof();
            rule = promptForRule();
            switch (rule) {
                case "END":
                    endProofSession();
                    break mainLoop;
                case "ASSUME":
                    promptForAssumption();
                    continue;
                case "SAVE":
                    promptForSave();
                    continue;
                default:
                    applyRule(rule);
            }
        }
    }

    public void run() {
        nd.displayRules();
        nd.setMainProof(promptForPremises());
        runProofSession();
    }
}