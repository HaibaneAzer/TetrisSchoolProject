package no.uib.inf101.tetris.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import no.uib.inf101.grid.GridCell;

import javax.swing.JPanel;

public class TetrisView extends JPanel {

    // instance variables

    private final ViewableTetrisModel VModel;

    private final ColorTheme Theme;

    private static final double OUTERMARGIN = 15;
    private static final double GRIDMARGIN = 2;

    // Constructor
  public TetrisView(ViewableTetrisModel VModel) {
    this.setFocusable(true);
    this.setPreferredSize(new Dimension(300, 400));
    this.VModel = VModel;
    this.Theme = new DefaultColorTheme();
    this.setBackground(this.Theme.getBackgroundColor());
  }
  
  // The paintComponent method is called by the Java Swing framework every time
  // either the window opens or resizes, or we call .repaint() on this object. 
  // Note: NEVER call paintComponent directly yourself
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    // draw everything
    drawGame(g2);
    
  }

  private void drawGame(Graphics2D Canva) {

    
    double x = OUTERMARGIN;
    double y = OUTERMARGIN;
    double width = this.getWidth() - 2 * OUTERMARGIN;
    double height = this.getHeight() - 2 * OUTERMARGIN;

    Rectangle2D drawRect = new Rectangle2D.Double(x, y, width, height);

    CellPositionToPixelConverter Convert = new CellPositionToPixelConverter(drawRect, VModel.getDimension(), GRIDMARGIN);
    
    drawCells(Canva, VModel.getTilesOnBoard(), Convert, this.Theme);


  }  

  private static void drawCells(Graphics2D Canvas, Iterable<GridCell<Character>> Cells, CellPositionToPixelConverter Converter, ColorTheme C) {
    
    for (GridCell<Character> GridChar : Cells) {
      Rectangle2D Rect = Converter.getBoundsForCell(GridChar.pos());
      // if statement to check for illegal args?
      Canvas.setColor(C.getCellColor(GridChar.value()));
      Canvas.fill(Rect);

    }

  }
  
}
