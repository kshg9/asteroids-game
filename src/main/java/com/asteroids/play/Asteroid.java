package com.asteroids.play;

import javafx.geometry.Point2D;
import java.util.Random;

public class Asteroid extends GameObject {

    private final double rotationalMovement;

    public Asteroid(double x, double y) {
        super(new PolygonFactory().createPolygon(), x, y);
        Random rnd = new Random();

        super.getCharacter().setRotate(rnd.nextInt(360));

        double angle = rnd.nextDouble() * 2 * Math.PI;
        double speed = Config.MIN_SPEED + rnd.nextDouble() * (Config.MAX_SPEED - Config.MIN_SPEED);
        // 20 + {assume max = 1, min = 0} * 30 :: range(20, 50)
        super.setVelocity(new Point2D(Math.cos(angle) * speed, Math.sin(angle) * speed));

        this.rotationalMovement = (rnd.nextDouble() - 0.5) * 2 * Config.MAX_ROTATIONAL_SPEED;
        updateCharacterPosition();
    }

    @Override
    public void update(double dt) {
        super.move(dt);
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
        updateCharacterPosition();
    }
}