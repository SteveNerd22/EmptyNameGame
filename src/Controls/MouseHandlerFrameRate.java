package Controls;

import Features.Components.Slider;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandlerFrameRate implements MouseMotionListener, MouseListener {

    Slider s;

    public MouseHandlerFrameRate(Slider s) {
        this.s = s;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("BUTTON PRESSED");
            System.out.println("dis: " + (e.getX()));
            if(e.getX() >= 0 && e.getX() <= 50)
                s.pos = e.getX();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
