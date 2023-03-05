package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.controller.TetrisController;
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
        TetrisBoard board = new TetrisBoard(20, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        ControllableTetrisModel model = new TetrisModel(board, factory);

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
}
