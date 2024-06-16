package Features.Components;

import java.awt.*;
import java.awt.event.KeyListener;

import Controls.PlayerKeyboardHandler;
import Window.GamePanel;

public class Player implements Drawable, Dynamism{

    int x;
    int y;
    int dir;
    private int layer = 3;
    private PlayerKeyboardHandler kHandler = new PlayerKeyboardHandler(this);
    private int speed = 10;

    public Player() {
        center();
    }

    @Override
    public void update() {
        if(kHandler.isActive())
            move();
        if(GamePanel.currentRoom.isOpen())
            doorListener();
    }

    private void move() {
        if(kHandler.isUpPressed()) {
            y -= speed;
        } else if(kHandler.isDownPressed()) {
            y += speed;
        }
        if(kHandler.isLeftPressed()) {
            x -= speed;
        } else if (kHandler.isRightPressed()) {
            x += speed;
        }
        fixPos();
    }

    private void fixPos() {
        if(x < 5)
            x = 5;
        else if(x > 995)
            x = 995;
        if(y < 5)
            y = 5;
        else if(y > 995)
            y = 995;
    }

    private void doorListener() {
        if(GamePanel.currentRoom.hasDoorNorth() && checkDistance(new int[]{GamePanel.paneWidth/2, 0}, 15) && (dir == 12 || dir <= 2)) {
            GamePanel.changeRoom("North");
            System.out.println("nuova stanza: "+GamePanel.currentRoom);
            GamePanel.currentRoom.printRoom();
            System.out.println(GamePanel.drawList);
            center();
        }
        if(GamePanel.currentRoom.hasDoorSouth() && checkDistance(new int[]{GamePanel.paneWidth/2, GamePanel.paneHeight}, 15) && (dir >= 6 && dir <= 8)) {
            GamePanel.changeRoom("South");
            System.out.println("nuova stanza: "+GamePanel.currentRoom);
            GamePanel.currentRoom.printRoom();
            System.out.println(GamePanel.drawList);
            center();
        }
        if(GamePanel.currentRoom.hasDoorWest() && checkDistance(new int[]{0, GamePanel.paneHeight/2}, 15) && (dir >= 9 && dir <= 11)) {
            GamePanel.changeRoom("West");
            System.out.println("nuova stanza: "+GamePanel.currentRoom);
            GamePanel.currentRoom.printRoom();
            System.out.println(GamePanel.drawList);
            center();
        }
        if(GamePanel.currentRoom.hasDoorEast() && checkDistance(new int[]{GamePanel.paneWidth, GamePanel.paneHeight/2}, 15)&& (dir >= 3 && dir <= 5)) {
            GamePanel.changeRoom("East");
            System.out.println("nuova stanza: "+GamePanel.currentRoom);
            GamePanel.currentRoom.printRoom();
            System.out.println(GamePanel.drawList);
            center();
        }
    }

    private void center() {
        x = 500;
        y = 500;
    }

    private boolean checkDistance(int[] pos, int maxDistance) {
        Point center = new Point(pos[0], pos[1]);
        Point playerPos = new Point(x*GamePanel.paneWidth/1000, y*GamePanel.paneHeight/1000);
        if(Math.abs(playerPos.distance(center)) <= maxDistance) {
            System.out.println("toccando porta");
            System.out.println("vecchia stanza: "+GamePanel.currentRoom);
            return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        fillRectCenter(g, 10, 10, x, y);
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public KeyListener getKeyboardHandler() {
        return kHandler;
    }
}
