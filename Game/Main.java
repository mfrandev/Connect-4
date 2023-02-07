package Game;

import java.util.HashMap;

public class Main {

    /**
     * Main method that creates the board, rules object, and starts the game loop
     */
    public static void main(String[] args) {

        // Create the board
        Board b = new Board();

        // Create a rules object 
        Rules r = new Rules(b);

        // If user specified any parameters
        // Too many arguemnts
        if(args.length > 2) {

            System.out.println("Please enter between 0 and 2 arguments (inclusive, corresponds to 1 arg per player)");
            System.exit(0);

        } 
        
        // At least 1 other player is not a local human player
        else if(args.length > 0 && args.length <= 2) {

            

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
    public String[][] processOptions(String[] args) {



        // Initialize the set of valid <option>=<value> pairs
        HashMap<String, String[]> validOptions = new HashMap<>();

        // These are the valid pairs
        validOptions.put("ai", new String[] {"basic"});

            for(String arg: args) {
                String[] option = arg.split("=");

                // If an argument was not correctly formatted, print an error message and terminate the program
                if(option.length != 2) {
                    System.out.println("Invalid argument format, must be entered as <option>=<value>\nCurrently available <option>=<value> pairs are:");
                    return new String[]{};
                } else {
                    System.out.println(option[0] + ", " + option[1]);
                    String[] values = set.get(option[0]);
                    if(values != null) {
                        for(String val: values) {
                            if(val.equals(options[1])) {

                            }
                        }
                    } else {

                    }
                }
            }

    }
}