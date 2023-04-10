package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.midi.TetrisSong;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class TetrisController implements KeyListener{
    
    private final ControllableTetrisModel controllModel;
    private final TetrisView tView;
    private Timer tTimer;
    private TetrisSong music;
    private int newDelayMusic; // new music after reaching Delay goal.
    private final int DELAYGOAL = 700;

    /**
     * TetrisController is a constructor used to controll the tetris game. 
     * Uses timers to controll tetromino drop speed.
     * Updates TetrisView by repainting the window for every key clicked or when timer runs.
     * 
     */
    public TetrisController(ControllableTetrisModel controllModel, TetrisView tView) {
        this.controllModel = controllModel;
        this.tView = tView;
        this.tTimer = new Timer(controllModel.getTimePerTick(), this::clockTick);
        this.music = new TetrisSong("JustTheTwoOfUsMenu.mid");
        
        // key input
        this.tView.setFocusable(true);
        this.tView.addKeyListener(this);
        this.music.run();
        this.newDelayMusic = DELAYGOAL;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (controllModel.getGameState().equals(GameState.GAME_MENU)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                this.controllModel.setGameState(GameState.ACTIVE_GAME);
                this.tTimer.start();
                this.music.doStopMidiSounds();
                this.music = new TetrisSong("tetris.midi");
                this.music.run();
                newDelayMusic = DELAYGOAL;
            }
        }
        else if (controllModel.getGameState().equals(GameState.GAME_OVER)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                controllModel.resetBoard();
                controllModel.setGameState(GameState.ACTIVE_GAME);
                this.tTimer.restart();
                this.music.doStopMidiSounds();
                this.music = new TetrisSong("tetris.midi");
                this.music.run();
                newDelayMusic = DELAYGOAL;
            } 
            else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                this.tTimer.stop();
                controllModel.resetBoard();
                controllModel.setGameState(GameState.GAME_MENU);
                this.music.doStopMidiSounds();
                this.music = new TetrisSong("JustTheTwoOfUsMenu.mid");
                this.music.run();
            }  
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
        
        if (controllModel.getGameState().equals(GameState.GAME_MENU)) {

        }
        else if (controllModel.getGameState().equals(GameState.ACTIVE_GAME)) {
            controllModel.clockTick();
            getTimerDelay();
            // change music after drop speed
            if (controllModel.getTimePerTick() <= newDelayMusic) {
                this.music.doStopMidiSounds();
                this.music = new TetrisSong("Tetris - A Theme.mid");
                this.music.run();
                newDelayMusic = 0;
            }
            this.tView.repaint();
        }
        else if (controllModel.getGameState().equals(GameState.GAME_OVER)) {
            this.music.doStopMidiSounds();
            this.music = new TetrisSong("DarkSoulsIIMenu2.mid");
            this.music.run();
            this.tTimer.stop();
        }
    }
    
    private void getTimerDelay() {
        // set new delay for timer
        int newDelay = controllModel.getTimePerTick();
        this.tTimer.setDelay(newDelay);
        this.tTimer.setInitialDelay(newDelay);
    }
    
}
