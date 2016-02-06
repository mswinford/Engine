package com.MyJogl.Model;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.MyJogl.Logger.Logger;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Model implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 834637561341814527L;
	private FloatBuffer fb;
	private IntBuffer vertexBuffer;
	private IntBuffer colorBuffer;
	private int matrixID;
	
	private float[] triangle = {-1, -1, 0,
 			1, -1, 0,
 			0,  1, 0,
 			};
	
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
		
		//mvp testing -> why isn't z position causing any change?
//		for(int i=0; i<3; i++) {
//			Vector4f v = new Vector4f(triangle[(i*3)], triangle[(i*3)+1], triangle[(i*3)+2], 1.0f);
//			Logger.writeToLog(v.mul(mvp).toString());
//		}
		
		
		//keep everything after this
		FloatBuffer mvpBuf = Buffers.newDirectFloatBuffer(16);
		mvp.get(mvpBuf);
		gl.glUniformMatrix4fv(matrixID, 1, false, mvpBuf);
		
		
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBuffer.get(0));
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 0, 0);
		
		gl.glEnableVertexAttribArray(1);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, colorBuffer.get(0));
		gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 0, 0);
		
		gl.glDrawArrays(GL.GL_TRIANGLES, 0, 18);
		gl.glDisableVertexAttribArray(0);
		gl.glDisableVertexAttribArray(1);
	}
	
	public void init(GL gl) {		
		fb = Buffers.newDirectFloatBuffer(pyramid);
		vertexBuffer = Buffers.newDirectIntBuffer(1);
		
		gl.glGenBuffers(1, vertexBuffer);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBuffer.get(0));
		gl.glBufferData(GL.GL_ARRAY_BUFFER, fb.capacity()*Buffers.SIZEOF_FLOAT, fb, GL.GL_STATIC_DRAW);
		
		
		fb = Buffers.newDirectFloatBuffer(pyramidColors);
		colorBuffer = Buffers.newDirectIntBuffer(1);
		
		gl.glGenBuffers(1, colorBuffer);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, colorBuffer.get(0));
		gl.glBufferData(GL.GL_ARRAY_BUFFER, fb.capacity()*Buffers.SIZEOF_FLOAT, fb, GL.GL_STATIC_DRAW);
		
	}
}
