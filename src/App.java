public class App {
    public static void main(String[] args) {
        Thread t = new Thread(new Prover());
        t.start();
    }
}
