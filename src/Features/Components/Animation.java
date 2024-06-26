package Features.Components;

import Window.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
    boolean repeat = true;


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
                frames[i] = ImageIO.read(new File("res/Images/" + name + "/" + (i+1) + ".png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Animation(String name, int[] delay, int x, int y, int width, int height, int layer, boolean repeat) {
        this.name = name;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.delay = delay;
        this.frameWidth = width;
        this.frameHeight = height;
        this.repeat = repeat;
        frames = new BufferedImage[delay.length];
        try {
            for(int i = 0; i < delay.length; i++) {
                frames[i] = ImageIO.read(new File("res/Images/" + name + "/" + (i+1) + ".png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Animation(String name, int counterDelay, int x, int y, int width, int height, int layer, boolean repeat) {
        this.name = name;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.delay = allOne(counterDelay);
        this.frameWidth = width;
        this.frameHeight = height;
        this.repeat = repeat;
        frames = new BufferedImage[delay.length];
        try {
            for(int i = 0; i < delay.length; i++) {
                frames[i] = ImageIO.read(new File("res/Images/" + name + "/" + (i+1) + ".png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public Animation(int counterDelay, BufferedImage[] frames, int x, int y, int width, int height, int layer, boolean repeat) {
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.delay = allOne(counterDelay);
        this.frameWidth = width;
        this.frameHeight = height;
        this.repeat = repeat;
        this.frames = frames;
    }

    public Animation(int counterDelay, BufferedImage[] frames, int layer, boolean repeat) {
        this.layer = layer;
        this.x = 0;
        this.y = 0;
        this.delay = allOne(counterDelay);
        this.frameWidth = GamePanel.paneWidth;
        this.frameHeight = GamePanel.paneHeight;
        this.repeat = repeat;
        this.frames = frames;
    }


    @Override
    public void update() {
        if (counter <= 0) {
            actualFrame = (actualFrame + 1)%frames.length;
            if(!repeat && actualFrame == 0) {
                GamePanel.clock.removeComponent(this);
                GamePanel.drawList.remove(this);
            }
            counter = delay[actualFrame];
        }
        counter--;
    }

    @Override
    public void draw(Graphics2D g) {

        drawImageCenter(g, frames[actualFrame], 1000, 1000, 500, 500);

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

    private int[] allOne(int counter) {
        int[] all = new int[counter];
        Arrays.fill(all, 1);
        return all;
    }

    public static BufferedImage[] genFrames(String name, int length) {
        try {
            BufferedImage[] frames = new BufferedImage[length];
            for(int i = 0; i < length; i++) {
                frames[i] = ImageIO.read(new File("res/Images/" + name + "/" + (i+1) + ".png"));
            }
            return frames;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
