package com.MyJogl.GameObject;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public abstract class GameObject {
	protected String name;
	protected Vector3f scale;
	protected Vector3f translation;
	protected Quaternionf rotation;
	protected ArrayList<GameObject> components;
	
	
	public GameObject() {
		name = "";
		
		scale = new Vector3f(1.0f);
		translation = new Vector3f();
		rotation = new Quaternionf();
		
		components = new ArrayList<GameObject>();
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
	public void setScale(Vector3f scale) {
		if( scale.x <= 0.0f ) {
			System.out.println("X scale is 0 or lower");
			return;
		}
		if( scale.y <= 0.0f ) {
			System.out.println("Y scale is 0 or lower");
			return;
		}
		if( scale.z <= 0.0f ) {
			System.out.println("Z scale is 0 or lower");
			return;
		}
		
		this.scale = scale;
	}
	public Vector3f getScale() {
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
		this.scale.mul(scale);
	}
	public void scale(Vector3f scale) {
		this.scale.mul(scale);
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
	
}
