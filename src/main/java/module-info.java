module Asteroids {
    requires javafx.controls;
    requires javafx.graphics;

    opens com.asteroids.play to javafx.graphics;
    exports com.asteroids.play;
}