package exercise_generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Dijkstra {
	public int vertices;
	public int relaxations;
	public int maxDistance;
	public LinkedHashMap<Integer, Integer> shortestDistance;
	public WeightedGraph.Graph graph;
	
	public Dijkstra(int vertices, int relaxations) {
		this.vertices = vertices;
		this.relaxations = relaxations;
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
		for(int i = 1; i <= this.vertices; i++) {
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
		for(Entry<Integer, Integer> entry : entriesList){
            result.put(entry.getKey(), entry.getValue());
        }
		return result;
	}
	
	public WeightedGraph.Graph generateGraph(){
		WeightedGraph.Graph result = new WeightedGraph.Graph(this.vertices);
		//connect vertices
		
		return result;
	}
	
	public static void main(String[] args) {
		for(int i = 0; i <1; i++) {
			Dijkstra test = new Dijkstra(7, 5);
			System.out.println(test.maxDistance);
			System.out.println(test.shortestDistance);
		}
	}
}
