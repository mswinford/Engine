package com.MyJogl;

public class GameObject {
	private Model model;
	
	
	public GameObject(Model model) {
		
	}
	public GameObject() {
		this(new Model());
	}
	
	public void setMesh(Model model) {
		this.model = model;
	}
}
