package no.uib.inf101.tetris.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.TetrisBoard;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

import javax.swing.JPanel;

public class TetrisView extends JPanel {

    private final ViewableTetrisModel VModel;
    private final ColorTheme setColor;
    private static final double OUTERMARGIN = 15;
    private static final double GRIDMARGIN = 1;
    private double SCOREBOARDWIDTH;
    private static final double WFactor = 0.4;
    private double SCOREBOARDHEIGHT;
    private static final double HFactor = 0.1;
    private static final int s = 40; // side lengths of cell
    private static final int m = 1; // margin

  /**
   * TetrisView constructor uses data from tetrisModel to create the graphics of the tetris game
   * displayed on the window. 
   * 
   */
  public TetrisView(ViewableTetrisModel VModel) {
    this.setFocusable(true);
    this.VModel = VModel;

    // calculating preferred grid size
    int columns = this.VModel.getDimension().cols();
    int rows = this.VModel.getDimension().rows();
    this.SCOREBOARDWIDTH = WFactor*this.getWidth() + 200;
    int preferredWidth = (int) ((s + m)*columns + m + OUTERMARGIN + this.SCOREBOARDWIDTH);
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
  private void drawGame(Graphics2D Canvas) {
    this.SCOREBOARDWIDTH = WFactor*this.getWidth(); // width of scoreboard
    this.SCOREBOARDHEIGHT = HFactor*this.getHeight(); // height of scoreboard
    
    double x = OUTERMARGIN;
    double y = OUTERMARGIN;
    double width = (this.getWidth() - this.SCOREBOARDWIDTH) - 2 * OUTERMARGIN;
    double height = this.getHeight() - 2 * OUTERMARGIN;
    // rectangles of cells, tetris background and Screen
    Rectangle2D drawRectangle = new Rectangle2D.Double(x, y, width, height);
    Rectangle2D drawScreenRect = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());

    double xScore = width + 2*OUTERMARGIN;
    double yScore = y + GRIDMARGIN;
    double widthScore = this.SCOREBOARDWIDTH - 2*OUTERMARGIN;
    double heightScore = this.SCOREBOARDHEIGHT - 2*OUTERMARGIN;
    // rectangles relative to scoreboard position
    Rectangle2D drawScoreRect = new Rectangle2D.Double(xScore, yScore, widthScore, heightScore);
    Rectangle2D drawLevelRect = new Rectangle2D.Double(xScore, yScore + heightScore + OUTERMARGIN, widthScore, heightScore);

    double xNewTetro = xScore + 3*OUTERMARGIN;
    double yNewTetro = yScore + 3*heightScore;
    double widthNewTetro = widthScore - 6*OUTERMARGIN;
    double heightNewTetro = 4*heightScore - 4*OUTERMARGIN;
    // rectangles relative to scoreboard
    Rectangle2D drawNewTetroRect = new Rectangle2D.Double(xNewTetro, yNewTetro, widthNewTetro, heightNewTetro);
    Rectangle2D drawNewTetroRect2 = new Rectangle2D.Double(xNewTetro, yNewTetro + heightNewTetro + OUTERMARGIN, widthNewTetro, heightNewTetro);
    Rectangle2D drawNewTetroRect3 = new Rectangle2D.Double(xNewTetro, yNewTetro + 2*heightNewTetro + 2*OUTERMARGIN, widthNewTetro, heightNewTetro);
    Rectangle2D[] newTetroList = {
      drawNewTetroRect3,
      drawNewTetroRect2,
      drawNewTetroRect
    };
    GridDimension nextTetroGrid = new TetrisBoard(4, 4);

    CellPositionToPixelConverter Convert = new CellPositionToPixelConverter(drawRectangle, VModel.getDimension(), GRIDMARGIN);
    CellPositionToPixelConverter ConvertNewTetro = new CellPositionToPixelConverter(drawNewTetroRect, nextTetroGrid, GRIDMARGIN);
    CellPositionToPixelConverter ConvertNewTetro2 = new CellPositionToPixelConverter(drawNewTetroRect2, nextTetroGrid, GRIDMARGIN);
    CellPositionToPixelConverter ConvertNewTetro3 = new CellPositionToPixelConverter(drawNewTetroRect3, nextTetroGrid, GRIDMARGIN);
    CellPositionToPixelConverter[] newTetroConverters = {
      ConvertNewTetro3,
      ConvertNewTetro2,
      ConvertNewTetro
    };
    // draw menu screen before game start
    drawMenuScreen(Canvas, drawScreenRect, this.setColor, VModel.getGameState());
    if (!VModel.getGameState().equals(GameState.GAME_MENU)) {
      // draw tetris background
      drawCells(Canvas, VModel.getTilesOnBoard(), Convert, this.setColor);
      // draw tetrominos
      drawCells(Canvas, VModel.getTetroTiles(), Convert, this.setColor);
      // draw scores at top-right of screen
      drawScoreBoard(Canvas, drawScoreRect, this.VModel.getCurrentScore(), this.setColor);
      // draw level below scoreboard.
      drawLevelBoard(Canvas, drawLevelRect, this.VModel.getCurrentLevel(), this.setColor);
      // draw upcoming tetromino
      drawUpcomingTetromino(Canvas, newTetroList, VModel.getUpcomingTetroTiles(), newTetroConverters, this.setColor);
      // draw game over screen when a tetromino can't spawn
      drawEndGameScreen(Canvas, drawScreenRect, drawScreenRect, this.setColor, VModel.getGameState());
    } 
  }  

  private static void drawCells(Graphics2D Canvas, Iterable<GridCell<Character>> Cells, CellPositionToPixelConverter Converter, ColorTheme newColor) {
    for (GridCell<Character> GridChar : Cells) {
      Rectangle2D newRectangle = Converter.getBoundsForCell(GridChar.pos());
      Canvas.setColor(newColor.getCellColor(GridChar.value()));
      Canvas.fill(newRectangle);
    }
  }

  private static void drawMenuScreen(Graphics2D Canvas, Rectangle2D MenuBackground, ColorTheme color, GameState gameStatus) {
    Rectangle2D title = new Rectangle2D.Double(0, 0.2*MenuBackground.getHeight(), MenuBackground.getWidth(), 100);
    Rectangle2D pressKey = new Rectangle2D.Double(0, 0.6*MenuBackground.getHeight(), MenuBackground.getWidth(), 60);
    if (gameStatus.equals(GameState.GAME_MENU)) {
      Canvas.setColor(color.getMenuScreenColor("back"));
      Canvas.fill(MenuBackground);
      
      Canvas.setColor(color.getMenuScreenColor("title"));
      Canvas.setFont(new Font("Arial", Font.BOLD, 100));
      Inf101Graphics.drawCenteredString(Canvas, "TETRIS", title);

      Canvas.setColor(color.getMenuScreenColor("key"));
      Canvas.setFont(new Font("Arial", Font.BOLD, 40));
      Inf101Graphics.drawCenteredString(Canvas, "Press enter to start", pressKey);
    }
  }

  private static void drawEndGameScreen(Graphics2D Canvas, Rectangle2D endGameBackground, Rectangle2D ScreenDimension,ColorTheme color, GameState gameStatus) {
    Rectangle2D pressContinue = new Rectangle2D.Double(0, 0.6*ScreenDimension.getHeight(), ScreenDimension.getWidth(), 30);
    Rectangle2D pressMenu = new Rectangle2D.Double(0, 0.7*ScreenDimension.getHeight(), ScreenDimension.getWidth(), 30);
    if (gameStatus.equals(GameState.GAME_OVER)) {

      Canvas.setColor(color.getGameOverColor("back"));
      Canvas.fill(endGameBackground);
      Canvas.setColor(color.getGameOverColor("gameover"));
      Canvas.setFont(new Font("Times New Roman", Font.PLAIN, 69));

      Inf101Graphics.drawCenteredString(Canvas, "GAME OVER", endGameBackground);

      Canvas.setColor(color.getGameOverColor("key"));
      Canvas.setFont(new Font("Arial", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(Canvas, "Retry? (enter)", pressContinue);
      Inf101Graphics.drawCenteredString(Canvas, "Go back to menu? (backspace)", pressMenu);
    }
  } 

  private static void drawScoreBoard(Graphics2D Canvas, Rectangle2D ScoreBackground, int currentScore, ColorTheme color) {
    Canvas.setColor(color.getScoreBoardColor("back"));
    Canvas.fill(ScoreBackground);
    Canvas.setColor(color.getScoreBoardColor("score"));
    Canvas.setFont(new Font("Arial", Font.BOLD, 20));
    Inf101Graphics.drawCenteredString(Canvas, "Score: " + String.valueOf(currentScore), ScoreBackground);
  }

  private static void drawLevelBoard(Graphics2D Canvas, Rectangle2D LevelBackground, int currentLevel, ColorTheme color) {
    Canvas.setColor(color.getLevelBoardColor("back"));
    Canvas.fill(LevelBackground);
    Canvas.setColor(color.getLevelBoardColor("level"));
    Canvas.setFont(new Font("Arial", Font.BOLD, 20));
    Inf101Graphics.drawCenteredString(Canvas, "Level: " + String.valueOf(currentLevel), LevelBackground);
  }

  private static void drawUpcomingTetromino(Graphics2D Canvas, Rectangle2D[] NewTetroBackgrounds, Tetromino[] TetroList, CellPositionToPixelConverter[] Converters, ColorTheme color) {
    Canvas.setColor(color.getTextColor("newTetro"));
    double x = NewTetroBackgrounds[2].getX();
    double y = NewTetroBackgrounds[2].getY() - 3.5*OUTERMARGIN;
    double width = NewTetroBackgrounds[2].getWidth();
    double height = NewTetroBackgrounds[2].getHeight()/2;
    Rectangle2D text = new Rectangle2D.Double(x, y, width, height);
    Canvas.setFont(new Font("Arial", Font.BOLD, 25));
    Inf101Graphics.drawCenteredString(Canvas, "NEXT", text);

    Canvas.setColor(color.getMenuScreenColor("back"));
    Canvas.fill(NewTetroBackgrounds[2]);
    drawCells(Canvas, TetroList[2], Converters[2], color);

    Canvas.setColor(color.getMenuScreenColor("back"));
    Canvas.fill(NewTetroBackgrounds[1]);
    drawCells(Canvas, TetroList[1], Converters[1], color);

    Canvas.setColor(color.getMenuScreenColor("back"));
    Canvas.fill(NewTetroBackgrounds[0]);
    drawCells(Canvas, TetroList[0], Converters[0], color);

  }
  
}
