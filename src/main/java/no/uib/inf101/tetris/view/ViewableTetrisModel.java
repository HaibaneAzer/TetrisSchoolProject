package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.grid.GridCell;

public interface ViewableTetrisModel {
    
    /** 
    * getDimension returns an GridDimension object.
    * Has no parameters.
    * @return the GridDimension of a model.
    */
    GridDimension getDimension();

    /** 
    * getNextTetrominoreturns an GridDimension object.
    * Has no parameters.
    * @return the TetrominoFactory-object.
    */
    Tetromino[] getUpcomingTetroTiles();

    /**
     * getTilesOnBoard returns an object, which when it's  
     * being iterated over, gives all positions on the board
     * with their respective symbol.
     * @return an Iterable list of tile values on given board.
     */
    Iterable<GridCell<Character>> getTilesOnBoard();


    /**
     * getTetroTIles returns an object, which when it's
     * being iterated over, gets all positions on the board
     * where tetromino-objects exists.
     * @return an Iterable list of tile values where tetromino-objects exist.
     */
    Iterable<GridCell<Character>> getTetroTiles();


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
     * resetBoard sets all tiles on board to blank, as well as reseting score, level, 
     * time delay and the list for current and upcoming tetromino.
     * Used when player continues after game over.
     * @return new board
     */
    void resetBoard();

    /**
     * getCurrentScore is getter for score
     * calculated from number of rows removed. 
     * @return current score as int
     */
    int getCurrentScore();

    /**
     * getCurrentLevel is getter for what level the player is at.
     * calculated using rows removed.
     * @return current level as int
     */
    int getCurrentLevel();


}
