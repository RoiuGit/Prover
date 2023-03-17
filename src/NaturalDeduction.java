import java.util.List;

public class NaturalDeduction {
    private final IERule ie;
    private final CE1Rule ce1;
    private final CE2Rule ce2;

    NaturalDeduction() {
        this.ie = new IERule();
        this.ce1 = new CE1Rule();
        this.ce2 = new CE2Rule();
    }
    public Formula applyRule(String rule, List<Formula> premises) {
        Formula result = switch (rule) {
            case "IE" -> ie.applyRule(premises);
            case "ce1" -> ce1.applyRule(premises);
            case "ce2" -> ce2.applyRule(premises);
            default -> null;
        };
        if (result != null)
            return result;
        else {
            System.out.println("Couldn't apply rule " + rule);
            return null;
        }
    }
}
