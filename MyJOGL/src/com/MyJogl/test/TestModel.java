package com.MyJogl.test;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;

import com.MyJogl.Logger.Logger;
import com.MyJogl.Model.Model;
import com.MyJogl.Model.RenderMode;
import com.MyJogl.Util.Util;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class TestModel extends Model {

	public TestModel() {
		super();
	}
	
	@Override
	public void load(GL2 gl) {
		//float[] values = { 0.0f, 0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 0.15f, 1.0f, 1.0f, 0.19f, 0.0f};
		float[] values = {0.0f, 0.0f, 0.0f, 1.0f, 0.19f, 0.0f, 1.0f, 0.15f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 0.19f, 0.0f};
		FloatBuffer vbo = Buffers.newDirectFloatBuffer(values);
		numIndices = 6;
		//numIndices = 3;
		int[] indices = { 0, 3, 2, 0, 1, 3 };
		//int[] indices = { 0, 1, 2 };
		IntBuffer ebo = Buffers.newDirectIntBuffer(indices);
		
		//generate VAO, VBO, and EBO buffer IDs
		gl.glGenVertexArrays(1, buffers, 0);
		gl.glGenBuffers(2, buffers, 1);
		Logger.writeToLog("test model buffer IDs: " + buffers);
		
		//bind the VAO
		gl.glBindVertexArray(buffers[0]);
		Logger.writeToLog("GL Error: " + gl.glGetError());
		
		//bind the VBO and copy the data for OpenGL
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, buffers[1]);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vbo.capacity()*Buffers.SIZEOF_FLOAT, vbo, GL.GL_STATIC_DRAW);
		
		//bind the EBO and copy the data for OpenGL
//		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, buffers[2]);
//		gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, ebo.capacity()*Buffers.SIZEOF_INT, ebo, GL.GL_STATIC_DRAW);
		
		//bind the attributes for OpenGL	
		//vertex
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 3 * Buffers.SIZEOF_FLOAT, 0);
		gl.glEnableVertexAttribArray(0);
		
		gl.glBindVertexArray(0);
	}
	
	@Override
	public void draw(GL2 gl, Matrix4f mvp) {
		//set the rendering mode
		if(mode == RenderMode.NORMAL) {
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
		}
		else if(mode == RenderMode.WIREFRAME) {
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_LINE);
		}
		
		//complete the calculation of the MVP matrix and pass it to the OpenGL uniform
		FloatBuffer mvpBuf = Buffers.newDirectFloatBuffer(16);
		mvp.get(mvpBuf);
		Logger.writeToLog( mvp );
		gl.glUniformMatrix4fv(matrixID, 1, false, mvpBuf);
		
		//set the shader
		gl.glUseProgram(shaderID);
		
		//draw the model
		gl.glBindVertexArray(buffers[0]);
		gl.glDrawArrays(GL.GL_TRIANGLES, 0, 2);
//		gl.glDrawElements(GL.GL_TRIANGLES, numIndices, GL.GL_UNSIGNED_INT, 0);
		gl.glBindVertexArray(0);
	}

}
