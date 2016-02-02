package com.MyJogl.Model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Model {
	private FloatBuffer fb;
	private IntBuffer vertexBuffer;
	private int shaderID;
	private int matrixID;
	
	private float[] triangle = {-1, -1, 0,
 			1, -1, 0,
 			0,  1, 0,
 			};
	
	private float[] square = {
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
		this.shaderID = shaderID;
	}
	public void setMatrixID(int matrixID) {
		this.matrixID = matrixID;
	}
	
	public void draw(GL2 gl, Matrix4f mvp) {
		FloatBuffer mvpBuf = Buffers.newDirectFloatBuffer(16);
		mvp.get(mvpBuf);
		gl.glUniformMatrix4fv(matrixID, 1, false, mvpBuf);
		
		
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBuffer.get(0));
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 0, 0);
		
		gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3);
		gl.glDisableVertexAttribArray(0);
	}
	
	public void init(GL gl) {		
		fb = Buffers.newDirectFloatBuffer(triangle);
		vertexBuffer = Buffers.newDirectIntBuffer(1);
		
		gl.glGenBuffers(1, vertexBuffer);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBuffer.get(0));
		gl.glBufferData(GL.GL_ARRAY_BUFFER, fb.capacity()*Buffers.SIZEOF_FLOAT, fb, GL.GL_STATIC_DRAW);
	}
}
