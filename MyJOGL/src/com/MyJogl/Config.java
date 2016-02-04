package com.MyJogl;

import java.awt.Dimension;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

public class Config {
	public static String configFilepath;
	public static GLProfile glp;
	public static GLCapabilities caps;
	public static Dimension windowSize = new Dimension(200, 150);
	public static boolean fullscreen = false;
	public static boolean vsync = true;
	public static float FOV = 60.0f;
	public static float aspectRatio = 4.0f/3.0f;
	public static float zNear = 0.01f;
	public static float zFar = 10.0f;
	
	public Config() {
		glp = GLProfile.getDefault();
		caps = new GLCapabilities(glp);
		caps.setRedBits(8);
		caps.setBlueBits(8);
		caps.setGreenBits(8);
		caps.setAlphaBits(8);
	}

	public static void initialize() {
		glp = GLProfile.getDefault();
		caps = new GLCapabilities(glp);
	}
	
	
}
