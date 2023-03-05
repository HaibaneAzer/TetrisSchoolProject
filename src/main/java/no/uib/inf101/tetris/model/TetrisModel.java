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
    private final Tetromino fallingTetro; 

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

        for (GridCell<Character> gc : this.fallingTetro) {

            if (!isValidPos(new CellPosition(gc.pos().row() + deltaRow, gc.pos().col() + deltaCol))) {
                return false;
            }
        }
        
        this.fallingTetro.shiftedBy(deltaRow, deltaCol);
        

        return true;

    }

    private boolean isValidPos(CellPosition Pos) {
        
        boolean withinBoard = Pos.row() >= 0 && Pos.row() < this.Board.rows() &&
                              Pos.col() >= 0 && Pos.col() < this.Board.cols();
        
        // get tiles of board
        
        // check if tetromino does not share pos with another tetro
    
    
        return withinBoard;
    }
    
}
