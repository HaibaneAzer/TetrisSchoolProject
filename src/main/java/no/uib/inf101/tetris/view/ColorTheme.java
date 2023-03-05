package no.uib.inf101.tetris.view;

import java.awt.Color;

public interface ColorTheme {
    
/**
 * getCellColor has one parameter of type Character and returns values of type Color.
 * Returned values cannot be "null" 
 * @param Char is of type Char
 * @return a not-null value of type Color.
 */
    Color getCellColor(Character Char);

/**
 * getFrameColor has no parameters and returns values of type Color. 
 * return value must be not-null, but values such as "new Color(0, 0, 0, 0)" can be substituted
 * if you don't want your own frame behind the cells
 * @return a not-null value of type Color.
 */
    Color getFrameColor();

/**
 * getBackgroundColor has no parameters and returns values of type Color.
 * Return value must not be transparent, but null values are allowed, which
 * returns a standard background color from Java
 * @return a none transparent value of type Color.
 */
    Color getBackgroundColor();
}
