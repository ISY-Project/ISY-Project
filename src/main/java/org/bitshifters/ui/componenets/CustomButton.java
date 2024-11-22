package org.bitshifters.ui.componenets;

import javafx.scene.control.Button;

public class CustomButton extends Button {
    /**
     * Custom Button, just a normal button
     * @param text the text to display on the button
     */
    public CustomButton(String text) {
        super(text);
        setFocusTraversable(false); // remove the focus from the button to make it look better
    }

    /**
     * Custom Button with style
     * @param text the text to display on the button
     * @param style the style of the button
     */
    public CustomButton(String text,String style) {
        super(text);
        setStyle(style);
        setFocusTraversable(false); // remove the focus from the button to make it look better
    }

    /**
     * Custom Button with pref width
     * @param text the text to display on the button
     * @param width the width of the button
     */
    public CustomButton(String text, double width) {
        super(text);
        setPrefWidth(width);
        setFocusTraversable(false); // remove the focus from the button to make it look better
    }

    /**
     * Custom Button with pref width and style
     * @param text the text to display on the button
     * @param width the width of the button
     * @param style the style of the button
     */
    public CustomButton(String text, double width, String style) {
        super(text);
        setPrefWidth(width);
        setStyle(style);
        setFocusTraversable(false); // remove the focus from the button to make it look better
    }

    /**
     * Custom Button with optional pref width and height
     * @param text the text to display on the button
     * @param width the width of the button (if less than 0, only the height will be set)
     * @param height the height of the button
     */
    public CustomButton(String text, double width, double height) {
        super(text);
        if (width < 0) {
            setPrefHeight(height);
        } else { 
            setPrefWidth(width);
            setPrefHeight(height);
        }
        setFocusTraversable(false); // remove the focus from the button to make it look better
        
    }

    /**
     * Custom Button with optional pref width, height and style
     * @param text the text to display on the button
     * @param width the width of the button (if less than 0, only the height will be set)
     * @param height the height of the button
     * @param style the style of the button
     */
    public CustomButton(String text, double width, double height, String style) {
        super(text);
        if (width < 0) {
            setPrefHeight(height);
        } else { 
            setPrefWidth(width);
            setPrefHeight(height);
        }
        setStyle(style);
        setFocusTraversable(false); // remove the focus from the button to make it look better
    }

}
