package no.uib.inf101.tetris.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.awt.Color;

public class TestDefaultColorTheme {
    
    @Test
    public void sanityTestDefaultColorTheme() {
        ColorTheme colors = new DefaultColorTheme();
        assertEquals(null, colors.getBackgroundColor());
        assertEquals(new Color(0, 0, 0, 0), colors.getFrameColor());
        assertEquals(Color.BLACK, colors.getCellColor('-'));
        assertEquals(Color.RED, colors.getCellColor('r'));
        assertThrows(IllegalArgumentException.class, () -> colors.getCellColor('\n'));
}

}
