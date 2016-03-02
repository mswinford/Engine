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
	FloatBuffer VBO;
	int size;
	
	public TerrainModel() {
		super();
	}
	
	public void load(GL2 gl, FloatBuffer heightmap, int size) {	
		VBO = heightmap;
		IntBuffer EBO;
		
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
		EBO = Buffers.newDirectIntBuffer( numIndices );
		int index = 0;
		for(int z=0; z<size-1; z++) {
			for(int x=0; x<size-1; x++) {
				index = (z * size) + x; // index is for the total vertex object. for now this is only 3 values (x, y, z), so we divide by three
				EBO.put( index ); // 1
				EBO.put( index + size + 1 ); // 2
				EBO.put( index + size ); // 3
				EBO.put( index ); // 4
				EBO.put( index + 1 ); // 5
				EBO.put( index + size + 1 ); // 6
			}
		}
		//end EBO
		
		Logger.writeToLog(Util.toStringIntBuffer(EBO));
		Logger.writeToLog(Util.toStringFloatBuffer(VBO));
		//System.exit(0);
		
		//generate VAO, VBO, and EBO buffer IDs
		gl.glGenVertexArrays(1, buffers, 0);
		gl.glGenBuffers(2, buffers, 1);
		
		//bind the VAO
		gl.glBindVertexArray(buffers[0]);
		
		//bind the VBO and copy the data for OpenGL
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, buffers[1]);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, VBO.capacity()*Buffers.SIZEOF_FLOAT, VBO, GL.GL_STATIC_DRAW);
		
		//bind the attributes for OpenGL	
		//vertex
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, STRIDE * Buffers.SIZEOF_FLOAT, 0);
		gl.glEnableVertexAttribArray(0);
		
		//bind the EBO and copy the data for OpenGL
		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, buffers[2]);
		gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, EBO.capacity()*Buffers.SIZEOF_INT, EBO, GL.GL_STATIC_DRAW);
		
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
		gl.glUniformMatrix4fv(matrixID, 1, false, mvpBuf);
		
		//set the shader
		gl.glUseProgram(shaderID);
		
		//draw the model
		gl.glBindVertexArray(buffers[0]);
//		gl.glDrawArrays(GL.GL_POINTS, 0, size*size);
		gl.glDrawElements(GL.GL_TRIANGLES, 4, GL.GL_UNSIGNED_INT, 0);
		gl.glBindVertexArray(0);
	}
	
	
}
