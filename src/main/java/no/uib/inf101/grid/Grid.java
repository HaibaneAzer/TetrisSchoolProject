package no.uib.inf101.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Grid<E> implements IGrid<E>{
    
    private int row;
    private int col;
    private E value;

    // Answer used in lab 4
    private List<List<GridCell<E>>> Cells = new ArrayList<List<GridCell<E>>>();
    private List<GridCell<E>> CellInfo;

    /**
     * Creates a grid using 2 int parameters (rows and cols). 
     * constructor without spesified generic value. 
     * default value inserted to GridCell is null.
     */
    public Grid(int row, int col) {
        this.row = row;
        this.col = col;
        for (int i = 0; i < this.row; i++) {
            CellInfo = new ArrayList<>();
            for (int j = 0; j < this.col; j++) {
              CellInfo.add(new GridCell<E>(new CellPosition(i,j), null));
            }
            this.Cells.add(CellInfo);
        }
    }

    /**
     * Creates a grid using 2 int parameters (rows and cols). 
     * constructor inserts given generic parameter value into every
     * GridCell CellPosition.
     */
    public Grid(int row, int col, E value) {
        this.row = row;
        this.col = col;
        this.value = value;
        for (int i = 0; i < this.row; i++) {
            CellInfo = new ArrayList<>();
            for (int j = 0; j < this.col; j++) {
              CellInfo.add(new GridCell<E>(new CellPosition(i,j), this.value));
            }
            this.Cells.add(CellInfo);
        }
    }

    // Fikk hjelp av Elias Ruud Aronsen til å finne feil i set metoden.
    @Override
    public void set(CellPosition pos, E value) {
        if (!this.positionIsOnGrid(pos)) {
            throw new IndexOutOfBoundsException("row and col values is out of bounds");
        }
        GridCell<E> setGridCell = new GridCell<E>(pos, value);
        this.Cells.get(pos.row()).set(pos.col(), setGridCell);

    }
  
    @Override
    public E get(CellPosition pos) {
        return this.Cells.get(pos.row()).get(pos.col()).value();
    }
  
    @Override
    public boolean positionIsOnGrid(CellPosition pos) {
        // method used in lab 5 griddimension
        return pos.row() >= 0 && pos.row() < this.rows()
        && pos.col() >= 0 && pos.col() < this.cols();
    }

    /**
     * getter for rows
     */
    public int rows() {
        return this.row;
    }

    /**
     * getter for columns
     */
    public int cols() {
        return this.col;
    }
    
    /**
     * iterator returns a list of cells of given grid. 
     * This list calls .iterator() to convert the list into
     * the type Iterator<GridCell<E>>.
     */
    public Iterator<GridCell<E>> iterator() {
        List<GridCell<E>> D1Cells = Cells.stream().flatMap(Collection::stream).collect(Collectors.toList());

        return D1Cells.iterator();
    }
    
}
