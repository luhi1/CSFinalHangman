public class App {
    public static void main(String[] args) throws Exception {
        Host myHost = new Host("127.0.0.1", 6666);
        myHost.startGame();
        myHost.stop();
    }
}
