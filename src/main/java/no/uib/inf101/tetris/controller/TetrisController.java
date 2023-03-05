package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.view.TetrisView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TetrisController implements KeyListener{
    
    // Instance variables
    private final ControllableTetrisModel controllModel;
    private final TetrisView tView;



    public TetrisController(ControllableTetrisModel controllModel, TetrisView tView) {
        this.controllModel = controllModel;
        this.tView = tView;

        // key input
        this.tView.setFocusable(true);
        this.tView.addKeyListener(this);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Left arrow was e
            this.controllModel.moveTetromino(0, -1);
            System.out.println("pressed <-");
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Right arrow was e
            this.controllModel.moveTetromino(0, 1);
            System.out.println("pressed ->");
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Down arrow was e
            this.controllModel.moveTetromino(1, 0);
            System.out.println("pressed v");
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.controllModel.moveTetromino(-1, 0);
            System.out.println("pressed ^");
            // Up arrow was e
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Spacebar was e
        }
        this.tView.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {/* ignore */}

    @Override
    public void keyTyped(KeyEvent e) {/* Ignore */}

}
