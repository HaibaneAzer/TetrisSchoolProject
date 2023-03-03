package no.uib.inf101.tetris.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.grid.CellPosition;

public class CellPositionToPixelConverter {
  // instance variables
  Rectangle2D box;
  GridDimension gridDim;
  double Margin;
  
  public CellPositionToPixelConverter(Rectangle2D box, GridDimension gridDim, double Margin) {
    this.box = box;
    this.gridDim = gridDim;
    this.Margin = Margin;
  }
  /** 
  * getBoundsForCell is a method which uses CellPosition values to create a grid with 
  * unifromly distributed cells. 
  * Method has only parameters of type CellPosition and returns values of type Rectangle2D.
  * @param cellPos 
  */
  public Rectangle2D getBoundsForCell(CellPosition cellPos) {

    // cell dimensions
    double cellWidth = (box.getWidth() - (gridDim.cols() + 1)*Margin)/gridDim.cols();
    double cellHeight = (box.getHeight() - (gridDim.rows() + 1)*Margin)/gridDim.rows();

    // cell position
    double cellX = box.getX() + (cellPos.col() + 1)*Margin + cellPos.col()*cellWidth;
    double cellY = box.getY() + (cellPos.row() + 1)*Margin + cellPos.row()*cellHeight;

    Rectangle2D CellBounds = new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);

    return CellBounds;
  }
}
