package no.uib.inf101.tetris.model.tetromino;

public class PatternedTetrominoFactory implements TetrominoFactory {

    final String blockShapeChain;
    int nextVal = 0;

    public PatternedTetrominoFactory(String blockShapeChain) {
        this.blockShapeChain = blockShapeChain;

    }

    @Override
    public Tetromino getNext() {
        
        String BlockShapes = this.blockShapeChain;
        Tetromino nextTetro = Tetromino.newTetromino(BlockShapes.charAt(this.nextVal));

        this.nextVal++;
        if (this.nextVal == BlockShapes.length()) {
            this.nextVal = 0;
        }
        return nextTetro;

    }
    
}
