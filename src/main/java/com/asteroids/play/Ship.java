package com.asteroids.play;

import javafx.scene.shape.Polygon;

public class Ship extends GameObject {
    private static final double ACCELERATION = 50;
    private static final double ROTATION_SPEED = 180; // degrees per second
    private static final double MAX_SPEED = 300;

    public Ship(int x, int y) {
        super(new Polygon(-8, -8, 15, 0, -8, 8), x, y);
    }

    @Override
    public void update(double dt) {
        move(dt);
        wrapAround();
        limitSpeed();
        updateCharacterPosition();
        System.out.println("Ship update: Position = " + position + ", Velocity = " + velocity + ", Rotation = " + rotation);
    }

    public void accelerate(double dt) {
        double accX = Math.cos(Math.toRadians(rotation)) * ACCELERATION * dt;
        double accY = Math.sin(Math.toRadians(rotation)) * ACCELERATION * dt;
        velocity = velocity.add(accX, accY);
        System.out.println("Velocity of Ship: " + velocity);
    }

    public void turnLeft(double dt) {
        rotation -= ROTATION_SPEED * dt;
        updateCharacterPosition();
    }

    public void turnRight(double dt) {
        rotation += ROTATION_SPEED * dt;
        updateCharacterPosition();
    }

    private void limitSpeed() {
        if (velocity.magnitude() > MAX_SPEED) {
            velocity = velocity.normalize().multiply(MAX_SPEED);
        }
    }

    public boolean isAlive() {
        return true; // Always alive!
    }
}
