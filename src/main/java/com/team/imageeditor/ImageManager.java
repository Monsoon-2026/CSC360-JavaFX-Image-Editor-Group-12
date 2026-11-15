package com.team.imageeditor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Handles image file loading and saving for the Image Annotator.
 *
 * <p>Coordinates between the user's file system dialogs and the {@link DrawingCanvas}.
 *
 * @author Member 3 (Image Loading &amp; Saving) &amp; Member 4 (Integration)
 * @version 1.0
 */
public class ImageManager {

    private final DrawingCanvas canvas;
    private final Consumer<String> statusReporter;

    /**
     * Creates an ImageManager with status reported via the given callback.
     *
     * @param canvas         the drawing canvas to operate upon; must not be {@code null}
     * @param statusReporter callback for reporting status and error messages; if {@code null},
     *                       messages are printed to {@code System.out}
     */
    public ImageManager(DrawingCanvas canvas, Consumer<String> statusReporter) {
        this.canvas = Objects.requireNonNull(canvas, "canvas must not be null");
        this.statusReporter = statusReporter != null ? statusReporter : System.out::println;
    }

    /**
     * Convenience constructor that outputs status messages to {@code System.out}.
     *
     * @param canvas the drawing canvas
     */
    public ImageManager(DrawingCanvas canvas) {
        this(canvas, System.out::println);
    }

    /**
     * Opens a file chooser dialog to select and load an image file.
     *
     * <p>Behavior rules:
     * <ul>
     *   <li>If the dialog is cancelled or loading fails, the existing image and markers are preserved.</li>
     *   <li>Only upon successful image loading are previous markers intentionally cleared.</li>
     * </ul>
     *
     * @param window the parent window for the file dialog
     */
    public void loadImage(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(window);
        if (file == null) {
            reportStatus("Image loading cancelled.");
            return;
        }

        Image loadedImage;
        try {
            loadedImage = new Image(file.toURI().toString());
            if (loadedImage.isError()) {
                reportStatus("Unable to load image: " +
                        (loadedImage.getException() != null ? loadedImage.getException().getMessage() : "Unknown error"));
                return;
            }
        } catch (Exception e) {
            reportStatus("Unable to load image: " + e.getMessage());
            return;
        }

        // Image successfully loaded: update canvas and intentionally clear previous markers
        canvas.setImage(loadedImage);
        canvas.clearMarkers();
        reportStatus("Loaded image: " + file.getName() +
                " (" + (int) loadedImage.getWidth() + "x" + (int) loadedImage.getHeight() + ")");
    }

    /**
     * Saves the current annotated canvas snapshot to a PNG file chosen by the user.
     *
     * @param window the parent window for the file dialog
     * @return {@code true} if saved successfully, {@code false} otherwise
     */
    public boolean saveImage(Window window) {
        if (canvas.getImage() == null) {
            reportStatus("Please load an image first.");
            return false;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Annotated Image");
        fileChooser.setInitialFileName("annotated-image.png");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );

        File file = fileChooser.showSaveDialog(window);
        if (file == null) {
            reportStatus("Save cancelled.");
            return false;
        }

        File targetFile = ensurePngExtension(file);

        try {
            WritableImage snapshot = canvas.snapshot(null, null);
            boolean written = ImageIO.write(
                    SwingFXUtils.fromFXImage(snapshot, null),
                    "png",
                    targetFile
            );

            if (written) {
                reportStatus("Image saved successfully: " + targetFile.getName());
                return true;
            } else {
                reportStatus("Error: No PNG writer found.");
                return false;
            }
        } catch (IOException e) {
            reportStatus("Error saving image: " + e.getMessage());
            return false;
        }
    }

    /**
     * Normalizes a file object to guarantee it has a {@code .png} extension.
     * Pure static helper function suitable for headless testing.
     *
     * @param file target file
     * @return file with {@code .png} extension
     */
    public static File ensurePngExtension(File file) {
        if (file == null) {
            return null;
        }
        if (!file.getName().toLowerCase().endsWith(".png")) {
            return new File(file.getParentFile(), file.getName() + ".png");
        }
        return file;
    }

    /**
     * Returns the drawing canvas managed by this ImageManager.
     *
     * @return the drawing canvas
     */
    public DrawingCanvas getCanvas() {
        return canvas;
    }

    /**
     * Returns the current background image from the canvas.
     *
     * @return the loaded image, or {@code null}
     */
    public Image getImage() {
        return canvas.getImage();
    }

    /**
     * Returns the finalized markers from the canvas.
     *
     * @return unmodifiable list of markers
     */
    public List<Marker> getMarkers() {
        return canvas.getMarkers();
    }

    private void reportStatus(String message) {
        if (statusReporter != null) {
            statusReporter.accept(message);
        }
    }
}
