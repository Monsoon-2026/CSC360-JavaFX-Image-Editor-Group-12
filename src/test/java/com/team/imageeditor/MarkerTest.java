package com.team.imageeditor;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Marker} model class.
 * Verifies property access, immutability, validation, and equality.
 */
public class MarkerTest {

    @Test
    @DisplayName("Marker constructs correctly with valid coordinates, dimensions and colour")
    void testConstructorAndGetters() {
        Marker marker = new Marker(10.5, 20.5, 100.0, 50.0, Color.BLUE);

        assertEquals(10.5, marker.getX(), 1e-6);
        assertEquals(20.5, marker.getY(), 1e-6);
        assertEquals(100.0, marker.getWidth(), 1e-6);
        assertEquals(50.0, marker.getHeight(), 1e-6);
        assertEquals(Color.BLUE, marker.getColor());
    }

    @Test
    @DisplayName("Marker fields are strictly private and final (immutability)")
    void testMarkerImmutability() {
        Field[] fields = Marker.class.getDeclaredFields();
        assertTrue(fields.length > 0, "Marker must declare fields");

        for (Field field : fields) {
            int modifiers = field.getModifiers();
            assertTrue(Modifier.isPrivate(modifiers), "Field " + field.getName() + " must be private");
            assertTrue(Modifier.isFinal(modifiers), "Field " + field.getName() + " must be final");
        }
    }

    @Test
    @DisplayName("Marker rejects null Color with NullPointerException")
    void testRejectsNullColor() {
        assertThrows(NullPointerException.class, () ->
                new Marker(0, 0, 10, 10, null)
        );
    }

    @Test
    @DisplayName("Marker rejects negative width or height with IllegalArgumentException")
    void testRejectsNegativeDimensions() {
        assertThrows(IllegalArgumentException.class, () ->
                new Marker(0, 0, -5.0, 10.0, Color.RED)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Marker(0, 0, 10.0, -5.0, Color.RED)
        );
    }

    @Test
    @DisplayName("Marker equality and hashCode work correctly for value comparisons")
    void testEqualsAndHashCode() {
        Marker m1 = new Marker(15.0, 25.0, 80.0, 60.0, Color.GREEN);
        Marker m2 = new Marker(15.0, 25.0, 80.0, 60.0, Color.GREEN);
        Marker different = new Marker(15.0, 25.0, 80.0, 60.0, Color.RED);

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
        assertNotEquals(m1, different);
        assertNotEquals(null, m1);
    }
}
