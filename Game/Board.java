package Game;

import java.io.PrintWriter;

public class Board {

    // Save the board as a 2D array of Piece objects
    Piece[][] theBoard = null;

    // Save the depth of each column of the board for every given point in time
    int[] boardMap = null;

    int numMovesPlayed;

    boolean gameOver;

    /**
     * Initialize the board by creating the Piece matrix and the boardMap
     */
    public Board() {
        theBoard = initBoard();
        boardMap = initBoardMap();
        numMovesPlayed = 0;
        gameOver = false;
    }

    /**
     * Constructor for copying board
     */
    public Board(Piece[][] theBoard, int[] boardMap, int numMovesPlayed, boolean gameOver) {
        this.theBoard = theBoard;
        this.boardMap = boardMap;
        this.numMovesPlayed = numMovesPlayed;
        this.gameOver = gameOver;
    }

    /**
     * @return a Piece[][] representing the matrix of pieces
     * Board is hard coded in first release for a standard 6x7 board
     */
    public Piece[][] initBoard() {

        // Hard code 6x7 board for first release
        Piece[][] tempBoard = new Piece[6][7];
        
        for(int i = 0; i < tempBoard.length; i++) {
            for(int j = 0; j < tempBoard[i].length; j++) {

                // Create an unclaimed piece at each board position
                tempBoard[i][j] = new Piece(0);
            }
        }

        return tempBoard;
    }

    /**
     * @return an int[] representing the depth of each column on the board at a given point in time
     * Board map is hard coded in first release for a 6x7 board
     */
    public int[] initBoardMap() {

        // Initially hard coded for first release 
        int[] tempBoardMap = new int[7];

        // Hard code the initial depth of each column to its lowest valid position
        for(int i = 0; i < tempBoardMap.length; i++) {
            tempBoardMap[i] = tempBoardMap.length - 2;
        }

        return tempBoardMap;
    }

    public void incrementNumMovesPlayed() {
        numMovesPlayed++;
    }

    public int getNumMovesPlayed() {
        return numMovesPlayed;
    }

    /**
     * Getter for the board map
     * @return an int[] representing the depths of each column
     */
    public int[] getBoardMap() {
        return boardMap;
    }

    public Piece[][] getBoard() {
        return theBoard;
    }

    /**
     * Let the board object print itself with the current state of the game
     */
    public void printBoard() {

        // Print the headers for each column 
        for(int i = 1; i < 8; i++) {

            // Debugging
            // System.out.print(" " + i + "  ");
            System.out.print(i + " ");
        }

        System.out.println();

        // Print the 6x7 board showing the state of each piece
        for(int i = 0; i < theBoard.length; i++) {
            for(int j = 0; j < theBoard[i].length; j++) {

                // If space is empty, print a point
                if(theBoard[i][j].getPlayer() == 0) {

                    //Debugging
                    // System.out.print(" .," + theBoard[i][j].getMaxNumConnected());
                    System.out.print(". " );
                } 
                
                // If Player 1 placed a piece in a given spot, print an X
                else if(theBoard[i][j].getPlayer() == 1) {

                    //Debugging
                    // System.out.print(" X," + theBoard[i][j].getMaxNumConnected());
                    System.out.print("X ");
                } 
                
                // If Player 2 placed a piece in a given spot, print an O
                else if(theBoard[i][j].getPlayer() == -1) {

                    //Debugging
                    // System.out.print(" O," + theBoard[i][j].getMaxNumConnected());
                    System.out.print("O ");
                }
            }
            System.out.println();
        }

    }

    /**
     * Prints the board to the socket buffer stream instead of the console
     * @param writer stream to which the board should be printed
     */
    public void sendBoardState(PrintWriter writer) {
        
        // Print the headers for each column 
        for(int i = 1; i < 8; i++) {

            // Debugging
            // writer.print(" " + i + "  ");
            writer.print(i + " ");
        }

        writer.println();

        // Print the 6x7 board showing the state of each piece
        for(int i = 0; i < theBoard.length; i++) {
            for(int j = 0; j < theBoard[i].length; j++) {

                // If space is empty, print a point
                if(theBoard[i][j].getPlayer() == 0) {

                    //Debugging
                    // writer.print(" .," + theBoard[i][j].getMaxNumConnected());
                    writer.print(". " );
                } 
                
                // If Player 1 placed a piece in a given spot, print an X
                else if(theBoard[i][j].getPlayer() == 1) {

                    //Debugging
                    // writer.print(" X," + theBoard[i][j].getMaxNumConnected());
                    writer.print("X ");
                } 
                
                // If Player 2 placed a piece in a given spot, print an O
                else if(theBoard[i][j].getPlayer() == -1) {

                    //Debugging
                    // writer.print(" O," + theBoard[i][j].getMaxNumConnected());
                    writer.print("O ");
                }
            }
            writer.println();
        }
        writer.flush();
    }

    /**
     * Copy the current state of the board for the AI solver
     */
    public Board copy() {

        // Hard code 6x7 board for first release
        Piece[][] tempBoard = new Piece[6][7];
        
        for(int i = 0; i < tempBoard.length; i++) {
            for(int j = 0; j < tempBoard[i].length; j++) {

                // Create an unclaimed piece at each board position
                tempBoard[i][j] = theBoard[i][j].copy();
            }
        }

        int[] tempBoardMap = new int[boardMap.length];
        for(int i = 0; i < tempBoardMap.length; i++) {
            tempBoardMap[i] = boardMap[i];
        }

        return new Board(tempBoard, tempBoardMap, numMovesPlayed, gameOver);

    }

    /**
     * @return a boolean that determines if the game is over as a result of the piece being placed
     */
    public boolean placePiece(int col, boolean p1Turn) {

        // Tell the board which player placed the piece
        if(p1Turn) {    
            theBoard[(getBoardMap()[col - 1])][col - 1].setPlayer(1);
        } else {
            theBoard[(getBoardMap()[col - 1])][col - 1].setPlayer(-1);
        }

        // Determine how the piece fits in the contenxt of the other piece sequences on the board
        BoardStateCalc.computeHorizontalAxis(this, col, p1Turn);
        BoardStateCalc.computeVerticalAxis(this, col, p1Turn);
        BoardStateCalc.computeLeftDiagonal(this, col, p1Turn);
        BoardStateCalc.computeRightDiagonal(this, col, p1Turn);

        // Tell the board another piece has been played
        incrementNumMovesPlayed();

        // Check if the player has "connected 4"
        return theBoard[(getBoardMap()[col - 1]--)][col - 1].getMaxNumConnected() >= 4;
        
    }

    public boolean isWinner(int col, boolean p1Turn) {
        Board temp = this.copy();
        return temp.placePiece(col, p1Turn);
    }

}