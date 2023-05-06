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
}
