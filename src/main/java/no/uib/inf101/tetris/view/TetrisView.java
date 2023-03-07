package no.uib.inf101.tetris.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.GameState;

import javax.swing.JPanel;

public class TetrisView extends JPanel {

  // instance variables
    private final ViewableTetrisModel VModel;

    private final ColorTheme setColor;

    private static final double OUTERMARGIN = 15;
    private static final double GRIDMARGIN = 2;

    private static final int s = 30; // cell lengths
    private static final int m = 5; // margin

  // Constructor
  public TetrisView(ViewableTetrisModel VModel) {
    this.setFocusable(true);
    this.VModel = VModel;
    // calculating preferred grid size
    int columns = this.VModel.getDimension().cols();
    int rows = this.VModel.getDimension().rows();
    int preferredWidth = (int) ((s + m)*columns + m + OUTERMARGIN);
    int preferredHeight = (int) ((s + m)*rows + m + OUTERMARGIN);

    this.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    this.setColor = new DefaultColorTheme();
    this.setBackground(this.setColor.getBackgroundColor());
  }
  
  @Override
  public void paintComponent(Graphics GraphComp) {
    super.paintComponent(GraphComp);
    Graphics2D Comp2D = (Graphics2D) GraphComp;

    drawGame(Comp2D);
    
  }

  // methods drawGame and drawCells used similar solutions from lab 4
  // NB: remember to write javadocs (/*  */) to describe methods that isn't private and/or are not 
  // borrowed from other classes or interfaces.
  private void drawGame(Graphics2D Canvas) {
    double x = OUTERMARGIN;
    double y = OUTERMARGIN;
    double width = this.getWidth() - 2 * OUTERMARGIN;
    double height = this.getHeight() - 2 * OUTERMARGIN;

    Rectangle2D drawRectangle = new Rectangle2D.Double(x, y, width, height);

    CellPositionToPixelConverter Convert = new CellPositionToPixelConverter(drawRectangle, VModel.getDimension(), GRIDMARGIN);
    
    // draw tetris background
    drawCells(Canvas, VModel.getTilesOnBoard(), Convert, this.setColor);
    // draw tetrominos
    drawCells(Canvas, VModel.getTetroTiles(), Convert, this.setColor);

    // draw game over screen when a tetromino can't spawn
    if (VModel.getGameState().equals(GameState.GAME_OVER)) {
      Rectangle2D drawGameOverScreen = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
      Canvas.setColor(this.setColor.getGameOverColor("back"));
      Canvas.fill(drawGameOverScreen);

      Canvas.setColor(this.setColor.getGameOverColor("front"));
      Canvas.setFont(new Font("Gill Sans MT", Font.BOLD, 50));
      Inf101Graphics.drawCenteredString(Canvas, "GAME OVER", 0, 0, this.getWidth(), this.getHeight());
      
    }

  }  

  private static void drawCells(Graphics2D Canvas, Iterable<GridCell<Character>> Cells, CellPositionToPixelConverter Converter, ColorTheme newColor) {
    for (GridCell<Character> GridChar : Cells) {
      Rectangle2D newRectangle = Converter.getBoundsForCell(GridChar.pos());
      
      Canvas.setColor(newColor.getCellColor(GridChar.value()));
      Canvas.fill(newRectangle);
    }
  }
  
}
