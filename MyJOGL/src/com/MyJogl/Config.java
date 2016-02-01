package com.MyJogl;

import java.awt.Dimension;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

public class Config {
	public static String configFilepath;
	public static GLProfile glp;
	public static GLCapabilities caps;
	public static Dimension windowSize;
	public static boolean fullscreen;
	public static boolean vsync;
	public static float FOV = 60;
	public static float aspectRatio = 16.0f/9.0f;
	public static float drawDistance = 1000.0f;
	
	public Config() {
		glp = GLProfile.getDefault();
		caps = new GLCapabilities(glp);
	}
	
	
}
