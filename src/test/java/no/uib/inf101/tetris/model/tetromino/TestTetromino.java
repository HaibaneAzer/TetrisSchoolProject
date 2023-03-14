package no.uib.inf101.tetris.model.tetromino;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

public class TestTetromino {

    @Test
    public void testHashCodeAndEquals() {
        Tetromino t1 = Tetromino.newTetromino('T');
        Tetromino t2 = Tetromino.newTetromino('T');
        Tetromino t3 = Tetromino.newTetromino('T').shiftedBy(1, 0);
        Tetromino s1 = Tetromino.newTetromino('S');
        Tetromino s2 = Tetromino.newTetromino('S').shiftedBy(0, 0);

        assertEquals(t1, t2);
        assertEquals(s1, s2);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(t1, t3);
        assertNotEquals(t1, s1);
}

    @Test
    public void tetrominoIterationOfT() {
        // Create a standard 'T' tetromino placed at (10, 100) to test
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.shiftedBy(10, 100);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 100), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'T')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'T')));
}

    @Test
    public void tetrominoIterationOfS() {
        // 
        Tetromino tetro = Tetromino.newTetromino('S');
        tetro = tetro.shiftedBy(10, 100);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 100), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'S')));


    }
    @Test
    public void tetrominoDisplacement() {
        //
        Tetromino tetro = Tetromino.newTetromino('S');
        tetro = tetro.shiftedBy(0, 10);
        tetro = tetro.shiftedBy(0, 10);

        // Collect which objects are iterated through
        List<GridCell<Character>> objs = new ArrayList<>();
        for (GridCell<Character> gc : tetro) {
            objs.add(gc);
        }

        // Check if tetro was displaced, then check if displacement is doubled on repeated shift.
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 20), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 21), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 22), 'S')));
        assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 21), 'S')));
    }

    @Test 
    public void tetrominoshiftedToTopCenterOf() {

        Grid<Character> grid1 = new Grid<Character>(20, 10, '-');
        Grid<Character> grid2 = new Grid<Character>(20, 10, '-');
        //
        Tetromino tetro1 = Tetromino.newTetromino('S');
        tetro1 = tetro1.shiftedToTopCenterOf(grid1);
        // 
        List<GridCell<Character>> objs1 = new ArrayList<>();
        for (GridCell<Character> gc : tetro1) {
            objs1.add(gc);
        }

        Tetromino tetro2 = Tetromino.newTetromino('O');
        tetro2 = tetro2.shiftedToTopCenterOf(grid2);
        //
        List<GridCell<Character>> objs2 = new ArrayList<>();
        for (GridCell<Character> gc : tetro2) {
            objs2.add(gc);
        }
        
        // check position for S block
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(0, 5), 'S')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(0, 6), 'S')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(1, 4), 'S')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(1, 5), 'S')));
        
        // check position for O block
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(1, 4), 'O')));

    }

    @Test
    public void tetrominoRotation() {
        //
        Grid<Character> grid = new Grid<Character>(20, 10, '-');
        Tetromino tetro1 = Tetromino.newTetromino('I');
        tetro1 = tetro1.shiftedToTopCenterOf(grid);
        tetro1 = tetro1.shiftedBy(1, 0);
        tetro1 = tetro1.rotateBy(true);
        
        List<GridCell<Character>> objs1 = new ArrayList<>();
        for (GridCell<Character> gc : tetro1) {
            objs1.add(gc);
        }

        Tetromino tetro2 = Tetromino.newTetromino('L');
        tetro2 = tetro2.shiftedToTopCenterOf(grid);
        tetro2 = tetro2.shiftedBy(1, 0);
        tetro2 = tetro2.rotateBy(true);

        List<GridCell<Character>> objs2 = new ArrayList<>();
        for (GridCell<Character> gc : tetro2) {
            objs2.add(gc);
        }

        // Check if I block is rotated correctly
        assertEquals(4, objs1.size());
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(1, 5), 'I')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(2, 5), 'I')));
        assertTrue(objs1.contains(new GridCell<>(new CellPosition(3, 5), 'I')));
        // see if 4 rotations returns original pos
        assertEquals(tetro1, tetro1.rotateBy(true).rotateBy(true).rotateBy(true).rotateBy(true));

        // Check if L block is rotated correctly
        assertEquals(4, objs2.size());
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(0, 4), 'L')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(0, 5), 'L')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(1, 5), 'L')));
        assertTrue(objs2.contains(new GridCell<>(new CellPosition(2, 5), 'L')));
    }

}
