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
     * @param clockwise is true, false is counter-clockwise
     * @return true if rotation was successful. 
     * returns false if tiles are obstructed or spot is out of bounds
     */
    boolean rotateTetromino(boolean clockwise);


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

    /**
     * setGameState changes current game status to
     * input game status
     * @param status is of type GameState
     * @return is of type GameState
     */
    GameState setGameState(GameState status);

    /**
     * resetBoard sets all tiles on board to blank.
     * Used when player continues after game over.
     * @return new board
     */
    void resetBoard();

    /**
     * getTimePerTick gets milliseconds for every game tick.
     * standard is 1000 (int value) which sets gametick to every second.
     * @return int value representing number of milliseconds per tick.
     */
    int getTimePerTick();

    /**
     * clockTick is an in-game clock which moves a tetromino-object down for every tick.
     */
    boolean clockTick();



}
