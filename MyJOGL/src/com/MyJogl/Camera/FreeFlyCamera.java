package com.MyJogl.Camera;

import org.joml.Vector3f;

public class FreeFlyCamera extends Camera {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5433513312576773245L;
	protected float moveSpeed;

	public FreeFlyCamera(float moveSpeed) {
		super();
		this.moveSpeed = moveSpeed;
		this.view.identity();
	}	

}
