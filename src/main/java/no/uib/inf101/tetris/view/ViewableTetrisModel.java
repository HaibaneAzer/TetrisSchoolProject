package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.GridCell;

public interface ViewableTetrisModel {
    
    /** 
    * getDimension returns an GridDimension object.
    * Has no parameters.
    * @return the GridDimension of a model.
    */
    GridDimension getDimension();


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
}
