
package com.team.imageeditor;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main window of the JavaFX Image Annotator.
 *
 * The window is divided into three parts using a BorderPane:
 * - Top: toolbar with Load, Save, Clear buttons and a colour picker
 * - Centre: canvas where the image and rectangles are drawn
 * - Bottom: status bar that shows short messages
 *
 * Main application UI and controller for the JavaFX Image Editor.
 *
 * @author UI and Application Layout; Integration
 * @version 1.0
 */
public class App extends Application {

    /** Drawing canvas where the image and rectangles are shown. */
    private DrawingCanvas canvas = new DrawingCanvas(800, 600);

    /** Button to open an image from the computer. */
    private Button loadButton = new Button("Load Image");

    /** Button to save the annotated image. */
    private Button saveButton = new Button("Save");

    /** Button to remove all rectangles. */
    private Button clearButton = new Button("Clear");

    /** Lets the user choose the colour of new rectangles. */
    private ColorPicker colorPicker = new ColorPicker(Color.RED);

    /** Label in the status bar that shows messages to the user. */
    private Label statusLabel =
            new Label("Ready. Load an image to begin.");

    /** Image manager handling file loading and saving. */
    private ImageManager imageManager;

    /** The main window. Needed by FileChooser dialogs. */
    private Stage stage;

    /**
     * Builds the user interface, connects components, and shows the main window.
     *
     * @param primaryStage the main window provided by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        // Initialize ImageManager and connect status reporting
        imageManager = new ImageManager(canvas, this::setStatus);

        // 1. Toolbar at the top
        Label colorLabel = new Label("Marker colour:");
        colorLabel.getStyleClass().add("toolbar-label");
        loadButton.getStyleClass().add("primary-button");
        clearButton.getStyleClass().add("danger-button");

        HBox toolbar = new HBox(
                10, loadButton, saveButton, colorLabel,
                colorPicker, clearButton
        );
        toolbar.setPadding(new Insets(10));
        toolbar.getStyleClass().add("toolbar");

        // 2. Canvas in the centre
        ScrollPane scrollPane = new ScrollPane(canvas);
        scrollPane.getStyleClass().add("canvas-scroll");

        // 3. Status bar at the bottom
        statusLabel.getStyleClass().add("status-label");

        HBox statusBar = new HBox(statusLabel);
        statusBar.setPadding(new Insets(5, 10, 5, 10));
        statusBar.getStyleClass().add("status-bar");

        // 4. Put everything in a BorderPane
        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(scrollPane);
        root.setBottom(statusBar);

        // Connect button actions
        loadButton.setOnAction(e -> imageManager.loadImage(stage));
        saveButton.setOnAction(e -> imageManager.saveImage(stage));

        clearButton.setOnAction(e -> {
            canvas.clearMarkers();
            setStatus("All markers cleared.");
        });

        // Connect colour picker to drawing canvas
        canvas.setCurrentColor(colorPicker.getValue());
        colorPicker.setOnAction(
                e -> canvas.setCurrentColor(colorPicker.getValue())
        );

        // 5. Create the scene and add CSS
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );

        stage.setTitle("JavaFX Image Annotator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Gives access to the drawing canvas.
     *
     * @return the drawing canvas
     */
    public DrawingCanvas getCanvas() {
        return canvas;
    }

    /**
     * Gives access to the image manager.
     *
     * @return the image manager
     */
    public ImageManager getImageManager() {
        return imageManager;
    }

    /**
     * Gives access to the colour picker.
     *
     * @return the marker colour picker
     */
    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    /**
     * Gives access to the Load Image button.
     *
     * @return the Load Image button
     */
    public Button getLoadButton() {
        return loadButton;
    }

    /**
     * Gives access to the Save button.
     *
     * @return the Save button
     */
    public Button getSaveButton() {
        return saveButton;
    }

    /**
     * Gives access to the Clear button.
     *
     * @return the Clear button
     */
    public Button getClearButton() {
        return clearButton;
    }

    /**
     * Gives access to the main window.
     *
     * @return the main window (stage)
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Shows a short message in the status bar.
     *
     * @param message the text to display
     */
    public void setStatus(String message) {
        statusLabel.setText(message);
    }

    /**
     * Starts the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
