package Game;

import AI.AI;
import Network.NetworkPlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Rules {

    // Store the board for this game
    Board board;

    // Track which player's turn it is
    boolean p1Turn;

    // Scanner used to get user input
    Scanner userInput;

    int numMovesPlayed;

    /**
     * Constructor initializes the board, the input scanner, and sets the first player's turn
     */
    public Rules(Board board) {
        this.board = board;
        this.p1Turn = true;
        userInput = new Scanner(System.in);
    }

    /**
     * This method contains the gameplay loop for 2 human players on the same machine
     * I.e., while game is not over, play game
     */
    public void play() {

        // Track whether the game is over
        boolean gameOver = false;

        // Track whether the game has produced a stalemate
        boolean stalemate = false;

        int input = -1;

        // Main gameplay loop
        while(!gameOver) {

            // Print whose turn it is
            if(p1Turn) {
                System.out.println("\nPlayer 1's Turn!\n");

                // Print the opponent's last move
                if(input != -1) {
                    System.out.println("Player 2 placed a piece in column " + input + "\n");
                }
            } else {
                System.out.println("\nPlayer 2's Turn!\n");

                // Print the opponent's last move
                if(input != -1) {
                    System.out.println("Player 1 placed a piece in column " + input + "\n");
                }
            }

            // Print the current board state
            board.printBoard();

            // Get the user input from the keyboard
            input = getMoveInput();

            // Place the piece on the board and return if the game is over or not 
            gameOver = board.placePiece(input, p1Turn);

            // If a stalemate is found, the game should also be considered over
            if(isStalemate()) {
                gameOver = true;
                stalemate = true;
            }

            numMovesPlayed++;

            // It is the next player's turn
            p1Turn = !p1Turn;

        }

        // It is the next player's turn
        p1Turn = !p1Turn;

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

    }

    /**
     * This method contains the gameplay loop for a human player and an AI player
     * I.e., while game is not over, play game
     */
    public void play(AI aiPlayer) {

        // Track whether the game is over
        boolean gameOver = false;

        // Track whether the game has produced a stalemate
        boolean stalemate = false;

        numMovesPlayed = 0;

        // Ask the user if wanting to be player 1 or 2
        int order = getOrderInput();

        // Mirror the turn choice the user selects
        boolean playerTurn = order == 1;

        aiPlayer.setPlayer(playerTurn ? 2 : 1);

        // Store where the next piece should be placed
        int input = -1;

        // Main gameplay loop
        while(!gameOver) {

            // Print whose turn it is
            if(p1Turn) {
                System.out.println("\nPlayer 1's Turn!\n");

                // Print the opponent's last move
                if(input != -1) {
                    System.out.println("Player 2 placed a piece in column " + input + "\n");
                }
            } else {
                System.out.println("\nPlayer 2's Turn!\n");

                // Print the opponent's last move
                if(input != -1) {
                    System.out.println("Player 1 placed a piece in column " + input + "\n");
                }
            }

            // Print the current board state
            board.printBoard();

            // If a human turn
            if(playerTurn) {
                
                // Get the user input from the keyboard
                input = getMoveInput();
            } 
            
            // If an AI turn
            else {

                // Print some status message
                System.out.println("\nAI making a move...");

                // Get the move choice from the AI player
                input = aiPlayer.getMove();

                // Wait 4 seconds to let the human to process the game state
                try {

                    // Perform the wait
                    TimeUnit.SECONDS.sleep(3);
                } 
                
                // Should never trigger
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Debugging
                // System.out.println("AI tried to play in column " + input);
            }

            // Place the piece on the board and return if the game is over or not 
            gameOver = board.placePiece(input, p1Turn);

            // Debugging
            // for(int depth: board.getBoardMap()) {
            //     System.out.print(depth + " ");
            // }
            // System.out.println();

            // If a stalemate is found, the game should also be considered over
            if(isStalemate()) {
                gameOver = true;
                stalemate = true;
            }

            numMovesPlayed++;

            // It is the next player's turn
            p1Turn = !p1Turn;

            // Note that it is either no longer the human's (or AI's) turn 
            playerTurn = !playerTurn;

        }

        // It is the next player's turn
        p1Turn = !p1Turn;

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

    }

    /**
     * This method contains the gameplay loop for 2 AI players
     * I.e., while game is not over, play game
     */
    public void play(AI aiPlayer1, AI aiPlayer2) {

        // Track whether the game is over
        boolean gameOver = false;

        // Track whether the game has produced a stalemate
        boolean stalemate = false;

        numMovesPlayed = 0;

        // This is a pseudo-random coin flip to determine which AI is player 1 and which is player 2
        int max = 2;
        int min = 1;
        int range = max - min + 1;

        aiPlayer1.setPlayer((int)(Math.random() * range) + min);
        aiPlayer2.setPlayer(aiPlayer1.getPlayer() == 1 ? 2 : 1);

        System.out.println("\nAI specified in argument 1 is player: " + aiPlayer1.getPlayer());
        System.out.println("AI specified in argument 2 is player: " + aiPlayer2.getPlayer());

        // Wait 4 seconds to let spectators register which argument corresponds to which AI
        try {
            // Perform the wait
            TimeUnit.SECONDS.sleep(2);
        } 
        
        // Should never trigger
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Store where the next piece should be placed
        int input = -1;

        // Main gameplay loop
        while(!gameOver) {

            // Print whose turn it is
            if(p1Turn) {
                System.out.println("\nPlayer 1's Turn!\n");

                // Print the opponent's last move
                if(input != -1) {
                    System.out.println("Player 2 placed a piece in column " + input + "\n");
                }
            } else {
                System.out.println("\nPlayer 2's Turn!\n");

                // Print the opponent's last move
                if(input != -1) {
                    System.out.println("Player 1 placed a piece in column " + input + "\n");
                }
            }

            // Print the current board state
            board.printBoard();

            // If player 1's turn
            if(p1Turn) {

                // If aiPlayer1 is the first player, get its move
                // Otherwise, get aiPlayer2's move
                input = aiPlayer1.getPlayer() == 1 ? aiPlayer1.getMove() : aiPlayer2.getMove();
                
            } 
            
            // If player 2's turn
            else {

                // If aiPlayer1 is the second player, get its move
                // Otherwise, get aiPlayer2's move
                input = aiPlayer1.getPlayer() == 2 ? aiPlayer1.getMove() : aiPlayer2.getMove();

                // Debugging
                // System.out.println("AI tried to play in column " + input);
            }

            // Wait 4 seconds to let spectators process the game state
            try {

                // Print some status message
                System.out.print("\nAI " + (p1Turn ? "player 1" : "player 2") + " making a move...\n");

                // Perform the wait
                TimeUnit.SECONDS.sleep(2);
            } 
            
            // Should never trigger
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Place the piece on the board and return if the game is over or not 
            gameOver = board.placePiece(input, p1Turn);

            // Debugging
            // for(int depth: board.getBoardMap()) {
            //     System.out.print(depth + " ");
            // }
            // System.out.println();

            // If a stalemate is found, the game should also be considered over
            if(isStalemate()) {
                gameOver = true;
                stalemate = true;
            }

            numMovesPlayed++;

            // It is the next player's turn
            p1Turn = !p1Turn;

        }

        // It is the next player's turn
        p1Turn = !p1Turn;

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

    }

    /**
     * Play between a local human and a human over the network
     */
    public void play(NetworkPlayer netPlayer) {

        // Track whether the game is over
        boolean gameOver = false;

        // Track whether the game has produced a stalemate
        boolean stalemate = false;

        numMovesPlayed = 0;

        // This is a pseudo-random coin flip to determine which AI is player 1 and which is player 2
        int max = 2;
        int min = 1;
        int range = max - min + 1;

        // Determine where in the order the host plays
        int hostPlayer = (int)(Math.random() * range) + min;
        boolean hostTurn = hostPlayer == 1;

        // Try with resources to open the communication buffer between the host and client
        try(PrintWriter pr = new PrintWriter(netPlayer.getSocket().getOutputStream())) {

            // To read from client
            InputStreamReader in = new InputStreamReader(netPlayer.getSocket().getInputStream());
            BufferedReader br = new BufferedReader(in);

            // Tell the client where it plays in the turn order
            netPlayer.setPlayer(hostTurn ? 2 : 1);
            pr.println("\nYou are player : " + netPlayer.getPlayer());
            pr.println("The host is player : " + (netPlayer.getPlayer() == 1 ? 2 : 1));

            // Print to turn order to the host console 
            System.out.println("\nYou are player: " + hostPlayer);
            System.out.println("The client (network) player is : " + netPlayer.getPlayer());

            // Wait 2 seconds to let spectators register which player is who
            try {

                // Perform the wait
                TimeUnit.SECONDS.sleep(2);
            } 
            
            // Should never trigger
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            int input = -1;

            while(!gameOver) {

                // Print whose turn it is
                if(hostTurn) {

                    // To the host
                    System.out.println("\nYour Turn!\n");

                    // To the client
                    pr.println("\nHost's Turn!\n");

                    // Print the opponent's last move
                    if(input != -1) {

                        // To the host
                        System.out.println("Opponent placed a piece in column " + input + "\n");

                        // To the client
                        pr.println("You placed a piece in column " + input + "\n");
                    }
                } else {

                    // Print to the host
                    System.out.println("\nOpponent's Turn!\n");

                    // To client
                    pr.println("\nYour Turn!\n");

                    // Print the opponent's last move
                    if(input != -1) {

                        // To host
                        System.out.println("You placed a piece in column " + input + "\n");

                        // To client
                        pr.println("Host placed a piece in column " + input + "\n");
                    }
                }

                // Send the state of the board to the client
                board.sendBoardState(pr);

                // Print the current board state on the console
                board.printBoard();

                // If it's your turn
                if(hostTurn) {
                    
                    // Notify the client it is waiting for a move from the host
                    pr.println("\nWaiting for the host to make a move...");
                    pr.flush();

                    // Get the user input from the keyboard
                    input = getMoveInput();
                } 

                // If client turn, get input from client
                else {

                    // Prompt the client for input
                    pr.println("\nEnter the column number in which you would like to place your piece:");

                    // This stands for "move" and signals the client to stop reading and make a move
                    pr.println("m");
                    pr.flush();

                    // Tell the host it is waiting for the client
                    System.out.println("\nWaiting for your opponent to make a move...");

                    // Get the client input and validate it
                    input = br.read();      
                    boolean valid = isValidInput(input, pr);  

                    // If invalid input...
                    while(!valid) {

                        // Get the client input until it is valid while sending errors to client
                        input = br.read();  
                        valid = isValidInput(input, pr); 
                    }     

                    // Notify the client a valid input has been recieved
                    pr.println("VALID");
                    pr.flush();   
                }

                // Place the piece on the board and return if the game is over or not 
                gameOver = board.placePiece(input, p1Turn);

                // Debugging
                // for(int depth: board.getBoardMap()) {
                //     System.out.print(depth + " ");
                // }
                // System.out.println();

                // If a stalemate is found, the game should also be considered over
                if(isStalemate()) {
                    gameOver = true;
                    stalemate = true;
                }

                numMovesPlayed++;

                // Note that it is either no longer the human's (or AI's) turn 
                hostTurn = !hostTurn;
                p1Turn = !p1Turn;

            }

            // It is the next player's turn
            hostTurn = !hostTurn;
            p1Turn = !p1Turn;
            
            // Client needs an extra line break before sending it the final board state
            pr.println();
            board.sendBoardState(pr);

            // Notify the players who has won the game and that the game is over
            if(!stalemate) {
                System.out.println();
                board.printBoard();

                // To client
                pr.println((hostTurn ? "\nThe host wins!\n" : "\nYou win!\n"));

                // To host
                System.out.println((hostTurn ? "\nYou win!\n" : "\nThe network (client) player wins!\n"));
            } 
            
            // Notify the players that a stalemate has been reached and that the game is over
            else {
                System.out.println();
                board.printBoard();

                // To client
                pr.println("\nStalemate!\n");

                // To host
                System.out.println("\nStalemate!\n");
            }

            // Tell the client the game is ended
            pr.println("END");
            pr.flush();

        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Play between a local human and a human over the network
     */
    public void play(AI aiPlayer, NetworkPlayer netPlayer) {

        // Track whether the game is over
        boolean gameOver = false;

        // Track whether the game has produced a stalemate
        boolean stalemate = false;

        numMovesPlayed = 0;

        // This is a pseudo-random coin flip to determine which AI is player 1 and which is player 2
        int max = 2;
        int min = 1;
        int range = max - min + 1;

        // Determine where in the order the host plays
        int hostPlayer = (int)(Math.random() * range) + min;
        boolean hostTurn = hostPlayer == 1;

        // Try with resources to open the communication buffer between the host and client
        try(PrintWriter pr = new PrintWriter(netPlayer.getSocket().getOutputStream())) {

            // To read from client
            InputStreamReader in = new InputStreamReader(netPlayer.getSocket().getInputStream());
            BufferedReader br = new BufferedReader(in);

            // Tell the client where it plays in the turn order
            netPlayer.setPlayer(hostTurn ? 2 : 1);
            pr.println("\nYou are player : " + netPlayer.getPlayer());
            pr.println("The AI is player : " + (netPlayer.getPlayer() == 1 ? 2 : 1));
            pr.println("WAIT");

            // Print to turn order to the host console 
            System.out.println("\nThe AI is player: " + hostPlayer);
            System.out.println("The client (network) player is : " + netPlayer.getPlayer());

            // Wait 2 seconds to let spectators register which player is who
            try {
                
                // Perform the wait
                TimeUnit.SECONDS.sleep(2);
            } 
            
            // Should never trigger
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            int input = -1;

            while(!gameOver) {

                // Print whose turn it is
                if(hostTurn) {

                    // To the host
                    System.out.println("\nAI's Turn!\n");

                    // To the client
                    pr.println("\nAI's Turn!\n");

                    // Print the opponent's last move
                    if(input != -1) {

                        // To the host
                        System.out.println("Opponent placed a piece in column " + input + "\n");

                        // To the client
                        pr.println("You placed a piece in column " + input + "\n");
                    }
                } else {

                    // Print to the host
                    System.out.println("\nOpponent's Turn!\n");

                    // To client
                    pr.println("\nYour Turn!\n");

                    // Print the opponent's last move
                    if(input != -1) {

                        // To host
                        System.out.println("AI placed a piece in column " + input + "\n");

                        // To client
                        pr.println("AI placed a piece in column " + input + "\n");
                    }
                }

                // Send the state of the board to the client
                board.sendBoardState(pr);

                // Print the current board state on the console
                board.printBoard();

                // If it's your turn
                if(hostTurn) {
                    
                    // Notify the client it is waiting for a move from the host
                    pr.println("\nWaiting for the AI to make a move...");
                    pr.println("WAIT");
                    pr.flush();

                    System.out.println("\nCalculating next move...");

                    // Get the user input from the keyboard
                    input = aiPlayer.getMove();

                    try {
                
                        // Perform the wait
                        TimeUnit.SECONDS.sleep(2);
                    } 
                    
                    // Should never trigger
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } 

                // If client turn, get input from client
                else {

                    // Prompt the client for input
                    pr.println("\nEnter the column number in which you would like to place your piece:");

                    // This stands for "move" and signals the client to stop reading and make a move
                    pr.println("m");
                    pr.flush();

                    // Tell the host it is waiting for the client
                    System.out.println("\nWaiting for your opponent to make a move...");

                    // Get the client input and validate it
                    input = br.read();      
                    boolean valid = isValidInput(input, pr);  

                    // If invalid input...
                    while(!valid) {

                        // Get the client input until it is valid while sending errors to client
                        input = br.read();  
                        valid = isValidInput(input, pr); 
                    }     

                    // Notify the client a valid input has been recieved
                    pr.println("VALID");
                    pr.flush();   
                }

                // Place the piece on the board and return if the game is over or not 
                gameOver = board.placePiece(input, p1Turn);

                // Debugging
                // for(int depth: board.getBoardMap()) {
                //     System.out.print(depth + " ");
                // }
                // System.out.println();

                // If a stalemate is found, the game should also be considered over
                if(isStalemate()) {
                    gameOver = true;
                    stalemate = true;
                }

                numMovesPlayed++;

                // Note that it is either no longer the human's (or AI's) turn 
                hostTurn = !hostTurn;
                p1Turn = !p1Turn;

            }

            // It is the next player's turn
            hostTurn = !hostTurn;
            p1Turn = !p1Turn;
            
            // Client needs an extra line break before sending it the final board state
            pr.println();
            board.sendBoardState(pr);

            // Notify the players who has won the game and that the game is over
            if(!stalemate) {
                System.out.println();
                board.printBoard();

                // To client
                pr.println((hostTurn ? "\nThe AI wins!\n" : "\nYou win!\n"));

                // To host
                System.out.println((hostTurn ? "\nThe AI wins!\n" : "\nThe network (client) player wins!\n"));
            } 
            
            // Notify the players that a stalemate has been reached and that the game is over
            else {
                System.out.println();
                board.printBoard();

                // To client
                pr.println("\nStalemate!\n");

                // To host
                System.out.println("\nStalemate!\n");
            }

            // Tell the client the game is ended
            pr.println("END");
            pr.flush();

        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @return a boolean representing if a stalemate has been found
     * Currently, a stalemate is when the board is completely full and no winner has been determined
     */
    public boolean isStalemate() {

        // Check each position in the board map and if there is still space available, no stalemate
        for(int depth: board.getBoardMap()) {

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
    public int getMoveInput() {

        int column = 0;

        System.out.print("\nEnter the column number in which you would like to place your piece:\n");
           
        // Loop until the user submits a valid submission
        boolean valid = false;
        while(!valid) {
        
            // Try block catches non-numeric input
            try {
                column = userInput.nextInt();
                valid = isValidInput(column);
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
     * Determines if user input is valid given the board state
     * @param column int that represents the move
     * @return a boolean, true if move is valid, else false
     */
    public boolean isValidInput(int column) {

         // Loop until the user submits a valid submission
         if(column <= 0 || column > 7 || board.getBoardMap()[column - 1] < 0) {

            // Retry if user submits a number whose column doesn't exist
            if(column <= 0 || column > 7) {
                System.out.println("Please enter a valid integer between 1 and 7 inclusive");
                return false;
            }
            
            // Retry if the user tries to place a piece in a column thats already full
            if(board.getBoardMap()[column - 1] < 0) {
                System.out.println("Column " + column + " is already full of pieces, please pick another column");
                return false;
            }

        }

        return true;
        
    }

    /**
     * Determines if user input is valid given the board state
     * Writes errors to client, rather than console
     * @param column int that represents the move
     * @param pr PrintWriter that writes the output to a client rather than the console
     * @return a boolean, true if move is valid, else false
     */
    public boolean isValidInput(int column, PrintWriter pr) {

        // Loop until the user submits a valid submission
        if(column <= 0 || column > 7 || board.getBoardMap()[column - 1] < 0) {

           // Retry if user submits a number whose column doesn't exist
           if(column <= 0 || column > 7) {
               pr.println("Please enter a valid integer between 1 and 7 inclusive");
               pr.flush();
               return false;
           }
           
           // Retry if the user tries to place a piece in a column thats already full
           if(board.getBoardMap()[column - 1] < 0) {
               pr.println("Column " + column + " is already full of pieces, please pick another column");
               pr.flush();
               return false;
           }

       }

       return true;
       
   }

    /**
     * Used to ask the user if wanting to be player 1 or 2
     * @return an integer signifying which player the user chooses to be
     */
    public int getOrderInput() {
        int input = 0;
           
        // Loop until the user submits a valid submission
        while(input != 1 && input != 2) {
        
            System.out.print("\nWould you like to move 1st (enter 1) or 2nd (enter 2)?\n");

            // Try block catches non-numeric input
            try {
                input = userInput.nextInt();
            } 
            
            // Catch the error when user tries to enter non-numeric data
            catch(Exception e) {
                userInput.next();
                input = 0;
            }

        }

        return input;
    }

}      