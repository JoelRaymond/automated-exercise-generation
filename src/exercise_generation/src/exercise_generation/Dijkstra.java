package exercise_generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
		} else {
			throw new IllegalArgumentException("Illegal vertex input.");
		}

		int possibleRelaxations = getRelaxations(vertices);
		if (relaxations <= possibleRelaxations) {
			this.relaxations = relaxations;
		} else {
			throw new IllegalArgumentException("Too many relaxations.");
		}

		this.maxDistance = calculateMaxDistance();
		this.shortestDistance = generateDistances();
		this.shortestPaths = new HashMap<Integer, String>();
		this.graph = generateGraph();
	}

	public int calculateMaxDistance() {
		return this.vertices * 3;
	}

	public LinkedHashMap<Integer, Integer> generateDistances() {
		LinkedHashMap<Integer, Integer> result = new LinkedHashMap<Integer, Integer>();
		result.put(0, 0);
		for (int i = 1; i < this.vertices; i++) {
			while (true) {
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

	Comparator<Entry<Integer, Integer>> valueComparator = new Comparator<Entry<Integer, Integer>>() {
		@Override
		public int compare(Entry<Integer, Integer> ent1, Entry<Integer, Integer> ent2) {
			Integer val1 = ent1.getValue();
			Integer val2 = ent2.getValue();
			return val1.compareTo(val2);
		}
	};

	public LinkedHashMap<Integer, Integer> sortMap(Map<Integer, Integer> map) {
		Set<Entry<Integer, Integer>> entries = map.entrySet();
		List<Entry<Integer, Integer>> entriesList = new ArrayList<Entry<Integer, Integer>>(entries);
		Collections.sort(entriesList, valueComparator);
		LinkedHashMap<Integer, Integer> result = new LinkedHashMap<Integer, Integer>(entriesList.size());
		for (Entry<Integer, Integer> e : entriesList) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}

	public static int getRelaxations(int v) {
		int n = v - 2;
		return (n * (n + 1)) / 2;
	}

	public WeightedGraph.Graph generateGraph() {
		WeightedGraph.Graph result = new WeightedGraph.Graph(this.vertices);
		HashMap<Integer, Integer> relaxedTo = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> relaxedWeight = new HashMap<Integer, Integer>();
		HashMap<Integer, List<Integer>> vertexToPath = new HashMap<>();
		HashMap<Integer, Integer> prevDist = new HashMap<>();

		int possibleRelaxations = getRelaxations(this.vertices);
		// connect vertices
		boolean first = true;
		int maxRelaxation = this.vertices - 2;
		NaryTreeNode root = new NaryTreeNode(0, 0);
		int maxLevel = 0;
		for (Entry<Integer, Integer> entry : this.shortestDistance.entrySet()) {

			if (entry.getValue() == 0) {
				this.shortestPaths.put(entry.getKey(), "v_{1}");
				vertexToPath.put(entry.getKey(), Arrays.asList(entry.getKey()));
				continue;
			} else if (first) {
				result.addEdge(0, entry.getKey(), entry.getValue());
				this.shortestPaths.put(entry.getKey(),
						this.shortestPaths.get(0) + " \\rightarrow v_{" + Integer.toString(entry.getKey() + 1) + "}");
				first = false;
				root.addChild(entry.getKey());
				maxLevel = 1;
				List<Integer> path = new ArrayList<>(vertexToPath.get(0));
				path.add(entry.getKey());
				vertexToPath.put(entry.getKey(), path);
			} else {
				int chosenLevel = 0;
				// System.out.println("possible");
				// System.out.println(possibleRelaxations);
				// System.out.println("needed");
				// System.out.println(relaxationsNeeded);
				// System.out.println("maxrelax");
				// System.out.println(maxRelaxation);
				int disparity = possibleRelaxations - this.relaxations;
				if (disparity < maxRelaxation) {
					chosenLevel = maxLevel;
				} else {
					// System.out.println("count");
					// System.out.println(count);
					chosenLevel = ThreadLocalRandom.current().nextInt(maxLevel + 1);
				}
				// System.out.println("chosen");
				// System.out.println(chosenLevel);
				List<NaryTreeNode> valuesAtLevel = root.getNodesAtLevel(root, chosenLevel);
				NaryTreeNode startNode = valuesAtLevel.get(ThreadLocalRandom.current().nextInt(valuesAtLevel.size()));
				int start = startNode.val;

				result.addEdge(start, entry.getKey(), entry.getValue() - this.shortestDistance.get(start));
				startNode.addChild(entry.getKey());
				if (chosenLevel == maxLevel) {
					maxLevel++;
				} else {
					possibleRelaxations -= maxRelaxation;
					maxRelaxation--;
				}
				this.shortestPaths.put(entry.getKey(), this.shortestPaths.get(start) + " \\rightarrow v_{"
						+ Integer.toString(entry.getKey() + 1) + "}");
				List<Integer> path = new ArrayList<>(vertexToPath.get(start));
				path.add(entry.getKey());
				vertexToPath.put(entry.getKey(), path);

			}
		}
		for (Integer v : vertexToPath.keySet()) {
			int list_size = vertexToPath.get(v).size();
			if (list_size > 2) {
				relaxedTo.put(v, vertexToPath.get(v).get(list_size - 3));
			}
		}
		// System.out.println(vertexToPath);
		// add more edges to get to no more edge relaxations
		while (this.relaxations > 0) {

			Object[] vertices = relaxedTo.keySet().toArray();
			Object randVert = vertices[ThreadLocalRandom.current().nextInt(vertices.length)];
			int start = (int) randVert;
			int end = relaxedTo.get(start);

			int weightIncrease = ThreadLocalRandom.current().nextInt(1, 4);

			int weight;
			if (relaxedWeight.containsKey(start)) {
				weight = relaxedWeight.get(start) + (prevDist.get(start) - this.shortestDistance.get(end))
						+ weightIncrease;
			} else {
				weight = (this.shortestDistance.get(start) - this.shortestDistance.get(end)) + weightIncrease;
			}
			relaxedWeight.put(start, weight);

			prevDist.put(start, this.shortestDistance.get(end));

			result.addEdge(start, end, weight);

			List<Integer> path = vertexToPath.get(start);
			int newRelax = 0;
			try {
				newRelax = path.get(path.indexOf(end) - 1);
				relaxedTo.put(start, newRelax);
			} catch (Exception e) {
				relaxedTo.remove(start);
			}
			this.relaxations--;
		}
		return result;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			Dijkstra test = new Dijkstra(10, 10);
			System.out.println(test.maxDistance);
			System.out.println(test.shortestDistance);
			System.out.println(test.shortestPaths);
			test.graph.printGraph();
		}
	}
}
