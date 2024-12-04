package org.bitshifters.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageTools {
    public static BufferedImage addLayer(final BufferedImage background, final BufferedImage foreground) {
        if (background.getWidth() != foreground.getWidth() || background.getHeight() != foreground.getHeight()) {
            throw new IllegalArgumentException("Images must have the same dimensions");
        }

        BufferedImage combinedImage = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combinedImage.createGraphics();
        g.drawImage(background, 0, 0, null);
        g.drawImage(foreground, 0, 0, null);
        return combinedImage;
    }
}
