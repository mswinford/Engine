package com.MyJogl;

import java.util.ArrayList;

import org.joml.Matrix4f;

import com.jogamp.opengl.GL2;

public class SceneManager {
	private ArrayList<Scene> scenes;
	private Scene scene;
	
	public SceneManager() {
		scene = new Scene();
		
	}
	
	public void drawScene(GL2 gl, Matrix4f vp) {
		scene.draw(gl, vp);
	}
	
	public void initializeScene() {
		
	}

	public void setScene(Scene scene) {
		this.scene = scene;
		
	}
	public Scene getScene() {
		return scene;
	}
	
	public void laodScene(String sceneName) {
		
	}
	
}
