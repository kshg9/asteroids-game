package com.asteroids.play;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Projectile extends GameObject {

    private static int bulletsFireCount = 0;
    private static double lastFireTime = 0;

    public Projectile(double x, double y, double rotation) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        getCharacter().setRotate(rotation);
        double bulletVelocityX = Math.cos(Math.toRadians(rotation)) * Config.BULLET_SPEED;
        double bulletVelocityY = Math.sin(Math.toRadians(rotation)) * Config.BULLET_SPEED;
        setVelocity(new Point2D(bulletVelocityX, bulletVelocityY));
    }

    @Override
    public void update(double dt) {
        super.move(dt);
        updateCharacterPosition();
    }

    public static Projectile shoot(double currentTime, Ship ship) {
        if (canShoot(currentTime)) {
            double shipRotation = ship.getCharacter().getRotate();
            Projectile bullet = new Projectile(
                    ship.getCharacter().getTranslateX(),
                    ship.getCharacter().getTranslateY(),
                    shipRotation);

            bulletsFireCount++;
            lastFireTime = currentTime;
            bullet.getCharacter().setFill(Config.BULLET_COLOR);

            return bullet;
        }
        return null;
    }

    private static boolean canShoot(double currentTime) {
        if (bulletsFireCount < Config.MAX_BULLETS) {
            return true;
        }
        if (currentTime - lastFireTime > Config.COOLDOWN_TIME) {
            bulletsFireCount = 0;
            return true;
        }
        return false;
    }

    public static void reset() {
        bulletsFireCount = 0;
        lastFireTime = 0;
    }
}