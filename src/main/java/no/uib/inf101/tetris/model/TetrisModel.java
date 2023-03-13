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
    private static final int WALLKICK_NORMAL[][][] = 
    {
        {{0, -1}, {-1, -1}, {2, 0}, {2, -1}},
    // 0>>R
        {{0, 1}, {1, 1}, {-2, 0}, {-2, 1}},
    // R>>0
        {{0, 1}, {1, 1}, {-2, 0}, {-2, 1}},
    // R>>2
        {{0, -1}, {-1, -1}, {2, 0}, {2, -1}},
    // 2>>R
        {{0, 1}, {-1, 1}, {2, 0}, {2, 1}},
    // 2>>L
        {{0, -1}, {1, -1}, {-2, 0}, {-2, -1}},
    // L>>2
        {{0, -1}, {1, -1}, {-2, 0}, {-2, -1}},
    // L>>0
        {{0, 1}, {-1, 1}, {2, 0}, {2, 1}},
    // 0>>L
    };
    private static final int WALLKICK_I[][][] = 
    {
        {{0, -2}, {0, 1}, {1, -2}, {-1, 2}},
    // 0>>R
        {{0, 2}, {0, -1}, {-1, 2}, {2, -1}},
    // R>>0
        {{0, -1}, {0, 2}, {-2, -1}, {1, 2}},
    // R>>2
        {{0, 1}, {0, -2}, {2, 1}, {1, 1}},
    // 2>>R
        {{0, 2}, {0, -1}, {-1, 2}, {1, -1}},
    // 2>>L
        {{0, -2}, {0, 1}, {1, -2}, {-2, 1}},
    // L>>2
        {{0, 1}, {0, -2}, {2, 1}, {-1, -2}},
    // L>>0
        {{0, -1}, {0, 2}, {-2, -1}, {1, 2}},
    // 0>>L
    };

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
        this.nextTetromino = this.tetroMaker.getNext(); // update tetromino list.
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

    @Override
    public boolean rotateTetromino(boolean clockwise) {
        if (!isValidPos(this.fallingTetro.rotateBy(clockwise))) {
            return superRotationSystem(clockwise);
        }
        this.fallingTetro = this.fallingTetro.rotateBy(clockwise);
        return true;
    }

    @Override 
    public boolean superRotateTetromino(int deltaRow, int deltaCol, boolean clockwise) {
        if (!isValidPos(this.fallingTetro.rotateBy(clockwise).shiftedBy(deltaRow, deltaCol))) {
            return false;
        }
        this.fallingTetro = this.fallingTetro.rotateBy(clockwise).shiftedBy(deltaRow, deltaCol);
        return true;
    }

    /**
     * Helper method for superRotationSystem method. Used to go through wallkick tests for tetrominos.
     * @param conditions is a list of criteria (boolean statements) the given tetromino-object must fulfill.
     * @param WALLKICK is a list of movements (int valued) used on {@link #superRotateTetromino}, for each lists of tests, for each lists of positions
     * a given tetromino-object can have.
     * @param Rotation is the second argument used on {@link #superRotateTetromino}. 
     * Can either be clockwise (boolean true) or counter-clockwise (boolean false)
     * @return returns true if method {@link #superRotateTetromino} succeeded, false otherwise.
     */
    private boolean superConditionChecker(boolean[] conditions, int[][][] WALLKICK, boolean Rotation) {
        for (int state = 0; state < conditions.length; state++) {
            if (conditions[state]) {
                for (int test = 0; test < WALLKICK[0].length; test++) {
                    if (superRotateTetromino(WALLKICK[state][test][0],WALLKICK[state][test][1], Rotation)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * superRotationSystem (SRS) is the helper method used by {@link #rotateTetromino}.
     * after a regular rotation has failed, checks if a Wallkick can be performed.
     * @param Rotation is the same boolean argument used in {@link #rotateTetromino}. 
     * @return result of {@link #superConditionChecker}. If tetromino is of type 'O', return false.
     */
    private boolean superRotationSystem(boolean Rotation) {
        // initial rotations
        Tetromino spawn = this.fallingTetro.getSpawnShape(this.fallingTetro.getType());
        // initial spawn rotation according to Tetris wiki
        if (!this.fallingTetro.getType().equals('I')) { 
            spawn = spawn.rotateBy(true).rotateBy(true);
        }
        Tetromino Right = spawn.rotateBy(true);
        Tetromino Right2 = spawn.rotateBy(true).rotateBy(true);
        Tetromino Left = spawn.rotateBy(false);
        Tetromino Left2 = spawn.rotateBy(false).rotateBy(false);

        // conditions
        boolean[] conditions = new boolean[] {
            (this.fallingTetro.equals(spawn) && this.fallingTetro.rotateBy(Rotation).equals(Right)), // 0->R
            (this.fallingTetro.equals(Right) && this.fallingTetro.rotateBy(Rotation).equals(spawn)), // R->0
            (this.fallingTetro.equals(Right) && this.fallingTetro.rotateBy(Rotation).equals(Right2)), // R->2
            (this.fallingTetro.equals(Right2) && this.fallingTetro.rotateBy(Rotation).equals(Right)), // 2->R
            (this.fallingTetro.equals(Left2) && this.fallingTetro.rotateBy(Rotation).equals(Left)), // 2->L
            (this.fallingTetro.equals(Left) && this.fallingTetro.rotateBy(Rotation).equals(Left2)), // L->2
            (this.fallingTetro.equals(Left) && this.fallingTetro.rotateBy(Rotation).equals(spawn)), // L->0
            (this.fallingTetro.equals(spawn) && this.fallingTetro.rotateBy(Rotation).equals(Left)) // 0->L
        };
        if (this.fallingTetro.getType().equals('I')) {
            return superConditionChecker(conditions, WALLKICK_I, Rotation);
        }
        else if (!this.fallingTetro.getType().equals('O')) {
            return superConditionChecker(conditions, WALLKICK_NORMAL, Rotation);
        }
        return false;
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
    /**
     * isValidPos is a helper method for methods performing transformations like {@link #moveTetromino} and {@link #rotateTetromino}, 
     * as well as {@link #getNextTetromino} which spawns new tetrominos on the board.
     * It takes a shifted version of a tetromino-object and checks if
     * it's CellPosition values is within the Board and/or not overlaping a colored tile.
     * See {@link #ColoredTileOverlap}.
     * @param shiftedTetro is a Tetromino-object
     * @return true if criteria is met, false otherwise.
     */
    private boolean isValidPos(Tetromino shiftedTetro) {
        for (GridCell<Character> gc : shiftedTetro) {
            if (!(gc.pos().row() >= 0 && gc.pos().row() < this.Board.rows() &&
                gc.pos().col() >= 0 && gc.pos().col() < this.Board.cols())) {
                return false;
           }
        }
        return ColoredTileOverlap(shiftedTetro); 
    }
    
    /**
     * ColoredTileOverlap is a helper method used by {@link #isValidPos}.
     * Checks if Position is empty/not colored.
     * @param shiftedTetro is a Tetromino-object
     * @return true if position is empty, false otherwise.
     */
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
     * Also updates the tetromino list, used to display the next tetromino that will spawn.
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
      * with corrosponding CellPosition. Uses {@link #getNextTetromino} after all cells has been glued. 
      * It also counts number of rows being removed and calculates score.
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
        getNextTetromino();
    }

}
