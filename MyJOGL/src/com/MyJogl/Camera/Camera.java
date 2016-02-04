package com.MyJogl.Camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.MyJogl.Logger.Logger;

public class Camera {
	protected Matrix4f view;
	protected Vector3f eye;
	protected Vector3f center;
	protected Vector3f up;
	
	public Camera() {		
		eye = new Vector3f(0.0f, 0.0f, -10.0f);
		center = new Vector3f(0.0f, 0.0f, 0.0f);
		up = new Vector3f(0.0f, 1.0f, 0.0f);
		view = new Matrix4f().setLookAt(eye, center, up);
		Logger.writeToLog(view.toString());
	}
	
	public Vector3f getEye() {
		return eye;
	}

	public void setEye(Vector3f eye) {
		this.eye = eye;
	}

	public Vector3f getCenter() {
		return center;
	}

	public void setCenter(Vector3f center) {
		this.center = center;
	}

	public Vector3f getUp() {
		return up;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}

	public Matrix4f getView() {
		return view;
	}
	
	public void setView(Matrix4f view) {
		this.view = view;
	}

	public void updateView() {
		view.setLookAt(eye, center, up);
	}
	
}
