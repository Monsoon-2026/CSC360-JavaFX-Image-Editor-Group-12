import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A {@link Canvas} that lets the user draw rectangle markers with the mouse.
 *
 * <p>The user presses the mouse button to set a start point, drags to preview
 * a rectangle, and releases to store it as a {@link Marker}. Every marker is
 * drawn with a fully opaque border and a translucent interior of the same colour.
 *
 * <p>Markers are final: once released they cannot be moved, resized or deleted.
 *
 * @author Member 2 (Drawing &amp; Mouse Interaction)
 * @version 1.0
 */
public class DrawingCanvas extends Canvas {

    /** Minimum drag distance in pixels on both axes; smaller drags are ignored. */
    private static final double MIN_DRAG_SIZE = 3.0;

    /** Alpha value for the translucent interior of a marker (0.0 to 1.0). */
    private static final double FILL_ALPHA = 0.3;

    /** Stroke width of the marker border, in pixels. */
    private static final double BORDER_WIDTH = 2.0;

    /** Length of each dash in the preview outline, in pixels. */
    private static final double PREVIEW_DASH_LENGTH = 6.0;

    /** Markers that have been finalised by the user. */
    private final List<Marker> markers = new ArrayList<>();

    /** Colour applied to the next marker drawn. */
    private Color currentColor = Color.RED;

    /** Start x coordinate of the current drag, in canvas coordinates. */
    private double startX;

    /** Start y coordinate of the current drag, in canvas coordinates. */
    private double startY;

    /** Current x coordinate during a drag, in canvas coordinates. */
    private double currentX;

    /** Current y coordinate during a drag, in canvas coordinates. */
    private double currentY;

    /** {@code true} while the mouse button is held down after a press on this canvas. */
    private boolean dragging;

    /**
     * Creates a canvas of the given size with mouse handlers attached.
     *
     * @param width  canvas width in pixels
     * @param height canvas height in pixels
     */
    public DrawingCanvas(double width, double height) {
        super(width, height);
        setOnMousePressed(this::handlePressed);
        setOnMouseDragged(this::handleDragged);
        setOnMouseReleased(this::handleReleased);
        redraw();
    }

    /**
     * Sets the colour used for the next marker and for the live preview.
     *
     * @param color the colour chosen by the user; must not be {@code null}
     * @throws NullPointerException if {@code color} is {@code null}
     */
    public void setCurrentColor(Color color) {
        this.currentColor = Objects.requireNonNull(color, "color must not be null");
    }

    /**
     * Returns an unmodifiable view of the finalised markers.
     *
     * @return the list of markers in the order they were created
     */
    public List<Marker> getMarkers() {
        return Collections.unmodifiableList(markers);
    }

    /**
     * Records the start point of a new rectangle.
     *
     * @param event the mouse press event
     */
    private void handlePressed(MouseEvent event) {
        startX = event.getX();
        startY = event.getY();
        currentX = startX;
        currentY = startY;
        dragging = true;
    }

    /**
     * Updates the live preview rectangle while the mouse is dragged.
     *
     * @param event the mouse drag event
     */
    private void handleDragged(MouseEvent event) {
        if (!dragging) {
            return;
        }
        currentX = event.getX();
        currentY = event.getY();
        redraw();
        drawMarker(startX, startY, currentX, currentY, currentColor, true);
    }

    /**
     * Finalises the rectangle if the drag was large enough, then redraws the canvas.
     *
     * @param event the mouse release event
     */
    private void handleReleased(MouseEvent event) {
        if (!dragging) {
            return;
        }
        dragging = false;
        currentX = event.getX();
        currentY = event.getY();

        double width = Math.abs(currentX - startX);
        double height = Math.abs(currentY - startY);
        if (width >= MIN_DRAG_SIZE && height >= MIN_DRAG_SIZE) {
            markers.add(new Marker(
                    Math.min(startX, currentX),
                    Math.min(startY, currentY),
                    width,
                    height,
                    currentColor));
        }
        redraw();
    }

    /**
     * Clears the canvas, paints the placeholder background and draws every marker.
     */
    private void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        for (Marker marker : markers) {
            drawMarker(marker.getX(), marker.getY(),
                       marker.getX() + marker.getWidth(),
                       marker.getY() + marker.getHeight(),
                       marker.getColor(), false);
        }
    }

    /**
     * Draws a rectangle defined by two opposite corners.
     *
     * <p>The corners may be given in any order; the method normalises them so
     * that dragging in any direction produces the same rectangle.
     *
     * @param x1      x coordinate of the first corner
     * @param y1      y coordinate of the first corner
     * @param x2      x coordinate of the opposite corner
     * @param y2      y coordinate of the opposite corner
     * @param base    the marker colour; its alpha is ignored
     * @param preview {@code true} to draw a dashed preview outline,
     *                {@code false} to draw a solid finalised marker
     */
    private void drawMarker(double x1, double y1, double x2, double y2,
                            Color base, boolean preview) {
        GraphicsContext gc = getGraphicsContext2D();
        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);
        double w = Math.abs(x2 - x1);
        double h = Math.abs(y2 - y1);

        gc.setFill(Color.color(base.getRed(), base.getGreen(), base.getBlue(), FILL_ALPHA));
        gc.fillRect(x, y, w, h);

        gc.setStroke(base);
        gc.setLineWidth(BORDER_WIDTH);
        if (preview) {
            gc.setLineDashes(PREVIEW_DASH_LENGTH);
        } else {
            gc.setLineDashes(null);
        }
        gc.strokeRect(x, y, w, h);
    }
}
