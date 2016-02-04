package com.MyJogl.GameObject;

import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;
import com.MyJogl.Model.Model;

public class PlayerCharacter extends Character {

	/**
	 * 
	 */
	private static final long serialVersionUID = 810174614080958148L;
	
	private Camera camera;
	private float moveSpeed = 0.1f;

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
		camera.getView().translate(0.0f, 0.0f, moveSpeed);
		//this.translate(new Vector3f(0.0f, 0.0f, 0.1f));
	}
	
	public void moveBackward() {
		camera.getView().translate(0.0f, 0.0f, -moveSpeed);
	}
	
	public void strafeLeft() {
		camera.getView().translate(-moveSpeed, 0.0f, 0.0f);
	}
	
	public void strafeRight() {
		camera.getView().translate(moveSpeed, 0.0f, 0.0f);
	}
}
