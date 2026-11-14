import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Entry point for the manual drawing test.
 *
 * <p>Hosts a {@link DrawingCanvas} in an 800x600 window so drag behaviour
 * can be checked by hand with {@code mvn javafx:run}.
 */
public class Main extends Application {

    /**
     * Builds and shows the test window.
     *
     * @param stage the primary stage supplied by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        DrawingCanvas canvas = new DrawingCanvas(800, 600);
        BorderPane root = new BorderPane(canvas);
        stage.setScene(new Scene(root));
        stage.setTitle("Member 2 - Drawing Test");
        stage.show();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
