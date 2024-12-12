package org.bitshifters.ui;

import org.bitshifters.FXSetup;
import org.bitshifters.ui.components.CustomButton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;

public class CustomButtonTest {
    @BeforeAll
    static void initJfxRuntime() {
        if (FXSetup.isSetup) {
            return;
        }
        FXSetup.isSetup = true;
        Platform.startup(() -> {});
    }

    @Test
    public void testCustomButtonWithText() {
        CustomButton button = new CustomButton("Click Me");
        assertEquals("Click Me", button.getText());
        assertFalse(button.isFocusTraversable());
    }

    @Test
    public void testCustomButtonWithTextAndStyle() {
        CustomButton button = new CustomButton("Click Me", "-fx-background-color: red;");
        assertEquals("Click Me", button.getText());
        assertEquals("-fx-background-color: red;", button.getStyle());
        assertFalse(button.isFocusTraversable());
    }

    @Test
    public void testCustomButtonWithTextAndWidth() {
        CustomButton button = new CustomButton("Click Me", 100.0);
        assertEquals("Click Me", button.getText());
        assertEquals(100.0, button.getPrefWidth());
        assertFalse(button.isFocusTraversable());
    }

    @Test
    public void testCustomButtonWithTextWidthAndStyle() {
        CustomButton button = new CustomButton("Click Me", 100.0, "-fx-background-color: red;");
        assertEquals("Click Me", button.getText());
        assertEquals(100.0, button.getPrefWidth());
        assertEquals("-fx-background-color: red;", button.getStyle());
        assertFalse(button.isFocusTraversable());
    }

    @Test
    public void testCustomButtonWithTextWidthAndHeight() {
        CustomButton button = new CustomButton("Click Me", 100.0, 50.0);
        assertEquals("Click Me", button.getText());
        assertEquals(100.0, button.getPrefWidth());
        assertEquals(50.0, button.getPrefHeight());
        assertFalse(button.isFocusTraversable());
    }

    @Test
    public void testCustomButtonWithTextWidthHeightAndStyle() {
        CustomButton button = new CustomButton("Click Me", 100.0, 50.0, "-fx-background-color: red;");
        assertEquals("Click Me", button.getText());
        assertEquals(100.0, button.getPrefWidth());
        assertEquals(50.0, button.getPrefHeight());
        assertEquals("-fx-background-color: red;", button.getStyle());
        assertFalse(button.isFocusTraversable());
    }

    @Test
    public void testCustomButtonWithNegativeWidth() {
        CustomButton button = new CustomButton("Click Me", -1, 50.0);
        assertEquals("Click Me", button.getText());
        assertEquals(50.0, button.getPrefHeight());
        assertFalse(button.isFocusTraversable());
    }
}
