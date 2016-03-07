package com.MyJogl;

import java.util.ArrayList;

import org.joml.Matrix4f;

import com.MyJogl.GameObject.GameObject;
import com.MyJogl.GameObject.Renderable;
import com.jogamp.opengl.GL2;

public class Scene extends GameObject {
	private String sceneName;
	private ArrayList<GameObject> components;
	
	public Scene() {
		components = new ArrayList<GameObject>();
	}
	public Scene(String sceneName) {
		this();
		this.sceneName = sceneName;
	}
	
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	public String getSceneName() {
		return sceneName;
	}
	public ArrayList<GameObject> getComponents() {
		return components;
	}
	
	public void add(GameObject object) {
		components.add(object);
	}
	
	
	public void draw(GL2 gl, Matrix4f vp) {
		for(GameObject object : components) {
			if(object != null && object instanceof Renderable) {
				((Renderable)object).draw(gl, vp);
			}
			else { 
//				Logger.writeToLog("null object in scene: " + sceneName);
			}
		}
	}
}
