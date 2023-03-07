package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;

public class TestTetrisBoard {

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
    public void testPrettyString() {
        TetrisBoard board = new TetrisBoard(3, 4);
        board.set(new CellPosition(0, 0), 'g');
        board.set(new CellPosition(0, 3), 'y');
        board.set(new CellPosition(2, 0), 'r');
        board.set(new CellPosition(2, 3), 'b');
        String expected = String.join("\n", new String[] {
            "g--y",
            "----",
            "r--b"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void testRemoveFullRows() {
        // Tester at fulle rader fjernes i brettet:
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "-T",
            "TT",
            "LT",
            "L-",
            "LL"
        });

        assertEquals(3, board.removeFullRows());

        String expected = String.join("\n", new String[] {
            "--",
            "--",
            "--",
            "-T",
            "L-"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void testKeepBottomRow() {
        // Tester at fulle rader fjernes i brettet:
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "---",
            "---",
            "OOJ",
            "OOJ",
            "-JJ"
        });

        assertEquals(2, board.removeFullRows());

        String expected = String.join("\n", new String[] {
            "---",
            "---",
            "---",
            "---",
            "-JJ"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void testRemoveTopRow() {
        // Tester at fulle rader fjernes i brettet:
        TetrisBoard board = getTetrisBoardWithContents(new String[] {
            "IIII",
            "TTT-",
            "LT--",
            "L---",
            "LL--"
        });

        assertEquals(1, board.removeFullRows());

        String expected = String.join("\n", new String[] {
            "----",
            "TTT-",
            "LT--",
            "L---",
            "LL--"
        });
        assertEquals(expected, board.prettyString());
    }
}
