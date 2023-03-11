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
    
    private TetrisBoard getTetrisBoardWithContents(String[] StringToBoard) {
        TetrisBoard board = new TetrisBoard(StringToBoard.length, StringToBoard[0].length());
        
        for (int row = 0; row < StringToBoard.length; row++) {
            for (int col = 0; col < StringToBoard[row].length(); col++) {
                board.set(new CellPosition(row, col), StringToBoard[row].charAt(col));
            }
        }
        return board;
    }

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
        assertTrue(model.rotateTetromino(true));

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
            model.rotateTetromino(true);
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
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "-----",
            "-----",
            "-----",
            "rrrrr",
            "r---r",
            "r-rrr"
            
        });
        TetrominoFactory factory = new PatternedTetrominoFactory("L");
        ControllableTetrisModel model = new TetrisModel(board, factory);

        model.moveTetromino(4, 0);
    
        // rotation should return false since L block is enclosed and superRotation has
        // no available moves
        assertFalse(model.rotateTetromino(true));

        model.moveTetromino(-3, 0);
        model.rotateTetromino(true);
        model.rotateTetromino(true);
        // rotation should return true since super rotation will move it up
        assertTrue(model.rotateTetromino(true));

        ViewableTetrisModel model2 = (ViewableTetrisModel) model;

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTetroTiles()) {
            tetroCells.add(gc);
        }
        
        // check position is within bounds and outside colored tile to the right
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 2), 'L')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 2), 'L')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 2), 'L')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 3), 'L')));

    }

    @Test
    public void testSuccessfulDrop() {
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

        // check if drop succeeded
        assertTrue(model.dropTetromino());

        ViewableTetrisModel model2 = (ViewableTetrisModel) model;

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTilesOnBoard()) {
            tetroCells.add(gc);
        }

        // check position is on bottom of board
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(19, 4), 'S')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(19, 5), 'S')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(18, 5), 'S')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(18, 6), 'S')));
    }

    @Test
    public void testClockTickMovement() {
        int row = 20;
        int col = 10;
        TetrisBoard board = new TetrisBoard(row, col);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        ControllableTetrisModel model = new TetrisModel(board, factory);

        assertTrue(model.clockTick());

        ViewableTetrisModel model2 = (ViewableTetrisModel) model;

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTetroTiles()) {
            tetroCells.add(gc);
        }

        // check position is moved one down.
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 6), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 5), 'T')));
    }

    @Test
    public void testClockTickFail() {
        int row = 20;
        int col = 10;
        TetrisBoard board = new TetrisBoard(row, col);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        ControllableTetrisModel model = new TetrisModel(board, factory);

        assertTrue(model.clockTick());

        for (int i = 0; i < row - 3; i++) {
            model.clockTick();
        }
        // return false when tetromino can't move anymore
        assertFalse(model.clockTick());

        ViewableTetrisModel model2 = (ViewableTetrisModel) model;

        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTilesOnBoard()) {
            tetroCells.add(gc);
        }

        // check if T block is at bottom and glued.
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(18, 4), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(18, 5), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(18, 6), 'T')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(19, 5), 'T')));
    }

    @Test
    public void testSuperRotationSuccessOfJ() {

        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "----------",
            "----rr----",
            "-----rrr--",
            "------rrrr",
            "-rrr---rrr",
            "rr----rrrr",
            "rrrr--rrrr",
            "rrrr--rrrr"
        });
        TetrominoFactory factory = new PatternedTetrominoFactory("J");
        ControllableTetrisModel model = new TetrisModel(board, factory);
        // check if final position is available
        model.moveTetromino(1, -3);
        model.rotateTetromino(true);
        assertTrue(model.moveTetromino(4, 3));
        model.moveTetromino(-4, -3);
        model.rotateTetromino(false);

        // set starting position
        assertTrue(model.rotateTetromino(true));
        assertTrue(model.rotateTetromino(true));
        assertTrue(model.moveTetromino(2, 2));
        
        ViewableTetrisModel model2 = (ViewableTetrisModel) model;
        List<GridCell<Character>> tetroCells = new ArrayList<>();
        for (GridCell<Character> gc : model2.getTetroTiles()) {
            tetroCells.add(gc);
        }

        // check if block is correctly positioned
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 3), 'J')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(3, 3), 'J')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(3, 4), 'J')));
        assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(3, 5), 'J')));

        // test super rotation
        assertTrue(model.rotateTetromino(false));
        // desired rotation is 0>>3
        
        ViewableTetrisModel model3 = (ViewableTetrisModel) model;
        List<GridCell<Character>> tetroCells2 = new ArrayList<>();
        for (GridCell<Character> gc : model3.getTetroTiles()) {
            tetroCells2.add(gc);
        }
    
        // check if block is correctly positioned
        assertTrue(tetroCells2.contains(new GridCell<>(new CellPosition(4, 5), 'J')));
        assertTrue(tetroCells2.contains(new GridCell<>(new CellPosition(5, 5), 'J')));
        assertTrue(tetroCells2.contains(new GridCell<>(new CellPosition(6, 5), 'J')));
        assertTrue(tetroCells2.contains(new GridCell<>(new CellPosition(6, 4), 'J')));
        

    }
}
