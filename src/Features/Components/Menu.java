package Features.Components;

import java.awt.*;
import Window.GamePanel;

public class Menu implements Drawable{

    public static final int MAIN_MENU = 0;

    int type;
    GamePanel panel;

    Menu(GamePanel panel, int type) {
        this.panel = panel;
        this.type = type;
    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public void setLayer(int layer) {

    }
}
