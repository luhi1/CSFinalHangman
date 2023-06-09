import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Host myHost = new Host("10.129.210.117", 6666);
        Boolean winOrLoss = myHost.startGame();
        String menuChoice = "";
        Scanner menuReader = new Scanner(System.in);


        do {
            System.out.println("If you quit out of the game, whatever chain you had going is lost!!!");
            System.out.println("No, you cannot recover the chain, and don't ask me to try and do that for you!");
            System.out.println("If you fail and attemp to start a new chain, your old one will be overwritten.");
            System.out.println("--------------------------------------------");
            System.out.println("Type \"chain\" to play another game or type \"leaderboard\" to see who has the highest chain wins!");
            System.out.println("Type \"quit\" to call it a day!");
            do {
                menuChoice = menuReader.nextLine();
            } while (!menuChoice.equals("chain") && !menuChoice.equals("leaderboard") && !menuChoice.equals("quit"));
    
            switch (menuChoice){
                case ("quit"):
                        System.out.println("Bye!");
                        break;
                case ("chain"):
                        if (winOrLoss){
                                myHost.incrementChain();
                        } else {
                                myHost.resetChain();
                        }
                        myHost.sendMessage(myHost.getClientSocket().getInetAddress().getHostAddress()+":"+ myHost.getChain());
                        Thread.sleep(100);
                        winOrLoss = myHost.startGame();
                        break;
                case ("leaderboard"):
                        myHost.sendMessage("leaderboard");
                        Game.clearScreen();
                        String terminator = "End Leaderboard";

                    
    
                        try {
                                StringBuilder resp = new StringBuilder();
                                String line;
                                resp.append("Current leader.");
                                resp.append("\n");
                                while( (line = myHost.getInputStream().readLine()) != null) {
                                        if (line.contains(terminator)){
                                                break;
                                        }
                                        if (line.contains("nobody")){
                                            System.out.println(line);
                                            break;
                                        }
                                        String[] arrayLine = line.split(":");
                                        resp.append(arrayLine[0]);
                                        resp.append("\n");
                                        resp.append("Score: "+arrayLine[1]);
                                        resp.append("\n");
                                }
                                System.out.println(resp.toString());    
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                default:
                        menuChoice = "quit";
                        break;
            }
            
        } while (!menuChoice.equals("quit"));
        menuReader.close();
    }
}
