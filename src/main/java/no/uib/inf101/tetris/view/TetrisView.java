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

import javax.swing.JPanel;

public class TetrisView extends JPanel {

  // instance variables
    private final ViewableTetrisModel VModel;

    private final ColorTheme setColor;

    private static final double OUTERMARGIN = 15;
    private static final double GRIDMARGIN = 2;
    private double SCOREBOARDWIDTH;
    private static final double WFactor = 0.4;
    private double SCOREBOARDHEIGHT;
    private static final double HFactor = 0.1;

    private static final int s = 30; // side lengths of cell
    private static final int m = 5; // margin

  // Constructor
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

    double x = OUTERMARGIN;
    double y = OUTERMARGIN;
    this.SCOREBOARDWIDTH = WFactor*this.getWidth(); // width of scoreboard
    this.SCOREBOARDHEIGHT = HFactor*this.getHeight(); // height of scoreboard
    double width = (this.getWidth() - this.SCOREBOARDWIDTH) - 2 * OUTERMARGIN;
    double height = this.getHeight() - 2 * OUTERMARGIN;
    // rectangle of cells and tetris background
    Rectangle2D drawRectangle = new Rectangle2D.Double(x, y, width, height);
    Rectangle2D drawMenuScreen = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
    Rectangle2D drawGameOverScreen = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());

    double xScore = width + 2*OUTERMARGIN;
    double yScore = y + GRIDMARGIN;
    double widthScore = this.SCOREBOARDWIDTH - 2*OUTERMARGIN;
    double heightScore = this.SCOREBOARDHEIGHT - 2*OUTERMARGIN;

    Rectangle2D drawScoreRect = new Rectangle2D.Double(xScore, yScore, widthScore, heightScore);
    Rectangle2D drawLevelRect = new Rectangle2D.Double(xScore, yScore + heightScore + OUTERMARGIN, widthScore, heightScore);
    Rectangle2D drawNewTetroRect = new Rectangle2D.Double(xScore, yScore + 3*heightScore + OUTERMARGIN, widthScore, 4*heightScore);
    GridDimension nextTetroGrid = new TetrisBoard(4, 4);

    CellPositionToPixelConverter Convert = new CellPositionToPixelConverter(drawRectangle, VModel.getDimension(), GRIDMARGIN);
    CellPositionToPixelConverter Convert2 = new CellPositionToPixelConverter(drawNewTetroRect, nextTetroGrid, GRIDMARGIN);
    // draw menu screen before game start
    drawMenuScreen(Canvas, drawMenuScreen, this.setColor, VModel.getGameState());
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
      drawUpcomingTetromino(Canvas, drawNewTetroRect, VModel.getUpcomingTetroTiles(), Convert2, this.setColor);
      // draw game over screen when a tetromino can't spawn
      drawEndGameScreen(Canvas, drawGameOverScreen, drawMenuScreen, this.setColor, VModel.getGameState());
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

  private static void drawUpcomingTetromino(Graphics2D Canvas, Rectangle2D NewTetroBackground, Iterable<GridCell<Character>> Cells, CellPositionToPixelConverter Converter, ColorTheme color) {

    Canvas.setColor(color.getMenuScreenColor("back"));
    Canvas.fill(NewTetroBackground);
    drawCells(Canvas, Cells, Converter, color);

  }
  
}
