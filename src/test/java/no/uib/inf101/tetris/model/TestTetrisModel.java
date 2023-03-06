package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.tetromino.PatternedTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;

public class TestTetrisModel {
    
    @Test
    public void initialPositionOfO() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        ViewableTetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getTetroTiles()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
    }
    
    @Test
    public void initialPositionOfI() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("I");
        ViewableTetrisModel model = new TetrisModel(board, factory);

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model.getTetroTiles()) {
            tetroCells.add(gc);
        }

        assertEquals(4, tetroCells.size());
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 3), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 6), 'I')));
    }

    @Test
    public void testSuccessfulMovement() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("O");
        ControllableTetrisModel model = new TetrisModel(board, factory);

        // move down one row. method should return true
        assertTrue(model.moveTetromino(1, 0));

        ViewableTetrisModel model2 = (ViewableTetrisModel) model;

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTetroTiles()) {
            tetroCells.add(gc);
        }
        
        // check position
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 4), 'O')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 5), 'O')));

    }

    @Test
    public void testFailedMovement() {
        int row = 20;
        int col = 10;
        TetrisBoard board = new TetrisBoard(row, col);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        ControllableTetrisModel model = new TetrisModel(board, factory);

        // colored edges
        board.set(new CellPosition(0, 0), 'g');
        board.set(new CellPosition(0, col - 1), 'y');
        board.set(new CellPosition(row - 1, 0), 'r');
        board.set(new CellPosition(row - 1, col - 1), 'b');

        for (int i = 0; i < 10; i++) {
            model.moveTetromino(0, 1);
        }

        // move up one row. method should return false
        // when moving towards grid boundary
        assertFalse(model.moveTetromino(-1, 0));

        // move right one column. method should return false
        // when moving towards a colored tile
        assertFalse(model.moveTetromino(0, 1));

        ViewableTetrisModel model2 = (ViewableTetrisModel) model;

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTetroTiles()) {
            tetroCells.add(gc);
        }
        
        // check position is within bounds and outside colored tile to the right
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 6), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 7), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 8), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 7), 'T')));

    }

    @Test
    public void testSuccessfulRotation() {
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        ControllableTetrisModel model = new TetrisModel(board, factory);

        // rotate T block
        model.moveTetromino(1, 0);
        assertTrue(model.rotateTetromino());

        ViewableTetrisModel model2 = (ViewableTetrisModel) model;
        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTetroTiles()) {
            tetroCells.add(gc);
        }

        // check if block rotated correctly
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 5), 'T')));

        // rotate block 4 times
        model = (ControllableTetrisModel) model2;
        for (int i = 0; i < 4; i++) {
            model.rotateTetromino();
        }
        model2 = (ViewableTetrisModel) model;

        List<GridCell<Character>> tetroCells2 = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTetroTiles()) {
            tetroCells2.add(gc);
        }
        
        // check position is the same as original
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 5), 'T')));

    }

    @Test
    public void testFailedRotation() {
        int row = 20;
        int col = 10;
        TetrisBoard board = new TetrisBoard(row, col);
        TetrominoFactory factory = new PatternedTetrominoFactory("S");
        ControllableTetrisModel model = new TetrisModel(board, factory);

        // colored edges
        board.set(new CellPosition(0, 0), 'g');
        board.set(new CellPosition(0, col - 1), 'y');
        board.set(new CellPosition(row - 1, 0), 'r');
        board.set(new CellPosition(row - 1, col - 1), 'b');

        model.moveTetromino(0, 2);
    
        // rotation should return false since T block cant rotate out of board.
        assertFalse(model.rotateTetromino());

        model.moveTetromino(1, 1);
        model.rotateTetromino();
        // rotation should return false since T block cant rotate into yellow corner
        assertFalse(model.rotateTetromino());

        ViewableTetrisModel model2 = (ViewableTetrisModel) model;

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTetroTiles()) {
            tetroCells.add(gc);
        }
        
        // check position is within bounds and outside colored tile to the right
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 7), 'S')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 7), 'S')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 8), 'S')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 8), 'S')));

    }
}
