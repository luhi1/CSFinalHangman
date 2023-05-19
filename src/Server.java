import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    private final String[] randWords = {
		"abandoned"
        };
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream gameOut;
    private ObjectInputStream gameIn;
    private PrintWriter messagesOut;
    private BufferedReader messagesIn;
    private String secretWord;
    private ArrayList<String> leaderboardIPs;
    private ArrayList<Integer> leaderboardScores;
    //This could be a hashmap or dictionary, but I want the arraylist point :)
    
    public Server(int port) throws Exception{
            System.out.println("Server object successfully created.");
            serverSocket = new ServerSocket(port);
            clientSocket = null;
            gameOut = null;
            gameIn = null;
            messagesIn = null;
            messagesOut = null;
            this.secretWord = "alzheimers";
            leaderboardIPs = new ArrayList<String>();
            leaderboardScores = new ArrayList<Integer>();
            System.out.println("All instance variables initialized");
    }

    public void initalizeSpawner() throws Exception{
        GameSpawner spawner = new GameSpawner();

        new Thread(spawner).start();
    }

    public void initalizeServer() throws IOException{
        while (true){
                System.out.println("Waiting for a connection.");
                clientSocket = serverSocket.accept();
                System.out.println("Made a connection.");
                
                ServerHostHandler clientHandler = new ServerHostHandler();
                new Thread(clientHandler).start();
                System.out.println("Handler Dispatched.");
                System.out.println("");
        }
    }

    private class ServerHostHandler extends Thread{
        public ServerHostHandler() throws IOException{
                OutputStream outputStream = clientSocket.getOutputStream();
                InputStream InputStream = clientSocket.getInputStream();
                gameOut = new ObjectOutputStream(outputStream);
                gameIn = new ObjectInputStream(InputStream);

                messagesOut = new PrintWriter(outputStream, true);
                messagesIn = new BufferedReader(new InputStreamReader(InputStream));
        }

        public void run(){
                String hostClientRequests = null;
                try {
                        do {

                                while (hostClientRequests == null){
                                        hostClientRequests = messagesIn.readLine();
                                        System.out.println(hostClientRequests);
                                        continue;
                                }
                                //Works just like the scanner class!

                                switch (hostClientRequests){
                                        case ("newGame"):
                                                gameOut.writeObject(new Game(secretWord));
                                                break;
                                        case ("leaderboard"):
                                                if (leaderboardScores.size() > 0){
                                                        Thread.sleep(10);
                                                        for (int i = 0; i < leaderboardIPs.size(); i++){
                                                                messagesOut.println(leaderboardIPs.get(i)+":"+ Integer.toString(leaderboardScores.get(i)));
                                                        }
                                                        messagesOut.println("End Leaderboard");
                                                } else {
                                                        messagesOut.println("Wow nobody likes my game :(");
                                                }
                                }
                                
                                if (hostClientRequests.contains(":")){
                                        String[] hostNameAndScore = hostClientRequests.split(":");
                                        if (leaderboardIPs.contains(hostNameAndScore[0])){
                                                int funnyIndex = leaderboardIPs.indexOf(hostNameAndScore[0]);
                                                leaderboardIPs.remove(funnyIndex);
                                                leaderboardScores.remove(funnyIndex);
                                        }
                                        leaderboardIPs.add(hostNameAndScore[0]);
                                        leaderboardScores.add(Integer.parseInt(hostNameAndScore[1]));
                                        int originalIndex = leaderboardScores.indexOf(Integer.parseInt(hostNameAndScore[1]));
                                        for (int i = 1; i < leaderboardScores.size(); ++i) {
                                                int currentValue = leaderboardScores.get(i);
                                                int previous = i - 1;

                                                while (previous >= 0 && leaderboardScores.get(previous) > currentValue) {
                                                        leaderboardScores.set(previous+1, leaderboardScores.get(previous));
                                                        previous--;
                                                }
                                                leaderboardScores.set(previous + 1,  currentValue);
                                        }
                                        leaderboardIPs.add(leaderboardScores.indexOf(Integer.parseInt(hostNameAndScore[1])), leaderboardIPs.get(originalIndex));
                                        leaderboardIPs.remove(originalIndex);
                                        messagesOut.println("oneMore");
                                }
                                hostClientRequests = null;
                                messagesOut.flush();
                
                        } while (hostClientRequests == null || !hostClientRequests.equals("quit"));
                } catch (Exception e) {
                        e.printStackTrace();
                        closeInputsAndOutputs();
                }
        }

        
        public void closeInputsAndOutputs(){
                try {
                        messagesIn.close();
                        messagesOut.close();
                        clientSocket.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
    }

    private class GameSpawner extends Thread{
        public GameSpawner() throws Exception{
                System.out.println("Spawner set up.");
        }

        public void run(){
                try {
                        while (true){
                                secretWord = randWords[(int) (Math.random()*randWords.length)];
                                Thread.sleep(3000);
                        }

                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
    }
}