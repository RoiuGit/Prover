import java.util.*;

class Formula{
    private String sign;
    private String antecedent;
    private String consequent;
    private int signIndex;

    Formula (String expression){
        sign = "";
        antecedent = expression;
        consequent = "";
        signIndex = expression.length() + 1;
        evaluate(normalize(expression));
    }

    private void evaluate(String expression){
        int lbrackets = 0;
        int rbrackets = 1;
        for (int i = 0; i < expression.length(); i++){

            switch (expression.charAt(i)) {
                case '(' -> lbrackets++;
                case ')' -> rbrackets++;
                case '>' -> {
                    if (lbrackets == rbrackets) {
                        sign = "->";
                        signIndex = i;
                        antecedent = expression.substring(0, signIndex - 1);
                        consequent = expression.substring(signIndex + 1);
                    }
                }
                case '&' -> {
                    if (lbrackets == rbrackets) {
                        sign = "&";
                        signIndex = i;
                        antecedent = expression.substring(0, signIndex);
                        consequent = expression.substring(signIndex + 1);
                    }
                }
            }
        }
    }

    private static String normalize(String expression){
        expression = expression.toUpperCase();
        expression = removeExcessParentheses(expression);
        return expression;
    }

    private static Formula normalize(Formula formula){
        String expression = formula.toString();
        expression = expression.toUpperCase();
        expression = removeExcessParentheses(expression);
        formula = new Formula(expression);
        return formula;
    }

    public static String removeExcessParentheses(String expression) {
        StringBuilder output = new StringBuilder();
        int nestedParens = 0;
        for (int i = 0; i < expression.length(); i++){
            char currentChar = expression.charAt(i);
            if (currentChar == '('){
                nestedParens++;
                output.append("(");
            }
            else if (isOperator(currentChar)){
                output.append(currentChar);
                nestedParens--;
            } else if (currentChar == ')'){
                boolean nestedParensExist = nestedParens > 0;
                boolean nextCharIsClosingParen = ((i + 1) < expression.length()) && (expression.charAt(i + 1) == ')');
                if (nestedParensExist && nextCharIsClosingParen) {
                    output.deleteCharAt(output.lastIndexOf("("));
                    nestedParens--;
                }
                else {
                    output.append(")");
                }
            }
            else {
                output.append(currentChar);
            }
        }
        System.out.println(output);
        return output.toString();
    }

    private static boolean isOperator(char o){
        return o == '&' || o == '>';
    }
    public String getSign() {
        return sign;
    }

    public Formula getAntecedent() {
        return new Formula(antecedent);
    }

    public Formula getConsequent() {
        return new Formula(consequent);
    }
    public int getSignIndex() {
        return signIndex;
    }

    @Override
    public String toString(){
        return antecedent + sign + consequent;
    }
}


class NaturalDeduction{
    public Formula applyRule(String rule, List<Formula> premises){
        if (rule.equals("MP")) {
            return premises.get(0).getConsequent();
        }
        return null;
    }
}

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
        System.out.println(nd.applyRule(rule, premises).toString());
    }
}