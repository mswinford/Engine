package com.MyJogl.GameObject;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.MyJogl.Logger.Logger;
import com.MyJogl.Model.Model;
import com.jogamp.opengl.GL2;

public class Character extends GameObject implements Renderable, Movable {
	
	protected Model model;
	
	public Character(String name) {
		super(name);
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(GL2 gl, Matrix4f vp) {
		if(model != null)
			model.draw(gl, calcMVP(vp));
		else 
			Logger.writeToLog(name + " has no model");
		
		for (GameObject comp : components) {
			if(comp != null && comp instanceof Renderable)
				((Renderable)comp).draw(gl, vp);
		}
	}
	
	private Matrix4f calcMVP(Matrix4f vp) {
		Matrix4f mvp = new Matrix4f(vp);
		Matrix4f m = new Matrix4f().translationRotateScale(translation, rotation, new Vector3f(scale, scale, scale));
		mvp.mul(m);
		
		return mvp;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public Model getModel() {
		return model;
	}
	
}
