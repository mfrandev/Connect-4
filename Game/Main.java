package Game;

import AI.AI;
import AI.BasicAI;

import java.util.HashMap;
import java.util.Map;

public class Main {

    /**
     * Main method that creates the board, rules object, and starts the game loop
     */
    public static void main(String[] args) {

        // Create the board 
        Board b = new Board();

        // Create a rules object 
        Rules r = new Rules(b);

        // Initialize the set of valid <option>=<value> pairs
        HashMap<String, String[]> validOptions = new HashMap<>();

        // These are the valid pairs (not case sensitive)
        validOptions.put("ai", new String[] {"basic"});

        // If user specified any parameters
        // Too many arguemnts
        if(args.length > 2) {

            // Tell the user what went wrong, how to fix it next time, and terminate the program
            System.out.println("\nPlease enter between 0 and 2 arguments (inclusive, corresponds to 1 arg per player)\nValid options and values are as follows:");
            printOptions(validOptions);
            System.exit(0);

        } 
        
        // At least 1 other player is not a local human player
        else if(args.length > 0 && args.length <= 2) {

            // Return the <option>=<value> pair(s) the user entered
            // Returns an empty array if an invalid pair was entered
            String[][] processedOptions = processOptions(args, validOptions);

            // If invalid args were specified, terminate 
            if(processedOptions.length == 0) System.exit(0);

            // Process 1 argument
            if(args.length == 1) {

                // If the argument entered specified an AI player
                if(processedOptions[0][0].equals("ai")) {

                    // Create the AI player and play the game
                    r.play(createAIPlayer(processedOptions[0][1], b));
                } 
                
                // TODO implement human vs human over network (this machine is server)
                else if(processedOptions[0][0].equals("net")) {

                    // TODO create a server socket 

                    // TODO wait for connection

                    // TODO start the game loop

                }
            } 
            
            // Process 2 arguments
            else {
                // TODO
                // If the argument entered specified an AI player
                if(processedOptions[0][0].equals("ai")) {

                    // Create the AI player and move to the second argument
                    AI aiPlayer1 = createAIPlayer(processedOptions[0][1], b);

                    // If the second argument is also an AI player
                    if(processedOptions[1][0].equals("ai")) {

                        // Create the AI player and play the game
                        AI aiPlayer2 = createAIPlayer(processedOptions[1][1], b);

                        r.play(aiPlayer1, aiPlayer2);

                    } 

                    // Coming soon
                    //TODO AI vs Client (this machine is server)
                    // else if() {

                    // }

                } 
                
                // Coming soon
                // TODO implement Client vs AI ONLY (no client vs client)
                // else if() {

                // }
            }

        } 
        
        // Both players are local and human
        else if(args.length == 0) {

            // Start the game loop
            r.play();
        }
    }

    /**
     * Determine if the options the user specified when running the command line are valid
     * @return a String[][] of the parsed options if valid, otherwise an empty String[][]
     */
    public static String[][] processOptions(String[] args, Map<String, String[]> validOptions) {

        // Create a list of options to store the processed data
        String[][] options = new String[2][2];

        // Used as an index for options
        int argCounter = 0;

        // For each argument
        for(String arg: args) {

            // Split by '=' since in <option>=<value> format
            String[] option = arg.split("=");

            // If an argument was not correctly formatted, print an error message, and return an empty array to signify failure
            if(option.length != 2) {

                System.out.println("\nInvalid argument format, must be entered as <option>=<value> ('=' is a reserved character)\nCurrently available <option>=<value> pairs are:");
                printOptions(validOptions);
                return new String[0][0];

            } 
            
            // If this argument was correctly formatted, check the data is meaninful
            else {

                // Make options non-case sensitive
                option[0] = option[0].toLowerCase();
                option[1] = option[1].toLowerCase();

                // Check if the option exists
                String[] values = validOptions.get(option[0]);

                // If it does, check if its value exists too
                if(values != null) {

                    boolean exists = false;

                    for(String val: values) {

                        // If the value exists, note it and stop looking for it
                        if(val.equals(option[1])) {
                            exists = true;
                            break;
                        }
                    }

                    // If the value exists, add the <option>=<value> pair to the options array
                    if(exists) {
                        options[argCounter] = option;
                    } 
                    
                    // Otherwise note that the value is not invalid, print an error message, and return an empty array to signify failure
                    else { 
                        System.out.println("\n\"" + option[1] + "\" is an invalid value for option " + option[0] + "\nValid <option>=<value> pairs are:");
                        printOptions(validOptions);
                        return new String[0][0];

                    }
                    
                } 
                
                // If the option does not exist, print an error message, and return an empty array to signify failure
                else {
                    System.out.println("\n\"" + option[0] + "\" is an invalid option\nValid <option>=<value> pairs are:");
                    printOptions(validOptions);
                    return new String[0][0];
                }
            }

            // Increment the next index in the options array if a valid pair was found
            argCounter++;
        }

        // All arguments were valid, return parsed data
        return options;
    }

    /**
     * Print the set of <option(s)> and <value(s)> the parser recognizes for the user
     */
    public static void printOptions(Map<String, String[]> validOptions) {

        // Iterate over the map keyset (options) and print each corresponding value (values)
        for(String option: validOptions.keySet()) {

            System.out.println("\n" + option + ":");
            for(String value: validOptions.get(option)) {
                System.out.println("- " + value);
            }

        }

        System.out.println();

    }

    /**
     * Takes the type of AI to create as a string and the board (to initialize the object)
     * @return an AI player
     */
    public static AI createAIPlayer(String type, Board b) {

        // Creates a basic AI 
        if(type.equals("basic")) {
            return new BasicAI(b);
        }

        // This case should never be executed
        // since the program would have noticed the user entered an invalid AI type earlier in execution
        // Here to satisfy the code linter
        else return null;

    }

}