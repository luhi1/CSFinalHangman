import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
    private final String[] randWords = {
		"abandoned","able","absolute","adorable","adventurous","academic","acceptable","acclaimed","accomplished",
		"accurate","aching","acidic","acrobatic","active","actual","adept","admirable","admired","adolescent",
		"adorable","adored","advanced","afraid","affectionate","aged","aggravating","aggressive","agile","agitated",
		"agonizing","agreeable","ajar","alarmed","alarming","alert","alienated","alive","all","altruistic","amazing",
		"ambitious","ample","amused","amusing","anchored","ancient","angelic","angry","anguished","animated","annual",
		"another","antique","anxious","any","apprehensive","appropriate","apt","arctic","arid","aromatic","artistic",
		"ashamed","assured","astonishing","athletic","attached","attentive","attractive","austere","authentic",
		"authorized","automatic","avaricious","average","aware","awesome","awful","awkward","babyish","bad","back",
		"baggy","bare","barren","basic","beautiful","belated","beloved","beneficial","better","best","bewitched","big",
		"bighearted","biodegradable","bitesized","bitter","black", "men"
        };
    private ServerSocket serverSocket;
    private ArrayList<String> clientIPs;
    private ArrayList<String> hostIPs;
    
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();

        }

        System.out.println("Waiting for client connections.");
        while (true) {
                Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new ServerClientHandler(clientSocket).start();
        }
    }

    private Game createGame() throws Exception{
        Game newGame;
        String secretWord = randWords[(int) (Math.random()*randWords.length)];
        newGame = new Game(secretWord);
        return newGame;
    }

    public class ServerClientHandler extends Thread{
        private Socket clientSocket;
        private PrintWriter messagesOut;
        private BufferedReader messagesIn;
        private String clientIP;

        public ServerClientHandler(Socket socket){
                this.clientSocket = socket;
                this.clientIP = clientSocket.getInetAddress().getHostAddress();
        }

        public void start(){
                try {
                        messagesOut = new PrintWriter(clientSocket.getOutputStream(), true);
                        messagesIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        messagesOut.println("Welcome to the rodeo! \n Type \"list\" to view the list of games, type \"quit\" to exit, or type \"host\" to host a game!");
                        String hostClientRequests = messagesIn.readLine();
                        while (!hostClientRequests.contains("Join: ") || !hostClientRequests.equals("quit")){
                                switch (hostClientRequests){
                                        case ("list"):
                                                clientIPs.add(clientIP);
                                                System.out.println("List of hosts you could join: WIP");
                                                //Print out list of hosts.
                                        case ("host"):
                                                hostIPs.add(clientIPs.remove(clientIPs.indexOf(clientIP)));
                                                new Host(clientSocket, 6666, createGame());
                                                
                                                break;
                                }
                        }
                        clientIPs.remove(clientIP);
                        clientSocket.close();
                        messagesIn.close();
                        messagesOut.close();        
                } catch (Exception e) {
                        e.printStackTrace();
                        return;
                }
        }
    }
}
    /*public Server(){
            clientIPs = new ArrayList<String>();
            System.out.println("Server object successfully created.");
    }

    public void startClientConnection(int port){
        try {
                serverSocket = new ServerSocket(port);
                clientSocket = serverSocket.accept();

                messagesOut = new PrintWriter(clientSocket.getOutputStream(), true);
                messagesIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String hostClientRequests = messagesIn.readLine();
                if (hostClientRequests.contains("User")){
                        clientIPs.add(hostClientRequests.replace("User: ", ""));
                        messagesOut.println("Welcome to the rodeo! \n Type \"list\" to view the list of games, or type quit to exit!");
                        while (true){
                                pushGameList();
                        }
                }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    private void pushGameList(){
        try {
                messagesOut = new PrintWriter(clientSocket.getOutputStream(), true);
                messagesIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String hostClientRequests = messagesIn.readLine();
                if (hostClientRequests.equals("list")){
                        messagesOut.println("Here's the list bro.");
                }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    public void startHostGame(int port){
            try {
                    serverSocket = new ServerSocket(port);
                    clientSocket = serverSocket.accept();

                    messagesOut = new PrintWriter(clientSocket.getOutputStream(), true);
                    messagesIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    gameOut = new ObjectOutputStream(clientSocket.getOutputStream());
                    gameIn = new ObjectInputStream(clientSocket.getInputStream());
                    String hostClientRequests = messagesIn.readLine();

                    if ("newGame".equals(hostClientRequests)){
                        gameOut.writeObject(createGame());;
                    } 
                    //Works just like the scanner class!
            } catch (Exception e) {
                    e.printStackTrace();
            }
    }

    public void stopMessages(){
            try {
                    messagesIn.close();
                    messagesOut.close();
                    clientSocket.close();
                    serverSocket.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }

    private Game createGame() throws Exception{
        Game newGame;
        String secretWord = randWords[(int) (Math.random()*randWords.length)];
        newGame = new Game(secretWord);
        return newGame;
    } */