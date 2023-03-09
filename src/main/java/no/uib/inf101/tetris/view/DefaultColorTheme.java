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
            // tetromino colors
            case 'L' -> Color.ORANGE;
            case 'J' -> Color.BLUE.darker();
            case 'S' -> Color.GREEN;
            case 'Z' -> Color.RED;
            case 'T' -> Color.MAGENTA.darker();
            case 'I' -> Color.CYAN;
            case 'O' -> Color.YELLOW;
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

    @Override
    public Color getScoreBoardColor(String C) {
        Color color = switch (C) {
            case "back" -> Color.DARK_GRAY;
            case "score" -> Color.WHITE;
            default -> throw new IllegalArgumentException("No available color for '" + C + "'\nTry 'back' or 'score'.");
        };
        return color;
    }
    
    @Override
    public Color getLevelBoardColor(String C) {
        Color color = switch (C) {
            case "back" -> Color.DARK_GRAY;
            case "level" -> Color.WHITE;
            default -> throw new IllegalArgumentException("No available color for '" + C + "'\nTry 'back' or 'score'.");
        };
        return color;
    }

    @Override
    public Color getGameOverColor(String C) {
        Color color = switch(C) {
            case "back" -> new Color(0, 0, 0, 128);
            case "front" -> Color.decode("#4F0001").brighter().brighter().brighter();
            default -> throw new IllegalArgumentException("No available color for '" + C + "'\nTry 'back' or 'front'.");
        };
        return color;
    }
    
}
