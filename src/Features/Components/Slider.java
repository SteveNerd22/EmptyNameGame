package Features.Components;

import Controls.MouseHandlerFrameRate;
import Window.GamePanel;

import javax.swing.*;
import java.awt.*;

public class Slider implements Drawable {

    private int layer;

    public int x;
    int y;
    int width;
    public int pos;
    JLabel label;
    MouseHandlerFrameRate m = new MouseHandlerFrameRate(this);

    public Slider(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        label = new JLabel();
        label.setBounds(x+pos-4,y-4,x+pos+4,y+4);
        label.addMouseListener(m);
        this.pos = 25;


    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        g.drawLine(x,y,x+width,y);
        g.setColor(Color.BLUE);
        g.fillOval(x+pos-4,y-4,8,8);
        String fps = value()+"";
        g.drawString(fps,x+ (width/2) - 4, y + 20 );
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }

    public double value() {
        return GamePanel.scalaLogaritmica(pos);
    }

    public JLabel getLabel() {
        return label;
    }
}
