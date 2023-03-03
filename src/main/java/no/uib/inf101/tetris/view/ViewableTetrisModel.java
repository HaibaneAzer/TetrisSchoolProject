package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.GridCell;

public interface ViewableTetrisModel {
    
    /** 
    * getDimension returns an GridDimension object.
    * Has no parameters.
    */
    GridDimension getDimension();


    /**
     * getTilesOnBoard returns an object, which when it's  
     * being iterated over, gives all positions on the board
     * with their respective symbol.
     */
    Iterable<GridCell<Character>> getTilesOnBoard();



    
}
