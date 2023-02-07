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

            System.out.println("Please enter between 0 and 2 arguments (inclusive, corresponds to 1 arg per player)\nValid options and values are as follows:");
            printOptions(validOptions);
            System.exit(0);

        } 
        
        // At least 1 other player is not a local human player
        else if(args.length > 0 && args.length <= 2) {

            // Initialize the set of valid <option>=<value> pairs
            HashMap<String, String[]> validOptions = new HashMap<>();

            // These are the valid pairs (not case sensitive)
            validOptions.put("ai", new String[] {"basic"});

            String[][] processedOptions = processOptions(args, validOptions);
            for(String[] s: processedOptions) {
                for(String st: s) {
                    System.out.print(st + " ");
                }
                System.out.println();
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
    public static String[][] processOptions(String[] args, HashMap<String, String[]> validOptions) {

        String[][] options = new String[2][2];

        int argCounter = 0;
        for(String arg: args) {
            String[] option = arg.split("=");

            // If an argument was not correctly formatted, print an error message and terminate the program
            if(option.length != 2) {
                System.out.println("Invalid argument format, must be entered as <option>=<value> ('=' is a reserved character)\nCurrently available <option>=<value> pairs are:");
                printOptions(validOptions);
                return options;
            } 
            
            else {

                option[0] = option[0].toLowerCase();
                option[1] = option[1].toLowerCase();

                System.out.println(option[0] + ", " + option[1]);

                String[] values = set.get(option[0]);

                if(values != null) {

                    boolean exists = false;

                    for(String val: values) {
                        if(val.equals(options[1])) {
                            exists = true;
                            break;
                        }
                    }

                    if(exists) {
                        options[argCounter] = option;
                    } else { 
                        System.out.println("\"" + option[1] + "\" is an invalid value for option " + option[0] + "\nValid <option>=<value> pairs are:");
                        printOptions(validOptions);
                    }
                    
                } else {
                    System.out.println("\"" + option[0] + "\" is an invalid option\nValid <option>=<value> pairs are:");
                    printOptions(validOptions);
                }
            }
            argCounter++;
        }
        return options;
    }
}

public static void printOptions(HashMap<String, String[]> validOptions) {

    for(String option: validOptions.keySet()) {

        System.out.println(option + ":");
        for(String value: validOptions.get(option)) {
            System.out.println("\t" + value);
        }

    }

}