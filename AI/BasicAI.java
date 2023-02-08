package AI;

import java.util.Random;

import Game.Board;

public class BasicAI extends AI {

    // Used for selecting random integers within a range
    Random random;

    /**
     * Constructor invoques the AI superclass constructor and initializes the random object
     */
    public BasicAI(Board board) {
        super(board);
        random = new Random();
    }

    /**
     * Strategy for the basic AI is to play a random move from the set of available options
     * @return an integer representing the column the AI chooses to play a piece in
     */
    @Override
    public int getMove() {

        // Store the AI's final column selection
        int choice = -1;

        // Debugging
        // for(int depth: board.getBoardMap()) {
        //     System.out.print(depth + " ");
        // }
        // System.out.println();

        // The indexing for choice was designed to mimic the placePiece() method in Game/Rules.java
        while(choice == -1 || getBoard().getBoardMap()[choice - 1] == -1) {

            // Select a random, valid piece location
            choice = random.nextInt(getBoard().getBoardMap().length - 1) + 1;

            // Debugging
            // System.out.println("Placing in column " + choice + " with depth " + getBoard().getBoardMap()[choice - 1]);
        }

        return choice;

    }

}