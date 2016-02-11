package com.MyJogl.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.MyJogl.Logger.Logger;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;

public class Util {

	public static void loadObj(GL gl, String filename, IntBuffer bufferIDs) {
		File file = new File(filename);
		ArrayList<Float> vertices = new ArrayList<Float>();
		ArrayList<Float> uvs = new ArrayList<>();
		ArrayList<Float> normals = new ArrayList<>();
		ArrayList<Short> vertexIndices = new ArrayList<>();
		ArrayList<Short> uvIndices = new ArrayList<>();
		ArrayList<Short> normalIndices = new ArrayList<>();
		
		//boolean hasUVs = false;
		//boolean hasNormals = false;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while( (line = br.readLine()) != null ) {
				String[] parsedLine = line.split(" ");
				
				if( parsedLine[0].equals("#") ) {
					//then line is a comment, so do nothing
				}
				else if( parsedLine[0].equals("v") ) {
					//then line is a vertex, add to vertices
					vertices.add(Float.parseFloat(parsedLine[1]));
					vertices.add(Float.parseFloat(parsedLine[2]));
					vertices.add(Float.parseFloat(parsedLine[3]));
				}
				else if( parsedLine[0].equals("vt") ) {
					//then line is a texture coordinate, add to uvs
					uvs.add(Float.parseFloat(parsedLine[1]));
					uvs.add(Float.parseFloat(parsedLine[2]));
				}
				else if( parsedLine[0].equals("vn") ) {
					//then line is a normal, add to normals
					normals.add(Float.parseFloat(parsedLine[1]));
					normals.add(Float.parseFloat(parsedLine[2]));
					normals.add(Float.parseFloat(parsedLine[3]));
				}
				else if( parsedLine[0].equals("f") ) {
					//If there are no uvs or normals, then we don't have indices for those either
					//if only uv is missing, then normal indices are one position forward in the split string
					
					for( int i=1; i<4; i++) {
						String[] face = parsedLine[i].split("/");
						vertexIndices.add(Short.parseShort(face[0]));
						if( !uvs.isEmpty() ) {
							uvIndices.add(Short.parseShort(face[1]));
						}
						if ( !normals.isEmpty() ) {
							if ( uvs.isEmpty() ) {
								normalIndices.add(Short.parseShort(face[1]));
							} else {
								normalIndices.add(Short.parseShort(face[2]));
							}
						}
					}
					
				}
			}			
		} catch (FileNotFoundException e) {
			Logger.writeToLog(filename + " not found. could not load object");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			Logger.writeToLog("Error occured while reading object file: " + filename);
			System.out.println(e.getMessage());
		} finally {
			
			if( br != null ) {
				try {
					br.close();
				} catch (IOException e) {
					Logger.writeToLog("cannot close object file: " + filename);
					System.out.println(e.getMessage());
				}
			}
		}
				
		//generate and bind the vertex data
		
		gl.glGenBuffers(1, bufferIDs);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, bufferIDs.get(0));
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.size()*Buffers.SIZEOF_FLOAT, Buffers.newDirectFloatBuffer(toPrimitiveFloatArray(vertices)), GL.GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, bufferIDs.get(1));
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.size()*Buffers.SIZEOF_FLOAT, Buffers.newDirectFloatBuffer(toPrimitiveFloatArray(vertices)), GL.GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, bufferIDs.get(2));
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.size()*Buffers.SIZEOF_FLOAT, Buffers.newDirectFloatBuffer(toPrimitiveFloatArray(vertices)), GL.GL_STATIC_DRAW);
		
		
		Logger.writeToLog("object successfully loaded into GPU");
		
	}
	
	public static float[] toPrimitiveFloatArray(List<Float> a) {
		float[] floats = new float[a.size()];
		int count = 0;
		for (Float f : a) {
			floats[count++] = ( f != null ? f : 0.0f );
		}
		
		return floats;
	}
	
	public static short[] toPrimitiveShortArray(List<Short> a) {
		short[] shorts = new short[a.size()];
		int count = 0;
		for (Short f : a) {
			shorts[count++] = ( f != null ? f : 0 );
		}
		
		return shorts;
	}
	
}
