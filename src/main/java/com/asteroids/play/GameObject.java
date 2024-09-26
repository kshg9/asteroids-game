package com.asteroids.play;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class GameObject {
    protected Point2D position;
    protected Point2D velocity;
    protected double rotation;
    protected boolean alive;
    protected Polygon character;

    private Point2D savedPosition;
    private Point2D savedVelocity;
    private double savedRotation;
    private boolean savedAlive;

    public GameObject(Polygon polygon, double x, double y) {
        this.position = new Point2D(x, y);
        this.velocity = new Point2D(0, 0);
        this.character = polygon;
        this.rotation = 0;
        this.alive = true;
    }

    public abstract void update(double deltaTime);

    public void move(double deltaTime) {
        position = position.add(velocity.multiply(deltaTime));
        wrapAround();
    }

    protected void wrapAround() {
        double x = position.getX();
        double y = position.getY();

        if (x < 0) x = Config.CANVAS_WIDTH;
        if (x > Config.CANVAS_WIDTH) x = 0;
        if (y < 0) y = Config.CANVAS_HEIGHT;
        if (y > Config.CANVAS_HEIGHT) y = 0;

        position = new Point2D(x, y);
    }

    protected void updateCharacterPosition() {
        if (character != null) {
            character.setTranslateX(position.getX());
            character.setTranslateY(position.getY());
            character.setRotate(rotation);
        }
    }

    public boolean collidesWith(GameObject other) {
        if (this.character == null || other.character == null) {
            return false;
        }
        return this.character.getBoundsInParent().intersects(other.character.getBoundsInParent());
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
        updateCharacterPosition();
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
        updateCharacterPosition();
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Shape getCharacter() {
        return character;
    }

    public void saveState() {
        this.savedPosition = new Point2D(position.getX(), position.getY());
        this.savedVelocity = new Point2D(velocity.getX(), velocity.getY());
        this.savedRotation = rotation;
        this.savedAlive = alive;
    }

    public void restoreState() {
        if (savedPosition != null) {
            this.position = savedPosition;
            this.velocity = savedVelocity;
            this.rotation = savedRotation;
            this.alive = savedAlive;
            updateCharacterPosition();
        }
    }
}