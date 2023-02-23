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

        // Score player 1's pieces
        int player1OneInARow = 0;
        int player1TwoInARow = 0;
        int player1ThreeInARow = 0;
        
        // Score player 2's pieces
        int player2OneInARow = 0;
        int player2TwoInARow = 0;
        int player2ThreeInARow = 0;

        // Iterate over the whole board
        for(Piece[] pcs: b.getBoard()) {
            for(Piece p: pcs) {

                // If there is no piece in this space, continue
                if(p.getPlayer() == 0) continue;

                // If this piece is not connected to any others, score it as "1"
                if(p.getMaxNumConnected() == 1) {

                    // Player 1's score
                    if(p.getPlayer() == 1) {
                        player1OneInARow += 1;
                    } 
                    
                    // Player 2's score
                    else {
                        player2OneInARow += 1;
                    }
                } 

                // If this piece is part of a chain of two, score it as "3"
                else if(p.getMaxNumConnected() == 2) {

                    // Player 1's score
                    if(p.getPlayer() == 1) {
                        player1TwoInARow += 3;
                    } 
                    
                    // Player 2's score
                    else {
                        player2TwoInARow += 3;
                    }
                }

                // If this piece is part of a chain of three, score it as "9"
                else if(p.getMaxNumConnected() == 3) {

                    // Player 1's score
                    if(p.getPlayer() == 1) {
                        player1ThreeInARow += 9;
                    } 
                    
                    // Player 2's score
                    else {
                        player2ThreeInARow += 9;
                    }
                }
            }
        }

        // Return the score as player 1's score - player 2's score
        // This means negative return values are board positions that favor player 2
        // Positive return values favor player 1
        return (player1OneInARow + player1TwoInARow + player1ThreeInARow) - (player2OneInARow + player2TwoInARow + player2ThreeInARow);
        
    }

}