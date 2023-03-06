package no.uib.inf101.tetris.controller;

public interface ControllableTetrisModel {
    
    /**
     * moveTetromino is used to move a tetromino-object across the board.
     * @param deltaRow is number of rows to move.
     * @param deltaCol is number of columns to move.
     * @return true if movement was successful. 
     * returns false if tiles are obstructed or spot is out of bounds
     * 
     */
    boolean moveTetromino(int deltaRow, int deltaCol);


    /**
     * rotateTetromino is used to rotate a tetromino-object clock-wise at its current posistion.
     * @return true if rotation was successful. 
     * returns false if tiles are obstructed or spot is out of bounds
     */
    boolean rotateTetromino();

}
