package AI;

import java.util.Random;

import Game.Board;

public class BasicAI extends AI {

    Random random;

    public BasicAI(Board board) {
        super(board);
        random = new Random();
    }

    /**
     * Strategy is that the basic AI plays a random move from the set of available options
     */
    @Override
    public int getMove() {

        int choice = -1;

        for(int depth: board.getBoardMap()) {
            System.out.print(depth + " ");
        }
        System.out.println();

        while(choice == -1 || getBoard().getBoardMap()[choice - 1] == -1) {
            choice = random.nextInt(getBoard().getBoardMap().length - 1) + 1;
            System.out.println("Placing in column " + choice + " with depth " + getBoard().getBoardMap()[choice - 1]);
        }

        return choice;

    }

}