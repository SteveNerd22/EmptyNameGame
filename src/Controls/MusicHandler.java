package Controls;

import Features.Components.Animation;
import Sound.SoundHandler;
import Window.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class MusicHandler implements KeyListener {

    SoundHandler sound;
    static BufferedImage[] images = Animation.genFrames("switch", 60);
    static Animation boh = new Animation(60, MusicHandler.images, 8, false);


    public MusicHandler(SoundHandler s) {
        sound = s;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_C) {
            sound.swapMusic();
            GamePanel.clock.addComponent(boh);
            GamePanel.drawList.add(boh);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
