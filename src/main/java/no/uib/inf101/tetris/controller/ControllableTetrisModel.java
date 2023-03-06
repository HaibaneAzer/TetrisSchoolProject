package no.uib.inf101.tetris.controller;

public interface ControllableTetrisModel {
    
    /**
     * moveTetromino is used to move a tetromino-object across the board
     * 
     */
    boolean moveTetromino(int deltaRow, int deltaCol);


    /**
     * 
     * 
     * 
     * 
     */
    boolean rotateTetromino();

}
