package com.MyJogl.Util;

import org.joml.Matrix4f;

public class MatrixUtil {

	public static Matrix4f perspective(float  fov, float aspect, float near, float far, boolean leftHanded) {
		Matrix4f mat = new Matrix4f(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
		if( fov <= 0 || aspect == 0 ) {
			assert(fov > 0 && aspect != 0);
			return null;
		}
		
//		float depth = far - near;
		float depthInverse = 1.0f / (far - near);
		
		mat.m11 = 1.0f / (float)Math.tan(0.5d * (double)fov);
		mat.m00 = (leftHanded ? 1.0f : -1.0f) * mat.m11 / aspect;
		mat.m22 = far * depthInverse;
		mat.m32 = (-far * near) * depthInverse;
		mat.m23 = 1.0f;
		mat.m33 = 0;
		
		return mat;
	}
	
}
