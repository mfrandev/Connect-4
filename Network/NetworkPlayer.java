package Network;

import java.net.Socket;
import java.net.ServerSocket;

public class NetworkPlayer {

    // Save the socket to communicate with the client
    private Socket socket;

    // Save the server socket
    private ServerSocket server;

    // Note if this player is player 1 (or 2)
    private int player;

    /**
     * Constructor to initialize communication with client
     * @param server ServerSocket object
     * @param socket Socket object
     */
    public NetworkPlayer(ServerSocket server, Socket socket) {
        setServer(server);
        setSocket(socket);
    }

    /**
     * Constructor to initialize client communication with server
     * @param socket Socket object
     */
    public NetworkPlayer(Socket socket) {
        setSocket(socket);
    }

    /**
     * Set the socket for communicating withi client
     * @param socket
     */
    private void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * Set the server socket
     * @param server
     */
    private void setServer(ServerSocket server) {
        this.server = server;
    }

    /**
     * Get the socket for two-way client communication
     * @return Socket connected to client
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Set which player this network player is playing as
     */
    public void setPlayer(int player) {
        this.player = player;
    }

    /**
     * @return an integer representing whether this network player is player 1 or 2
     */
    public int getPlayer() {
        return player;
    } 
    
}
