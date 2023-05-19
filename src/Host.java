import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Host{
    private String guess;
    private Socket clientSocket;

private ObjectOutputStream gameOut;
    private ObjectInputStream gameIn;
    private PrintWriter messagesOut;
    private BufferedReader messagesIn;
    private int chain;
    
        public int getChain() {
                return chain;
        }

        public void getWinOrLoss(){

        }
        public void resetChain() {
                this.chain = 0;
        }

        public void incrementChain() {
                this.chain++;
        }

        public void sendMessage(String m){
                messagesOut.println(m);
        }

        public BufferedReader getInputStream(){
                return messagesIn;
        }

        public Socket getClientSocket() {
                return clientSocket;
        }

public Host(){
        this.guess = "";
    }
    
    public Host(String serverIP, int serverPort){
        this.guess = "";
        chain = 0;
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

    public boolean startGame(){
            messagesOut.println("newGame");
            //Println = send the message, scanner reading is just like user input!
            Game resp;
            try {
                    resp = (Game) gameIn.readObject();
                    return this.currentGame(resp);
            } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
            return false;
    }

    public boolean currentGame(Game g) throws Exception{
        if (this.guess.equals("home")){
                this.guess = "";
                Game.clearScreen();
                return g.getWinOrLoss();
        }
        g.displayScreen();

        Scanner guessReader = new Scanner(System.in);
        System.out.println("Type here: ");
        this.guess = guessReader.nextLine();
        g.checkGuess(guess);
        currentGame(g);
        
        return g.getWinOrLoss();
    }
}
