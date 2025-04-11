# Manual

This manual describes how to run and use the Automated Exercise Generation tool. The program generates questions and solutions for Dijkstra's algorithm, Knuth-Morris-Pratt (KMP) string matching, and Radix Sort, outputting LaTeX files and optionally rendering them as PDFs.

## Getting Started

### Requirements

- Java 8
- Two required JAR dependencies:
    - `aspose-tex-22.12.jar`
    - `commons-io-2.11.0.jar`
- IntelliJ IDEA or command line access for running Java

## Project Directory Structure

```
automated-exercise-generation/
├── dependencies/            # External .jar libraries
├── exercises/               # Generated exercise LaTeX and PDFs
│   ├── tex/
│   └── pdf/
├── solutions/               # Generated solution LaTeX and PDFs
│   ├── tex/
│   └── pdf/
├── src/                     # Source Java files
│   └── exercise_generation/
│       ├── Dijkstra.java
│       ├── KMP.java
│       ├── RadixSort.java
│       ├── NaryTreeNode.java
│       ├── WeightedGraph.java
│       └── Visualiser.java
```

## Running the Program

### Using IntelliJ IDEA

1. Open IntelliJ and load the `automated-exercise-generation` project.
2. In **Project Structure**, add the JAR files in `dependencies/` as external libraries.
3. Right-click `Visualiser.java` and run the `main()` method.
4. Use the terminal to follow on-screen prompts and generate exercises.

### Using the Command Line

1. Compile the project:
   ```bash
   javac -d out -cp "dependencies/*:src" $(find src -name "*.java")
   ```
2. Create the JAR file:
   ```bash
   jar cfe AutomatedExerciseGenerator.jar exercise_generation.Visualiser -C out .
   ```
3. Run the program:
   ```bash
   java -jar AutomatedExerciseGenerator.jar
   ```

## How to Use the Program

1. Choose whether to generate solutions
2. Enable or disable PDF rendering.
3. Select which algorithms (Dijkstra, KMP, or Radix Sort) to generate exercises for.
4. Provide algorithm-specific parameters:
    - For Dijkstra: number of graphs, vertices, and edge relaxations
    - For KMP: number of strings, string size, border size, and overlap option
    - For Radix Sort: number of exercises, bits (6 or 8), array length, bucket factor, and whether to allow empty buckets
5. View generated `.tex` and `.pdf` files in `exercises/` and `solutions/`.

## Notes

- Radix Sort exercises are tailored for educational evaluation, using binary encodings and customisable bucket logic.
- PDF generation depends on the Aspose TeX library; LaTeX files can be compiled manually if needed.