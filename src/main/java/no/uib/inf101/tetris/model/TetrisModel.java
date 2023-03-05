package no.uib.inf101.tetris.model;

import java.util.ArrayList;
import java.util.List;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel{
    
    private final TetrisBoard Board;
    private final TetrominoFactory tetroMaker;
    private Tetromino fallingTetro; 

    public TetrisModel(TetrisBoard Board, TetrominoFactory tetroMaker) {
        this.Board = Board;
        this.tetroMaker = tetroMaker;
        this.fallingTetro = tetroMaker.getNext().shiftedToTopCenterOf(Board);
    }

    @Override
    public GridDimension getDimension() {
        return this.Board;
    }

    @Override
    public Iterable<GridCell<Character>> getTilesOnBoard() {
       return this.Board;

    }

    @Override
    public Iterable<GridCell<Character>> getTetroTiles() {
        return this.fallingTetro;
    }

    @Override
    public boolean moveTetromino(int deltaRow, int deltaCol) {

        List<GridCell<Character>> TetroCells = new ArrayList<>();
        // check bounds
        for (GridCell<Character> gc : this.fallingTetro) {
            TetroCells.add(gc);
            if (!isValidPos(new CellPosition(gc.pos().row() + deltaRow, gc.pos().col() + deltaCol))) {
                return false;
            }
        }
        // check overlap
        if (!ColoredTileOverlap(TetroCells, deltaRow, deltaCol)) {
            return false;
        }
        
        this.fallingTetro = this.fallingTetro.shiftedBy(deltaRow, deltaCol);
        return true;

    }
    
    private boolean isValidPos(CellPosition Pos) {
        boolean withinBoard = Pos.row() >= 0 && Pos.row() < this.Board.rows() &&
                              Pos.col() >= 0 && Pos.col() < this.Board.cols();
        return withinBoard;
    }
    
    private boolean ColoredTileOverlap(List<GridCell<Character>> TetroTiles, int deltaRow, int deltaCol) {
        // check overlap
        for (GridCell<Character> gc2 : this.Board) {
            for (int i = 0; i < TetroTiles.size(); i++) {
                // NB: make a method for this. checks all tiles that are not blank on board
                // and check if tetroTilePos + deltaPos is equal to pos of colored tile.
                if (gc2.value() != '-' && TetroTiles.get(i).pos().row() + deltaRow == gc2.pos().row()
                                       && TetroTiles.get(i).pos().col() + deltaCol == gc2.pos().col()) {
                    return false;
                }
            }
        }
        return true;

    }

}
