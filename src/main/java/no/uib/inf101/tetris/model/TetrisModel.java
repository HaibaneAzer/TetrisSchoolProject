package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel{
    
    private final TetrisBoard Board;
    final TetrominoFactory tetroMaker;
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

        
        if (!isValidPos(this.fallingTetro.shiftedBy(deltaRow, deltaCol))) {
            return false;
        }
        if (!ColoredTileOverlap(this.fallingTetro.shiftedBy(deltaRow, deltaCol))) {
            return false;
        }

        this.fallingTetro = this.fallingTetro.shiftedBy(deltaRow, deltaCol);
        return true;

    }
    
    private boolean isValidPos(Tetromino shiftedTetro) {
        // check on board
        for (GridCell<Character> gc : shiftedTetro) {
            if (!(gc.pos().row() >= 0 && gc.pos().row() < this.Board.rows() &&
                gc.pos().col() >= 0 && gc.pos().col() < this.Board.cols())) {
                return false;
           }
        }
        return true;
        
    }
    
    private boolean ColoredTileOverlap(Tetromino shiftedTetro) {
        // check overlap
        for (GridCell<Character> gc2 : this.Board) {
            for (GridCell<Character> gc : shiftedTetro) {
                if (gc2.value() != '-' 
                && gc.pos().row() == gc2.pos().row()
                && gc.pos().col() == gc2.pos().col()) {
                return false;
                }
            }
        }
        return true;

    }
    // NB: implement super rotation?
    @Override
    public boolean rotateTetromino() {

        if (!isValidPos(this.fallingTetro.rotateBy())) {
            return false;
        }
        if (!ColoredTileOverlap(this.fallingTetro.rotateBy())) {
            return false;
        }

        this.fallingTetro = this.fallingTetro.rotateBy();
        return true;
    }

}
