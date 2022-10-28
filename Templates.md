##Dijkstra's Algorithm

Example problems:

![image](https://user-images.githubusercontent.com/77453616/197855184-a6872223-de1c-4959-96b0-e6d1a1174223.png)

Question: 
Find the shortest paths, and their lengths, from vertex v1 to each other vertices in the graph shown below.

Properties:
  - 7-9 vertices RECOMMENDED (input depending on difficulty, kept uniform)
  - 1-23 weight values (for not too difficult calculations)
  - 12 edges for 7 vertices, 16 edges for 8 vertices, 21 edges for 9 vertices RECOMMENDED, allowed up to complete (same ratio, to keep uniform difficulty across number of vertices input)
  - Connected (No redundant nodes)
  - Pseudo random edge placement, ensuring somewhat even distribution and outside circle
  - To eliminate multiple answers either: have unique weights OR have generally larger weights on the inside OR just give multiple answers in solution OR ...
  - Vertices displayed in order in a circle

##KMP Algorithm

Example problems:
![image](https://user-images.githubusercontent.com/77453616/198338456-365255b3-7687-4d68-93b5-f4407ad590a1.png)
![image](https://user-images.githubusercontent.com/77453616/198339300-6679fd45-969e-429e-baec-de0318b43c64.png)

Question:
Construct the border table B for the KMP algorithm for the string:

Properties:
  - String length 13-15 RECOMMENDED (chosen to vary difficulty)
  - Random 3 character alphabet chosen from the English alphabet
  - Generation of order (to be tested): fully random (to be tested) OR (at least one border of size ~0.3*n (4 in recommended case) and build rest of the string around it)
