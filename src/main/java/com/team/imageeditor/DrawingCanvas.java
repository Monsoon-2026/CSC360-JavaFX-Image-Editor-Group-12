package com.team.imageeditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A {@link Canvas} that displays a loaded background image and lets the user
 * draw rectangle markers with the mouse.
 *
 * <p>Interaction:
 * <ul>
 *   <li>The user presses the mouse button to set a start point.</li>
 *   <li>Dragging updates a live dashed preview rectangle.</li>
 *   <li>Releasing stores the rectangle as a permanent {@link Marker} if it meets the minimum size.</li>
 * </ul>
 *
 * <p>Every marker is rendered with a translucent interior (alpha 0.3) and an opaque border (2.0 px).
 *
 * <p>Markers are final once released: they cannot be individually moved or resized.
 *
 * @author Rectangle Drawing and Mouse Interaction
 * @version 1.0
 */
public class DrawingCanvas extends Canvas {

    /** Minimum drag distance in pixels on both axes; smaller drags are ignored. */
    public static final double MIN_DRAG_SIZE = 3.0;

    /** Alpha value for the translucent interior of a marker (0.0 to 1.0). */
    public static final double FILL_ALPHA = 0.3;

    /** Stroke width of the marker border, in pixels. */
    public static final double BORDER_WIDTH = 2.0;

    /** Length of each dash in the preview outline, in pixels. */
    public static final double PREVIEW_DASH_LENGTH = 6.0;

    /**
     * Immutable geometric representation of a normalized rectangle.
     */
    public record RectangleBounds(double x, double y, double width, double height) {}

    /** The loaded background image, or {@code null} if none is loaded. */
    private Image image;

    /** Markers that have been finalised by the user. */
    private final List<Marker> markers = new ArrayList<>();

    /** Colour applied to the next marker drawn and to the active preview. */
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
     * Sets the background image to display on the canvas.
     * Canvas width and height are updated to match the image dimensions if non-null.
     *
     * @param newImage the image to display, or {@code null} to show a blank canvas
     */
    public void setImage(Image newImage) {
        this.image = newImage;
        if (newImage != null) {
            setWidth(newImage.getWidth());
            setHeight(newImage.getHeight());
        }
        redraw();
    }

    /**
     * Returns the currently loaded background image.
     *
     * @return the current image, or {@code null} if no image is loaded
     */
    public Image getImage() {
        return image;
    }

    /**
     * Clears all finalised markers and redraws the canvas.
     * Note: The loaded background image is preserved.
     */
    public void clearMarkers() {
        markers.clear();
        redraw();
    }

    /**
     * Sets the colour used for subsequent markers and for the live preview.
     *
     * @param color the colour chosen by the user; must not be {@code null}
     * @throws NullPointerException if {@code color} is {@code null}
     */
    public void setCurrentColor(Color color) {
        this.currentColor = Objects.requireNonNull(color, "color must not be null");
    }

    /**
     * Returns the current marker drawing colour.
     *
     * @return the current drawing colour
     */
    public Color getCurrentColor() {
        return currentColor;
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
     * Normalizes two opposing corner points into top-left (x, y) coordinates and non-negative dimensions.
     * This is a pure static helper function suitable for headless testing.
     *
     * @param x1 x coordinate of first corner
     * @param y1 y coordinate of first corner
     * @param x2 x coordinate of opposite corner
     * @param y2 y coordinate of opposite corner
     * @return a normalized {@link RectangleBounds}
     */
    public static RectangleBounds calculateBounds(double x1, double y1, double x2, double y2) {
        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);
        double width = Math.abs(x2 - x1);
        double height = Math.abs(y2 - y1);
        return new RectangleBounds(x, y, width, height);
    }

    /**
     * Checks if a drag rectangle meets the minimum size threshold on both axes.
     * This is a pure static helper function suitable for headless testing.
     *
     * @param width  drag width
     * @param height drag height
     * @return {@code true} if both width and height are at least {@value #MIN_DRAG_SIZE}
     */
    public static boolean isValidDrag(double width, double height) {
        return width >= MIN_DRAG_SIZE && height >= MIN_DRAG_SIZE;
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
     * Updates the live preview rectangle coordinates while the mouse is dragged.
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

        RectangleBounds bounds = calculateBounds(startX, startY, currentX, currentY);
        if (isValidDrag(bounds.width(), bounds.height())) {
            markers.add(new Marker(
                    bounds.x(),
                    bounds.y(),
                    bounds.width(),
                    bounds.height(),
                    currentColor));
        }
        redraw();
    }

    /**
     * Redraws the canvas following the required 4-step sequence:
     * <ol>
     *   <li>Clear the canvas entirely.</li>
     *   <li>Draw the loaded image, or a white background if no image exists.</li>
     *   <li>Draw all finalised markers with translucent fill and opaque border.</li>
     *   <li>Draw the active dashed preview when the user is dragging.</li>
     * </ol>
     */
    public void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        double w = getWidth();
        double h = getHeight();

        // 1. Clear canvas
        gc.clearRect(0, 0, w, h);

        // 2. Draw loaded image, or white background if no image exists
        if (image != null) {
            gc.drawImage(image, 0, 0);
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, w, h);
        }

        // 3. Draw all finalized markers
        for (Marker marker : markers) {
            renderRectangle(gc, marker.getX(), marker.getY(),
                    marker.getWidth(), marker.getHeight(),
                    marker.getColor(), false);
        }

        // 4. Draw the active dashed preview when the user is dragging
        if (dragging) {
            RectangleBounds bounds = calculateBounds(startX, startY, currentX, currentY);
            renderRectangle(gc, bounds.x(), bounds.y(),
                    bounds.width(), bounds.height(),
                    currentColor, true);
        }
    }

    /**
     * Renders a single rectangle with translucent fill and border.
     *
     * @param gc      the graphics context
     * @param x       top-left x coordinate
     * @param y       top-left y coordinate
     * @param w       width
     * @param h       height
     * @param base    base colour
     * @param preview {@code true} for dashed preview outline, {@code false} for solid border
     */
    private void renderRectangle(GraphicsContext gc, double x, double y, double w, double h,
                                 Color base, boolean preview) {
        // Translucent interior
        gc.setFill(Color.color(base.getRed(), base.getGreen(), base.getBlue(), FILL_ALPHA));
        gc.fillRect(x, y, w, h);

        // Border outline
        gc.setStroke(base);
        gc.setLineWidth(BORDER_WIDTH);
        if (preview) {
            gc.setLineDashes(PREVIEW_DASH_LENGTH);
        } else {
            gc.setLineDashes(null);
        }
        gc.strokeRect(x, y, w, h);
        if (preview) {
            gc.setLineDashes(null);
        }
    }
}
