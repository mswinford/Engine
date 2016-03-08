package com.MyJogl.GameObject;

import com.MyJogl.Camera.Camera;
import com.jogamp.opengl.GL2;

public interface Renderable {	
	public void draw(GL2 gl, Camera activeView);
}
