package AI;

import Game.Board;

public abstract class AI {

    protected Board board;
    protected int player;

    protected AI(Board board) {
        this.board = board;
    }

    protected AI(Board board, int player) {
        this.board = board;
        this.player = player;
    }

    public int getPlayer() {
        return player;
    } 

    public Board getBoard() {
        return board;
    }

    public int getMove() {
        int temp = 0;
        return temp + 1;
    }

}