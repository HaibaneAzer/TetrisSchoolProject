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
            case 'T' -> Color.MAGENTA;
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
            case "back" -> new Color(0, 0, 0, 192);
            case "gameover" -> Color.decode("#4F0001").brighter().brighter().brighter();
            case "key" -> Color.decode("#4F0001").brighter().brighter().brighter();
            default -> throw new IllegalArgumentException("No available color for '" + C + "'\nTry 'back' or 'front'.");
        };
        return color;
    }

    @Override
    public Color getMenuScreenColor(String C) {
        Color color = switch(C) {
            case "back" -> Color.BLACK.brighter();
            case "title" -> Color.WHITE.darker();
            case "key" -> Color.WHITE.darker();
            default -> throw new IllegalArgumentException("No available color for '" + C + "'\nTry 'back' or 'front'.");
        };
        return color;
    }

    @Override 
    public Color getTextColor(String C) {
        Color color = switch(C) {
            case "score" -> Color.GRAY;
            case "level" -> Color.GRAY;
            case "newTetro" -> Color.GRAY.darker().darker();
            default -> throw new IllegalArgumentException("No available color for '" + C + "'\nTry 'score', 'level' or 'newTetro'.");
        };
        return color;
    }
    
}
