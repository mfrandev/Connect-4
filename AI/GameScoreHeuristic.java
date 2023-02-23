package AI;

import Game.Board;
import Game.Piece;

public class GameScoreHeuristic {

    /**
     * @param Board b representing the board state 
     * Evalueate the board state and return a value representing how well player 1 and player 2 are positioned
     * Positive values are beneficial for player 1, negative values are beneficial for player 2
     * @return an int representing the result of this evaluation
     */
    public static int getGameScore(Board b) {

        int player1OneInARow = 0;
        int player1TwoInARow = 0;
        int player1ThreeInARow = 0;
        
        int player2OneInARow = 0;
        int player2TwoInARow = 0;
        int player2ThreeInARow = 0;

        for(Piece[] pcs: b.getBoard()) {
            for(Piece p: pcs) {

                if(p.getPlayer() == 0) continue;

                if(p.getMaxNumConnected() == 1) {
                    if(p.getPlayer() == 1) {
                        player1OneInARow += 1;
                    } else {
                        player2OneInARow += 1;
                    }
                } 
                
                else if(p.getMaxNumConnected() == 2) {
                    if(p.getPlayer() == 1) {
                        player1TwoInARow += 3;
                    } else {
                        player2TwoInARow += 3;
                    }
                }

                else if(p.getMaxNumConnected() == 3) {
                    if(p.getPlayer() == 1) {
                        player1ThreeInARow += 9;
                    } else {
                        player2ThreeInARow += 9;
                    }
                }
            }
        }

        return (player1OneInARow + player1TwoInARow + player1ThreeInARow) - (player2OneInARow + player2TwoInARow + player2ThreeInARow);
        
    }

}