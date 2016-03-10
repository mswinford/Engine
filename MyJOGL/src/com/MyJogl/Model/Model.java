package com.MyJogl.Model;

import java.nio.FloatBuffer;
import org.joml.Matrix4f;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Model {
	//fields needed to draw the model. These are obtained from the loadObj method.
	protected int[] buffers; //buffer to store the model's VAO, VBO, and EBO in that order
	protected FloatBuffer vbo;
	
	protected int shaderID;
	protected int matrixID;
	protected RenderMode mode;
	
	protected String name;
	protected int numOfPolygons;
	
	public Model() {
		mode = RenderMode.NORMAL;
		buffers = new int[3];
	}
	
	public Model(String name) {
		this();
		this.name = name;
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
		gl.glBindVertexArray(buffers[0]);
		
		draw(gl);
		
		gl.glBindVertexArray(0);		
	}

	protected void draw(GL2 gl) {
		//draw the model
		gl.glDrawArrays(GL.GL_TRIANGLES, 0, numOfPolygons);
		//gl.glDrawElements(GL.GL_TRIANGLES, numIndices, GL.GL_UNSIGNED_INT, 0);
	}
	
	public void setShaderID(int shaderID) {
		this.shaderID = shaderID;
	}
	
	public void setMatrixID(int matrixID) {
		this.matrixID = matrixID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setRenderMode(RenderMode mode) {
		this.mode = mode;
	}
	
	public FloatBuffer getVBO() {
		return vbo;
	}
	
	public void setVBO(FloatBuffer vbo) {
		this.vbo = vbo;
	}

	public RenderMode getMode() {
		return mode;
	}

	public void setMode(RenderMode mode) {
		this.mode = mode;
	}

	public int getNumOfPolygons() {
		return numOfPolygons;
	}

	public void setNumOfPolygons(int numOfPolygons) {
		this.numOfPolygons = numOfPolygons;
	}

	public int getShaderID() {
		return shaderID;
	}

	public int getMatrixID() {
		return matrixID;
	}

	public void setName(String name) {
		this.name = name;
	}
}
