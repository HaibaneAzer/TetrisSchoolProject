package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.midi.TetrisSong;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class TetrisController implements KeyListener{
    
    // Instance variables
    private final ControllableTetrisModel controllModel;
    private final TetrisView tView;
    private Timer tTimer;
    private TetrisSong music;


    public TetrisController(ControllableTetrisModel controllModel, TetrisView tView) {
        this.controllModel = controllModel;
        this.tView = tView;
        this.tTimer = new Timer(controllModel.getTimePerTick(), this::clockTick);
        this.music = new TetrisSong("tetris.midi");

        // key input
        this.tView.setFocusable(true);
        this.tView.addKeyListener(this);
        this.tTimer.start();
        this.music.run();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (controllModel.getGameState().equals(GameState.GAME_OVER)) {
            /* DO NOTHING */
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Left arrow was e
            this.controllModel.moveTetromino(0, -1);
            
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Right arrow was e
            this.controllModel.moveTetromino(0, 1);
            
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Down arrow was e
            boolean moveSuccess = this.controllModel.moveTetromino(1, 0);
            if (moveSuccess) {
                this.tTimer.restart();
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            // Up arrow was e
            this.controllModel.rotateTetromino(true);

        }
        else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            // CTRL was e
            this.controllModel.rotateTetromino(false);

        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.controllModel.dropTetromino();
            this.tTimer.restart();
            // Spacebar was e
        }
        this.tView.repaint();
        
    }

    @Override
    public void keyReleased(KeyEvent e) {/* ignore */}

    @Override
    public void keyTyped(KeyEvent e) {/* Ignore */}

    public void clockTick(ActionEvent e) {

        if (controllModel.getGameState().equals(GameState.ACTIVE_GAME)) {
            controllModel.clockTick();
            this.tView.repaint();
        }
        else {
            this.music.doStopMidiSounds();
            this.music = new TetrisSong("DarkSoulsIIMenu2.mid");
            this.music.run();
            this.tTimer.stop();
        }
    }
    // bonus oppg:
   /*  private void getTimerDelay() {
        // set new delay for timer
        this.tTimer.setDelay(controllModel.getTimePerTick());
        this.tTimer.setInitialDelay(controllModel.getTimePerTick());

    } */
}
