package exercise_generation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NaryTreeNode {
	int val;
	int level;
	List<NaryTreeNode> children;

	public NaryTreeNode(int val, int level) {
		this.val = val;
		this.level = level;
		this.children = new ArrayList<NaryTreeNode>();
	}

	public void addChild(int val) {
		NaryTreeNode child = new NaryTreeNode(val, level + 1);
		children.add(child);
	}

	public List<NaryTreeNode> getNodesAtLevel(NaryTreeNode root, int level) {
		List<NaryTreeNode> nodesAtLevel = new ArrayList<>();
		if (root == null || level < 0) {
			return nodesAtLevel;
		}
		Queue<NaryTreeNode> queue = new LinkedList<>();
		queue.offer(root);
		int currentLevel = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				NaryTreeNode node = queue.poll();
				if (currentLevel == level) {
					nodesAtLevel.add(node);
				}
				if (node.children != null) {
					for (NaryTreeNode child : node.children) {
						queue.offer(child);
					}
				}
			}
			currentLevel++;
		}
		return nodesAtLevel;
	}
}
