package com.MyJogl.Camera;

import org.joml.Matrix4f;

import com.MyJogl.GameObject.GameObject;
import com.MyJogl.Logger.Logger;

public class Camera extends GameObject{
	protected Matrix4f view;
	protected Matrix4f projection;
	protected Matrix4f vp;
	
	public Camera() {		
		super();
		view = new Matrix4f();
		projection = new Matrix4f();
		vp = new Matrix4f();
		updateView();
	}

	public Matrix4f getView() {
		return view;
	}
	public void setView(Matrix4f view) {
		this.view = view;
		updateView();
	}
	public Matrix4f getProjection() {
		return projection;
	}
	public void setProjection(Matrix4f projection) {
		this.projection = projection;
		updateView();
	}

	public void updateView() {
		view.identity()
		.rotate(rotation)
		.translate(translation)
		.lookAt(0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f)
		;
		
		vp = projection.mul(view, new Matrix4f());
	}
	
	public Matrix4f getVP() {
		return vp;
	}
	
}