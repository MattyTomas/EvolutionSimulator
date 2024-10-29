package dev.tomaselli.players;

import dev.tomaselli.SimulatorPanel;
import dev.tomaselli.items.MovementType;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Circle extends Entity {
    /*
    * Circles can eat Spheres if they're in the same Block (Same posRow and posCol)
    * Every TOT frames, everything updates and every entity chooses randomly the place where to move
    * And the beginning of every update, the System checks for collisions
     */

    public int remainingFood;

    public Circle(SimulatorPanel simulatorPanel, Integer posX, Integer posY) {
        this.simulatorPanel = simulatorPanel;
        this.remainingFood = 61;
        this.dupeCooldown = 30;
        this.baseSize = 1;
        this.scaleSize = 1;
        this.movementType = MovementType.CIRCLE;

        this.posX = posX;
        this.posY = posY;
    }

    public Circle(SimulatorPanel simulatorPanel, Float scaleSize, Integer posX, Integer posY) {
        this.simulatorPanel = simulatorPanel;
        this.remainingFood = 61;
        this.dupeCooldown = 30;
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
                System.out.println("ERROR: Circle.java/1");
            }
        }

        Circle newCircle = new Circle(this.simulatorPanel, newScale, this.posX, this.posY);
        SimulatorPanel.circlesList.add(newCircle);
    }

    private void die() {
        // Simply removes the Object from the Object List
        SimulatorPanel.circlesList.remove(this);
        this.simulatorPanel.repaint();
    }

    public void updateStatus() {
        if(this.remainingFood <= 0) {
            this.die();
            return;
        }

        this.remainingFood--;
        this.dupeCooldown--;

        SimulatorPanel.circlesList.remove(this);

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

        this.remainingFood--;

        if(dupeCooldown <= 0) {
            this.duplicate();
            this.dupeCooldown = 30;
        }

        SimulatorPanel.circlesList.add(this);

        for(Sphere sphere : SimulatorPanel.spheresList) {
            if(this.contiene(sphere)) {
                sphere.die();
                System.out.println("KILLED");
                this.remainingFood += 60;
                break;
            }
        }
    }

    public boolean contiene(Sphere sphere) {
        // Calcola la distanza tra i centri dei due cerchi
        float distanzaCentri = (float) Math.sqrt(Math.pow(sphere.posX - this.posX, 2) + Math.pow(sphere.posY - this.posY, 2));

        // Verifica se il cerchio corrente contiene completamente l'altro cerchio
        return distanzaCentri + sphere.getRadius() <= this.getRadius();
    }

    public void draw(Graphics2D g2) {
        if(!isInsideScreen()) {
            return;
        }

        float radius = 16 * this.baseSize * this.scaleSize;
        g2.setColor(Color.GREEN);
        g2.draw(new Ellipse2D.Float(this.posX, this.posY, radius, radius));
    }
}
