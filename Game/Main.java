package Game;

public class Main {

    /**
     * Main method that creates the board, rules object, and starts the game loop
     */
    public static void main(String[] args) {

        // Create the board
        Board b = new Board();

        // Create a rules object 
        Rules r = new Rules(b);

        // Start the game loop
        r.play();
    }
}