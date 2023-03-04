package no.uib.inf101.tetris.model.tetromino;

import java.util.Random;

public class RandomTetrominoFactory implements TetrominoFactory{

    @Override
    public Tetromino getNext() {

        String BlockShapes = "LJSZTIO";
        Random randomChar = new Random();
        Character Char = BlockShapes.charAt(randomChar.nextInt(BlockShapes.length()));

        Tetromino nextTetro = Tetromino.newTetromino(Char);
        return nextTetro;

    }
    
}
