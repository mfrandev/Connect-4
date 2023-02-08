package AI;

import Game.Board;

public abstract class AI {

    protected Board board;
    protected int player;

    public AI(Board board, int player) {
        this.board = board;
        this.player = player;
    }

    public int getPlayer() {
        return player;
    } 

    public Board getBoard() {
        return board;
    }

}