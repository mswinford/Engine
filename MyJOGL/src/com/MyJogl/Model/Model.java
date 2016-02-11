package com.MyJogl.Model;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;

import com.MyJogl.Util.Util;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Model implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 834637561341814527L;
	private FloatBuffer fb; // will be removed after obj loading is completed
	
	//fields needed to draw the model. These are obtained from the loadObj method.
	private IntBuffer bufferIDs; //buffer to store the model's OpenGL IDs from 0 to 3 it goes: vertex data, uv data, normal data, indices
	
	
	private int matrixID;
	private String modelPath;
	private RenderMode mode;
	
	private String name;
	
	private float[] pyramid = {
			-1, -1, -1,
 			 1, -1, -1,
 			 0,  1,  0,
 			 1, -1, -1,
 			 1, -1,  1,
 			 0,  1,  0,
 			 1, -1,  1,
 			-1, -1,  1,
 			 0,  1,  0,
 			-1, -1,  1,
 			-1, -1, -1,
 			 0,  1,  0,
 			-1, -1, -1,
 			 1, -1, -1,
 			 1, -1,  1,
 			 1, -1,  1,
 			-1, -1,  1,
 			-1, -1, -1
 			};
	
	private float[] pyramidColors = {
		1.0f, 0.0f, 0.0f,
		1.0f, 0.0f, 0.0f,
		1.0f, 0.0f, 0.0f,
		0.0f, 1.0f, 0.0f,
		0.0f, 1.0f, 0.0f,
		0.0f, 1.0f, 0.0f,
		0.0f, 0.0f, 1.0f,
		0.0f, 0.0f, 1.0f,
		0.0f, 0.0f, 1.0f,
		1.0f, 1.0f, 0.0f,
		1.0f, 1.0f, 0.0f,
		1.0f, 1.0f, 0.0f,
		0.0f, 1.0f, 1.0f,
		0.0f, 1.0f, 1.0f,
		0.0f, 1.0f, 1.0f,
		0.0f, 1.0f, 1.0f,
		0.0f, 1.0f, 1.0f,
		0.0f, 1.0f, 1.0f
	};
	
	private float[] cube = {
		      -1.0f,-1.0f,-1.0f, // triangle 1 : begin
		      -1.0f,-1.0f, 1.0f,
		      -1.0f, 1.0f, 1.0f, // triangle 1 : end
		      1.0f, 1.0f,-1.0f, // triangle 2 : begin
		      -1.0f,-1.0f,-1.0f,
		      -1.0f, 1.0f,-1.0f, // triangle 2 : end
		     1.0f,-1.0f, 1.0f,
		     -1.0f,-1.0f,-1.0f,
		     1.0f,-1.0f,-1.0f,
		     1.0f, 1.0f,-1.0f,
		     1.0f,-1.0f,-1.0f,
		     -1.0f,-1.0f,-1.0f,
		     -1.0f,-1.0f,-1.0f,
		     -1.0f, 1.0f, 1.0f,
		     -1.0f, 1.0f,-1.0f,
		     1.0f,-1.0f, 1.0f,
		     -1.0f,-1.0f, 1.0f,
		     -1.0f,-1.0f,-1.0f,
		     -1.0f, 1.0f, 1.0f,
		     -1.0f,-1.0f, 1.0f,
		     1.0f,-1.0f, 1.0f,
		     1.0f, 1.0f, 1.0f,
		     1.0f,-1.0f,-1.0f,
		     1.0f, 1.0f,-1.0f,
		     1.0f,-1.0f,-1.0f,
		     1.0f, 1.0f, 1.0f,
		     1.0f,-1.0f, 1.0f,
		     1.0f, 1.0f, 1.0f,
		     1.0f, 1.0f,-1.0f,
		     -1.0f, 1.0f,-1.0f,
		     1.0f, 1.0f, 1.0f,
		     -1.0f, 1.0f,-1.0f,
		     -1.0f, 1.0f, 1.0f,
		     1.0f, 1.0f, 1.0f,
		     -1.0f, 1.0f, 1.0f,
		     1.0f,-1.0f, 1.0f
		 };
	
	public Model() {
	}
	
	public void setShaderID(int shaderID) {
	}
	public void setMatrixID(int matrixID) {
		this.matrixID = matrixID;
	}
	
	public void draw(GL2 gl, Matrix4f mvp) {		
		//set the rendering mode
		if(mode == RenderMode.NORMAL) {
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
		}
		else if(mode == RenderMode.WIREFRAME) {
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_LINE);
		}
		
		
		//keep everything after this
		FloatBuffer mvpBuf = Buffers.newDirectFloatBuffer(16);
		mvp.get(mvpBuf);
		gl.glUniformMatrix4fv(matrixID, 1, false, mvpBuf);
		
		
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, bufferIDs.get(0));
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 0, 0);
		
		gl.glEnableVertexAttribArray(1);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, bufferIDs.get(1));
		gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 0, 0);
		
		gl.glDrawArrays(GL.GL_TRIANGLES, 0, 18);
		gl.glDisableVertexAttribArray(0);
		gl.glDisableVertexAttribArray(1);
	}
	
	public void load(GL gl) {
		mode = RenderMode.NORMAL;
		bufferIDs = Buffers.newDirectIntBuffer(4);
		
		Util.loadObj(gl, "src/assets/models/pyramid.obj", bufferIDs);
		
		
		
		fb = Buffers.newDirectFloatBuffer(pyramidColors);
		
		gl.glGenBuffers(1, bufferIDs);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, bufferIDs.get(1));
		gl.glBufferData(GL.GL_ARRAY_BUFFER, fb.capacity()*Buffers.SIZEOF_FLOAT, fb, GL.GL_STATIC_DRAW);
		
	}
	
	private enum RenderMode {
		NORMAL,
		WIREFRAME,
	}

	public String getModelPath() {
		return modelPath;
	}

	public String getName() {
		return name;
	}
}
