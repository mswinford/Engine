package com.MyJogl.Model;

import java.nio.FloatBuffer;
import org.joml.Matrix4f;

import com.MyJogl.Util.Util;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Model {
	//fields needed to draw the model. These are obtained from the loadObj method.
	protected int[] buffers; //buffer to store the model's VAO, VBO, and EBO in that order
	
	protected int shaderID;
	protected int matrixID;
	protected String modelPath;
	protected RenderMode mode;
	
	protected String name;
	protected int numIndices;
	
	public Model() {
		mode = RenderMode.NORMAL;
		buffers = new int[3];
	}
	
	public void setShaderID(int shaderID) {
		this.shaderID = shaderID;
	}
	public void setMatrixID(int matrixID) {
		this.matrixID = matrixID;
	}
	
	public void load(GL2 gl) {
		numIndices = Util.loadObj(gl, "src/assets/models/pyramid.obj", buffers);
	}
	
	public void draw(GL2 gl, Matrix4f mvp) {		
		//set the rendering mode
		if(mode == RenderMode.NORMAL) {
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
		}
		else if(mode == RenderMode.WIREFRAME) {
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_LINE);
		}
		
		//set the shader
		gl.glUseProgram(shaderID);
		
		//complete the calculation of the MVP matrix and pass it to the OpenGL uniform
		FloatBuffer mvpBuf = Buffers.newDirectFloatBuffer(16);
		mvp.get(mvpBuf);
		gl.glUniformMatrix4fv(matrixID, 1, false, mvpBuf);
		
		//draw the model
		gl.glBindVertexArray(buffers[0]);
		gl.glDrawArrays(GL.GL_TRIANGLES, 0, numIndices);
		//gl.glDrawElements(GL.GL_TRIANGLES, numIndices, GL.GL_UNSIGNED_INT, 0);
		gl.glBindVertexArray(0);		
	}

	public String getModelPath() {
		return modelPath;
	}

	public String getName() {
		return name;
	}
	
	public void setRenderMode(RenderMode mode) {
		this.mode = mode;
	}
}
