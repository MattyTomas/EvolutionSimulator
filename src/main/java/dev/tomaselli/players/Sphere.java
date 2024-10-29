package dev.tomaselli.players;

import dev.tomaselli.SimulatorPanel;
import dev.tomaselli.items.MovementType;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Sphere extends Entity {

    public Sphere(SimulatorPanel simulatorPanel, Integer posX, Integer posY) {
        this.simulatorPanel = simulatorPanel;
        this.posX = posX;
        this.posY = posY;
        this.movementType = MovementType.CIRCLE;

        this.baseSize = 1;
        this.scaleSize = 1;
        this.dupeCooldown = 80;
    }

    public Sphere(SimulatorPanel simulatorPanel, Float scaleSize, Integer posX, Integer posY) {
        this.simulatorPanel = simulatorPanel;
        this.dupeCooldown = 80;
        this.baseSize = 1;
        this.scaleSize = scaleSize;
        this.movementType = MovementType.CIRCLE;

        this.posX = posX;
        this.posY = posY;
    }

    private void duplicate() {
        // Generates random (0-2)
        Random random = new Random();
        int prob = random.nextInt(3);
        float newScale;

        /*
         * 33% -> Size++
         * 33% -> Size--
         * 33% -> Size==
         */
        switch (prob) {
            case 0 -> newScale = this.scaleSize + 0.1f;
            case 1 -> newScale = this.scaleSize - 0.1f;
            case 2 -> newScale = this.scaleSize;
            default -> {
                newScale = this.scaleSize;
                System.out.println("ERROR: Sphere.java/1");
            }
        }

        Sphere newSphere = new Sphere(this.simulatorPanel, newScale, this.posX, this.posY);
        SimulatorPanel.spheresList.add(newSphere);
    }


    public void die() {
        // Simply removes the Object from the Object List
        SimulatorPanel.spheresList.remove(this);
        this.simulatorPanel.repaint();
    }


    public void updateStatus() {

        SimulatorPanel.spheresList.remove(this);

        this.dupeCooldown--;

        Random random = new Random();
        int prob = random.nextInt(8);

        switch (prob) {
            case 0 -> posY += 8;
            case 1 -> posY -= 8;
            case 2 -> posX += 8;
            case 3 -> posX -= 8;
            case 4 -> {
                posX += 8;
                posY -= 8;
            }
            case 5 -> {
                posX -= 8;
                posY += 8;
            }
            case 6 -> {
                posX += 8;
                posY += 8;
            }
            case 7 -> {
                posX -= 8;
                posY -= 8;
            }
            default -> System.out.println("ERROR: Circle.java/2");
        }

        this.simulatorPanel.repaint();

        if(dupeCooldown <= 0) {
            this.duplicate();
            this.dupeCooldown = 80;
        }

        SimulatorPanel.spheresList.add(this);
    }

    public void draw(Graphics2D g2) {
        if(!isInsideScreen()) {
            return;
        }

        float radius = 16 * this.baseSize * this.scaleSize;
        g2.setColor(Color.RED);
        g2.fill(new Ellipse2D.Float(this.posX, this.posY, radius, radius));
    }
}
