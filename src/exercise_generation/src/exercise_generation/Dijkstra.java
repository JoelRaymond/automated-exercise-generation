package exercise_generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
	private HashMap<Integer, String> shortestPaths;
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
	
	
	public void setShortestPaths(HashMap<Integer, String> shortestPaths) {
		this.shortestPaths = shortestPaths;
	}

	public HashMap<Integer, String> getShortestPaths() {
		return shortestPaths;
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
		
		int possibleRelaxations = getRelaxations(vertices);
		if (relaxations <= possibleRelaxations) {
			this.relaxations = relaxations;
		}
		else {
			throw new IllegalArgumentException("Too many relaxations.");
		}
		
		this.maxDistance = calculateMaxDistance();
		this.shortestDistance = generateDistances();
		this.shortestPaths = new HashMap<Integer, String>();
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
	
	public int getRelaxations(int v) {
		int n = v-2;
		return (n * (n + 1))/2;
	}
	
	public WeightedGraph.Graph generateGraph(){
		WeightedGraph.Graph result = new WeightedGraph.Graph(this.vertices);
		LinkedList<Integer> verticesInGraph = new LinkedList<Integer>();
		HashMap<Integer, Integer> relaxedTo = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> relaxedWeight = new HashMap<Integer, Integer>();
		Integer[] verticesInOrder = this.shortestDistance.keySet().toArray(new Integer[this.shortestDistance.keySet().size()]);
		HashMap<Integer, List<Integer>> vertexToPath = new HashMap<>();
		
		int possibleRelaxations = getRelaxations(this.vertices);
		int relaxationsNeeded = this.relaxations;
		//connect vertices
		boolean first = true;
		int vertexEdgeRelaxation = 1;
		int deepestVertex = 0;
		for (Entry<Integer, Integer> entry : this.shortestDistance.entrySet()) {

			if (entry.getValue() == 0) {
				this.shortestPaths.put(entry.getKey(), "v_{1}");
				verticesInGraph.add(entry.getKey());
				vertexToPath.put(entry.getKey(), Arrays.asList(entry.getKey()));
				continue;
			}
			else if (first) {
				result.addEdge(0, entry.getKey(), entry.getValue());
				this.shortestPaths.put(entry.getKey(), this.shortestPaths.get(0) + 
						" \\rightarrow v_{" + Integer.toString(entry.getKey()+1) + "}");
				verticesInGraph.add(entry.getKey());
				first = false;
				deepestVertex = entry.getKey();
				List<Integer> path = new ArrayList<>(vertexToPath.get(0));
				path.add(entry.getKey());
				vertexToPath.put(entry.getKey(), path);
			}
			else {
				int min = (this.vertices-2) - (possibleRelaxations - relaxationsNeeded);
				int randomStart = 0;
				if (min < 0) min = 0;
				
				if (min >= verticesInGraph.size()) randomStart = verticesInGraph.size()-1;
				else randomStart = ThreadLocalRandom.current().nextInt(min, verticesInGraph.size());
				
				int start = verticesInOrder[randomStart];
				if (start == deepestVertex) deepestVertex = entry.getKey();
				result.addEdge(start, entry.getKey(), entry.getValue() - this.shortestDistance.get(start));
				this.shortestPaths.put(entry.getKey(), this.shortestPaths.get(start) + 
						" \\rightarrow v_{" + Integer.toString(entry.getKey()+1) + "}");
				verticesInGraph.add(entry.getKey());
				List<Integer> path = new ArrayList<>(vertexToPath.get(start));
				path.add(entry.getKey());
				relaxationsNeeded -= (path.size() - 2);
				vertexToPath.put(entry.getKey(), path);
				possibleRelaxations -= vertexEdgeRelaxation;
				if (randomStart == deepestVertex) vertexEdgeRelaxation++;
			}
		}
		System.out.println(vertexToPath);
		for (Integer v : vertexToPath.keySet()) {
			int list_size = vertexToPath.get(v).size();
			if (list_size > 2) {
				relaxedTo.put(v, vertexToPath.get(v).get(list_size-3));
			}
		}
		//add more edges to get to no more edge relaxations
		int prevDist = 0;
		while (this.relaxations > 0) {
			System.out.println(relaxedTo);
			Object[] vertices = relaxedTo.keySet().toArray();
			Object randVert = vertices[ThreadLocalRandom.current().nextInt(vertices.length)];
			int start = (int) randVert;
			int end = relaxedTo.get(start);
				
			int weightIncrease = ThreadLocalRandom.current().nextInt(1, 4);
			
			int weight;
			if (relaxedWeight.containsKey(start)) {
				weight = relaxedWeight.get(start) + (prevDist - this.shortestDistance.get(end)) + weightIncrease;
			}
			else {
				weight = (this.shortestDistance.get(start) - this.shortestDistance.get(end)) + weightIncrease;
			}
			relaxedWeight.put(start, weight);
			
			result.addEdge(start, end, weight);
			
			prevDist = this.shortestDistance.get(end);
			
			List<Integer> path = vertexToPath.get(start);
			int newRelax = 0;
			boolean success = true;
			try {
				newRelax = path.get(path.indexOf(end)-1);
			}
			catch (Exception e) {
				relaxedTo.remove(start);
				success = false;
			}
			if (success) relaxedTo.put(start, newRelax);
			this.relaxations--;
		}
		return result;
	}
	
	public static void main(String[] args) {
		for(int i = 0; i <1; i++) {
			Dijkstra test = new Dijkstra(5, 5);
			System.out.println(test.maxDistance);
			System.out.println(test.shortestDistance);
			System.out.println(test.shortestPaths);
			test.graph.printGraph();
		}
	}
}
