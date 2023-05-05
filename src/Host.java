import java.util.Scanner;

public class Host extends Server {
    private String guess;
    public Host(Server s){
        super();
        this.guess = "";
    }

    public void startGame(Game g) throws Exception{
        if (this.guess.equals("home")){
            g.clearScreen();
            return;
        }
        g.displayScreen();

        Scanner guessReader = new Scanner(System.in);
        System.out.println("Type here: ");
        this.guess = guessReader.nextLine();

        g.checkGuess(guess);
        startGame(g);
        
        guessReader.close();
    }
}
