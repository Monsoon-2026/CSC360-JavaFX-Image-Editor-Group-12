# CSC360 Group Project: JavaFX Image Editor

**Topic:** JavaFX Image Editor â€“ Rectangle Marker for Image Annotation
**Group:** Group 12
**Target Environment:** Java 21 LTS, JavaFX 21.0.8, Maven 3.9+

---

## 1. Project Overview

The JavaFX Image Editor is an interactive desktop application enabling users to load an image, draw translucent rectangle markers with solid borders, choose marker colours, clear annotations, and save the final annotated image as a PNG file.

### Team Members & Contributions
- **Member 1 (UI & Layout):** JavaFX layout using `BorderPane`, top toolbar, status bar, `ScrollPane`, and CSS styling (`src/main/resources/css/style.css`).
- **Member 2 (Drawing & Mouse Interaction):** `DrawingCanvas` implementation, mouse press-drag-release interaction, live dashed preview, translucent fill (`alpha = 0.3`), and 2 px solid borders.
- **Member 3 (Image Loading & Saving):** `ImageManager`, `FileChooser` dialogs, snapshot generation, and PNG export using `SwingFXUtils` and `ImageIO`.
- **Member 4 (Testing, Documentation & Integration):** Architecture consolidation under `com.team.imageeditor`, Java 21 / JavaFX 21 Maven project setup, JUnit 5 headless test suite, and integration verification.

---

## 2. Requirements

- **JDK:** Java 21 LTS or newer
- **Build Tool:** Apache Maven 3.9+
- **Platform:** Windows, macOS, or Linux

---

## 3. How to Build, Test, and Run

From the repository root:

### Compile the project:
```bash
mvn clean compile
```

### Run automated unit tests:
```bash
mvn test
```

### Run the application:
```bash
mvn javafx:run
```

---

## 4. User Guide

1. **Load Image:** Click **Load Image** to select a `.png`, `.jpg`, or `.jpeg` file. The canvas automatically resizes to fit the image.
   *Note: Successfully loading a new image intentionally clears previous markers. If loading is cancelled, the existing image and annotations are preserved.*
2. **Choose Colour:** Use the **Marker colour** picker in the toolbar to change the colour of subsequent rectangles.
3. **Draw Rectangles:** Press and drag on the canvas. A dashed preview rectangle follows your cursor. Releasing the mouse creates a permanent marker with a translucent fill and solid border. Drags smaller than 3 px are ignored.
4. **Clear Markers:** Click **Clear** to remove all markers. The underlying image remains loaded.
5. **Save Annotated Image:** Click **Save** to export the annotated canvas to a `.png` file.

---

## 5. Architecture & Project Layout

```
CSC360-JavaFX-Image-Editor-Group-12/
â”œâ”€â”€ pom.xml                                   # Root Maven POM (Java 21, JavaFX 21.0.8)
â”œâ”€â”€ README.md                                 # Integrated project documentation
â”œâ”€â”€ Decisions/
â”‚   â””â”€â”€ member2_tech_selection.md            # Member 2 technology choices and justifications
â”œâ”€â”€ methodology/
â”‚   â””â”€â”€ Member2-Drawing-Methodology.md       # Member 2 drawing methodology and test checklist
â”œâ”€â”€ JavaFxAnnotator/                          # Preserved legacy module from Member 2
â””â”€â”€ src/
    â”œâ”€â”€ main/
    â”‚   â”œâ”€â”€ java/
    â”‚   â”‚   â””â”€â”€ com/team/imageeditor/
    â”‚   â”‚       â”œâ”€â”€ App.java                  # Main application UI and controller
    â”‚   â”‚       â”œâ”€â”€ DrawingCanvas.java        # Canvas managing drawing, preview, and redraw
    â”‚   â”‚       â”œâ”€â”€ ImageManager.java         # Image loading, saving, and FileChooser logic
    â”‚   â”‚       â””â”€â”€ Marker.java               # Immutable marker data model
    â”‚   â””â”€â”€ resources/
    â”‚       â””â”€â”€ css/
    â”‚           â””â”€â”€ style.css                 # Application styling
    â””â”€â”€ test/
        â””â”€â”€ java/
            â””â”€â”€ com/team/imageeditor/
                â”œâ”€â”€ DrawingGeometryTest.java  # Headless unit tests for drag normalization
                â”œâ”€â”€ ImageManagerFileTest.java # Headless unit tests for file handling
                â””â”€â”€ MarkerTest.java           # Unit tests for Marker model
```
