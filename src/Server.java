import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

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
    private Socket clientSocket;
    private ObjectOutputStream gameOut;
    private ObjectInputStream gameIn;
    private PrintWriter messagesOut;
    private BufferedReader messagesIn;
    
    public Server(){
            System.out.print("Server object successfully created.");
    }

    public void startHostGame(int port){
            try {
                    serverSocket = new ServerSocket(port);
                    clientSocket = serverSocket.accept();

                    gameOut = new ObjectOutputStream(clientSocket.getOutputStream());
                    gameIn = new ObjectInputStream(clientSocket.getInputStream());

                    messagesOut = new PrintWriter(clientSocket.getOutputStream(), true);
                    messagesIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String hostClientRequests = messagesIn.readLine();
                    //Works just like the scanner class!

                    if ("newGame".equals(hostClientRequests)){
                            gameOut.writeObject(createGame());;
                    } else {
                            gameOut.writeObject(new Game());
                    }
            } catch (Exception e) {
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
    }
}