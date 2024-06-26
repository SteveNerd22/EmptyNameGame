package Window;

import Controls.MusicHandler;
import Features.Components.Drawable;
import Features.Components.Player;
import Features.Map.Map;
import Features.Map.Room;
import Sound.SoundHandler;
import Features.Components.Animation;
import Structures.DrawableList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;


public class GamePanel extends JPanel implements Runnable{

    static int ERROR_CODE;

    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    static int LAST_FULLSCREEN_WIDTH;
    static int LAST_FULLSCREEN_HEIGHT;

    public static int frameWidth;
    public static int frameHeight;

    public static int paneWidth;
    public static int paneHeight;

    public static int tick;
    double fps = 100;

    Thread gameThread;
    static boolean gameRunning = false;
    public static GamePhases phase = GamePhases.Starting;
    public static Map map;
    public static Room currentRoom;
    JFrame frame;
    public static DrawableList<Drawable> drawList = new DrawableList<>(0);
    SoundHandler sounds;
    Player p;
    private MusicHandler m;
    public static Clock clock;
    public static Animation menuBackground;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCREEN_WIDTH = (int) screenSize.getWidth();
        SCREEN_HEIGHT = (int) screenSize.getHeight();
    }

    public GamePanel(JFrame jframe) {
        frame = jframe;
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    toggleFullscreen();
                }
            }
        });
        frame.setMinimumSize(new Dimension(400, 600));
        frameWidth = frame.getSize().width;
        frameHeight = frame.getSize().height;
        paneWidth = frame.getContentPane().getWidth();
        paneHeight = frame.getContentPane().getHeight();
        LAST_FULLSCREEN_WIDTH = frame.getSize().width;
        LAST_FULLSCREEN_HEIGHT = frame.getSize().height;
        frame.setPreferredSize(new Dimension(LAST_FULLSCREEN_WIDTH, LAST_FULLSCREEN_HEIGHT));
        addComponentListener(new FrameResizeListener());
        this.setDoubleBuffered(true);
        this.setFocusable(true);

    }

    private void toggleFullscreen() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (frame.isUndecorated()) {
            // Se la finestra è già senza decorazioni, riportala alla modalità normale
            frame.dispose();
            frame.setUndecorated(false);
            device.setFullScreenWindow(null);
            frame.setSize(LAST_FULLSCREEN_WIDTH, LAST_FULLSCREEN_HEIGHT); // Ripristina le dimensioni originali
            frame.setLocationRelativeTo(null); // Centra la finestra sullo schermo
            frame.setVisible(true);
        } else {
            // Altrimenti, entra a schermo intero senza bordi
            frame.dispose(); // Chiudi la finestra corrente
            frame.setUndecorated(true); // Rimuovi la decorazione della finestra
            device.setFullScreenWindow(frame);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Assicurati che il programma termini quando si chiude la finestra
            LAST_FULLSCREEN_WIDTH = frameWidth;
            LAST_FULLSCREEN_HEIGHT = frameHeight;
            frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT); // Imposta le dimensioni dello schermo
            frame.setLocation(0, 0); // Imposta la posizione in alto a sinistra dello schermo
            frame.setVisible(true); // Rendi la finestra visibile
        }
    }


    @Override
    public void run() {
        while(gameRunning) {
            long start = System.nanoTime();
            long period = (long)(1000/fps);
            repaint();
            long end = System.nanoTime();
            long delta = end - start;
            try {
                long sleep = period - delta;
                if (sleep > 0)
                    Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stopGameAsync(null);
    }

    public void startGameThread() {
        initExecution();
        try {
            gameRunning = defaultValue();
        } catch (Throwable e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            String stackTraceString = Arrays.toString(stackTrace);
            stopGame(stackTraceString);
        }
        if (gameRunning) {
            gameThread = new Thread(this);
            gameThread.start();
            phase = GamePhases.MainMenu;
        }
    }

    private void initExecution() {
        sounds = new SoundHandler();
        m = new MusicHandler(sounds);
        this.addKeyListener(m);
        clock = new Clock();
    }

    private boolean defaultValue() {
        boolean result = true;

        menuBackground = new Animation(60, Animation.genFrames("night", 60), 1, true);
        clock.addComponent(menuBackground);
        drawList.add(menuBackground);

        drawList.sort();
        System.out.println(drawList);
        return result;
    }

    private void defaultGameInitValues() {
        sounds.playSound("Track");
        sounds.queueMusic("TrackDistorta");
        map = new Map(1);
        map.printMap();
        currentRoom = map.getStartingRoom(drawList);
        drawList.add(currentRoom);
        p = new Player();
        this.addKeyListener(p.getKeyboardHandler());
        clock.addComponent(p);
        drawList.add(p);
        drawList.sort();
    }

    public void changeGamePhase(GamePhases gamePhase) {
        GamePhases oldPhase = phase;
        phase = gamePhase;
        if(oldPhase == GamePhases.MainMenu && phase == GamePhases.inGame) {
            defaultGameInitValues();
            clock.removeComponent(menuBackground);
            drawList.remove(menuBackground);
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(0, 0, paneWidth, paneHeight);

        drawList.draw(g2);
        System.out.println(phase);

        g2.dispose();
    }


    public void stopGameAsync(final String crashReason) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                stopGame(crashReason);
            }
        }).start();
    }

    private void stopGame(String crashReason) {
        phase = GamePhases.Closing;
        gameRunning = false;
        if(gameThread != null && gameThread.isAlive())
            gameThread.interrupt();
        if(crashReason != null)
            makeCrashReport(crashReason);
    }

    private void makeCrashReport(String crashReason) {
    }

    private class FrameResizeListener extends ComponentAdapter {

        public void componentResized(ComponentEvent e) {
            Dimension newSize = frame.getSize();
            frameWidth = newSize.width;
            frameHeight = newSize.height;
            paneWidth = frame.getContentPane().getWidth();
            paneHeight = frame.getContentPane().getHeight();
        }

    }


    public static double scalaLogaritmica(int valore) {
        if(valore >= 0 && valore < 10)
            return (0.001*valore) + 0.001;
        if(valore >= 10 && valore < 20)
            return ((0.01*(valore-10)) + 0.01);
        if(valore >= 20 && valore < 30)
            return ((0.1*(valore-20)) + 0.1);
        if(valore >= 30 && valore < 40)
            return ((valore-30) + 1);
        if(valore >= 40 && valore < 50)
            return ((10*(valore-40)) + 10);
        return 60;
    }

    public static void changeRoom(String dir) {
        switch (dir) {
            case "North" -> currentRoom = map.changeRoom(currentRoom.x-1, currentRoom.y, drawList);
            case "South" -> currentRoom = map.changeRoom(currentRoom.x+1, currentRoom.y, drawList);
            case "East" -> currentRoom = map.changeRoom(currentRoom.x, currentRoom.y+1, drawList);
            case "West" -> currentRoom = map.changeRoom(currentRoom.x, currentRoom.y-1, drawList);
        }
    }

}


