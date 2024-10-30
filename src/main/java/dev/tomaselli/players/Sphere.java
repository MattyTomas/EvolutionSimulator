package dev.tomaselli.players;

import dev.tomaselli.SimulatorPanel;
import dev.tomaselli.items.MovementType;

import java.util.ArrayList;
import java.util.List;

public class Sphere extends Entity {

    public static List<Sphere> spheresList = new ArrayList<>();

    public Sphere(SimulatorPanel simulatorPanel, Integer posX, Integer posY) {
        this.setDefaultParameters(simulatorPanel);
        this.posX = posX;
        this.posY = posY;
    }

    public Sphere(SimulatorPanel simulatorPanel, Float scaleSize, Integer posX, Integer posY) {
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
        this.dupeCooldown = 40;
        this.movementType = MovementType.CIRCLE;
    }

    public void updateStatus() {
        this.dupeCooldown--;
        this.move();

        if(dupeCooldown <= 0) {
            this.duplicate();
        }
    }

}
