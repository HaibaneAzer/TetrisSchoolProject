package no.uib.inf101.tetris.model.tetromino;

public interface TetrominoFactory {
    
    /**
     * getNext() returns a new Tetromino-object. 
     * Has no parameters.
     * @return next tetromino-object
     */
    Tetromino getNext();

}
