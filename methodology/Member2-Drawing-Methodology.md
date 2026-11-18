# Member 2: Drawing and Mouse Interaction — Methodology

## Objective

The objective of this part was to let the user draw rectangle annotations on an image with the mouse. The user presses, drags and releases, and a rectangle is stored as a permanent marker.

This is the core interaction of the JavaFX Image Annotator. The file loading, colour selection and saving built by other members depend on it.

## Approach

We followed a step-by-step approach:

1. Created a Maven project with JavaFX 21 and set the main class in `pom.xml`.
2. Created a `Marker` class to store each finalised rectangle.
3. Created a `DrawingCanvas` that extends JavaFX `Canvas` and attaches mouse handlers.
4. Created a `Main` class that opens an 800×600 test window.
5. Ran the app and drew rectangles in every drag direction to verify behaviour.

## Mouse Interaction

Three mouse events drive the drawing:

| Event | Action |
|---|---|
| Mouse pressed | Record the start point and begin a drag |
| Mouse dragged | Clear the canvas, redraw existing markers, draw a dashed preview from start to current point |
| Mouse released | If the drag is at least 3 px on both axes, store a `Marker`; then redraw |

Ignoring drags smaller than 3 px means that an accidental click does not create a marker.

## Drawing Rules

- **Any drag direction:** a drag from bottom-right to top-left gives negative width and height. We normalise it with `Math.min` for the top-left corner and `Math.abs` for the size, so every direction produces the same rectangle.
- **Opaque border:** the border uses the chosen colour at full opacity (alpha 1.0), so it is always clearly visible.
- **Translucent fill:** the interior uses the same RGB values at alpha 0.3, so the marked area is visible without hiding the image.
- **Preview vs final:** while dragging, the outline is dashed. After release, it is solid.
- **Redraw model:** the canvas is cleared and every marker redrawn on each event. This avoids leftover preview rectangles (trails).

## Markers Are Final

Once released, a marker cannot be moved, resized or deleted. This was a deliberate scope decision to keep the feature simple and testable.

## Testing

Manual test checklist, run with `mvn clean javafx:run`:

- [x] Dragging top-left to bottom-right creates a rectangle
- [x] Dragging bottom-right to top-left creates a rectangle
- [x] Dragging in the other two directions creates a rectangle
- [x] Several rectangles stay on screen after release
- [x] Overlapping rectangles blend (the fill gets darker where they overlap)
- [ ] A click without dragging creates nothing
- [ ] No trails are left behind during a drag

## Limitations and Next Steps

- The background is a plain white placeholder. Loading an image with `FileChooser` is handled by Member 3.
- The colour is fixed to red. The `ColorPicker` from Member 1 will call `setCurrentColor()`.
- Markers are not saved to file yet. Saving is handled by Member 3.
