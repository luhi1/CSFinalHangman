public class ServerApp {
        
        public static void main(String[] args) {
                try {
                        Server myServer = new Server(6666);
                        myServer.initalizeSpawner();
                        myServer.initalizeServer();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
