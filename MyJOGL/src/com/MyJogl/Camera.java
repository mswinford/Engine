package com.MyJogl;

import com.jogamp.opengl.util.PMVMatrix;

public class Camera {
	PMVMatrix matrix = new PMVMatrix();
	
	public Camera(float FOV, float aspectRation, float far) {
		//matrix.glMatrixMode(PMVMatrix.GL_PROJECTION);
		//matrix.gluLookAt(eyex, eyey, eyez, centerx, centery, centerz, upx, upy, upz);
		matrix.gluPerspective(FOV, aspectRation, 0.01f, far);
		System.out.println(matrix.glGetMatrixf(PMVMatrix.GL_PROJECTION));
	}
	
	
}
