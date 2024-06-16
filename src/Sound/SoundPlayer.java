package Sound;

import javax.sound.sampled.*;
import java.io.IOException;

public class SoundPlayer {

    private int currentTrackIndex = -1;
    private boolean loop;
    private Clip clip;

    public boolean playSound(int trackIndex, boolean loop) {
        if(trackIndex < 0 || trackIndex > SoundHandler.MAX_SOUNDS_INDEX) {
            System.out.println("index:"+trackIndex + "out of range");
            return false;
        }
        if(currentTrackIndex == trackIndex && this.loop) {
            System.out.println("selected track is already in loop");
            return false;
        }
        if(clip != null)
            stop();
        setFile(trackIndex);
        currentTrackIndex = trackIndex;
        this.loop = loop;
        return play(loop);
    }

    private void setFile(int trackIndex) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundHandler.sounds[trackIndex]);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean play(boolean loop) {
        if(loop)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        else
            clip.start();
        return true;
    }

    public boolean stop() {
        if(clip.isOpen()) {
            clip.stop();
            currentTrackIndex = -1;
            loop = false;
            clip.setFramePosition(0);
            clip.close();
            return !clip.isOpen();
        }
        return false;
    }

    public boolean pause() {
        if (clip != null && clip.isOpen() && clip.isRunning()) {
            clip.stop();
            return !clip.isRunning();
        }
        return false;
    }

    public boolean resume() {
        if(clip != null && clip.isOpen() && !clip.isRunning()) {
            if(loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.start();
            return clip.isRunning();
        }
        return false;
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

    public void setVolume(float volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float dB = min + (volume * (max - min));
            gainControl.setValue(dB);
        }
    }

    public float getVolume() {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float currentDB = gainControl.getValue();
            return (currentDB - min) / (max - min);
        }
        return 0.0f; // Se il clip è nullo, ritorna il volume minimo
    }

    public int getTrack() {
        return currentTrackIndex;
    }

    public int getTime() {
        return (clip.getFramePosition() % clip.getFrameLength());
    }

    public void setTime(int time) {
        clip.setFramePosition(time);
    }


    public boolean queue(int trackIndex, boolean loop) {
        if(trackIndex < 0 || trackIndex > SoundHandler.MAX_SOUNDS_INDEX) {
            System.out.println("index:"+trackIndex + "out of range");
            return false;
        }
        if(currentTrackIndex == trackIndex && this.loop) {
            System.out.println("selected track is already in loop");
            return false;
        }
        if(clip != null)
            stop();
        setFile(trackIndex);
        currentTrackIndex = trackIndex;
        this.loop = loop;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        pause();
        return true;
    }
}
