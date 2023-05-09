import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Client user = new Client("127.0.0.1", 6666);
        Scanner readUInput = new Scanner(System.in);
        System.out.println("Type Here: ");
        String uInput = readUInput.nextLine();
        user.sendMessage(uInput);
        user.stop();
        readUInput.close();
    }
}
