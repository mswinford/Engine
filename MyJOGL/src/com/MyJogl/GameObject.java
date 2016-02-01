package com.MyJogl;

import java.util.ArrayList;

import com.MyJogl.Model.Model;

public abstract class GameObject {
	protected String name;
	protected Model model;
	protected ArrayList<GameObject> components;
	
	public GameObject(Model model) {
		
	}
	public GameObject(String name) {
		model = null;
		this.name = name;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	public Model getModel() {
		return model;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void addComponent(GameObject comp) {
		components.add(comp);
	}
	
	public GameObject getComponent(String name) {
		for (GameObject component : components) {
			if (component.getName().equals(name)) {
				return component;
			}
		}
		
		return null;
	}
	
	public void draw() {
		model.draw();
	}
}
