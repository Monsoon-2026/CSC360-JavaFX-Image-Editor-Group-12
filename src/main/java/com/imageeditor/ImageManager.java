
package com.imageeditor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class ImageManager {

    private Canvas canvas;
    private Image image;

    private ArrayList<Marker> markers = new ArrayList<>();

    public ImageManager(Canvas canvas) {
        this.canvas = canvas;
    }

    public void loadImage(Window window) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(
                "Image Files", "*.png", "*.jpg", "*.jpeg"
            )
        );

        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return;
        }

        Image loadedImage = new Image(file.toURI().toString());

        if (loadedImage.isError()) {
            System.out.println("Unable to load image.");
            return;
        }

        image = loadedImage;

        canvas.setWidth(image.getWidth());
        canvas.setHeight(image.getHeight());

        redrawAll();
    }

    public void redrawAll() {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (image != null) {
            gc.drawImage(image, 0, 0);
        }

        for (Marker marker : markers) {
            gc.setStroke(marker.getColor());

            gc.strokeRect(
                marker.getX(),
                marker.getY(),
                marker.getWidth(),
                marker.getHeight()
            );
        }
    }

    public ArrayList<Marker> getMarkers() {
        return markers;
    }

    public Image getImage() {
        return image;
    }

    public void saveImage(Window window) {

        if (image == null) {
            System.out.println("Please load an image first.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Annotated Image");
        fileChooser.setInitialFileName("annotated-image.png");

        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );

        File file = fileChooser.showSaveDialog(window);

        if (file == null) {
            return;
        }

        if (!file.getName().toLowerCase().endsWith(".png")) {
            file = new File(file.getAbsolutePath() + ".png");
        }

        try {
            WritableImage snapshot = canvas.snapshot(null, null);

            ImageIO.write(
                SwingFXUtils.fromFXImage(snapshot, null),
                "png",
                file
            );

            System.out.println("Image saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }
}
