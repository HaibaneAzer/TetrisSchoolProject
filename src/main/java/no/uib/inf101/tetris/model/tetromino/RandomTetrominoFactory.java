package no.uib.inf101.tetris.model.tetromino;

import java.util.Random;

public class RandomTetrominoFactory implements TetrominoFactory{

    private final String BlockShapes = "LJSZTIO";
    private Random randomChar;

    @Override
    public Tetromino getNext() {

        randomChar = new Random();
        Character Char = BlockShapes.charAt(randomChar.nextInt(BlockShapes.length()));

        Tetromino nextTetro = Tetromino.newTetromino(Char);
        return nextTetro;

    }

}
