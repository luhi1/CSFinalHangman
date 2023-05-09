import java.io.Serializable;

public class Game implements Serializable{
    private int numGuesses;
    private int strikes;
    private String hangman;
    public static String[] hangmanParts = {"  O", "-----", "/|||\\", " / \\", "  X"};
    private String guessString;
    private String secretWord;

    public Game(){
        this.secretWord = "";
        this.numGuesses = 0;
        this.strikes = 0;
        this.hangman = "";
        this.guessString = "";
    }

    public Game(String word) throws Exception{
        this.secretWord = word;
        this.numGuesses = 0;
        this.strikes = 0;
        this.hangman = "";
        this.guessString = "";
        for (int i = 0; i < secretWord.length(); ++i){
            this.guessString += "_ ";
        }
    }

    public void displayScreen() throws Exception{
        clearScreen();
        hangman = "";
        if (strikes == 5){
            System.out.println("You lost! The hangman got buried.");
            for (int i = this.strikes-1; i >= 0; i--){
                hangman += "\n              "+hangmanParts[i];
            }
            System.out.println(hangman);
            System.out.println("Type \"home\" to return to the menu.");
            return;

        }
        String spacedSecret = "";
        for (int i = 0; i < secretWord.length(); ++i){
            spacedSecret += secretWord.charAt(i) + " ";
        }
        if (spacedSecret.equals(guessString)){
            System.out.println("GG You Won!");
            System.out.println("Type \"home\" to return to the menu.");
            return;
        }
        try {
            System.out.println("ìììììììììììììììììììììììììììììì");

            System.out.printf("          Guesses: %d\n", this.numGuesses);
            System.out.println("ìììììììììììììììììììììììììììììì");
            for (int i = 0; i < this.strikes; ++i){
                hangman += "\n              "+hangmanParts[i];
            }
            System.out.println(hangman);
            System.out.println(guessString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearScreen() throws Exception{
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkGuess(String guess){
        String spacedSecret = "";
        for (int i = 0; i < secretWord.length(); ++i){
            spacedSecret += secretWord.charAt(i) + " ";
        }
        if (spacedSecret.equals(guessString)){
            return;
        }
        if (strikes == 5){
            return;
        }
        if (secretWord.contains(guess) && guess.length() == 1 && this.strikes != 5){
            String[] guessArray = this.guessString.split(" ");
            this.guessString = "";
            for (int i = 0; i < secretWord.length(); ++i){
                if (secretWord.charAt(i) == (guess.toCharArray()[0])){
                    guessArray[i] = guess;
                }
                this.guessString += guessArray[i] + " ";
            }
            return;
        }
        this.strikes++;
    }
}
