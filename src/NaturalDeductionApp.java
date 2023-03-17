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
            System.out.println();
        }
        System.out.print("Enter the rule you want to apply: ");
        String rule = scn.nextLine();
        Formula result = nd.applyRule(rule, premises);
        if (result != null)
            System.out.println(result);
    }
}