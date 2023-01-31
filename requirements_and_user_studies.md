<h2>User stories</h2>

  1. Lecturer (uses program) 
  - As an algorithmics lecturer, I want to be able to generate many exercises and solutions, so that each one of my students get unique exercises.
  - As an algorithmics lecturer, I want to have solutions generated for exercises, so that students can double check their answers.
  - As an algorithmics lecturer, I want to have the option of generated step-by-step answer keys instead of just solutions, so that it explains in detail how to arrive at the solution.
  - As an algorithmics lecturer, I want to be able to choose how many exercises are generated, so that it matches the number of students I teach.
  - As an algorithmics lecturer, I want my students to get unique exercises, so that there is no collusion between them, maintaining academic integrity.
  - As an algorithmics lecturer, I want to have some control over the difficulties of exercises, so that I can generate for varying skill levels.
  - As an algorithmics lecturer, I want the generated exercises and solutions in easily distributable files, so that I can distribute them amongst my students easily.
  - As an algorithmics lecturer, I want to have the generated exercises and solutions in separate files, so that students can try solve questions before checking the solution.
  - As an algorithmics lecturer, I want to have the option of generating to PDFs or just LATEX files, in case I need the raw LATEX files.
  - As an algorithmics lecturer, I want to be able to choose which algorithms to generate for, including multiple algorithms at the same time, so that I don't have to create them separately.
  - As an algorithmics lecturer, I want a simple, easy to use application, so that I can generate exercises and solutions with little effort.
  - As an algorithmics lecturer, I want to be able to generate relatively quickly, so that they can be generated during tutorials.

  2. Student (recieves exercise and solution)
  - As an algorithmics student, I want generated exercises to be visually clear, so that they are not unneccessarily more difficult to solve than hand written exercises.
  - As an algorithmics student, I want the step-by-step answer keys to clearly explain each step of solving the exercise, so that I can trace the algorithm.
  - As an algorithmics student, I want my exercise to be of equal difficulty to everyone else's, so that nobody has an unfair advantage. 
  - As an algorithmics student, I want an exercise which is solvable similarly to the exercises I'm used to, so that there are no unnecessary confusion.
  - As an algorithmics student, I want an exercise which looks similar to the class examples I have been practising, so it applies well to my course.
  - As an algorithmics student, I want clear instructions as to what an exercise is asking me to do, so that I can solve it effectively.

<h2>Output Requirements</h2>

  1. Dijkstra (Exercise and solution)
  - Must have
    - Exercise corresponding to user inputs
    - Vertices with labels
    - Edges with weight labels
    - Solvable exercise
  - Should have
    - Correct solution
    - Clear instructions for solving exerecise
    - Answers for shortest paths and lengths provided in a table (updating for each step in answer key)
    - Description of what is being done at each step of solution
    - Similar visuals to standard Dijkstra questions
  - Could have
    - Highlighted edges for step-by-step answer key
    - Adjacency matrix for exercise
    - Edge relaxations highlighted
  - Will not have
    - Custom edge weight placement

  2. KMP (Exercise and solution)
  - Must have
    - Exercise corresponding to user inputs
    - String with given length from given alphabet
    - Solvable exercise
  - Should have
    - Correct solution
    - Clear instructions for solving exercise
    - Border table as the solution
    - Similar visuals to class exercises
  - Could have
    - Highlights on updating border table to show progression
    - Description of what happens each step in the answer key
    - Borders found highlighted
  - Will not have
    - String search


<h2>Project Requirements</h2>

Must Have
 - Automatic exercise generation for at least two different assessment tasks (Dijkstra's Algorithm and KMP Algorithm)
 - Ensure uniform difficulty between generated examples
 - Generate at most 50 unique and solvable instances of a given exercise 
 - An object oriented approach for dealing with different exercises (Java)

Should Have
 - Basic user interface (command line)
 - Automatic answer key
 - Allow users to adjust certain settings before generation (number of exercises, difficulty, etc.)
 - Generated PDFs for each exercise (LaTeX generation)

Could Have
 - Simple user interface (JavaFX)
 - Automatic exercise generation for at least three different assessment tasks
 - Step by step solutions for each generated exercise
 - Proper exam formatting for questions

Will Not Have
 - Refined user interface
 - Automatic exercise generation for at least four different assessment tasks
 - Automatic feedback
 
