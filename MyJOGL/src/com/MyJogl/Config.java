package com.MyJogl;

import java.awt.Dimension;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

public class Config {
	String configFilepath;
	GLProfile glp;
	GLCapabilities caps;
	Dimension windowSize;
	boolean fullscreen;
	boolean vsync;
	int FOV;
	
	public Config() {
		glp = GLProfile.getDefault();
		caps = new GLCapabilities(glp);
	}
	
	
}
