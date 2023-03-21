# Readme

The program consists of five Java files: 
* `Dijkstra.java` (handling Dijktstra exercise generation) 
* `KMP.java` (handling KMP exercise generations)
* `NaryTreeNode.java` (custom representations of an n-ary tree for Dijkstra vertex insertion)
* `Visualiser.java`(user interface and LaTeX/pdf generation)
* `WeightedGraph.java` (custom representation of graphs for Dijkstra exercises)

These raw Java files can be found in the exercise_generation package (`exercise_generation/src/exercise_generation`). Generated exercises and solutions are saved in `exercise_generation/exercises`and `exercise_generation/solutions` respectively. 

## Build instructions

### Requirements

* Java 8
* Packages: `aspose-tex-22.12.jar` and `commons-io-2.11.0.jar` both of which can be found in `exercise_generation/dependencies/`
* Tested on Windows 10 and MacOS 13

### Build steps

* Open project (`exercise_generation`) in Java IDE (tested in Eclipse).
* Add packages highlighted above to runtime.
* Export to runnable JAR file.

### Test steps

* Start the software by running `java -jar AutomatedExerciseGenerator.jar` in command line (JAR file can be found in this folder or built as described).
