package com.MyJogl.GameObject;

import com.MyJogl.Camera.Camera;
import com.MyJogl.Model.Model;
import com.jogamp.opengl.GL2;

public interface Renderable {	
	public Model getModel();
	public void setModel(Model model);
	public void draw(GL2 gl, Camera activeView);
}
