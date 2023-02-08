package AI;

import java.util.Random;

import Game.Board;

public class BasicAI extends AI {

    Random random;

    public BasicAI(Board board, int player) {
        super(board, player);
        random = new Random();
    }

    /**
     * Strategy is that the basic AI plays a random move from the set of available options
     */
    public int getMove() {

        int choice = -1;

        while(choice == -1 || getBoard().getBoardMap()[choice] == -1) {
            choice = random.nextInt(getBoard().getBoardMap().length);
        }

        return choice;

    }

}