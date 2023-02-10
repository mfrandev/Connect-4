package Network.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import Network.NetworkPlayer;

public class Server {

    /**
     * Hide the public default constructor
     */ 
    private Server() {

    }

    /**
     * 
     * @param timeout integer value representing how long the server should wait for a connection before terminating
     * @return a NetworkPlayer object, containing all of the communication channels for a game
     */
    public static NetworkPlayer createNetworkPlayer(int timeout) {

        // Try with resources to automatically shut server socket when destroyed
        try(ServerSocket server = new ServerSocket(5050)) {

            // Adjust the timeout for seconds, and do not allow integer overflow (since max int is too long anyway)
            timeout = timeout > 2147483 ? Integer.MAX_VALUE : timeout * 1000;

            // Could not set the timeout, so return null and try again
            if(!(setTimeout(server, timeout))) return null;

            // Tell the user what is happening
            System.out.println("Waiting for network opponent (" + (timeout / 1000) + " seconds)...");

            // Wait for connection from other player
            Socket playerSocket = server.accept();

            // Print some success message
            System.out.println("Network player connected :)");

            // Return the network player
            return new NetworkPlayer(server, playerSocket);

        } 
        
        // Something went wrong when waiting for client connection
        catch (Exception e) {

            String message = "";
            
            // Timeout triggered
            if(e instanceof SocketTimeoutException) {
                message = "timeout ocurred";
            }

            // Misc IO error
            else if(e instanceof IOException) {
                message = "IO exception, please try again";
            }

            // No config currently exists, but case exists
            else if(e instanceof SecurityException) {
                message = "security manager does not allow operation";
            }

            // No config currently exists, but case exists
            else if(e instanceof java.nio.channels.IllegalBlockingModeException) {
                message = " socket channel is in non-blocking mode";
            }

            // Print the error
            System.out.println("Could not find player: " + message);

            // Return null because something went wrong
            return null;
        }
    
    }

    /**
     * Set the timeout threshold for the server while waiting for a connection
     * @param server object whose timeout is set
     * @param timeout length of time in seconds for the timeout
     * @return a boolean, true represents the value was set, false an error occured
     */
    public static boolean setTimeout(ServerSocket server, int timeout) {

        // Try/catch block for setting the timeout
        try {

            // Set timeout to specified value (in seconds)
            server.setSoTimeout(timeout);

            // Return true on success
            return true;
        } 
        
        // Exception was found
        catch(Exception e) {
            String message = "";

            // Should never be triggered
            if(e instanceof IllegalArgumentException) {
                message = ", please chose a positive timeout value when restarting the server";
            } 

            // Network error
            else if(e instanceof SocketException) {
                message = ", try checking your internet connection when restarting the server";
            }

            // Print the problem to the console
            System.out.println("There was an issue setting the timeout" + message);

            // Return false because something went wrong
            return false;
        }
    }
    
}
