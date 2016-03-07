package com.MyJogl.GameObject;

import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;
import com.MyJogl.Model.Model;

public class PlayerCharacter extends Character {

	private Camera camera;
	private float moveSpeed = 0.01f;

	public PlayerCharacter(String name) {
		super(name);
	}

	public PlayerCharacter(Model model) {
		super(model);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void moveForward() {
		camera.translate(new Vector3f(0.0f, 0.0f, moveSpeed));
		camera.updateView();
	}
	
	public void moveBackward() {
		camera.translate(new Vector3f(0.0f, 0.0f, -moveSpeed));
		camera.updateView();
	}
	
	public void strafeLeft() {
		camera.translate(new Vector3f(-moveSpeed, 0.0f, 0.0f));
		camera.updateView();
	}
	
	public void strafeRight() {
		camera.translate(new Vector3f(moveSpeed, 0.0f, 0.0f));
		camera.updateView();
	}
}
