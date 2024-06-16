package Features.Components;

import Window.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Animation implements Drawable, Dynamism{

    int x;
    int y;

    private int layer;

    BufferedImage[] frames;
    String name;
    int[] delay;
    int counter = 0;
    int actualFrame = 0;
    int lastTick = GamePanel.tick;
    int frameWidth, frameHeight;


    public Animation(String name, int[] delay, int x, int y, int width, int height, int layer) {
        this.name = name;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.delay = delay;
        this.frameWidth = width;
        this.frameHeight = height;
        frames = new BufferedImage[delay.length];
        try {
            for(int i = 0; i < delay.length; i++) {
                frames[i] = ImageIO.read(new File("res/Images/" + name + "_" + (i+1) + ".png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {
        if (counter <= 0) {
            actualFrame = (actualFrame + 1)%frames.length;
            counter = delay[actualFrame];
        }
        counter--;
    }

    @Override
    public void draw(Graphics2D g) {

        g.drawImage(frames[actualFrame], x, y, frameWidth, frameHeight, null);

        if(lastTick != GamePanel.tick) {
            counter--;
            lastTick = GamePanel.tick;
        }
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }

    @Override
    public String toString() {
        return name+" in "+layer;
    }

}
