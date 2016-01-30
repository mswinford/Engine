package com.MyJogl;

import com.MyJogl.Model.Model;

public abstract class GameObject {
	private Model model;
	
	
	public GameObject(Model model) {
		
	}
	public GameObject() {
		model = null;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
}
