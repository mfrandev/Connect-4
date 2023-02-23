package AI;

import Game.Board;

public class SearchAI extends AI {

    // Save how deep in the search tree the AI should look for the next move
    int depth;

    /**
     * Constructor for the search AI that stores the board and depth
     */ 
    public SearchAI(Board board, int depth) {
        super(board);
        setDepth(depth);
    }

    /**
     * Set the depth value for this AI instance
     */
    private void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * @return an int representing the depth value for this AI instance
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Use the minmax algorithm with the specified AI player parameters to calculate the next move
     * @return an integer value representing the best column to play the next move
     */
    @Override
    public int getMove() {

        return minmax(getBoard(), getDepth(), getPlayer())[1];

    }

    /**
     * @param Board board
     * @param int depth
     * @param int player
     * Use the minmax algorithm to find the best move for the AI player
     * @return an int[] representing the tuple (score, index) for the best move discovered
     */
    public int[] minmax(Board board, int depth, int player) {

        // Event of a stalemate
        if(board.getNumMovesPlayed() == board.getBoard().length * board.getBoardMap().length) {
            return new int[] {0, -1};
        }

        // Typical recursive base case that triggers evaluation for a specific board position
        if(depth == 0) {
            return new int[] {GameScoreHeuristic.getGameScore(board), -1}
        }

        // Maximizing Player
        if(player == 1) {

            // Max score and index values (need index for final return)
            int maxScore = Integer.MIN_VALUE;
            int maxIndex = -1;

            // Check all of the possible child board positions and look for the max score available
            for(int i = 0; i < board.getBoardMap().length; i++) {

                // If the column is full, do not place a piece there
                if(board.getBoardMap()[i + 1] == -1) continue;

                // Copy the board and place a new piece
                Board copy = board.copy();
                boolean gameOver = copy.placePiece(i + 1, true);

                // If that previous piece placement triggered a victory for the maximizing player, 
                // return a large number and the index of the placement
                if(gameOver) return new int[] {1000000, i + 1};

                // Recurse and send the board state to the next player
                int[] scoreIndex = minmax(copy, depth - 1, 2);

                // If the score found is better (larger) than the current best score, make it the new best score (and save the index)
                if(scoreIndex[0] > maxScore) {
                    maxIndex = i + 1;
                    maxScore = scoreIndex[0];
                }

            }

            // Return the tuple of (bestScore, bestIndex) for the maximizing player
            return new int[] {maxScore, maxIndex};

        } 
        
        // Minimizing Player
        else {

            // Min score and index values (need index for final return)
            int minScore = Integer.MAX_VALUE;
            int minIndex = -1;

            // Check all of the possible child board positions and look for the min score available
            for(int i = 0; i < board.getBoardMap().length; i++) {

                // If the column is full, do not place a piece there
                if(board.getBoardMap()[i + 1] == -1) continue;

                // Copy the board and place a new piece
                Board copy = board.copy();
                boolean gameOver = copy.placePiece(i + 1, false); 

                // If that previous piece placement triggered a victory for the minimizing player, 
                // return a very small number and the index of the placement
                if(gameOver) return new int[] {-1000000, i + 1};

                // Recurse and send the board state to the next player
                int[] scoreIndex = minmax(copy, depth - 1, 1);

                // If the score found is better (smaller) than the current best score, make it the new best score (and save the index)
                if(scoreIndex[0] < minScore) {
                    minScore = scoreIndex[0];
                    minIndex = i + 1;
                }

            }

            // Return the tuple of (bestScore, bestIndex) for the minimizing player
            return new int[] {minScore, minIndex};
        }

    }

}