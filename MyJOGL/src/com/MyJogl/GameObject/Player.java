package com.MyJogl.GameObject;

import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;
import com.MyJogl.Logger.Logger;

public class Player extends Character {

	private Camera camera;
	private float moveSpeed = 0.1f;

	public Player(String name) {
		super(name);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void moveForward() {
		this.translate(new Vector3f(0.0f, 0.0f, moveSpeed));
		camera.translate(new Vector3f(0.0f, 0.0f, moveSpeed));
		camera.updateView();
	}
	
	public void moveBackward() {
		this.translate(new Vector3f(0.0f, 0.0f, -moveSpeed));
		camera.translate(new Vector3f(0.0f, 0.0f, -moveSpeed));
		camera.updateView();
	}
	
	public void strafeLeft() {
		this.translate(new Vector3f(moveSpeed, 0.0f, 0.0f));
		camera.translate(new Vector3f(moveSpeed, 0.0f, 0.0f));
		camera.updateView();
	}
	
	public void strafeRight() {
		this.translate(new Vector3f(-moveSpeed, 0.0f, 0.0f));
		camera.translate(new Vector3f(-moveSpeed, 0.0f, 0.0f));
		camera.updateView();
	}
	
	public void moveUp() {
		this.translate(new Vector3f(0.0f, -moveSpeed, 0.0f));
		camera.translate(new Vector3f(0.0f, -moveSpeed, 0.0f));
		camera.updateView();
	}
	
	public void moveDown() {
		this.translate(new Vector3f(0.0f, moveSpeed, 0.0f));
		camera.translate(new Vector3f(0.0f, moveSpeed, 0.0f));
		camera.updateView();
	}
	
	public void lookLeft() {
		float[] rot = {0.0f, -0.05f, 0.0f};
		this.rotate( rot );
		camera.rotate( rot );
		camera.updateView();
	}
	
	public void lookRight() {
		float[] rot = {0.0f, 0.05f, 0.0f};
		this.rotate( rot );
		camera.rotate( rot );
		camera.updateView();
	}
	
	
}
