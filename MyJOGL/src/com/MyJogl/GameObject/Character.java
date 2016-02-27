package com.MyJogl.GameObject;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.MyJogl.Model.Model;
import com.jogamp.opengl.GL2;

public class Character extends GameObject implements Renderable, Movable {
	
	protected Model model;
	protected boolean transparent;
	protected float speed;
	
	public Character(String name, Model model) {
		super(name);
		this.name = name;
		this.model = model;
	}
	public Character(String name) {
		this(name, null);
	}
	public Character(Model model) {
		this("", model);
	}
	public Character() {
		this("", null);
	}
	
	@Override
	public void move(Vector3f move) {
		this.translate(move);
	}
	
	@Override
	public void draw(GL2 gl, Matrix4f vp) {
		if(model != null) {
			model.draw(gl, calcMVP(vp));
		}
		else 
			//Logger.writeToLog(name + " has no model");
		
		for (GameObject comp : components) {
			if(comp != null && comp instanceof Renderable)
				((Renderable)comp).draw(gl, vp);
		}
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public boolean isTransparent() {
		return transparent;
	}

	@Override
	public void setTransparent(boolean trans) {
		this.transparent = trans;
		
	}
	@Override
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	@Override
	public float getSpeed() {
		return speed;		
	}
	
}
