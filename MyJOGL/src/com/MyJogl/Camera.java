package com.MyJogl;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	Matrix4f projection;
	Matrix4f view;
	Vector3f position;
	Vector3f center;
	Vector3f up;
	
	public Camera() {
		projection = new Matrix4f().ortho(-100, 100, -100, 100, 0.1f, 100);//.perspective((float)Math.toRadians(Config.FOV), Config.aspectRatio, 0.1f, Config.drawDistance);
		position = new Vector3f(0.0f, 0.0f, -1.0f);
		center = new Vector3f(0.0f, 0.0f, 0.0f);
		up = new Vector3f(0.0f, 1.0f, 0.0f);
		view = new Matrix4f().lookAt(position, center, up);
	}
	
	public Matrix4f getPerspective() {
		return projection;
	}
	public void setPerspective(Matrix4f perspective) {
		this.projection = perspective;
	}
	public Matrix4f getView() {
		return view;
	}
	public void setView(Matrix4f view) {
		this.view = view;
	}

	public Matrix4f getVP() {
		return projection.mul(view);
		
	}
	
	
	
}
