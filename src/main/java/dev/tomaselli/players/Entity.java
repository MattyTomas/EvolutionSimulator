package dev.tomaselli.players;

import dev.tomaselli.SimulatorPanel;
import dev.tomaselli.items.MovementType;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Entity {

    public int posX, posY;
    public SimulatorPanel simulatorPanel;
    public MovementType movementType;
    public int movementSize, baseSize, dupeCooldown;
    public float scaleSize;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isInsideScreen() {
        return !(this.posX > 850 || this.posX < -50 || this.posY > 650 || this.posY < -50);
    }

    public float getRadius() {
        return this.simulatorPanel.screenScale * this.baseSize * this.scaleSize;
    }

    protected void move() {
        Random random = new Random();
        int prob = random.nextInt(8);
        switch (prob) {
            case 0 -> this.posY += this.movementSize;
            case 1 -> this.posY -= this.movementSize;
            case 2 -> this.posX += this.movementSize;
            case 3 -> this.posX -= this.movementSize;
            case 4 -> {
                this.posX += this.movementSize;
                this.posY -= this.movementSize;
            }
            case 5 -> {
                this.posX -= this.movementSize;
                this.posY += this.movementSize;
            }
            case 6 -> {
                this.posX += this.movementSize;
                this.posY += this.movementSize;
            }
            default -> {
                this.posX -= this.movementSize;
                this.posY -= this.movementSize;
            }
        }
    }

    protected void die() {
        if(this instanceof Circle) {
            Circle.circlesList.remove(this);
        }

        if(this instanceof Sphere) {
            Sphere.spheresList.remove(this);
        }
    }

    public void draw(Graphics2D g2) {
        if(!isInsideScreen()) {
            this.die();
            return;
        }

        float radius = this.simulatorPanel.screenScale * this.baseSize * this.scaleSize;

        if(this instanceof Circle) {
            g2.setColor(Color.GREEN);
            g2.draw(new Ellipse2D.Float(this.posX, this.posY, radius, radius));
        }

        if(this instanceof Sphere) {
            g2.setColor(Color.WHITE);
            g2.draw(new Ellipse2D.Float(this.posX, this.posY, radius, radius));
            g2.setColor(Color.RED);
            g2.fill(new Ellipse2D.Float(this.posX, this.posY, radius, radius));
        }
    }

    protected void duplicate() {
        float newScale = Entity.generateScaleSize(this.scaleSize);

        if(Circle.circlesList.size() + Sphere.spheresList.size() > SimulatorPanel.entityCap) {
            System.out.println("ENTITY CAP REACHED! S: " + Sphere.spheresList.size() + " | C: " + Circle.circlesList.size());
            return;
        }

        if(this instanceof Circle) {
            Circle newCircle = new Circle(this.simulatorPanel, newScale, this.posX, this.posY);
            Circle.circlesList.add(newCircle);
        }

        if(this instanceof Sphere) {
            Sphere newSphere = new Sphere(this.simulatorPanel, newScale, this.posX, this.posY);
            Sphere.spheresList.add(newSphere);
        }

        this.dupeCooldown = 60;

    }

    protected static float generateScaleSize(float oldScale) {
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
            case 0 -> newScale = oldScale + 0.1f;
            case 1 -> newScale = oldScale - 0.1f;
            default ->  newScale = oldScale;
        }

        return newScale;
    }

}
