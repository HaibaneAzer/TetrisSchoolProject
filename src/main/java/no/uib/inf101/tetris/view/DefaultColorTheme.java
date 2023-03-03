package no.uib.inf101.tetris.view;

import java.awt.Color;

public class DefaultColorTheme implements ColorTheme {

    @Override
    public Color getCellColor(Character C) {
        Color color = switch(C) {
            case 'r' -> Color.RED;
            case 'g' -> Color.GREEN;
            case 'b' -> Color.BLUE;
            case 'y' -> Color.YELLOW;
            case 'm' -> Color.MAGENTA;
            case '-' -> Color.BLACK;
            default -> throw new IllegalArgumentException("No available color for '" + C + "'");
        };
        return color;
    }

    @Override
    public Color getFrameColor() {
        Color c = new Color(0, 0, 0, 0);
        return c;
    }

    @Override
    public Color getBackgroundColor() {
        return null;
    }
    
}
