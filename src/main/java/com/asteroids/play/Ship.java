package com.asteroids.play;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Ship extends GameObject {
    private static final double ACCELERATION = 50;
    private static final double ROTATION_SPEED = 180; // degrees per second
    private static final double MAX_SPEED = 300;
    private static final double DECELERATION_FACTOR = 0.97;

    private int lives;
    private int bulletVelocity;

    public Ship(int x, int y) {
        super(new Polygon(-8, -8, 15, 0, -8, 8), x, y);
        this.lives = 3;
        this.bulletVelocity = 500;
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

    public void decelerate(double dt) {
        double decelerationFactor = Math.pow(DECELERATION_FACTOR, dt);
        velocity = velocity.multiply(decelerationFactor);

        if (velocity.magnitude() < 1) {
            velocity = Point2D.ZERO;
        }
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

    public Projectile shoot(double totalGameTime) {
        return Projectile.shoot(totalGameTime, this);
    }

    public void hit() {
        lives--;
        if (lives < 0) {
            setAlive(false);
        }
    }

    public int getLives() {
        return lives;
    }

    public boolean isAlive() {
        return lives > 0;
    }
}
