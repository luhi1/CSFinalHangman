import java.util.Scanner;

public class Host extends Server {
    private String guess;
    private String[] players;

    public Host(Server s){
        super();
        this.guess = "";
        Game hostGame = s.createGame();
        try {
            gameService(hostGame);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gameService(Game g) throws Exception{
        if (this.guess.equals("home")){
            g.clearScreen();
            return;
        }
        g.displayScreen();
        Scanner guessReader = new Scanner(System.in);
        System.out.println("Type here: ");
        this.guess = guessReader.nextLine();
        g.checkGuess(guess);
        gameService(g);
    }
}
