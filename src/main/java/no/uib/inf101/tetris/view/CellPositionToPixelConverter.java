package no.uib.inf101.tetris.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.CellPosition;

public class CellPositionToPixelConverter {
  // instansvariabler
  Rectangle2D box;
  GridDimension gd;
  double Margin;

  public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double Margin) {
    this.box = box;
    this.gd = gd;
    this.Margin = Margin;
  }

  Rectangle2D getBoundsForCell(CellPosition cp) {

    // cell dimensions
    double cellWidth = (box.getWidth() - (gd.cols() + 1)*Margin)/gd.cols();
    double cellHeight = (box.getHeight() - (gd.rows() + 1)*Margin)/gd.rows();

    // cell position
    double cellX = box.getX() + (cp.col() + 1)*Margin + cp.col()*cellWidth;
    double cellY = box.getY() + (cp.row() + 1)*Margin + cp.row()*cellHeight;

    Rectangle2D CellBounds = new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);

    return CellBounds;
  }
}
