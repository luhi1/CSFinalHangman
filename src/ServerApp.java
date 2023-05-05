public class ServerApp {
        
        public static void main(String[] args) {
                Server myServer = new Server();
                myServer.startHostGame(6666);
                myServer.stop();
        }
}
