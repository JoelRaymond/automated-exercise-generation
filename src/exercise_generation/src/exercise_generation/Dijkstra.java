package exercise_generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Dijkstra {
	private int vertices;
	private int relaxations;
	private int maxDistance;
	private LinkedHashMap<Integer, Integer> shortestDistance;
	private WeightedGraph.Graph graph;
	
	public int getVertices() {
		return vertices;
	}

	public void setVertices(int vertices) {
		this.vertices = vertices;
	}

	public int getRelaxations() {
		return relaxations;
	}

	public void setRelaxations(int relaxations) {
		this.relaxations = relaxations;
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public LinkedHashMap<Integer, Integer> getShortestDistance() {
		return shortestDistance;
	}

	public void setShortestDistance(LinkedHashMap<Integer, Integer> shortestDistance) {
		this.shortestDistance = shortestDistance;
	}

	public WeightedGraph.Graph getGraph() {
		return graph;
	}

	public void setGraph(WeightedGraph.Graph graph) {
		this.graph = graph;
	}

	public Dijkstra(int vertices, int relaxations) {
		if (vertices > 0) {
			this.vertices = vertices;
		}
		else {
			throw new IllegalArgumentException("Illegal vertex input.");
		}
		
		if (relaxations <= (vertices * (vertices - 1))/2) {
			this.relaxations = relaxations;
		}
		else {
			throw new IllegalArgumentException("Too many relaxations.");
		}
		
		this.maxDistance = calculateMaxDistance();
		this.shortestDistance = generateDistances();
		this.graph = generateGraph();
	}
	
	public int calculateMaxDistance(){
		return this.vertices * 3;
	}
	
	public LinkedHashMap<Integer, Integer> generateDistances(){
		LinkedHashMap<Integer, Integer> result = new LinkedHashMap<Integer, Integer>();
		result.put(0, 0);
		for(int i = 1; i < this.vertices; i++) {
			while(true) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, this.maxDistance);
				if (result.containsValue(randomNum) == false) {
					result.put(i, randomNum);
					break;
				}
			}
		}
		result = sortMap(result);
		return result;
	}
	
	Comparator<Entry<Integer, Integer>> valueComparator = new Comparator<Entry<Integer,Integer>>() {
		 @Override
		 public int compare(Entry<Integer, Integer> ent1, Entry<Integer, Integer> ent2) {
		     Integer val1 = ent1.getValue();
		     Integer val2 = ent2.getValue();
		     return val1.compareTo(val2);
		 }
	};
	
	public LinkedHashMap<Integer, Integer> sortMap(Map<Integer, Integer> map){
		Set<Entry<Integer, Integer>> entries = map.entrySet();
		List<Entry<Integer, Integer>> entriesList = new ArrayList<Entry<Integer, Integer>>(entries);
		Collections.sort(entriesList, valueComparator);
		LinkedHashMap<Integer, Integer> result = new LinkedHashMap<Integer, Integer>(entriesList.size());
		for(Entry<Integer, Integer> e : entriesList){
            result.put(e.getKey(), e.getValue());
        }
		return result;
	}
	
	public WeightedGraph.Graph generateGraph(){
		WeightedGraph.Graph result = new WeightedGraph.Graph(this.vertices);
		LinkedList<Integer> verticesInGraph = new LinkedList<Integer>();
		int firstWeight = 0;
		//connect vertices
		boolean first = true;
		for (Entry<Integer, Integer> entry : this.shortestDistance.entrySet()) {

			if (entry.getValue() == 0) {
				continue;
			}
			else if (first) {
				result.addEdge(0, entry.getKey(), entry.getValue());
				firstWeight = entry.getValue();
				verticesInGraph.add(entry.getKey());
				first = false;
			}
			else if (this.relaxations > 0) {
				//either add to start or another node based on chance and edge relaxations remaining
				int chance = ThreadLocalRandom.current().nextInt(1, 7);
				if (chance == 1) {
					result.addEdge(0, entry.getKey(), entry.getValue());
					verticesInGraph.add(entry.getKey());
				}
				else {
					int start = verticesInGraph.get(ThreadLocalRandom.current().nextInt(0, verticesInGraph.size()));
					result.addEdge(start, entry.getKey(), entry.getValue() - this.shortestDistance.get(start));
					verticesInGraph.add(entry.getKey());
					this.relaxations --;
				}
			}
			else {
				result.addEdge(0, entry.getKey(), entry.getValue());
				verticesInGraph.add(entry.getKey());
			}
		}
			
		//add more edges to get to no more edge relaxations
		while (this.relaxations > 0) {
			LinkedList noEdgeWithVertices = new LinkedList();
			noEdgeWithVertices = (LinkedList) verticesInGraph.clone();
			int start = ThreadLocalRandom.current().nextInt(1, noEdgeWithVertices.size());
			for (int i = 0; i < result.adjacencylist[start].size(); i++) {
				noEdgeWithVertices.remove(Integer.valueOf(result.adjacencylist[start].get(i).end));
			}
			noEdgeWithVertices.remove(Integer.valueOf(start));
			
			if (noEdgeWithVertices.size() != 0) {
				int end = (int) noEdgeWithVertices.get(ThreadLocalRandom.current().nextInt(0, noEdgeWithVertices.size()));
				int weightIncrease = ThreadLocalRandom.current().nextInt(1, 6);
				int weight = Math.max(this.shortestDistance.get(start), this.shortestDistance.get(end)) + weightIncrease;
				result.addEdge(start, end, weight);
				this.relaxations--;
			}
			else {
				continue;
			}
		}
			
		//optionally add decorator edges
		LinkedList noEdgeWith = new LinkedList();
		noEdgeWith = (LinkedList) verticesInGraph.clone();
		for (int i = 0; i < result.adjacencylist[0].size(); i++) {
			noEdgeWith.remove(Integer.valueOf(result.adjacencylist[0].get(i).end));
		}
		noEdgeWith.remove(Integer.valueOf(0));
		while (result.adjacencylist[0].size() < (this.vertices / 2)) {
			int end = (int) noEdgeWith.get(ThreadLocalRandom.current().nextInt(0, noEdgeWith.size()));
			int weightIncrease = ThreadLocalRandom.current().nextInt(1, 6);
			int weight = firstWeight + weightIncrease;
			result.addEdge(0, end, weight);
			noEdgeWith.remove(Integer.valueOf(end));
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		for(int i = 0; i <1; i++) {
			Dijkstra test = new Dijkstra(5, 5);
			System.out.println(test.maxDistance);
			System.out.println(test.shortestDistance);
			test.graph.printGraph();
		}
	}
}
