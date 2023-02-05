import java.util.Scanner;

public class Rules {

    // Store the board for this game
    Board board;

    // Track which player's turn it is
    boolean p1Turn;

    // Scanner used to get user input
    Scanner userInput;

    /**
     * Constructor initializes the board, the input scanner, and sets the first player's turn
     */
    public Rules(Board board) {
        this.board = board;
        this.p1Turn = true;
        userInput = new Scanner(System.in);
    }

    /**
     * This method contains the gameplay loop
     * I.e., while game is not over, play game
     */
    public void play() {

        // Track whether the game is over
        boolean gameOver = false;

        // Track whether the game has produced a stalemate
        boolean stalemate = false;

        // Main gameplay loop
        while(!gameOver) {

            // Print whose turn it is
            if(p1Turn) {
                System.out.println("\nPlayer 1's Turn!\n");
            } else {
                System.out.println("\nPlayer 2's Turn!\n");
            }

            // Print the current board state
            board.printBoard();

            // Get the user input from the keyboard
            int input = getInput();

            // Place the piece on the board and return if the game is over or not 
            gameOver = placePiece(input);

            // If a stalemate is found, the game should also be considered over
            if(isStalemate()) {
                gameOver = true;
                stalemate = true;
            }

        }

        // Notify the players who has won the game and that the game is over
        if(!stalemate) {
            System.out.println();
            board.printBoard();
            System.out.println("\nPlayer " + (p1Turn ? "1" : "2") + " wins!\n");
        } 
        
        // Notify the players that a stalemate has been reached and that the game is over
        else {
            System.out.println();
            board.printBoard();
            System.out.println("\nStalemate!\n");
        }

        // It is the next player's turn
        p1Turn = !p1Turn;

    }

    /**
     * @return a boolean representing if a stalemate has been found
     * Currently, a stalemate is when the board is completely full and no winner has been determined
     */
    public boolean isStalemate() {

        // Check each position in the board map and if there is still space available, no stalemate
        for(int depth: board.boardMap) {

            // Debugging
            // System.out.print(depth + ", ");

            // Space on the board has been found
            if(depth >= 0) return false;
        }

        // Debugging
        // System.out.println();

        return true;
    }

    /**
     * @return an integer represent the column in which the user wants to place a piece
     */
    public int getInput() {

        int column = 0;

        System.out.print("\nEnter the column number in which you would like to place your piece:\n");
           
        // Loop until the user submits a valid submission
        while(column <= 0 || column > 7 && board.boardMap[column - 1] < 0) {
        
            // Try block catches non-numeric input
            try {
                column = userInput.nextInt();

                // Retry if user submits a number whose column doesn't exist
                if(column <= 0 || column > 7) {
                    System.out.println("Please enter a valid integer between 1 and 7 inclusive");
                    column = 0;
                    continue;
                }
                
                // Retry if the user tries to place a piece in a column thats already full
                if(board.boardMap[column - 1] < 0) {
                    System.out.println("Column " + column + " is already full of pieces, please pick another column");
                    column = 0;
                    continue;
                }

                return column;
            } 
            
            // Catch the error when user tries to enter non-numeric data
            catch(Exception e) {
                System.out.println("Please enter a valid integer between 1 and 7 inclusive");
                userInput.next();
                column = 0;
            }

        }

        return column;

    }

    /**
     * @return a boolean that determines if the game is over as a result of the piece being placed
     */
    public boolean placePiece(int col) {

        // Determine how the piece fits in the contenxt of the other piece sequences on the board
        computeHorizontalAxis(col);
        computeVerticalAxis(col);
        computeLeftDiagonal(col);
        computeRightDiagonal(col);

        // Check if the player has "connected 4"
        boolean isOver = board.theBoard[(board.boardMap[col - 1])][col - 1].getMaxNumConnected() >= 4;

        // Tell the board which player placed the piece
        if(p1Turn) {    
            board.theBoard[(board.boardMap[col - 1])--][col - 1].player = 1;
        } else {
            board.theBoard[(board.boardMap[col - 1])--][col - 1].player = -1;
        }

        return isOver;
        
    }

    /**
     * Computes the length of the horizontal sequence at the current position  
     */
    public void computeHorizontalAxis(int col) {

        int left = 0;
        int right = 0;

        // Get the length of the sequence on the left
        try {

            // Determines if looking for pieces for player 1 or 2
            if(p1Turn) {
                left = board.theBoard[(board.boardMap[col - 1])][col - 2].player == 1 ? board.theBoard[(board.boardMap[col - 1])][col - 2].horizontalNumConnected : 0;
            } else {
                left = board.theBoard[(board.boardMap[col - 1])][col - 2].player == -1 ? board.theBoard[(board.boardMap[col - 1])][col - 2].horizontalNumConnected : 0;
            }
        } catch(Exception e) {
            left = 0;
        }

        // Get the length of the sequence on the right
        try {
            if(p1Turn) {
                right = board.theBoard[(board.boardMap[col - 1])][col].player == 1 ? board.theBoard[(board.boardMap[col - 1])][col].horizontalNumConnected : 0;
            } else {
                right = board.theBoard[(board.boardMap[col - 1])][col].player == -1 ? board.theBoard[(board.boardMap[col - 1])][col].horizontalNumConnected : 0;
            }
        } catch(Exception e) {
            right = 0;
        }

        // The length of the sequence at the current position is equal to the length of the left sequence plus the right sequence plus 1 (for the current piece)
        board.theBoard[(board.boardMap[col - 1])][col - 1].horizontalNumConnected = Math.max(left + right + 1, 1);

    }

    /**
     * 
     */
    public void computeVerticalAxis(int col) {

        int left = 0;
        int right = 0;

        try {
            if(p1Turn) {
                left = board.theBoard[(board.boardMap[col - 1]) - 1][col - 1].player == 1 ? board.theBoard[(board.boardMap[col - 1]) - 1][col - 1].verticalNumConnected : 0;
            } else {
                left = board.theBoard[(board.boardMap[col - 1]) - 1][col - 1].player == -1 ? board.theBoard[(board.boardMap[col - 1]) - 1][col - 1].verticalNumConnected : 0;
            }
        } catch(Exception e) {
            left = 0;
        }

        try {
            if(p1Turn) {
                right = board.theBoard[(board.boardMap[col - 1]) + 1][col - 1].player == 1 ? board.theBoard[(board.boardMap[col - 1]) + 1][col - 1].verticalNumConnected : 0;
            } else {
                right = board.theBoard[(board.boardMap[col - 1]) + 1][col - 1].player == -1 ? board.theBoard[(board.boardMap[col - 1]) + 1][col - 1].verticalNumConnected : 0;
            }
        } catch(Exception e) {
            right = 0;
        }

        board.theBoard[(board.boardMap[col - 1])][col - 1].verticalNumConnected = Math.max(left + right + 1, 1);

    }

    /**
     * 
     */
    public void computeLeftDiagonal(int col) {

        int left = 0;
        int right = 0;

        try {
            if(p1Turn) {
                left = board.theBoard[(board.boardMap[col - 1]) - 1][col - 2].player == 1 ? board.theBoard[(board.boardMap[col - 1]) - 1][col - 2].leftDiagonalNumConnected : 0;
            } else {
                left = board.theBoard[(board.boardMap[col - 1]) - 1][col - 2].player == -1 ? board.theBoard[(board.boardMap[col - 1]) - 1][col - 2].leftDiagonalNumConnected : 0;
            }
        } catch(Exception e) {
            left = 0;
        }
        try {
            if(p1Turn) {
                right = board.theBoard[(board.boardMap[col - 1]) + 1][col].player == 1 ? board.theBoard[(board.boardMap[col - 1]) + 1][col].leftDiagonalNumConnected : 0;
            } else {
                right = board.theBoard[(board.boardMap[col - 1]) + 1][col].player == -1 ? board.theBoard[(board.boardMap[col - 1]) + 1][col].leftDiagonalNumConnected : 0;
            }
        } catch(Exception e) {
            right = 0;
        }

        board.theBoard[(board.boardMap[col - 1])][col - 1].leftDiagonalNumConnected = Math.max(left + right + 1, 1);


    }

    /**
     * 
     */
    public void computeRightDiagonal(int col) {

        int left = 0;
        int right = 0;

        try {
            if(p1Turn) {
                left = board.theBoard[(board.boardMap[col - 1]) - 1][col].player == 1 ? board.theBoard[(board.boardMap[col - 1]) - 1][col].rightDiagonalNumConnected : 0;
            } else {
                left = board.theBoard[(board.boardMap[col - 1]) - 1][col].player == -1 ? board.theBoard[(board.boardMap[col - 1]) - 1][col].rightDiagonalNumConnected : 0;
            }
        } catch(Exception e) {
            left = 0;
        }
        try {
            if(p1Turn) {
                right = board.theBoard[(board.boardMap[col - 1]) + 1][col - 2].player == 1 ? board.theBoard[(board.boardMap[col - 1]) + 1][col - 2].rightDiagonalNumConnected : 0;
            } else {
                right = board.theBoard[(board.boardMap[col - 1]) + 1][col - 2].player == -1 ? board.theBoard[(board.boardMap[col - 1]) + 1][col - 2].rightDiagonalNumConnected : 0;
            }
        } catch(Exception e) {
            right = 0;
        }

        board.theBoard[(board.boardMap[col - 1])][col - 1].rightDiagonalNumConnected = Math.max(left + right + 1, 1);

    }

}      