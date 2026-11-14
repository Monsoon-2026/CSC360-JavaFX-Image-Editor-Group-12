# JavaFX Annotator — Drawing Module (Member 2)

Drag-to-draw rectangle markers on a canvas, as part of the JavaFX Image Annotator project.

## Requirements

- Java 21 or newer
- Maven 3.9 or newer

## Run

```bash
cd JavaFxAnnotator
mvn clean javafx:run
```

## Usage

1. Press the mouse on the canvas.
2. Drag. A dashed preview rectangle follows the mouse.
3. Release. The rectangle becomes a solid, final marker.

Drags smaller than 3 px are ignored. Markers cannot be deleted once released.

## Files

| File | Purpose |
|---|---|
| `Marker.java` | Stores one finalised rectangle (position, size, colour) |
| `DrawingCanvas.java` | Handles mouse events and draws markers |
| `Main.java` | Opens the test window |
