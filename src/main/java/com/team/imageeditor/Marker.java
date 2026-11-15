package com.team.imageeditor;

import javafx.scene.paint.Color;
import java.util.Objects;

/**
 * A finalised rectangle annotation.
 *
 * <p>Represents an immutable rectangle marker defined by its top-left coordinates,
 * dimensions, and display colour.
 *
 * @author Member 2 (Drawing &amp; Mouse Interaction) &amp; Member 4 (Integration)
 * @version 1.0
 */
public class Marker {

    /** X coordinate of the top-left corner, in canvas pixels. */
    private final double x;

    /** Y coordinate of the top-left corner, in canvas pixels. */
    private final double y;

    /** Width of the rectangle, in pixels. */
    private final double width;

    /** Height of the rectangle, in pixels. */
    private final double height;

    /** Colour of the rectangle. */
    private final Color color;

    /**
     * Creates an immutable marker.
     *
     * @param x      top-left x coordinate
     * @param y      top-left y coordinate
     * @param width  rectangle width (must be non-negative)
     * @param height rectangle height (must be non-negative)
     * @param color  rectangle colour (must not be {@code null})
     * @throws NullPointerException     if {@code color} is {@code null}
     * @throws IllegalArgumentException if {@code width} or {@code height} is negative
     */
    public Marker(double x, double y, double width, double height, Color color) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height must be non-negative");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Objects.requireNonNull(color, "color must not be null");
    }

    /** @return the x coordinate of the top-left corner */
    public double getX() {
        return x;
    }

    /** @return the y coordinate of the top-left corner */
    public double getY() {
        return y;
    }

    /** @return the rectangle width */
    public double getWidth() {
        return width;
    }

    /** @return the rectangle height */
    public double getHeight() {
        return height;
    }

    /** @return the rectangle colour */
    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marker marker = (Marker) o;
        return Double.compare(marker.x, x) == 0 &&
               Double.compare(marker.y, y) == 0 &&
               Double.compare(marker.width, width) == 0 &&
               Double.compare(marker.height, height) == 0 &&
               Objects.equals(color, marker.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, color);
    }

    @Override
    public String toString() {
        return "Marker[x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", color=" + color + "]";
    }
}
