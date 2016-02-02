package com.MyJogl.GameObject;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.MyJogl.Model.Model;
import com.jogamp.opengl.GL2;

public abstract class GameObject {
	protected String name;
	protected Model model;
	protected float scale;
	protected Vector3f translation;
	protected Quaternionf rotation;
	//protected Matrix4f mvp;
	protected ArrayList<GameObject> components;
	
	public GameObject(Model model) {
		
	}
	public GameObject(String name) {
		this.name = name;
		model = null;
		
		scale = 1.0f;
		translation = new Vector3f(0.0f, 0.0f, 0.0f);
		rotation = new Quaternionf();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Model getModel() {
		return model;
	}
	public void setScale(float scale) {
		if (scale <= 0.0f) {
			System.out.println("Scale cannot be 0 or lower");
			return;
		}
		this.scale = scale;
	}
	public float getScale() {
		return scale;
	}
	public void setTranslation(Vector3f trans) {
		this.translation = trans;
	}
	public Vector3f getTranslation() {
		return translation;
	}
	public void setRotation(Quaternionf rotation) {
		this.rotation = rotation;
	}
	public Quaternionf getRotation() {
		return rotation;
	}
	
	public void translate(Vector3f trans) {
		translation.add(trans);
	}
	public void Rotate(float[] rot) {
		rotation.rotate(rot[0], rot[1], rot[2]);
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
	
	public void draw(GL2 gl, Matrix4f vp) {
		model.draw(gl, calcMVP(vp));
		
//		for (GameObject comp : components) {
//			comp.draw(gl, proj);
//		}
	}
	
	private Matrix4f calcMVP(Matrix4f vp) {
		Matrix4f mvp = vp;
		mvp.scale(scale).translate(translation).rotate(rotation);
		return mvp;
	}
}
