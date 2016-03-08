package com.MyJogl.GameObject.terrain;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;

import com.MyJogl.Model.Model;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

//Just a simple model that will draw using triangle fans.
//creating and updating the model is left to the QuadTree itself
public class QTModel extends Model {
	
	
	public QTModel() {
		super();
	}
	
	private void init(GL2 gl) {
		gl.glGenVertexArrays(1, buffers, 0);
		gl.glGenBuffers(2, buffers, 1);
		
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 3 * Buffers.SIZEOF_FLOAT, 0);
		gl.glEnableVertexAttribArray(0);
		
		gl.glBindVertexArray(0);
	}

	@Override
	protected void draw(GL2 gl) {
		gl.glDrawArrays(GL.GL_TRIANGLE_FAN, 0, numOfPolygons);		
	}
	
	public void draw(GL2 gl, Matrix4f mvp, FloatBuffer vbo) {
		//bind the VBO data
		gl.glBindVertexArray(buffers[0]);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, buffers[1]);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vbo.capacity()*Buffers.SIZEOF_FLOAT, vbo, GL.GL_STATIC_DRAW);
		
		gl.glBindVertexArray(0);
		
		
		//then draw the model
		draw(gl, mvp);
	}
	
}
