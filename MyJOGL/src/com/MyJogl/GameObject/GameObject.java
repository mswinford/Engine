package com.MyJogl.GameObject;

import java.io.Serializable;
import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.MyJogl.Logger.Logger;

import com.MyJogl.Logger.Logger;
import com.MyJogl.Model.Model;
import com.jogamp.opengl.GL2;

public abstract class GameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9168945136392555460L;
	protected String name;
	protected Model model;
	protected float scale;
	protected Vector3f translation;
	protected Quaternionf rotation;
	protected ArrayList<GameObject> components;
	
	
	public GameObject() {
		name = "";
		model = null;
		
		scale = 1.0f;
		translation = new Vector3f();
		rotation = new Quaternionf();
		
		components = new ArrayList<GameObject>();
	}
	public GameObject(Model model) {
		this();
		this.model = model;
	}
	public GameObject(String name) {
		this();
		this.name = name;
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
	
	public void scale(float scale) {
		this.scale *= scale;
	}
	public void translate(Vector3f trans) {
		translation.add(trans);
	}
	public void rotate(float[] rot) {
		rotation.rotate(rot[0], rot[1], rot[2]);
	}
	public void rotate(Quaternionf rot) {
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
	
<<<<<<< master
	public void draw(GL2 gl, Matrix4f vp) {
		if(model != null)
			model.draw(gl, calcMVP(vp));
		else 
			Logger.writeToLog(name + " has no model");
		
		for (GameObject comp : components) {
			if(comp != null)
				comp.draw(gl, vp);
			else 
				Logger.writeToLog("null component in object: " + name);
		}
	}
	
	private Matrix4f calcMVP(Matrix4f vp) {
		Matrix4f mvp = new Matrix4f(vp);
		Matrix4f m = new Matrix4f().translationRotateScale(translation, rotation, new Vector3f(scale, scale, scale));
		mvp.mul(m);
		
		return mvp;
	}
=======
	protected Matrix4f calcMVP(Matrix4f vp) {
		Matrix4f mvp = new Matrix4f(vp);
		Matrix4f m = new Matrix4f().translationRotateScale( translation, rotation, new Vector3f(scale) );
		mvp.mul(m);
//		Logger.writeToLog("Initial VP:\n" + vp);
//		Logger.writeToLog("Final VP:\n" + vp);
//		Logger.writeToLog("MVP:\n" + mvp);
//		Logger.writeToLog("M:\n" + m);
		
		return mvp;
	}
	
	public void updateTRS() {
		
	}
	
>>>>>>> a4246be Big update. Changes to testing. Began Terrain implementation.
}
