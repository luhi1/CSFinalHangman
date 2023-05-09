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
            connectToServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectToServer(){
        //Bruh, all the complicated socket multithreading shit works, but not the reading of a buffered reader into a string. What is life anymore!!!
        try {
            String fullResp = null;
            do {
                String resp = messagesIn.readLine ();
                fullResp += resp;
            } while (messagesIn.readLine() != null);
            System.out.println(fullResp.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getList(){
            messagesOut.println("list");
            try {
                Game.clearScreen();
                StringBuilder resp = new StringBuilder();
                String line;
                while( (line = messagesIn.readLine()) != null) {
                    resp.append(line);
                    resp.append("\n");
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
