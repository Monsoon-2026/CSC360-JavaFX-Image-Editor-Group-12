package com.team.imageeditor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Headless unit tests for file handling and extension normalization in {@link ImageManager}.
 */
public class ImageManagerFileTest {

    @Test
    @DisplayName("Appends .png extension when filename lacks any extension")
    void testAppendsPngWhenMissing() {
        File original = new File("annotated-image");
        File result = ImageManager.ensurePngExtension(original);

        assertNotNull(result);
        assertEquals("annotated-image.png", result.getName());
    }

    @Test
    @DisplayName("Preserves existing lowercase .png extension")
    void testPreservesExistingLowercasePng() {
        File original = new File("output-diagram.png");
        File result = ImageManager.ensurePngExtension(original);

        assertNotNull(result);
        assertEquals("output-diagram.png", result.getName());
    }

    @Test
    @DisplayName("Preserves existing uppercase .PNG extension")
    void testPreservesExistingUppercasePng() {
        File original = new File("SCREENSHOT.PNG");
        File result = ImageManager.ensurePngExtension(original);

        assertNotNull(result);
        assertEquals("SCREENSHOT.PNG", result.getName());
    }

    @Test
    @DisplayName("Appends .png when file has non-png extension (e.g., .jpg)")
    void testAppendsPngToNonPngExtension() {
        File original = new File("sample.jpg");
        File result = ImageManager.ensurePngExtension(original);

        assertNotNull(result);
        assertEquals("sample.jpg.png", result.getName());
    }

    @Test
    @DisplayName("Returns null when input file is null")
    void testReturnsNullForNullInput() {
        assertNull(ImageManager.ensurePngExtension(null));
    }

    @Test
    @DisplayName("ImageManager constructor rejects null canvas")
    void testConstructorRejectsNullCanvas() {
        assertThrows(NullPointerException.class, () ->
                new ImageManager(null)
        );
    }
}
