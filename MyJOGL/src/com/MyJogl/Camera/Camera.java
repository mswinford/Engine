package com.MyJogl.Camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.MyJogl.GameObject.GameObject;
import com.MyJogl.Logger.Logger;

public class Camera extends GameObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8379629951213957611L;
	protected Matrix4f view;
	
	public Camera() {		
		view = new Matrix4f();
	}

	public Matrix4f getView() {
		return view;
	}
	
	public void setView(Matrix4f view) {
		this.view = view;
	}

	public void updateView() {
		Matrix4f cameraTransform = new Matrix4f().translationRotateScale(translation, rotation, new Vector3f(scale, scale, scale));
		Logger.writeToLog("Camera Transform:\n" + cameraTransform);
		view.setLookAt(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f))
		.mul(cameraTransform.invert());
	}
	
}
