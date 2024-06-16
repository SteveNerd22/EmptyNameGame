package Window;

import Features.Components.Dynamism;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    Timer timer = new Timer();
    LinkedList<Dynamism> updateList = new LinkedList<Dynamism>();

    public Clock() {
        timer.schedule(new TimerTask() {
            public void run() {
                GamePanel.tick = (GamePanel.tick + 1)%20;
                for(Dynamism dynamism : updateList) {
                    dynamism.update();
                }
            }
        }, 0, 20);
    }

    public void addComponent(Dynamism dynamism) {
        updateList.add(dynamism);
    }
    public void removeComponent(Dynamism dynamism) {
        updateList.remove(dynamism);
    }
}
