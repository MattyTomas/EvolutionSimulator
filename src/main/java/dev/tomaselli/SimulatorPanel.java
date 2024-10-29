package dev.tomaselli;

import dev.tomaselli.players.Circle;
import dev.tomaselli.players.Sphere;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class SimulatorPanel extends JPanel implements Runnable {

    private final int screenScale = 4;
    private final int screenWidth = 800;
    private final int screenHeight = 600;

    public static List<Circle> circlesList = new ArrayList<>();
    public static List<Sphere> spheresList = new ArrayList<>();

    private final int FPS = 15;

    Thread mainThread;

    public SimulatorPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.setDefaultStart();
        this.startMainThread();
    }

    public void startMainThread() {
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
                timer = 0;
                drawCount = 0;
            }
        }
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void update() {
        for(int i = 0; i < circlesList.size(); i++) {
            circlesList.get(i).updateStatus();
        }

        for(int i = 0; i < spheresList.size(); i++) {
            spheresList.get(i).updateStatus();
        }
    }


    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for(int i = 0; i < circlesList.size(); i++) {
            circlesList.get(i).draw(g2);
        }

        for(int i = 0; i < spheresList.size(); i++) {
            spheresList.get(i).draw(g2);
        }

        g2.dispose();
    }


    public void setDefaultStart() {
        for(int i = 0; i < 25; i++) {
            Circle circle = new Circle(this, 350, 300);
            circlesList.add(circle);
        }

        for(int i = 0; i < 25; i++) {
            Sphere sphere = new Sphere(this, 446, 300);
            spheresList.add(sphere);
        }
    }
}
