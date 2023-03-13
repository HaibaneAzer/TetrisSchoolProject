package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class TetrisBoard extends Grid<Character> {

    public TetrisBoard(int row, int col) {
        super(row, col, '-'); 
    }

    /**
     * prettyString gets char values of every cellPosition on the Board and creates a 
     * String.
     * @return a String representation of a tetrisBoard.
     */
    public String prettyString() {
        String[] stringRow = new String[this.rows()];

        for (int row = 0; row < this.rows(); row++) {
            stringRow[row] = "";
            for (int col = 0; col < this.cols(); col++) {
                stringRow[row] += this.get(new CellPosition(row, col));
            }
            
        }
        String pretty = String.join("\n", stringRow);
        return pretty;
    }

    /**
     * removeFullRows removes alls rows that doesn't contain value '-'. 
     * @return number of rows that got removed. Used for calculating score.
     */
    public int removeFullRows() {
        int rowWriter = this.rows() - 1;
        int savedRows = this.rows() - 1;
        int removedRows = 0;

        for (;rowWriter >= 0;rowWriter--) {
            for (; savedRows >= 0 && getRowElement(savedRows);) {
                removedRows++;
                savedRows--;
            }
            if (savedRows >= 0) {
                copyPasteRows(savedRows, rowWriter);
            }
            else {
                setWholeRow(rowWriter);
            }
            savedRows--;
        }
        return removedRows;
    }

    private boolean getRowElement(int savedRows) {
        // check if an element in a row has value '-'.
        for (int i = 0; i < this.cols(); i++) {
            if (this.get(new CellPosition(savedRows, i)).equals('-')) {
                return false;
            }
        }
        return true;
    }

    private void setWholeRow(int rowWriter) {
        // set all values in row to value '-'
        for (int i = 0; i < this.cols(); i++) {
            this.set(new CellPosition(rowWriter, i), '-');
        }
    }

    private void copyPasteRows(int savedRows, int rowWriter) {
        // copy values in row b and paste dem into row a.
        for (int i = 0; i < this.cols(); i++) {
            this.set(new CellPosition(rowWriter, i), this.get(new CellPosition(savedRows, i)));
        }
    }
}
