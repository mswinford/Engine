package com.MyJogl;

import java.util.ArrayList;

import org.joml.Matrix4f;

import com.MyJogl.GameObject.GameObject;
import com.jogamp.opengl.GL2;

public class Scene {
	private String sceneName;
	private ArrayList<GameObject> objects;
	
	public Scene() {
		objects = new ArrayList<GameObject>();
	}
	
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	public String getSceneName() {
		return sceneName;
	}
	
	public void add(GameObject object) {
		objects.add(object);
	}
	
	public void draw(GL2 gl, Matrix4f vp) {
		for(GameObject object : objects) {
			object.draw(gl, vp);
		}
	}
}
