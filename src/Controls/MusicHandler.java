package Controls;

import Sound.SoundHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MusicHandler implements KeyListener {

    SoundHandler sound;

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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
