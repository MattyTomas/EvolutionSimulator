package dev.tomaselli;

import dev.tomaselli.players.Circle;
import dev.tomaselli.players.Sphere;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("FieldCanBeLocal")
public class SimulatorPanel extends JPanel implements Runnable {

    public final int screenScale = 16;
    private final int screenWidth = 800;
    private final int screenHeight = 600;

    private final int FPS = 60;
    public static final int entityCap = 5000;

    private Thread mainThread;

    public SimulatorPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(new KeyHandler());
        this.setDefaultStart();
        this.startMainThread();
    }

    private void startMainThread() {
        mainThread = new Thread(this);
        mainThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(mainThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                saveStatistics();
                timer = 0;
                drawCount = 0;
            }
        }
    }

    public void update() {
        for(int i = 0; i < Circle.circlesList.size(); i++) {
            Circle.circlesList.get(i).updateStatus();
        }

        for(int i = 0; i < Sphere.spheresList.size(); i++) {
            Sphere.spheresList.get(i).updateStatus();
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for(int i = 0; i < Sphere.spheresList.size(); i++) {
            Sphere.spheresList.get(i).draw(g2);
        }

        for(int i = 0; i < Circle.circlesList.size(); i++) {
            Circle.circlesList.get(i).draw(g2);
        }

        g2.dispose();
    }

    private void saveStatistics() {

    }

    public void resetSimulation() {
        Circle.circlesList.clear();
        Sphere.spheresList.clear();
        this.setDefaultStart();
    }

    private void setDefaultStart() {
        for(int i = 0; i < 25; i++) {
            Circle circle = new Circle(this, 350, 300);
            Circle.circlesList.add(circle);
        }

        for(int i = 0; i < 25; i++) {
            Sphere sphere = new Sphere(this, 446, 300);
            Sphere.spheresList.add(sphere);
        }
    }
}
