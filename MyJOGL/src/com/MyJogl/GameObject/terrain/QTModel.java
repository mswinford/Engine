package com.MyJogl.GameObject.terrain;

import com.MyJogl.Model.Model;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

//Just a simple model that will draw using triangle fans.
//for the traingle fans we need to use a primitive restart value
//creating and updating the model is left to the QuadTree itself
public class QTModel extends Model {
	private int[] first;
	private int[] count;
	
	public int[] getFirst() {
		return first;
	}

	public void setFirst(int[] first) {
		this.first = first;
	}

	public int[] getCount() {
		return count;
	}

	public void setCount(int[] count) {
		this.count = count;
	}

	public QTModel() {
		super();
	}
	
	public void initialize(GL2 gl) {
		gl.glGenVertexArrays(1, buffers, 0);
		gl.glGenBuffers(2, buffers, 1);
		
		gl.glBindVertexArray(buffers[0]);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, buffers[1]);
		
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 3 * Buffers.SIZEOF_FLOAT, 0);
		gl.glEnableVertexAttribArray(0);
		
		gl.glBindVertexArray(0);
	}

	@Override
	protected void draw(GL2 gl) {
		vbo.rewind();
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vbo.capacity() * Buffers.SIZEOF_FLOAT, vbo, GL.GL_DYNAMIC_DRAW);
		
		gl.glMultiDrawArrays(GL.GL_TRIANGLE_FAN, first, 0, count, 0, count.length);
	}
	
}
