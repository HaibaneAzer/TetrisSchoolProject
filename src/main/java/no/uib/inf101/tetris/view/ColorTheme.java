package no.uib.inf101.tetris.view;

import java.awt.Color;

public interface ColorTheme {
    
/**
 * getCellColor har parameter char og returnerer verdier av typen Color.
 * @param Char
 * return verdi kan ikke være null.
 */
    Color getCellColor(Character Char);

/**
 * getFrameColor har ingen parametre og returnerer verdier av typen Color.
 * return verdi kan ikke vøre null, alternativt kan (new Color(0, 0, 0, 0)) brukes
 * om du ikke vil ha egen ramme rundt rutene. 
 */
    Color getFrameColor();

/**
 * getBackgroundColor har ingen parametre og returnerer verdier av typen Color.
 * return verdi kan ikke være gjennomsiktig, 
 * men null kan brukes, som gir standard bakgrunnsfarge fra Java. 
 */
    Color getBackgroundColor();
}
