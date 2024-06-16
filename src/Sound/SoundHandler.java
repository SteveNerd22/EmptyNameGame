package Sound;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundHandler {
    public static int MAX_SOUNDS_INDEX = 30;
    static URL[] sounds;
    public static Map<String, Integer> soundMap = new HashMap<String, Integer>();
    static SoundPlayer activePlayer;
    static SoundPlayer musicPlayer1 = new SoundPlayer();
    static SoundPlayer musicPlayer2 = new SoundPlayer();
    static SoundPlayer sound1 = new SoundPlayer();
    static SoundPlayer sound2 = new SoundPlayer();
    static SoundPlayer sound3 = new SoundPlayer();

    public SoundHandler() {
        sounds = fetchSounds("res/Sounds");
        activePlayer = musicPlayer1;
    }

    public URL[] fetchSounds(String folderPath) {
        File folder = new File(folderPath);
        List<URL> soundUrls = new ArrayList<>();

        if (folder.exists() && folder.isDirectory()) {
            File[] soundFiles = folder.listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".wav")
            );

            if (soundFiles != null) {
                int i = 0;
                for (File soundFile : soundFiles) {
                    try {
                        soundUrls.add(soundFile.toURI().toURL());
                        soundMap.put(soundFile.getName(), i);
                        i++;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                MAX_SOUNDS_INDEX = i;
            }
        } else {
            System.err.println("Folder not found: " + folderPath);
        }

        return soundUrls.toArray(new URL[0]);
    }


    public boolean playSound(String soundName) {
        if(soundMap.containsKey(soundName+".wav")){
            return play(soundMap.get(soundName+".wav"));
        }
        return false;
    }

    public boolean queueMusic(String soundName) {
        if(soundMap.containsKey(soundName+".wav")){
            return queue(soundMap.get(soundName+".wav"));
        }
        return false;
    }

    public boolean queue(int soundIndex) {
        if (soundIndex >= 5 || soundIndex < 0) {
            return false;
        }
        if(activePlayer == musicPlayer1) {
            return musicPlayer2.queue(soundIndex, true);
        }
        return musicPlayer1.queue(soundIndex, true);
    }

    public boolean play(int soundIndex) {
        if(soundIndex < 0 || soundIndex >= MAX_SOUNDS_INDEX)
            return false;
        if(soundIndex < 5) {
            return activePlayer.playSound(soundIndex, true);
        }
        boolean playerFound = false;
        if(!sound1.isPlaying())
            playerFound = sound1.playSound(soundIndex, false);
        if(!sound2.isPlaying() && !playerFound)
            playerFound = sound2.playSound(soundIndex, false);
        if(!sound3.isPlaying() && !playerFound)
            playerFound = sound3.playSound(soundIndex, false);
        return playerFound;
    }

     public String currentTrack() {
        return sounds[activePlayer.getTrack()].toString();
     }

     public int getMusicTime() {
        return activePlayer.getTime();
     }

     public void setMusicTime(int time) {
        activePlayer.setTime(time);
     }

     public void setActivePlayer(SoundPlayer activePlayer) {
        SoundHandler.activePlayer = activePlayer;
     }

     public void swapMusic() {
        activePlayer.pause();
        int time = getMusicTime();
        if(activePlayer == musicPlayer1)
            setActivePlayer(musicPlayer2);
        else
            setActivePlayer(musicPlayer1);
        activePlayer.setTime(time);
        activePlayer.resume();
     }



}