package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel{
    
    private TetrisBoard Board;
    private final TetrominoFactory tetroMaker;
    private Tetromino fallingTetro; 
    private Tetromino nextTetromino;
    private Tetromino[] tetroList;
    private GameState gameStatus;
    private int tetrisLevel;
    private int[] levelChecker = new int[2];
    private int timeDelay;
    private int totalRemovedRows;
    private int score;

    public TetrisModel(TetrisBoard Board, TetrominoFactory tetroMaker) {
        this.Board = Board;
        this.tetroMaker = tetroMaker;
        this.fallingTetro = this.tetroMaker.getNext().shiftedToTopCenterOf(Board);
        this.nextTetromino = this.tetroMaker.getNext();
        this.tetroList = new Tetromino[] {
            this.nextTetromino,
            this.fallingTetro
        };
        this.gameStatus = GameState.GAME_MENU;
        this.score = 0;
        this.totalRemovedRows = 0;
        this.tetrisLevel = 1;
        this.levelChecker[0] = 4; // rows for level up
        this.levelChecker[1] = 8; // rows for next level up
        this.timeDelay = 1000; // default delay per tick
    }

    @Override
    public GridDimension getDimension() {
        return this.Board;
    }

    @Override
    public Iterable<GridCell<Character>> getUpcomingTetroTiles() {
        return this.nextTetromino;
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
    public GameState getGameState() {
        return this.gameStatus;
    }

    @Override
    public GameState setGameState(GameState status) {
        this.gameStatus = status;
        return this.gameStatus;
    }

    @Override
    public void resetBoard() {
        this.Board = new TetrisBoard(this.Board.rows(), this.Board.cols());
        this.score = 0;
        this.totalRemovedRows = 0;
        this.tetrisLevel = 1;
        this.levelChecker[0] = 10; // rows for level up
        this.levelChecker[1] = 20; // rows for next level up
        this.timeDelay = 1000; // default delay per tick
        this.nextTetromino = this.tetroMaker.getNext(); // update list.
        this.tetroList[1] = this.fallingTetro;
        this.tetroList[0] = this.nextTetromino;
    }

    @Override
    public int getTimePerTick() {
        
        if (this.totalRemovedRows >= this.levelChecker[0]) {
            this.tetrisLevel++;
            if (this.totalRemovedRows < 70) {
                timeDelay -= 50; // stops at 700 time delay
                this.levelChecker[0] = this.levelChecker[1];
                this.levelChecker[1] = this.levelChecker[0] + 10;
            }
            else if (this.totalRemovedRows < 160) {
                timeDelay -= 25; // stops at 500 time delay
                this.levelChecker[0] = this.levelChecker[1];
                this.levelChecker[1] = this.levelChecker[0] + 10;
            }
            else {
                this.levelChecker[0] = this.levelChecker[1];
                this.levelChecker[1] = this.levelChecker[0] + 10;
            }
        }
        return timeDelay;
    }

    @Override
    public int getCurrentScore() {
        return this.score;
    }

    @Override
    public int getCurrentLevel() {
        return this.tetrisLevel;
    }

    @Override
    public boolean clockTick() {
        
        if (moveTetromino(1, 0)) {
            return true;
        }
        glueTetrominoToBoard();
        return false;
        
    }

    @Override
    public boolean moveTetromino(int deltaRow, int deltaCol) {
        
        if (!isValidPos(this.fallingTetro.shiftedBy(deltaRow, deltaCol))) {
            return false;
        }
        this.fallingTetro = this.fallingTetro.shiftedBy(deltaRow, deltaCol);
        return true;

    }

    // NB: implement super rotation?
    @Override
    public boolean rotateTetromino(boolean clockwise) {

        if (!isValidPos(this.fallingTetro.rotateBy(clockwise))) {
            return false;
        }
        this.fallingTetro = this.fallingTetro.rotateBy(clockwise);
        return true;
    }

    @Override
    public boolean dropTetromino() {

        while (isValidPos(this.fallingTetro.shiftedBy(1, 0))) {
            moveTetromino(1, 0);
        }
        glueTetrominoToBoard();
        return true;
    }
    
    // Fikk hjelp av Elias Ruud Aronsen til å finne feil i IsValidPos metoden.
    private boolean isValidPos(Tetromino shiftedTetro) {
        // check on board
        for (GridCell<Character> gc : shiftedTetro) {
            if (!(gc.pos().row() >= 0 && gc.pos().row() < this.Board.rows() &&
                gc.pos().col() >= 0 && gc.pos().col() < this.Board.cols())) {
                return false;
           }
        }
        return ColoredTileOverlap(shiftedTetro); 
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

    /**
     * getNextTetromino spawns a new tetromino-object and checks if it has any legal moves.
     * If not, set game status to GAME_OVER.
     * 
     */
    private void getNextTetromino() {
        
        this.fallingTetro = this.nextTetromino.shiftedToTopCenterOf(Board);
        if (!isValidPos(this.fallingTetro)) {
            setGameState(GameState.GAME_OVER);

        }
        this.nextTetromino = this.tetroMaker.getNext();
        // update list
        this.tetroList[1] = this.fallingTetro;
        this.tetroList[0] = this.nextTetromino;
    }

     /** 
      * glueTetrominoToBoard changes the char value on a Board tile to that of the Tetromino-object,
      * with corrosponding CellPosition.
      */
    private void glueTetrominoToBoard() {

        for (GridCell<Character> gc : getTetroTiles()) {
            for (GridCell<Character> boardCell : getTilesOnBoard()) {
                if (boardCell.pos().equals(gc.pos())) {
                    this.Board.set(gc.pos(), gc.value());
                }
            }
        }
        // check if rows are full
        int removedRows = this.Board.removeFullRows();
        this.totalRemovedRows += removedRows;
        if (removedRows != 0) {
            // score calculation:
            if (removedRows == 4) {
                this.score += 800;
            }
            else if (removedRows == 3) {
                this.score += 500;
            }
            else if (removedRows == 2) {
                this.score += 300;
            }
            else if (removedRows == 1) {
                this.score += 100;
            }

            removedRows = 0;
            
        }
        // get new tetromino
        getNextTetromino();
    }


}
