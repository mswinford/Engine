package com.MyJogl.GameObject;

import org.joml.Matrix4f;

public interface Existable {
	public Matrix4f calcMVP(Matrix4f vp);
}
