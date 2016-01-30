package com.MyJogl;

import java.util.ArrayList;
import java.util.List;

public class SceneNode {
	SceneNode parent;
	List<SceneNode> children;
	
	
	public SceneNode() {
		parent = null;
		children = new ArrayList<SceneNode>();
	}
	
	public void addParent(SceneNode node) {
		parent = node;
	}
	
	public void addChild(SceneNode node) {
		children.add(node);
	}
	
	

}
