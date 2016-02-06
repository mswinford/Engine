package com.MyJogl.Camera;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
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
		Matrix4f camTransform = new Matrix4f().translationRotateScale(translation, rotation, new Vector3f(scale, scale, scale));
		view.mul(camTransform.invert());
	}
	
}
