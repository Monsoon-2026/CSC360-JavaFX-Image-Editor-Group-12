# Project Methodology

## Member 1 — Image Loading

### Development Approach

The image loading module was developed to allow users to select images from their computer and display them within the JavaFX application.

### Implementation Steps

1. Used JavaFX `FileChooser` to allow users to browse and select image files.
2. Used the JavaFX `Image` class to load the selected image.
3. Used `ImageView` to display the loaded image in the application.
4. Added file validation and error handling for unsupported or invalid image files.
5. Implemented functionality to replace the currently displayed image when a new image is selected.

### Testing

- Tested image selection from the computer.
- Verified that selected images were displayed correctly.
- Checked the behaviour when invalid or unsupported files were selected.
- Verified that loading a new image replaced the previous image.

---

## Member 3 — User Interface

### Development Approach

The user interface module was developed to provide a simple and user-friendly environment for interacting with the image annotation application.

### Implementation Steps

1. Created the main application layout using JavaFX layout containers.
2. Organized the interface into different sections, including the toolbar and image display area.
3. Added JavaFX controls such as buttons, labels and colour selection controls.
4. Used CSS to customize the application's appearance and maintain consistent styling.
5. Connected the interface controls with their respective application functionalities.

### Testing

- Checked the arrangement of interface components.
- Verified that buttons and other controls worked correctly.
- Tested the colour selection functionality.
- Checked the overall appearance and usability of the application.

---

## Member 4 — Saving, Undo/Redo and Integration

### Development Approach

The saving and integration module was developed to preserve the final annotated image and connect the individual project modules into one working application.

### Implementation Steps

1. Implemented the image-saving functionality to export the annotated image.
2. Used JavaFX snapshot functionality to capture the image along with its rectangle annotations.
3. Used Java image utilities to convert and save the captured image in a supported format.
4. Worked on undo and redo functionality to manage changes made to annotations.
5. Integrated the image loading, rectangle annotation and user interface modules.
6. Performed final testing to check the behaviour of the complete application.

### Testing

- Verified that annotated images could be saved successfully.
- Checked whether exported images retained the rectangle annotations.
- Tested undo and redo functionality.
- Checked whether all modules worked together correctly.
- Performed final application testing to identify integration issues.

---

## Overall Project Methodology

The project followed a modular development approach in which responsibilities were divided among four team members. Each member developed and tested their assigned module before integration.

GitHub was used for version control and collaboration. After individual development, all modules were integrated and tested to ensure that the final JavaFX Image Annotator worked as expected.
