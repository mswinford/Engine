package com.MyJogl;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.MyJogl.GameObject.GameObject;
import com.MyJogl.Logger.Logger;
import com.jogamp.opengl.GL2;

public class Scene extends GameObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3641979294974138859L;
	private String sceneName;
	private ArrayList<GameObject> objects;
	
	public Scene() {
		objects = new ArrayList<GameObject>();
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
	
	public void add(GameObject object) {
		objects.add(object);
	}
	
	
	public void draw(GL2 gl, Matrix4f vp) {
		for(GameObject object : objects) {
			if(object != null) {
				object.draw(gl, vp);
			}
			else { 
				Logger.writeToLog("null object in scene: " + sceneName);
			}
		}
	}
}
