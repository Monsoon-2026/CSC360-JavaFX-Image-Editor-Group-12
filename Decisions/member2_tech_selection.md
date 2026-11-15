# Member 2 — Technology Choices and Justification

This section records the key technology choices made for the drawing and mouse interaction part of the JavaFX Image Annotator, and the reasoning behind them.

| Choice | Why We Chose It | Why Not the Alternative |
|---|---|---|
| JavaFX `Canvas` + `GraphicsContext` | Gives direct control over drawing shapes, which is needed for rectangles with a border and a translucent fill. | `ImageView` only displays images and cannot draw shapes on top. `Pane` with `Rectangle` nodes would work, but each rectangle would be a separate scene node that must be managed individually. |
| Mouse event handlers (`setOnMousePressed`, `setOnMouseDragged`, `setOnMouseReleased`) | Matches the press → drag → release interaction the project requires, using the standard JavaFX event model. | Polling the mouse position in a timer would be harder to read and less accurate. |
| Redrawing the whole canvas on each drag event | Keeps the code simple: the canvas state is always the image plus the list of markers, so no leftover preview can remain. | Incremental drawing would need manual erasing of the old preview, which is error-prone. |
| `Color.color(r, g, b, alpha)` for the fill | Lets the border and fill share one RGB value, with only the alpha different. | Choosing a second colour for the fill would mean two colours to manage per marker. |
| Java 21 + JavaFX 21 with Maven | Matches the rest of the project so all members use the same setup. | Using a different version would cause compatibility issues when the parts are merged. |

## Design Approach

We stored each finalised rectangle as a `Marker` object rather than only drawing it. This means the app can redraw, select or save markers later without reconstructing them from the screen.

## Key Decision

**Markers are final once released.** Undo and delete were left out to keep the scope achievable within the project timeline. This can be added later as an extension.
