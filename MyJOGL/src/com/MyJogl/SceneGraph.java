package com.MyJogl;



public class SceneGraph {
	SceneNode head;
	
	public SceneGraph(){
		head = new SceneNode();
	}
	
	public SceneNode getHead() {
		return head;
	}
}
