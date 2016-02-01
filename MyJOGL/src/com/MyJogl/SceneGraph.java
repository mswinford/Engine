package com.MyJogl;



public class SceneGraph {
	SceneNode head;
	int numOfNodes;
	
	public SceneGraph(){
		head = new SceneNode();
	}
	
	public SceneNode getHead() {
		return head;
	}
}
