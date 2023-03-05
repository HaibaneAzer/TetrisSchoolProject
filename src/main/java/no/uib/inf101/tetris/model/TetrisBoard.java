package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.Grid;

public class TetrisBoard extends Grid<Character> {

    
    public TetrisBoard(int row, int col) {
        super(row, col, '-'); 
        
    }

    public String prettyString() {
        return "g--y\n----\nr--b";
    }
}
