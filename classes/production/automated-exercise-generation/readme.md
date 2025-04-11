# README

This project is an automated exercise generation tool for computing science algorithms. It allows users to generate LaTeX-formatted questions and solutions for three algorithms: **Dijkstra's algorithm**, **Knuth-Morris-Pratt (KMP)** string matching, and **Radix Sort**. The exercises are customisable, printable, and optionally rendered to PDF using the Aspose TeX engine.

## Java Files

The main logic of the program is implemented across six Java files:

* `Dijkstra.java` — Generates shortest path exercises using Dijkstra's algorithm.
* `KMP.java` — Generates border table exercises based on the Knuth-Morris-Pratt algorithm.
* `RadixSort.java` — Generates exercises that trace the Radix Sort algorithm, including customisable bucket sizes and binary representations.
* `NaryTreeNode.java` — Custom n-ary tree used internally for graph generation in Dijkstra exercises.
* `WeightedGraph.java` — Custom graph representation for Dijkstra graphs.
* `Visualiser.java` — Command-line interface for exercise generation, LaTeX rendering, and PDF export.

All source files are located in the `automated-exercise-generation/src` directory.  
Generated LaTeX and PDF files are saved under:
- `automated-exercise-generation/exercises`
- `automated-exercise-generation/solutions`

## Build Instructions

### Requirements

- **Java 8**
- JAR dependencies (included in `automated-exercise-generation/dependencies/`):
    - `aspose-tex-22.12.jar`
    - `commons-io-2.11.0.jar`
- Compatible with **macOS** and **Windows**

### Using IntelliJ IDEA (Recommended)

1. Open IntelliJ and select **Open Project**, then choose the `automated-exercise-generation` directory.
2. In Project Structure:
    - Add the `.jar` files in `dependencies/` as **external libraries**.
3. Set the main class to `exercise_generation.Visualiser`.
4. Run the project using IntelliJ's run configuration.

### Using Command Line

1. Compile the Java files:
   ```bash
   javac -d out -cp "dependencies/*:src" $(find src -name "*.java")
   ```
2. Create a runnable JAR:
   ```bash
   jar cfe AutomatedExerciseGenerator.jar exercise_generation.Visualiser -C out .
   ```
3. Run the JAR:
   ```bash
   java -jar AutomatedExerciseGenerator.jar
   ```

## Features

- Interactive CLI to choose which algorithms to generate exercises for.
- PDF generation from LaTeX using Aspose TeX (optional).
- Customisability of input size, difficulty, borders, and bucket configurations.

## Notes

- The Radix Sort implementation includes features such as user-defined number of bits, bucket factors, and support for empty bucket rules.
- The project has been updated to support evaluations specific to the Radix Sort module for teaching and testing purposes.