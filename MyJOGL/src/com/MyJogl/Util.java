package com.MyJogl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.math.Matrix4;

public class Util {

	public static int loadShaders(GL2ES2 gl, String vertexShaderPath, String fragmentShaderPath) {
		int v = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
		int f = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
		
		int[] results = new int[2];
		
		//read and compile the vertex shader
		BufferedReader brv = null;
		try {
			brv = new BufferedReader(new FileReader(vertexShaderPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] vsrc = { "" };
		int[] vlength = new int[1];
		String line;
		try {
			while ((line=brv.readLine()) != null) {
			  vsrc[0] += (line + "\n");
			}
			System.out.println(vsrc[0]);
			brv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vlength[0] = vsrc[0].length();
		gl.glShaderSource(v, 1, vsrc, vlength, 0);
		gl.glCompileShader(v);
		
		gl.glGetShaderiv(v, GL2ES2.GL_COMPILE_STATUS, results, 0);
		gl.glGetShaderiv(v, GL2ES2.GL_INFO_LOG_LENGTH, results, 1);
		if (results[1] > 0) {
			byte[] log = new byte[results[1]];
			gl.glGetShaderInfoLog(v, results[1], results, 1, log, 0);
			for (int i=0; i<results[1]; i++) {
				System.out.print((char)log[i]);
			}
		}
		
		//read and compile the fragment shader
		BufferedReader brf = null;
		try {
			brf = new BufferedReader(new FileReader(fragmentShaderPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] fsrc = { "" };
		int[] flength = new int[1];
		line = "";
		try {
			while ((line=brf.readLine()) != null) {
			  fsrc[0] += (line + "\n");
			}
			brf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		flength[0] = fsrc[0].length();
		gl.glShaderSource(f, 1, fsrc, flength, 0);
		gl.glCompileShader(f);
		
		gl.glGetShaderiv(f, GL2ES2.GL_COMPILE_STATUS, results, 0);
		gl.glGetShaderiv(f, GL2ES2.GL_INFO_LOG_LENGTH, results, 1);
		if (results[1] > 0) {
			byte[] log = new byte[results[1]];
			gl.glGetShaderInfoLog(f, results[1], results, 1, log, 0);
			for (int i=0; i<results[1]; i++) {
				System.out.print((char)log[i]);
			}
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
