package com.MyJogl.GameObject;

import org.joml.Matrix4f;

import com.MyJogl.Model.Model;
import com.jogamp.opengl.GL2;

public interface Renderable {	
	public void setModel(Model model);
	public Model getModel();
	public void draw(GL2 gl, Matrix4f vp);
	public boolean isTransparent();
	public void setTransparent(boolean trans);
}
