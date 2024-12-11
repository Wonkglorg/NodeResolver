package com.wonkglorg.noderesolver;

import com.wonkglorg.noderesolver.nodes.base.BaseNode;

import java.util.HashMap;
import java.util.Map;

public class NodeService {
	private final Map<Class<? extends BaseNode>, Map<String, ? super BaseNode>> nodeMap =
			new HashMap<>();


	public void addNode(final String name, final BaseNode baseNode) {
		final BaseNode clonedNode = baseNode.clone();
		nodeMap.computeIfAbsent(clonedNode.getClass(), node -> new HashMap<>()).put(name, clonedNode);
	}


	@SuppressWarnings("unchecked")
	public <T extends BaseNode> T getNode(Class<T> nodeType, String name) {
		return (T) ((T) nodeMap.get(nodeType).get(name)).clone();
	}
}
