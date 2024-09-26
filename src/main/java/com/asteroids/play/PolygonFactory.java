package com.asteroids.play;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class PolygonFactory {
    public Polygon createPolygon() {
        Random rnd = new Random();
        double size = 10 + rnd.nextInt(10); // [10, 20)

        Polygon polygon = new Polygon();
        double c1 = Math.cos(Math.PI * 2 / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);

        double s2 = Math.sin(Math.PI * 4 / 5); // sin(- pi/5)
        double c2 = Math.cos(Math.PI / 5);

        polygon.getPoints().addAll(
                size, 0.0,
                size * c1, -1 * size * s1,
                -1 * size * c2, -1 * size * s2,
                -1 * size * c2, size * s2,
                size * c1, size * s1
        );

        polygon.getPoints().replaceAll(vertex -> vertex + rnd.nextInt(5) - 2);

        polygon.setFill(Config.ASTEROID_COLOR);
        return polygon;
    }
}
