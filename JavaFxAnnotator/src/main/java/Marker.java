import javafx.scene.paint.Color;

/**
 * A finalised rectangle annotation.
 *
 * <p>Temporary placeholder until Member 3 provides the shared model class.
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
     * Creates a marker.
     *
     * @param x      top-left x coordinate
     * @param y      top-left y coordinate
     * @param width  rectangle width
     * @param height rectangle height
     * @param color  rectangle colour
     */
    public Marker(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
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
}
