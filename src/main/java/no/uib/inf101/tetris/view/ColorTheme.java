package no.uib.inf101.tetris.view;

import java.awt.Color;

public interface ColorTheme {
    
/**
 * getCellColor has one parameter of type Character and returns values of type Color.
 * Returned values cannot be "null".
 * valid inputs are any of the Chars in "LJSZTIO", and corrosponding input is given a Color
 * based on standard colors for Tetrominos. Other inputs are 'r', 'b', 'y' and 'g'.
 * @param Char is of type Char
 * @return a not-null value of type Color.
 */
    Color getCellColor(Character Char);

/**
 * getFrameColor has no parameters and returns values of type Color. 
 * return value must be not-null, but values such as "new Color(0, 0, 0, 0)" can be substituted
 * if you don't want your own frame behind the cells.
 * @return a not-null value of type Color.
 */
    Color getFrameColor();

/**
 * getBackgroundColor has no parameters and returns values of type Color.
 * Return value must not be transparent, but null values are allowed, which
 * returns a standard background color from Java.
 * @return a none transparent value of type Color.
 */
    Color getBackgroundColor();

/**
 * getScoreBoardColor has parameter of type string and returns values of type Color.
 * valid inputs are "back"; corrosponds to color dark gray, and "score"; corrosponds to
 * color white.
 * @param C is of type string
 * @return a not-null value of type Color
 */
    Color getScoreBoardColor(String C);

/**
 * getGameOverColor has parameter of type String and returns values of type Color.
 * valid inputs are "front"; which corrosponds to color red, and "back"; which corrosponds
 * to a transparent color and "key"; which corrosponds to color red.
 * @param C is of type String
 * @return a not-null value of type Color
 */
    Color getGameOverColor(String C);

/**
 * getMenuScreenColor has parameter of type String and returns values of type Color.
 * valid inputs are "title"; which corrosponds to color White, and "back"; which corrosponds
 * to color black and "key"; which corrosponds to color white.
 * @param C is of type String
 * @return a not-null value of type Color
 */
Color getMenuScreenColor(String C);

/**
 * getLevelBoardColor has parameter of type string and returns values of type Color.
 * valid inputs are "back"; corrosponds to color dark gray, and "level"; corrosponds to
 * color white.
 * @param C is of type string
 * @return a not-null value of type Color
 */
    Color getLevelBoardColor(String C);
}
