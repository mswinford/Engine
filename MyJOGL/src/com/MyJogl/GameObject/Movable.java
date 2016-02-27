package com.MyJogl.GameObject;

import org.joml.Vector3f;

public interface Movable {	
	public void move(Vector3f move);
	public void setSpeed(float speed);
	public float getSpeed();
}
