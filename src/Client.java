import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

public class Client{
    private String ip;

    private Socket clientSocket;
    private PrintWriter messagesOut;
    private BufferedReader messagesIn;

    public Client(String serverIP, int serverPort){
        try {

            clientSocket = new Socket(serverIP, serverPort);
            messagesOut = new PrintWriter(clientSocket.getOutputStream(), true);
            messagesIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.ip = clientSocket.getInetAddress().getHostAddress();
            sendMessage("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
            messagesOut.println(message);
            try {
                Game.clearScreen();
                    StringBuilder resp = new StringBuilder();
                    String line;
                    while( (line = messagesIn.readLine()) != null) {
                        resp.append(line);
                        resp.append("\n");
                        if (resp.toString().contains("Type \"list\"")){
                            break;
                        }
                    }
                    System.out.println(resp.toString());
    
                
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void stop(){
        try {
                messagesIn.close();
                messagesOut.close();
                clientSocket.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
