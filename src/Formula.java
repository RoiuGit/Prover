import java.util.Objects;

public class Formula {
    private String sign;
    private Formula antecedent;
    private Formula consequent;
    private int signIndex;
    private boolean isAtomicFormula;
    private final String expression;

    Formula(String expression) {
        this.expression = expression;
        sign = "";
        evaluate(normalize(expression));
        if (Objects.equals(sign, "") || Objects.equals(sign, "~")){
            isAtomicFormula = true;
        }
        if (isAtomicFormula){
            signIndex = 0;
            antecedent = this;
        }
    }

    private void evaluate(String expression) {
        int openParens = -1;
        for (int i = 0; i < expression.length(); i++) {

            switch (expression.charAt(i)) {
                case '(' -> openParens++;
                case ')' -> openParens--;
                case '>' -> {
                    if (openParens == 0) {
                        sign = "->";
                        signIndex = i;
                        antecedent = new Formula(expression.substring(1, signIndex - 1));
                        consequent = new Formula(expression.substring(signIndex + 1, expression.length() - 1));
                    }
                }
                case '&' -> {
                    if (openParens == 0) {
                        sign = "&";
                        signIndex = i;
                        antecedent = new Formula(expression.substring(1, signIndex));
                        consequent = new Formula(expression.substring(signIndex + 1, expression.length() - 1));
                    }
                }
            }
        }
    }

    private static String normalize(String expression) {
        expression = expression.toUpperCase();
        expression = removeExcessParentheses(expression);
        return expression;
    }

    private static String removeExcessParentheses(String expression) {
    //To-do: implement
    return expression;
    }


    private static boolean isOperator(char o) {
        return o == '&' || o == '>';
    }

    public String getSign() {
        return sign;
    }

    public Formula getAntecedent() {
        return antecedent;
    }

    public Formula getConsequent() {
        return consequent;
    }

    @Override
    public String toString() {
        if (isAtomicFormula)
            return expression;
        else return "(" + antecedent + sign + consequent + ")";
    }
}
