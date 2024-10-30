package dev.tomaselli;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public static SimulatorPanel simulatorPanel;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {
            simulatorPanel.resetSimulation();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
