package org.bitshifters.ui.enums;

public enum XO {
    X("X"), 
    O("O");

    private final String xoChar;
    private final String path = "src\\main\\resources\\images";

    XO(String xoChar) {
        this.xoChar = xoChar;
    }

    public String getXoChar() {
        return xoChar;
    }

    public String getRedPath() {
        return path + "\\Red" + xoChar + ".png";
    }

    public String getBluePath() {
        return path + "\\Blue" + xoChar + ".png";
    }

}
