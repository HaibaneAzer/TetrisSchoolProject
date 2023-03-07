package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

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


    /**
     * dropTetromino is used to move a tetromino-object down until moveTetromino method returns false,
     * After which "glues" the tetromino-object to the Board and spawns a new tetromino-object.
     * @return true if drop was successful, tetromino was glued and a new one spawns.
     * if false, see {@link #getGameState}
     */
    boolean dropTetromino();

    /**
     * getGameState is called to see if game status is either
     * ACTIVE_GAME or GAME_OVER.
     * @return GameState-object.
     */
    GameState getGameState();

}
