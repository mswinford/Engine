package com.MyJogl.Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.MyJogl.Logger.Logger;
import com.jogamp.opengl.GL2ES2;

public class ShaderUtil {
	
	public static int loadShaders(GL2ES2 gl, String vertexShaderPath, String fragmentShaderPath) {	
		int v = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
		int f = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
		
		int[] results = new int[2];
		
		//read and compile the vertex shader
		Logger.writeToLog("Loading Shader: " + vertexShaderPath);
		BufferedReader brv = null;
		try {
			brv = new BufferedReader(new FileReader(vertexShaderPath));
		} catch (FileNotFoundException e) {
			Logger.writeToLog("Faild to open vertex shader: " + vertexShaderPath);
		}
		String[] vsrc = { "" };
		int[] vlength = new int[1];
		String line;
		try {
			while ((line=brv.readLine()) != null) {
			  vsrc[0] += (line + "\n");
			}
		} catch (IOException e) {
			Logger.writeToLog("Failed to read lines from vertex shader file: " + vertexShaderPath);
		} finally {
			try {
				brv.close();
			} catch (IOException e) {
				Logger.writeToLog("Couldn't close vertex shader: " + vertexShaderPath);
			}
		}
		vlength[0] = vsrc[0].length();
		gl.glShaderSource(v, 1, vsrc, vlength, 0);
		gl.glCompileShader(v);
		
		gl.glGetShaderiv(v, GL2ES2.GL_COMPILE_STATUS, results, 0);
		gl.glGetShaderiv(v, GL2ES2.GL_INFO_LOG_LENGTH, results, 1);
		if (results[1] > 0) {
			byte[] log = new byte[results[1]];
			gl.glGetShaderInfoLog(v, results[1], results, 1, log, 0);
			line = "";
			for (int i=0; i<results[1]; i++) {
				line += (char)log[i];
			}
			Logger.writeToLog(line);
		}
		
		//read and compile the fragment shader
		Logger.writeToLog("Loading Shader: " + fragmentShaderPath);
		BufferedReader brf = null;
		try {
			brf = new BufferedReader(new FileReader(fragmentShaderPath));
		} catch (FileNotFoundException e) {
			Logger.writeToLog("Faild to open fragment shader: " + fragmentShaderPath);
		}
		String[] fsrc = { "" };
		int[] flength = new int[1];
		line = "";
		try {
			while ((line=brf.readLine()) != null) {
			  fsrc[0] += (line + "\n");
			}
		} catch (IOException e) {
			Logger.writeToLog("Failed to read lines from fragment shader file: " + fragmentShaderPath);
		} finally {
			try {
				brf.close();
			} catch (IOException e) {
				Logger.writeToLog("Couldn't close fragment shader: " + fragmentShaderPath);
			}
		}
		flength[0] = fsrc[0].length();
		gl.glShaderSource(f, 1, fsrc, flength, 0);
		gl.glCompileShader(f);
		
		gl.glGetShaderiv(f, GL2ES2.GL_COMPILE_STATUS, results, 0);
		gl.glGetShaderiv(f, GL2ES2.GL_INFO_LOG_LENGTH, results, 1);
		if (results[1] > 0) {
			byte[] log = new byte[results[1]];
			gl.glGetShaderInfoLog(f, results[1], results, 1, log, 0);
			line = "";
			for (int i=0; i<results[1]; i++) {
				line += (char)log[i];
			}
			Logger.writeToLog(line);
		}

		
		
		int shaderProgramID = gl.glCreateProgram();
		gl.glAttachShader(shaderProgramID, v);
		gl.glAttachShader(shaderProgramID, f);
		gl.glLinkProgram(shaderProgramID);
		
		//check the program
		gl.glGetProgramiv(shaderProgramID, GL2ES2.GL_COMPILE_STATUS, results, 0);
		gl.glGetProgramiv(shaderProgramID, GL2ES2.GL_INFO_LOG_LENGTH, results, 1);
		if (results[1] > 0) {
			byte[] log = new byte[results[1]];
			gl.glGetProgramInfoLog(shaderProgramID, results[1], results, 1, log, 0);
			for (int i=0; i<results[1]; i++) {
				System.out.print((char)log[i]);
			}
		}


		gl.glDetachShader(shaderProgramID, v);
		gl.glDetachShader(shaderProgramID, f);
		
		gl.glDeleteShader(v);
		gl.glDeleteShader(f);
		
		return shaderProgramID;
	}
	
}
