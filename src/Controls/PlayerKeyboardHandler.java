package Controls;

import Features.Components.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerKeyboardHandler implements KeyListener {
    boolean active;
    boolean leftPressed;
    boolean rightPressed;
    boolean upPressed;
    boolean downPressed;
    Player player;

    public PlayerKeyboardHandler(Player player) {
        this.player = player;
    }


    public boolean isActive() {
        return active;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_A -> {
                active = true;
                leftPressed = true;
                player.setDir(10);
            }
            case KeyEvent.VK_D -> {
                active = true;
                rightPressed = true;
                player.setDir(4);
            }
            case KeyEvent.VK_W -> {
                active = true;
                upPressed = true;
                player.setDir(1);
            }
            case KeyEvent.VK_S -> {
                active = true;
                downPressed = true;
                player.setDir(7);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_A -> {
                leftPressed = false;
            }
            case KeyEvent.VK_D -> {
                rightPressed = false;
            }
            case KeyEvent.VK_W -> {
                upPressed = false;
            }
            case KeyEvent.VK_S -> {
                downPressed = false;
            }
        }

        if (!leftPressed && !rightPressed && !upPressed && !downPressed) {

            active = false;

        } else {

            if(leftPressed)
                player.setDir(10);
            if(rightPressed)
                player.setDir(4);
            if(upPressed)
                player.setDir(1);
            if(downPressed)
                player.setDir(7);

        }
    }
}
