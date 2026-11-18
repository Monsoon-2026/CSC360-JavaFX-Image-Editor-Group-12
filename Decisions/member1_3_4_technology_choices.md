# Member 1 — Technology Choices and Justification

This section records the key technology choices made for the image loading part of the JavaFX Image Annotator, and the reasoning behind them.

| Choice | Why We Chose It | Why Not the Alternative |
|---|---|---|
| JavaFX `FileChooser` | Provides a built-in file selection dialog that allows users to browse their computer and select an image. | A manually created file browser would require additional code and handling. |
| JavaFX `Image` | Loads image files into a format that can be displayed and used within the JavaFX application. | Using raw file data would require additional processing before displaying the image. |
| JavaFX `ImageView` | Displays the loaded image inside the application and allows it to fit within the available display area. | A `Label` is mainly designed for text and is not suitable for displaying and managing images. |
| File extension and loading validation | Helps handle unsupported or invalid image files and prevents loading errors from affecting the application. | Loading files without validation could result in unexpected errors. |
| Java 21 + JavaFX 21 with Maven | Matches the rest of the project so all members use the same development environment. | Using different versions could create compatibility issues during integration. |

## Design Approach

We used JavaFX's `FileChooser` to allow users to select images from their computer. The selected file is loaded using the JavaFX `Image` class and displayed through `ImageView`.

The image loading functionality is kept separate from the drawing and annotation logic so that different project modules can work together without unnecessary dependencies.

## Key Decision

**The application displays one currently selected image at a time.** When a new image is loaded, it replaces the previously displayed image. Invalid or unsupported files are handled without crashing the application.

---

# Member 3 — Technology Choices and Justification

This section records the key technology choices made for the user interface part of the JavaFX Image Annotator, and the reasoning behind them.

| Choice | Why We Chose It | Why Not the Alternative |
|---|---|---|
| JavaFX Layout Containers (`BorderPane`, `VBox`, `HBox`) | Help organize the application into different sections such as the toolbar, image area and control panels. | Manually positioning every component would make the layout difficult to maintain and adjust. |
| JavaFX Controls (`Button`, `Label`, `ColorPicker`) | Provide ready-made interactive components for actions such as opening images, selecting colours and controlling annotation options. | Creating custom controls for every action would require unnecessary additional code. |
| JavaFX CSS | Allows the application's appearance to be customized separately from its Java logic, making styling easier to maintain. | Applying all styles directly in Java code would mix interface design with application logic. |
| JavaFX `Scene` and `Stage` | Provide the main application window and the environment in which all interface components are displayed. | Building a separate window management system would add unnecessary complexity. |
| Java 21 + JavaFX 21 with Maven | Keeps the user interface compatible with the other project modules. | Using a different framework or version could create integration and compatibility problems. |

## Design Approach

We organized the application interface using JavaFX layout containers so that the toolbar, image display area and other controls remain properly arranged.

The styling is handled through a separate CSS file, while JavaFX controls are connected to their respective application actions.

This separation makes the interface easier to maintain and allows visual changes without modifying the main application logic.

## Key Decision

**The user interface is kept separate from the core application logic.** This makes the application easier to understand, maintain and integrate with the image loading, rectangle annotation and saving modules.

---

# Member 4 — Technology Choices and Justification

This section records the key technology choices made for the saving, undo/redo and integration part of the JavaFX Image Annotator, and the reasoning behind them.

| Choice | Why We Chose It | Why Not the Alternative |
|---|---|---|
| JavaFX `FileChooser` | Allows users to select the destination and filename when saving the annotated image. | A fixed output location would provide less flexibility to users. |
| JavaFX `WritableImage` and `Snapshot` | Allow the application to capture the canvas content, including the displayed image and rectangle annotations. | Saving only the original image would not preserve the annotations. |
| `SwingFXUtils` and Java `ImageIO` | Help convert JavaFX image data into a format that can be written to common image file formats such as PNG. | Manually converting pixel data would require additional implementation. |
| Java Collections (`List` and `Stack`) | Can maintain annotation history and support undo/redo operations by tracking changes made by the user. | Keeping separate variables for every previous state would become difficult to manage. |
| Java 21 + JavaFX 21 with Maven | Ensures that the saving and integration modules remain compatible with the rest of the project. | Different Java or JavaFX versions could introduce compatibility issues. |

## Design Approach

We used JavaFX image snapshot functionality to capture the final annotated canvas. The captured image can then be converted and saved using Java image utilities.

For undo and redo functionality, annotation history is maintained so that previous changes can be restored when required.

The integration process connects the image loading, rectangle annotation, user interface and saving modules into one working application.

## Key Decision

**The exported image should preserve the rectangle annotations along with the original image.** This ensures that users receive the final annotated result rather than only the unmodified image.
