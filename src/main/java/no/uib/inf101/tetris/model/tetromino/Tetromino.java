package no.uib.inf101.tetris.model.tetromino;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

import java.lang.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

public final class Tetromino implements Iterable<GridCell<Character>>{

    // Instance variables
    Character blockType;
    boolean[][] blockShape;
    private CellPosition Pos;

    List<GridCell<Character>> BlockList = new ArrayList<GridCell<Character>>();

    private Tetromino(Character blockType, boolean[][] blockShape, CellPosition Pos) {
        this.blockType = blockType;
        this.blockShape = blockShape;
        this.Pos = Pos;

    }

    /** 
     * newTetromino is a standard static (package-private) method which contains a list of valid tetromino shapes to be returned when
     * given a valid char type value. Valid Chars are "LJSZTIO".
     * @param newBlockType value of type Char.
     * @return a tetromino block of any valid shape.
     * @throws IllegalArgumentException if input value does not match any of the valid Chars.
     */
    static Tetromino newTetromino(Character newBlockType) {
        Tetromino TetrominoBlock = switch(newBlockType) {
            case 'L' -> new Tetromino(newBlockType, new boolean[][] {
                { false, false, false },
                {  true,  true,  true },
                {  true, false, false }
            }, new CellPosition(0, 0));
            case 'J' -> new Tetromino(newBlockType, new boolean[][] {
                { false, false, false },
                {  true,  true,  true },
                { false, false,  true }
            }, new CellPosition(0, 0));
            case 'S' -> new Tetromino(newBlockType, new boolean[][] {
                { false, false, false },
                { false,  true,  true },
                {  true,  true, false }
            }, new CellPosition(0, 0));
            case 'Z' -> new Tetromino(newBlockType, new boolean[][] {
                { false, false, false },
                {  true,  true, false },
                { false,  true,  true }
            }, new CellPosition(0, 0));
            case 'T' -> new Tetromino(newBlockType, new boolean[][] {
                { false, false, false },
                {  true,  true,  true },
                { false,  true, false }
            }, new CellPosition(0, 0));
            case 'I' -> new Tetromino(newBlockType, new boolean[][] {
                { false, false, false, false},
                {  true,  true,  true,  true},
                { false, false, false, false},
                { false, false, false, false}
            }, new CellPosition(0, 0));
            case 'O' -> new Tetromino(newBlockType, new boolean[][] {
                { false, false, false, false},
                { false,  true,  true, false},
                { false,  true,  true, false},
                { false, false, false, false}
            }, new CellPosition(0, 0));
            default -> throw new IllegalArgumentException("Value '" + newBlockType + "' does not match one of the seven Characters");
        };
        return TetrominoBlock;
    }

    /**
     * shiftedBy creates a copy of an existing tetromino and shifts its CellPosition values relative
     * to it's current position. Returns a new shifted copy of the given tetromino
     * @param deltaRow number of rows to be shifted
     * @param deltaCol number of columns to be shifted
     * @return a shifted tetromino copy
     */
    public Tetromino shiftedBy(int deltaRow, int deltaCol) {
        
        Tetromino shiftedTetro = new Tetromino(this.blockType, this.blockShape, new CellPosition(this.Pos.row() + deltaRow, this.Pos.col() + deltaCol));
        return shiftedTetro;
    }

    /**
     * shiftedToTopCenterOf uses the shiftedBy method to create a tetromino copy. It's CellPosition values are calculated to make 
     * the tetromino be centered at the top of a grid of any size.
     * @param dimension is of type GridDimension
     * @return a tetromino copy centered at top of grid
     */
    public Tetromino shiftedToTopCenterOf(GridDimension dimension) {
        if (this.blockShape.length == 4)
        return shiftedBy(-1, (int) Math.floor(dimension.cols()/2) - 2);
        else 
        return shiftedBy(-1, (int) Math.floor(dimension.cols()/2) - 1);
    }

    /**
     * rotateBy creates an empty boolean[][] which gets filled with the coordinates of the old boolean[][]
     * rotated 90 degrees (clockwise). It then uses the new boolean[][] to make a copy of the previous tetromino-object.
     * formula for rotation of a matrix used is using formula: 
     * (r2, c2) = (n - 1 - c1, r1)
     * and (r2, c2) = (c1, n - 1 - r1)
     * where n is the side length of the grid
     * @param clockwise is true, false is counter-clockwise
     * @return a rotated tetromino copy.
     */
    public Tetromino rotateBy(boolean clockwise) {
        // default true if value is not boolean.
        if (!(clockwise == true || clockwise == false)) {
            clockwise = true;
        }
        int sideLength = this.blockShape.length;
        // old point coordinates
        int oldRow;
        int oldCol;
        boolean[][] newBlockShape = new boolean[sideLength][sideLength];
        // calculate rotation
        for (int row = 0; row < sideLength; row++) {
            for (int col = 0; col < sideLength; col++) {
                if (clockwise) {
                    oldRow = (this.blockShape.length - 1) - col;
                    oldCol = row;
                    newBlockShape[row][col] = this.blockShape[oldRow][oldCol];
                }
                else {
                    oldRow = col;
                    oldCol = (this.blockShape.length - 1) - row;
                    newBlockShape[row][col] = this.blockShape[oldRow][oldCol];
                }
            }
        }
        Tetromino rotatedTetro = new Tetromino(this.blockType, newBlockShape, this.Pos);
        return rotatedTetro;
    }

    @Override
    public Iterator<GridCell<Character>> iterator() {

        int row = this.Pos.row();
        int col = this.Pos.col();

        for (int i = 0; i < this.blockShape.length; i++) {
            for (int j = 0; j < this.blockShape[i].length; j++) {
                if (this.blockShape[i][j]) {
                    BlockList.add(new GridCell<Character>(new CellPosition(row + i, col + j), this.blockType));
                }
            }
        }

        return BlockList.iterator();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((blockType == null) ? 0 : blockType.hashCode());
        result = prime * result + Arrays.deepHashCode(blockShape);
        result = prime * result + ((Pos == null) ? 0 : Pos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Tetromino other = (Tetromino) obj;
        if (blockType == null) {
            if (other.blockType != null)
                return false;
        } 
        else if (!blockType.equals(other.blockType))
            return false;

        if (!Arrays.deepEquals(blockShape, other.blockShape))
            return false;

        if (Pos == null) {
            if (other.Pos != null)
                return false;
        } 
        else if (!Pos.equals(other.Pos))
            return false;

        return true;
    }

}
