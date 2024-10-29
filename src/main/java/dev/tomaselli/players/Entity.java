package dev.tomaselli.players;

import dev.tomaselli.SimulatorPanel;
import dev.tomaselli.items.MovementType;

public class Entity {

    public int posX;
    public int posY;

    public SimulatorPanel simulatorPanel;
    public MovementType movementType;
    public int baseSize;
    public float scaleSize;
    public int dupeCooldown;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isInsideScreen() {
        return !(this.posX > 850 || this.posX < -50 || this.posY > 650 || this.posY < -50);
    }

    public float getRadius() {
        return 16 * this.baseSize * this.scaleSize;
    }

}
