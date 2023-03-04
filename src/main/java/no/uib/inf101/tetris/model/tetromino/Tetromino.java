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
    public CellPosition Pos;

    List<GridCell<Character>> BlockList = new ArrayList<GridCell<Character>>();

    private Tetromino(Character blockType, boolean[][] blockShape, CellPosition Pos) {
        this.blockType = blockType;
        this.blockShape = blockShape;
        this.Pos = Pos;

    }

    // standard static (klassemetode)
    /**
     * 
     * 
     * 
     */
    public static Tetromino newTetromino(Character newBlockType) {
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
     * 
     * 
     * 
     */
    public Tetromino shiftedBy(int deltaRow, int deltaCol) {
        
        Tetromino shiftedTetro = new Tetromino(this.blockType, this.blockShape, new CellPosition(this.Pos.row() + deltaRow, this.Pos.col() + deltaCol));
        return shiftedTetro;
    }

    /**
     * 
     * 
     * 
     */
    public Tetromino shiftedToTopCenterOf(GridDimension dimension) {
        if (this.blockShape.length == 4)
        return shiftedBy(-1, (int) Math.floor(dimension.cols()/2) - 2);
        else 
        return shiftedBy(-1, (int) Math.floor(dimension.cols()/2) - 1);
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
