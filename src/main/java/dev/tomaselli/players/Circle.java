package dev.tomaselli.players;

import dev.tomaselli.SimulatorPanel;
import dev.tomaselli.items.MovementType;

import java.util.ArrayList;
import java.util.List;

public class Circle extends Entity {

    public static List<Circle> circlesList = new ArrayList<>();
    private int remainingFood;

    public Circle(SimulatorPanel simulatorPanel, Integer posX, Integer posY) {
        this.setDefaultParameters(simulatorPanel);
        this.posX = posX;
        this.posY = posY;
    }

    public Circle(SimulatorPanel simulatorPanel, Float scaleSize, Integer posX, Integer posY) {
        this.setDefaultParameters(simulatorPanel);
        this.scaleSize = scaleSize;
        this.posX = posX;
        this.posY = posY;
    }

    private void setDefaultParameters(SimulatorPanel simulatorPanel) {
        this.simulatorPanel = simulatorPanel;
        this.baseSize = 1;
        this.scaleSize = 1;
        this.movementSize = 8;
        this.dupeCooldown = 60;
        this.remainingFood = 70;
        this.movementType = MovementType.CIRCLE;
    }

    // Called every frame, updates every characteristic about the Entity
    public void updateStatus() {
        if(this.remainingFood <= 0) {
            this.die();
            return;
        }

        this.remainingFood--;
        this.dupeCooldown--;
        this.move();

        /*
        if(dupeCooldown <= 0) {
            this.duplicate();
        }
        */

        for(int i = 0; i < Sphere.spheresList.size(); i++) {
            if(this.contains(Sphere.spheresList.get(i))) {
                Sphere.spheresList.get(i).die();
                this.duplicate();
                this.remainingFood += 30;
                break;
            }
        }
    }

    // Calcola la distanza tra i centri dei due cerchi e verifica se è minore dei raggi stessi
    private boolean contains(Sphere sphere) {
        float distanzaCentri = (float) Math.sqrt(Math.pow(sphere.posX - this.posX, 2) + Math.pow(sphere.posY - this.posY, 2));
        return distanzaCentri + sphere.getRadius() <= this.getRadius();
    }

}
