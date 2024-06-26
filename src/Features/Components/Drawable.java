package Features.Components;

import java.awt.*;
import java.awt.image.BufferedImage;

import Window.GamePanel;

public interface Drawable {

    void draw(Graphics2D g);

    int getLayer();

    void setLayer(int layer);

    default void drawStringCenter(Graphics2D g, String text, int percX, int percY) {
        int frameWidth = GamePanel.paneWidth;
        int frameHeight = GamePanel.paneHeight;

        int x = (percX * frameWidth) / 1000;
        int y = (percY * frameHeight) / 1000;

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        int centerX = x - (textWidth / 2);
        int centerY = y - (textHeight / 2) + metrics.getAscent();

        g.drawString(text, centerX, centerY);
    }

    default void drawStringCenter(Graphics2D g, String text, int percx, int percy, Color color) {
        int frameWidth = GamePanel.paneWidth;
        int frameHeight = GamePanel.paneHeight;

        int x = (percx * frameWidth) / 1000;
        int y = (percy * frameHeight) / 1000;

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        int centerX = x - (textWidth / 2);
        int centerY = y - (textHeight / 2) + metrics.getAscent();

        Color originalColor = g.getColor();
        g.setColor(color);
        g.drawString(text, centerX, centerY);
        g.setColor(originalColor);
    }

    default void drawImageCenter(Graphics2D g, BufferedImage image, int widthPerc, int heightPerc, int percx, int percy) {
        int frameWidth = GamePanel.paneWidth;
        int frameHeight = GamePanel.paneHeight;

        int centerX = (percx * frameWidth) / 1000;
        int centerY = (percy * frameHeight) / 1000;

        int width = widthPerc * frameWidth/1000;
        int height = heightPerc * frameHeight/1000;

        int x = centerX - (width / 2);
        int y = centerY - (height / 2);

        g.drawImage(image, x, y, width, height, null);
    }

    default void fillRectCenter(Graphics2D g, int width, int height, int percx, int percy) {
        int frameWidth = GamePanel.paneWidth;
        int frameHeight = GamePanel.paneHeight;

        int x = (percx * frameWidth) / 1000;
        int y = (percy * frameHeight) / 1000;

        int centerX = x - (width / 2);
        int centerY = y - (height / 2);

        g.fillRect(centerX, centerY, width, height);
    }

    default void fillRectCenter(Graphics2D g, int width, int height, int percx, int percy, Color color) {
        int frameWidth = GamePanel.paneWidth;
        int frameHeight = GamePanel.paneHeight;

        int x = (percx * frameWidth) / 1000;
        int y = (percy * frameHeight) / 1000;

        int centerX = x - (width / 2);
        int centerY = y - (height / 2);

        Color originalColor = g.getColor();
        g.setColor(color);

        g.fillRect(centerX, centerY, width, height);

        g.setColor(originalColor);
    }
}
