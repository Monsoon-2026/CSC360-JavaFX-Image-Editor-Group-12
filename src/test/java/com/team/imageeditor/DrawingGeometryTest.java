package com.team.imageeditor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Headless unit tests for rectangle coordinate normalization and drag size validation.
 * Verifies all 4 drag directions, tiny drag rejection, and boundary conditions.
 */
public class DrawingGeometryTest {

    @Test
    @DisplayName("Direction 1: Top-left to bottom-right drag normalizes correctly")
    void testDragTopLeftToBottomRight() {
        // Dragging from (20, 30) down-right to (100, 80)
        DrawingCanvas.RectangleBounds bounds = DrawingCanvas.calculateBounds(20.0, 30.0, 100.0, 80.0);

        assertEquals(20.0, bounds.x(), 1e-6);
        assertEquals(30.0, bounds.y(), 1e-6);
        assertEquals(80.0, bounds.width(), 1e-6);
        assertEquals(50.0, bounds.height(), 1e-6);
        assertTrue(DrawingCanvas.isValidDrag(bounds.width(), bounds.height()));
    }

    @Test
    @DisplayName("Direction 2: Bottom-right to top-left drag normalizes to identical positive bounds")
    void testDragBottomRightToTopLeft() {
        // Dragging from (100, 80) up-left to (20, 30)
        DrawingCanvas.RectangleBounds bounds = DrawingCanvas.calculateBounds(100.0, 80.0, 20.0, 30.0);

        assertEquals(20.0, bounds.x(), 1e-6);
        assertEquals(30.0, bounds.y(), 1e-6);
        assertEquals(80.0, bounds.width(), 1e-6);
        assertEquals(50.0, bounds.height(), 1e-6);
        assertTrue(DrawingCanvas.isValidDrag(bounds.width(), bounds.height()));
    }

    @Test
    @DisplayName("Direction 3: Top-right to bottom-left drag normalizes correctly")
    void testDragTopRightToBottomLeft() {
        // Dragging from (100, 30) down-left to (20, 80)
        DrawingCanvas.RectangleBounds bounds = DrawingCanvas.calculateBounds(100.0, 30.0, 20.0, 80.0);

        assertEquals(20.0, bounds.x(), 1e-6);
        assertEquals(30.0, bounds.y(), 1e-6);
        assertEquals(80.0, bounds.width(), 1e-6);
        assertEquals(50.0, bounds.height(), 1e-6);
        assertTrue(DrawingCanvas.isValidDrag(bounds.width(), bounds.height()));
    }

    @Test
    @DisplayName("Direction 4: Bottom-left to top-right drag normalizes correctly")
    void testDragBottomLeftToTopRight() {
        // Dragging from (20, 80) up-right to (100, 30)
        DrawingCanvas.RectangleBounds bounds = DrawingCanvas.calculateBounds(20.0, 80.0, 100.0, 30.0);

        assertEquals(20.0, bounds.x(), 1e-6);
        assertEquals(30.0, bounds.y(), 1e-6);
        assertEquals(80.0, bounds.width(), 1e-6);
        assertEquals(50.0, bounds.height(), 1e-6);
        assertTrue(DrawingCanvas.isValidDrag(bounds.width(), bounds.height()));
    }

    @Test
    @DisplayName("Pure click with zero distance is rejected")
    void testZeroDistanceClickRejected() {
        DrawingCanvas.RectangleBounds bounds = DrawingCanvas.calculateBounds(50.0, 50.0, 50.0, 50.0);

        assertEquals(0.0, bounds.width(), 1e-6);
        assertEquals(0.0, bounds.height(), 1e-6);
        assertFalse(DrawingCanvas.isValidDrag(bounds.width(), bounds.height()),
                "Zero-distance click must not be a valid drag");
    }

    @Test
    @DisplayName("Tiny drag below 3px on X-axis is rejected")
    void testTinyDragXAxisRejected() {
        DrawingCanvas.RectangleBounds bounds = DrawingCanvas.calculateBounds(10.0, 10.0, 12.9, 50.0);

        assertEquals(2.9, bounds.width(), 1e-6);
        assertEquals(40.0, bounds.height(), 1e-6);
        assertFalse(DrawingCanvas.isValidDrag(bounds.width(), bounds.height()),
                "Drag with width < 3px must be rejected");
    }

    @Test
    @DisplayName("Tiny drag below 3px on Y-axis is rejected")
    void testTinyDragYAxisRejected() {
        DrawingCanvas.RectangleBounds bounds = DrawingCanvas.calculateBounds(10.0, 10.0, 50.0, 12.9);

        assertEquals(40.0, bounds.width(), 1e-6);
        assertEquals(2.9, bounds.height(), 1e-6);
        assertFalse(DrawingCanvas.isValidDrag(bounds.width(), bounds.height()),
                "Drag with height < 3px must be rejected");
    }

    @Test
    @DisplayName("Tiny drag below 3px on both axes is rejected")
    void testTinyDragBothAxesRejected() {
        DrawingCanvas.RectangleBounds bounds = DrawingCanvas.calculateBounds(10.0, 10.0, 12.0, 12.0);

        assertEquals(2.0, bounds.width(), 1e-6);
        assertEquals(2.0, bounds.height(), 1e-6);
        assertFalse(DrawingCanvas.isValidDrag(bounds.width(), bounds.height()),
                "Drag with both dimensions < 3px must be rejected");
    }

    @Test
    @DisplayName("Exact boundary drag of 3px by 3px is accepted")
    void testExactThresholdAccepted() {
        DrawingCanvas.RectangleBounds bounds = DrawingCanvas.calculateBounds(10.0, 10.0, 13.0, 13.0);

        assertEquals(3.0, bounds.width(), 1e-6);
        assertEquals(3.0, bounds.height(), 1e-6);
        assertTrue(DrawingCanvas.isValidDrag(bounds.width(), bounds.height()),
                "Drag with dimensions >= 3.0 must be accepted");
    }
}
