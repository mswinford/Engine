package com.MyJogl.Model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;

import com.MyJogl.Logger.Logger;
import com.MyJogl.Util.Util;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class TerrainModel extends Model {
	final int STRIDE = 3;
	FloatBuffer vbo;
	int size;
	
	public TerrainModel() {
		super();
	}
	
	@Override
	public void load(GL2 gl) {
		float[] values = { 0.0f, 0.0f, 0.5f, 0.5f, 0.0f, 0.25f, 0.0f, -0.5f, 0.0f };
		vbo = Buffers.newDirectFloatBuffer(values);
		
		gl.glGenVertexArrays(1, buffers, 0);
		gl.glGenBuffers(2, buffers, 1);
		
		gl.glBindVertexArray(buffers[0]);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, buffers[1]);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vbo.capacity()*Buffers.SIZEOF_FLOAT, vbo, GL.GL_STATIC_DRAW);
		
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, STRIDE * Buffers.SIZEOF_FLOAT, 0);
		gl.glEnableVertexAttribArray(0);
		
		gl.glBindVertexArray(0);
	}
	
	public void load(GL2 gl, FloatBuffer heightmap, int size) {	
		vbo = heightmap;
		vbo.rewind();
//		float[] values = { 0.0f, 0.7019608f, 0.0f, 1.0f, 0.7019608f, 0.0f, 0.0f, 0.7019608f, 1.0f, 1.0f, 0.7019608f, 1.0f };
//		vbo = Buffers.newDirectFloatBuffer(values);
		IntBuffer ebo;
		
		//generate the EBO: size of EBO is number of indices needed.
		/*
		 * Formula for this is:
		 * # of indices = (size-1)^2 quads * 2 triangles/quad * 3 indices/triangle
		 * 
		 * example for 5x5 grid:
		 * # of indices = 4^2 * 2 * 3 = 16 * 2 * 3 = 96 indices
		 * 
		 * loop through the points and add the indices in this order:
		 *                    *    *   *
		 * 					  3   2/6  *
		 * 					 1/4   5   *
		 */
		
		//start EBO
		numIndices = ((size-1) * (size-1)) * 3 * 2;
		ebo = Buffers.newDirectIntBuffer( numIndices );
		int index = 0;
		for(int z=0; z<size-1; z++) {
			for(int x=0; x<size-1; x++) {
				index = (z * size) + x; // index is for the total vertex object. for now this is only 3 values (x, y, z), so we divide by three
				ebo.put( index ); // 1
				ebo.put( index + size + 1 ); // 2
				ebo.put( index + size ); // 3
				ebo.put( index ); // 4
				ebo.put( index + 1 ); // 5
				ebo.put( index + size + 1 ); // 6
			}
		}
		ebo.rewind();
		//end EBO
		
		Logger.writeToLog(Util.toStringIntBuffer(ebo));
		Logger.writeToLog(Util.toStringFloatBuffer(vbo));
		//System.exit(0);
		
		//generate VAO, VBO, and EBO buffer IDs
		gl.glGenVertexArrays(1, buffers, 0);
		gl.glGenBuffers(2, buffers, 1);
		
		//bind the VAO
		gl.glBindVertexArray(buffers[0]);
		
		//bind the VBO and copy the data for OpenGL
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, buffers[1]);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vbo.capacity()*Buffers.SIZEOF_FLOAT, vbo, GL.GL_STATIC_DRAW);
		
		//bind the attributes for OpenGL	
		//vertex
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, STRIDE * Buffers.SIZEOF_FLOAT, 0);
		gl.glEnableVertexAttribArray(0);
		
		//bind the EBO and copy the data for OpenGL
		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, buffers[2]);
		gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, ebo.capacity()*Buffers.SIZEOF_INT, ebo, GL.GL_STATIC_DRAW);
		
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
		
		//set the shader
		gl.glUseProgram(shaderID);
		
		//complete the calculation of the MVP matrix and pass it to the OpenGL uniform
		FloatBuffer mvpBuf = Buffers.newDirectFloatBuffer(16);
		mvp.get(mvpBuf);
		gl.glUniformMatrix4fv(matrixID, 1, false, mvpBuf);
		
		//bind the VAO
		gl.glBindVertexArray(buffers[0]);
		
		//draw the model
//		gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3);
		
		gl.glDrawElements(GL.GL_TRIANGLES, numIndices, GL.GL_UNSIGNED_INT, 0);
		
		//unbind the VAO
		gl.glBindVertexArray(0);
	}
	
	
}
