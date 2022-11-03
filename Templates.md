##Dijkstra's Algorithm

Example problems:

![image](https://user-images.githubusercontent.com/77453616/197855184-a6872223-de1c-4959-96b0-e6d1a1174223.png)

Question: 
Find the shortest paths, and their lengths, from vertex v1 to each other vertices in the graph shown below.

Properties:
  - 7-9 vertices ~ RECOMMENDED (input depending on difficulty, kept uniform)
  - Undirected graph
  - Connected (No redundant nodes)
  - Pseudo random edge placement, ensuring somewhat even distribution and outside circle
  - Number of edge relaxations: at most e (e = number of edges) relaxations, so the number edges varies difficulty more than number of vertices (vertices just makes for more calcuations), more relaxations if edge distribution is uneven; at most (|v|(|v|-1))/2, RECOMMEMDED ~ |v| edge relaxations
  - Each vertex must have a unique minimum distance (up to ~2.5 * |v|) from starting vertex
  - Build graph by reversing Dijkstra's algorithm (filling in adjacency matrix? -> start with adjacency matrix with all zeros):
  
  1. Get number of vertices and number of edge relaxations as input
  2. Assign each vertex with unique minimum distance (0 for starting vertex)
  3. Add vertices to graph in increasing order of minimum distance (after adding first two with edge in between them of weight dist(second vertex))
  4. If more edge relaxations are required, add the vertex with an indirect path to the start (attached to any other random vertex), adding up to its minimum distance (dist(vertex being attached to) + **remaining dist** = dist(new vertex)) OR just add it to start and make up for edge relaxations in next step (rare occasion, 1/6 chance)
  5. Once all the vertices are added, and more edge relaxations are needed, add edges between random non-starting vertices, with higher than dist(origin) and dist(dest) weights until no more edge relaxations
  6. Optionally , add edges from starting vertex to others with weights higher than dist(second vertex) to fill out the graph (should have about |v|/2 edges).

##KMP Algorithm

Example problems:
![image](https://user-images.githubusercontent.com/77453616/198338456-365255b3-7687-4d68-93b5-f4407ad590a1.png)
![image](https://user-images.githubusercontent.com/77453616/198339300-6679fd45-969e-429e-baec-de0318b43c64.png)

Question:
Construct the border table B for the KMP algorithm for the string:

Properties:
  - String length 13-15 RECOMMENDED (chosen to vary difficulty)
  - Random 3 character alphabet chosen from {A, C, G, U, T}
  - Generation of order (to be tested): fully random (to be tested) OR (at least one border of size ~0.3*n (4 in recommended case) and build rest of the string around it)
  - Optional input of largest border
  - Two types of borders (overlapping and non-overlapping)
  - Generation algorithm:
  
  1. 
