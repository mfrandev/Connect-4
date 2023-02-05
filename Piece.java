public class Piece {

    // Represents whether the space is empty, if player 1 occupies the space, or player 2 occupies the space
    int player;

    // Store the size of the horizontal sequence that this piece belongs to 
    int horizontalNumConnected;

    // Store the size of the vertical sequence that this piece belongs to 
    int verticalNumConnected;

    // Store the size of the left diagonal sequence that this piece belongs to 
    int leftDiagonalNumConnected;

    // Store the size of the right diagonal sequence that this piece belongs to 
    int rightDiagonalNumConnected;

    public Piece(int player) {

        setPlayer(player);
        setHorizontalNumConnected(0);
        setVerticalNumConnected(0);
        setLeftDiagonalNumConnected(0);
        setRightDiagonalNumConnected(0);

    }

    /**
     * Set the player that owns this piece
     */
    public void setPlayer(int player) {
        this.player = player;
    }

    /**
     * Return which player owns this piece, if any
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Setter for horizontalNumConnected
     * Stores the size of the horizontal sequence that this piece belongs to 
     */
    public void setHorizontalNumConnected(int horizontalNumConnected) {
        this.horizontalNumConnected = horizontalNumConnected;
    }

    /**
     * Getter for horizontalNumConnected
     * @return an int containing the size of the horizontal sequence that this piece belongs to 
     */
    public int getHorizontalNumConnected() {
        return horizontalNumConnected;
    }

    /**
     * Setter for verticalNumConnected
     * Stores the size of the vertical sequence that this piece belongs to 
     */
    public void setVerticalNumConnected(int verticalNumConnected) {
        this.veritcalNumConnected = veritcalNumConnected;
    }

    /**
     * Getter for verticalNumConnected
     * @return an int containing the size of the vertical sequence that this piece belongs to 
     */
    public int getVerticalNumConnected() {
        return verticalNumConnected;
    }

    /**
     * Setter for leftDiagonalNumConnected
     * Stores the size of the leftDiagonal sequence that this piece belongs to 
     */
    public void setLeftDiagonalNumConnected(int leftDiagonalNumConnected) {
        this.leftDiagonalNumConnected = leftDiagonalNumConnected;
    }

    /**
     * Getter for leftDiagonalNumConnected
     * @return an int containing the size of the left diagonal sequence that this piece belongs to 
     */
    public int getLeftDiagonalNumConnected() {
        return leftDiagonalNumConnected;
    }

    /**
     * Setter for rightDiagonalNumConnected
     * Stores the size of the rightDiagonal sequence that this piece belongs to 
     */
    public void setRightDiagonalNumConnected(int rightDiagonalNumConnected) {
        this.rightDiagonalNumConnected = rightDiagonalNumConnected;
    }

    /**
     * Getter for rightDiagonalNumConnected
     * @return an int containing the size of the right diagonal sequence that this piece belongs to 
     */
    public int getRightDiagonalNumConnected() {
        return rightDiagonalNumConnected;
    }

    /**
     * Compute the size of the largest continuous sequence of pieces that this piece belongs to
     */
    public int getMaxNumConnected() {
        return Math.max(Math.max(horizontalNumConnected, verticalNumConnected), Math.max(leftDiagonalNumConnected, rightDiagonalNumConnected));
    }
    
}
