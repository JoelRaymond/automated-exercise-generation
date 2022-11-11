package exercise_generation;

import java.util.LinkedList;

public class WeightedGraph {
	 static class Edge {
		 int start;
		 int end;
		 int weight;
	
		 Edge(int start, int end, int weight) {
			 this.start = start;
			 this.end = end;
			 this.weight = weight;
		 }
	 }

	 static class Graph {
		 int vertices;
		 LinkedList<Edge> [] adjacencylist;
	
		 Graph(int vertices) {
			 this.vertices = vertices;
			 adjacencylist = new LinkedList[vertices];

			 for (int i = 0; i <vertices ; i++) {
				 adjacencylist[i] = new LinkedList<>();
			 }
		 }

		 public void addEdge(int start, int end, int weight) {
			 Edge edge = new Edge(start, end, weight);
			 adjacencylist[start].addFirst(edge); 
		 }
	
		 public void printGraph(){
			 for (int i = 0; i <vertices ; i++) {
				 LinkedList<Edge> list = adjacencylist[i];
				 for (int j = 0; j <list.size() ; j++) {
					 System.out.println("vertex-" + i + " is connected to " + list.get(j).end + " with weight " + list.get(j).weight);
				 }
			 }
		 }
	 }
	 public static void main(String[] args) {
		 int vertices = 6;
		 Graph graph = new Graph(vertices);
		 graph.addEdge(0, 1, 4);
		 graph.addEdge(0, 2, 3);
		 graph.addEdge(1, 3, 2);
		 graph.addEdge(1, 2, 5);
		 graph.addEdge(2, 3, 7);
		 graph.addEdge(3, 4, 2);
		 graph.addEdge(4, 0, 4);
		 graph.addEdge(4, 1, 4);
		 graph.addEdge(4, 5, 6);
		 graph.printGraph();
		 System.out.println(graph.adjacencylist[0].get(0).end);
		 }
}
