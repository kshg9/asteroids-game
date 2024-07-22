package com.asteroids.play;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends GameObject {

    private final double rotationalSpeed;

    public Asteroid(double x, double y) {
        super(new PolygonFactory().createPolygon(), x, y);
        Random rnd = new Random();


        super.setRotation(rnd.nextDouble() * 360);

        // init random movement speed and direction
        double angle = rnd.nextDouble() * 2 * Math.PI;

        double speed = Config.MIN_SPEED + rnd.nextDouble() * (Config.MAX_SPEED - Config.MIN_SPEED);
        super.setVelocity(new Point2D(Math.cos(angle) * speed, Math.sin(angle) * speed));

        // init a random, rotational speed
        double minRotSpeed = -180;
        double maxRotSpeed = 180;
        this.rotationalSpeed = minRotSpeed + rnd.nextDouble() * (maxRotSpeed - minRotSpeed);

        centerPolygon();
        updateCharacterPosition();
    }

    @Override
    public void update(double dt) {
        super.move(dt);

        double oldRotation = getRotation();
        double newRotation = oldRotation + rotationalSpeed * dt;
        newRotation = newRotation % 360; // set bounds (0, 359), 361 -> 1

        if (newRotation < 0) newRotation += 360; // -1 -> 359
        super.setRotation(newRotation);

        updateCharacterPosition();
//        System.out.println("asteroid updated: pos=" + position + ", oldRot=" + oldRotation + ", newRot=" + newRotation + ", dt=" + dt);
    }

    private void centerPolygon() {
        if (character != null) { // Geogebra visualization helps!
            Polygon polygon = character;
            double centerX = 0;
            double centerY = 0;
            int pointCount = polygon.getPoints().size() / 2; // If getPoints -> 10 co-ordinates then => 5 ( halved)

            for (int i = 0; i < polygon.getPoints().size(); i += 2) {
                centerX += polygon.getPoints().get(i);
                centerY += polygon.getPoints().get(i + 1);
            }

            centerX /= pointCount;
            centerY /= pointCount;

            for (int i = 0; i < polygon.getPoints().size(); i += 2) {
                polygon.getPoints().set(i, polygon.getPoints().get(i) - centerX);
                polygon.getPoints().set(i + 1, polygon.getPoints().get(i + 1) - centerY);
            }
        }
    }
}