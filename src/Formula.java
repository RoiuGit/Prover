import java.util.Objects;

public class Formula {
    private final String expression;
    private String sign;
    private Formula antecedent;
    private Formula consequent;
    private int signIndex;
    private boolean isAtomicFormula;

    Formula(String expression) {
        this.expression = expression;
        sign = "";
        evaluate(normalize(expression));
        if (Objects.equals(sign, "") || Objects.equals(sign, "~")) {
            isAtomicFormula = true;
        }
        if (Objects.equals(sign, "")) {
            signIndex = 0;
            antecedent = this;
        }
        if (Objects.equals(sign, "~")) {
            signIndex = 0;
            antecedent = new Formula(expression.substring(1));
        }
    }

    private static String normalize(String expression) {
        expression = expression.toUpperCase().replaceAll("\\s+","");
        expression = removeExcessParentheses(expression);
        return expression;
    }

    private static String removeExcessParentheses(String expression) {
        //To-do: implement
        return expression;
    }

    private void evaluate(String expression) {
        int openParens = -1;
        for (int i = 0; i < expression.length(); i++) {

            switch (expression.charAt(i)) {
                case '(' -> openParens++;
                case ')' -> openParens--;
                case '>' -> {
                    if (openParens == 0 && Objects.equals(sign, "")) {
                        sign = "->";
                        signIndex = i;
                        antecedent = new Formula(expression.substring(1, signIndex - 1));
                        consequent = new Formula(expression.substring(signIndex + 1, expression.length() - 1));
                    }
                }
                case '&' -> {
                    if (openParens == 0 && Objects.equals(sign, "")) {
                        sign = "&";
                        signIndex = i;
                        antecedent = new Formula(expression.substring(1, signIndex));
                        consequent = new Formula(expression.substring(signIndex + 1, expression.length() - 1));
                    }
                }
                case 'v' -> {
                    if (openParens == 0 && Objects.equals(sign, "")) {
                        sign = "v";
                        signIndex = i;
                        antecedent = new Formula(expression.substring(1, signIndex));
                        consequent = new Formula(expression.substring(signIndex + 1, expression.length() - 1));
                    }
                }
                case '~' -> {
                    if (openParens < 1 && Objects.equals(sign, "")) {
                        sign = "~";
                    }
                }
            }
        }
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Formula formula = (Formula) o;

        return Objects.equals(expression, formula.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sign, antecedent, consequent);
    }

    @Override
    public String toString() {
        if (isAtomicFormula) return expression;
        else return "(" + antecedent + sign + consequent + ")";
    }
}
