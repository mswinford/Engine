package com.MyJogl;

import java.util.ArrayList;
import java.util.List;

public class SceneNode {
	List<SceneNode> children;
	
	
	public SceneNode() {
		children = new ArrayList<SceneNode>();
	}
	
	public void addChild(SceneNode node) {
		children.add(node);
	}

}
