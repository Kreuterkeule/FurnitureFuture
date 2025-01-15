# Project Documentation: Furniture Future

## Overview

"Furniture Future" is a Java-based application developed using the **jMonkeyEngine (jME3)** game engine. The project allows users to interact with virtual furniture in a 3D environment, offering functionality to display, scale, and manipulate furniture models and apply various textures.

---

## Table of Contents

1. [Project Structure](#project-structure)
2. [Main Components](#main-components)
3. [Class Descriptions](#class-descriptions)
4. [Future Improvements](#future-improvements)
5. [Questions/Clarifications](#questionsclarifications)

---

## Project Structure

### Packages

- **`org.example`**: Contains all the core classes, including the application entry point, furniture definitions, material handlers, and menu logic.

### Dependencies

- **jMonkeyEngine 3.x**: Provides the 3D engine and rendering framework.
- **Assets**: The project relies on external `.obj` model files and texture assets located in the "objects" and "Textures" directories.

### Assets (You may get your own)

- Models:
    - `objects/couch.obj`
    - `objects/table2.obj`
    - `objects/eb_metal_shelf_01.obj`
- Textures:
    - `Textures/floor.png`
    - `Textures/wall_carpet.png`
    - `Textures/block/stone.png`

---

## Main Components

### 1. **Main Application**

The entry point is the `Main` class, which initializes the application window and settings.

### 2. **Furniture Models**

Each furniture type (e.g., `Couch`, `Table`, `Shelf`) implements the `Furniture` interface, defining methods for:

- Retrieving the 3D model (`getModel`)
- Applying scaling adjustments (`getDefaultScale`)
- Compensating for positional offsets (`getCompensationVector`)

### 3. **Materials and Textures**

The `Materials` class manages material creation, supporting both color-based materials and texture-based materials. Lazy initialization ensures materials are created only when accessed.

### 4. **Menu System**

The `MenuState` class implements a graphical menu overlay using the jME3 `guiNode`. It provides options for selecting furniture, applying textures, and toggling menu visibility.

---

## Class Descriptions

### 1. `Main`

Responsible for setting up the application:

- Initializes the window with resolution, fullscreen, and resizable settings.
- Sets the application title to "Furniture Future."

**Key Functions:**

- `main(String[] args)`: The application entry point.
- Configures and starts the `Application` instance.

---

### 2. `Furniture` (Interface)

Defines the structure for furniture objects. Classes implementing this interface must provide:

- `getModel()`: Returns the 3D model.
- `getDefaultScale()`: Returns a scale factor for the model.
- `getCompensationVector()`: Adjusts the model’s position relative to the scene.

---

### 3. Furniture Implementations

#### `Couch`

Represents a 3D couch model loaded from `objects/couch.obj`.

- Default scale: `0.01`
- Compensation vector: `(0, -4, 0)`

#### `Table`

Represents a 3D table model loaded from `objects/table2.obj`.

- Rotates the model by –90 degrees about the X-axis.
- Default scale: `0.2`
- Compensation vector: `(0, -3, 0)`

#### `Shelf`

Represents a 3D shelf model loaded from `objects/eb_metal_shelf_01.obj`.

- Default scale: `0.08`
- Compensation vector: `(0, -3, 0)`

---

### 4. `Materials`

Handles material and texture management.

- Creates materials either from colors or from texture files.
- Implements lazy initialization to optimize memory usage.

**Available Materials:**

- Color-Based: `RED`, `GREEN`, `BLUE`, `WHITE`, `YELLOW`
- Texture-Based: `FLOOR`, `WALL`, `STONE`

---

### 5. `MenuState`

Manages the graphical user interface (GUI) menu.

- Displays furniture selection options and texture application shortcuts.
- Toggles visibility with `TAB` key.
- Renders a semi-transparent background with text options.

**Key Features:**

- Keyboard shortcuts to select furniture and textures.
- A smooth toggle mechanism for showing/hiding the menu.

---

## Future Improvements

### 1. Enhanced Features

- **Add More Furniture:** Expand the catalog with additional models (e.g., chairs, beds).
- **Dynamic Scaling:** Allow users to adjust furniture size in real-time.

### 2. Improved User Interface

- **Mouse Interactions:** Add mouse-based navigation and selection.
- **Create a Tutorial:** create a tutorial, that guides the user through the application.

### 3. Optimization

- **Resource Management:** Ensure proper cleanup of assets to reduce memory overhead.
- **LOD Models:** Use level-of-detail (LOD) models for performance optimization.

### 4. Additional Visual Enhancements

- **Lighting and Shadows:** Integrate better lighting setups to improve scene realism.
- **Reflection/Refraction:** Add advanced material effects for glass or reflective surfaces.

