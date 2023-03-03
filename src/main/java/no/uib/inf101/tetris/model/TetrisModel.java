package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel{
    
    final TetrisBoard Board;

    public TetrisModel(TetrisBoard Board) {
        this.Board = Board;
    }

    @Override
    public GridDimension getDimension() {
        return this.Board;
    }

    @Override
    public Iterable<GridCell<Character>> getTilesOnBoard() {
       return this.Board;

    }

    
}
