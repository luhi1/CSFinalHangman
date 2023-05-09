import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Host{
        private Socket hostServerSocket;
        private ServerSocket hostingSocket;
        private Game myGame;

        public Host(Socket socket, int port, Game game){
                this.hostServerSocket = socket;
                PrintWriter beginGameInput;
                try {
                        beginGameInput = new PrintWriter(hostServerSocket.getOutputStream(), true);
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                this.myGame = game;
                try {
                        hostingSocket = new ServerSocket(port);
                        hostingSocket.setSoTimeout(10000);
                    } catch (IOException e) {
                        e.printStackTrace();
            
                    }

                    System.out.println("Type \"beginGame\" to start game (may take upwards of 10 seconds to start, be patient!)");
                    while (!beginGameInput.equals("beginGame")) {
                        Socket clientSocket = null;
                        try {
                            clientSocket = hostingSocket.accept();
                        } catch (IOException e) {
                            System.out.println("I/O error: " + e);
                        }
                        // new thread for a client
                        new HostClientHandler(clientSocket).start();
                    }

                    //Start the game, get all the clients in there too.

        }

        public class HostClientHandler extends Thread{
                private Socket clientSocket;
                private PrintWriter messagesOut;
                private BufferedReader messagesIn;
        
                public HostClientHandler(Socket socket){
                        this.clientSocket = socket;
                }
        
                public void run(){
                        try {
                                messagesOut = new PrintWriter(clientSocket.getOutputStream(), true);
                                messagesIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
                                String hostClientRequests = messagesIn.readLine();
                                while (!hostClientRequests.equals("beginGame")){
                                        messagesOut.println("Waiting for game to begin.");     
                                }

                                //Now get them into the game.
                                clientSocket.close();
                                messagesIn.close();
                                messagesOut.close();
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
            }
}

        /*
    private Socket clientSocket;
    private ObjectOutputStream gameOut;
    private ObjectInputStream gameIn;
    private PrintWriter messagesOut;
    private BufferedReader messagesIn;
    
    public Host(){
        this.guess = "";
    }
    
    public Host(String serverIP, int serverPort){
        this.guess = "";
        connect(serverIP, serverPort);
    }
    
    //Important, IP's are strings!
    public void connect(String ip, int port){
            try {
                    clientSocket = new Socket(ip, port);
                    gameOut = new ObjectOutputStream(clientSocket.getOutputStream());
                    gameIn = new ObjectInputStream(clientSocket.getInputStream());

                    messagesOut = new PrintWriter(clientSocket.getOutputStream(), true);
                    messagesIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                    e.printStackTrace();
            }

    }

    public void startGame(){
            messagesOut.println("newGame");
            //Println = send the message, scanner reading is just like user input!
            Game resp;
            try {
                    resp = (Game) gameIn.readObject();
                    this.currentGame(resp);
            } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
    }

    public void stop(){
        try {
                gameIn.close();
                gameOut.close();
                messagesIn.close();
                messagesOut.close();
                clientSocket.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
}

    public void currentGame(Game g) throws Exception{
        if (this.guess.equals("home")){
            g.clearScreen();
            return;
        }
        g.displayScreen();

        Scanner guessReader = new Scanner(System.in);
        System.out.println("Type here: ");
        this.guess = guessReader.nextLine();

        g.checkGuess(guess);
        currentGame(g);
        
        guessReader.close();
    }
     */
