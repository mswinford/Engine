package com.MyJogl.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3i;

import com.MyJogl.Logger.Logger;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Util {

	public static int loadObj(GL2 gl, String filename, int[] buffers) {	
		//VertexData is an object that tells the about what attributes are being loaded
		File file = new File(filename);
		boolean[] atts = { false, false, false }; //currently 4 attributes: vertex, texCoord, normal, and Color. Vertex must be present, assume the others are missing.
		ArrayList<Float> VBO = new ArrayList<Float>();
		ArrayList<Integer> EBO = new ArrayList<>();
		
		ArrayList<Face> faces = new ArrayList<>();
		
		int vCount = 0; //no offset for the vertices because they are always at the beginning
		int uvOffset = 0, uvCount = 0;
		int nOffset = 0, nCount = 0;
		int stride = 0;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while( (line = br.readLine()) != null ) {
				line = line.trim();
				String[] parsedLine = line.split(" ");
				
				if( parsedLine.length == 0 ){
					//do nothing for blank lines.
				}		
				else if( parsedLine[0].equals("v") ) {
					//then line is a vertex, add to vertices
					if (vCount == 0) {
						stride += parsedLine.length - 1;
						uvOffset += parsedLine.length - 1;
						nOffset += parsedLine.length - 1;
					}
					for( int i=0; i<parsedLine.length - 1; i++) {
						VBO.add( (vCount * stride) + i, Float.parseFloat(parsedLine[i+1]));
					}
					vCount++;
				}
				else if( parsedLine[0].equals("vt") ) {
					//then line is a texture coordinate, add to uvs
					if (uvCount == 0) {
						atts[0] = true;
						stride += parsedLine.length - 1;
						nOffset += parsedLine.length - 1;
					}
					for( int i=0; i<parsedLine.length - 1; i++) {
						VBO.add( (uvCount * stride) + uvOffset + i, Float.parseFloat(parsedLine[i+1]));
					}
					uvCount++;
				}
				else if( parsedLine[0].equals("vn") ) {
					//then line is a normal, add to normals
					if (nCount == 0) {
						atts[1] = true;
						stride += parsedLine.length - 1;
					}
					for( int i=0; i<parsedLine.length - 1; i++) {
						VBO.add( (nCount * stride) + nOffset + i, Float.parseFloat(parsedLine[i+1]));
					}
					nCount++;
				}
				else if( parsedLine[0].equals("f") ) {
					//If there are no uvs or normals, then we don't have indices for those either
					//if only uv is missing, then normal indices are one position forward in the split string
					processFace ( parsedLine, faces, atts[1], atts[2] );
				}
				else {
					//do nothing for any other types of lines
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
		
		populateEBO(EBO, faces);
		//add indices to the EBO
//		for( Face face : faces) {
//			EBO.add(face.getVertexIndex().x);
//			EBO.add(face.getVertexIndex().y);
//			EBO.add(face.getVertexIndex().z);
//			EBO.add(face.getUVIndex().x);
//			EBO.add(face.getUVIndex().y);
//			EBO.add(face.getUVIndex().z);
//		}
		
		Logger.writeToLog(VBO);
		Logger.writeToLog(EBO);
		
				
		//generate VAO, VBO, and EBO buffer IDs
		gl.glGenVertexArrays(1, buffers, 0);
		gl.glGenBuffers(2, buffers, 1);
		
		//bind the VAO
		gl.glBindVertexArray(buffers[0]);
		
		//bind the VBO and copy the data for OpenGL
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, buffers[1]);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, VBO.size()*Buffers.SIZEOF_FLOAT, Buffers.newDirectFloatBuffer(toPrimitiveFloatArray(VBO)), GL.GL_STATIC_DRAW);
		
		//bind the attributes for OpenGL	
		
		//vertex
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, stride * Buffers.SIZEOF_FLOAT, 0);
		gl.glEnableVertexAttribArray(0);
		//texture
		if(atts[0]) {
			gl.glVertexAttribPointer(1, 2, GL.GL_FLOAT, false, stride * Buffers.SIZEOF_FLOAT, uvOffset * Buffers.SIZEOF_FLOAT);
			gl.glEnableVertexAttribArray(1);
		}
		//normal
		if(atts[1]) {
			gl.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, stride * Buffers.SIZEOF_FLOAT, nOffset * Buffers.SIZEOF_FLOAT);
			gl.glEnableVertexAttribArray(2);
		}
		
		//bind the EBO and copy the data for OpenGL
		//gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, buffers[2]);
		//gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, EBO.size()*Buffers.SIZEOF_INT, Buffers.newDirectIntBuffer(toPrimitiveIntArray(EBO)), GL.GL_STATIC_DRAW);
		
		gl.glBindVertexArray(0);
		
		Logger.writeToLog("object successfully loaded into GPU");
		
		return vCount;
	}

	private static void populateEBO(ArrayList<Integer> EBO, ArrayList<Face> faces) {
		
		
	}

	private static void processFace(String[] line, ArrayList<Face> faces, boolean hasUVs, boolean hasNormals ) {
		String[] i1 = line[1].split("/");
		String[] i2 = line[2].split("/");
		String[] i3 = line[3].split("/");
		
		Vector3i v = null;
		Vector3i u = null;
		Vector3i n = null;
		
		Logger.writeToLog(hasUVs);
		Logger.writeToLog(hasNormals);
		
		v = new Vector3i(Integer.parseInt(i1[0])-1, Integer.parseInt(i2[0])-1, Integer.parseInt(i3[0])-1);
		if ( hasUVs ) {
			u = new Vector3i(Integer.parseInt(i1[1])-1, Integer.parseInt(i2[1])-1, Integer.parseInt(i3[1])-1);
		}
		if ( hasNormals ) {
			if( !hasUVs ) 
				n = new Vector3i(Integer.parseInt(i1[1])-1, Integer.parseInt(i2[1])-1, Integer.parseInt(i3[1])-1);
			else
				n = new Vector3i(Integer.parseInt(i1[2])-1, Integer.parseInt(i2[2])-1, Integer.parseInt(i3[2])-1);
		}
		
		Face face = Face.createFace(v, u, n);
		faces.add(face);
		
		Logger.writeToLog(face.getVertexIndex());
//		Logger.writeToLog(face.getUVIndex());
//		Logger.writeToLog(face.getNormalIndex());
	}

	public static float[] toPrimitiveFloatArray(List<Float> a) {
		float[] floats = new float[a.size()];
		int count = 0;
		for (Float f : a) {
			floats[count++] = ( f != null ? f : 0.0f );
		}
		
		return floats;
	}
	
	public static int[] toPrimitiveIntArray(List<Integer> a) {
		int[] ints = new int[a.size()];
		int count = 0;
		for (Integer i : a) {
			ints[count++] = ( i != null ? i : 0 );
		}
		
		return ints;
	}
	
	public static <E> void printList(List<E> a) {
		System.out.println(a.toString());
	}
	
	public static float clamp(float value, float min, float max) {
		return 0.0f;
	}

	public static String toStringIntBuffer(IntBuffer buf) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		sb.append(buf.get(0));
		for( int i=1; i<buf.capacity(); i++) {
			sb.append(", " + buf.get(i));
		}
		sb.append(" ]");
		
		return sb.toString();
	}

	public static Object toStringFloatBuffer(FloatBuffer buf) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		sb.append(buf.get(0));
		for( int i=1; i<buf.capacity(); i++) {
			sb.append(", " + buf.get(i));
		}
		
		sb.append(" ]");
		
		return sb.toString();
	}
	
	public static String toString2DFloatArray(float[][] array) {
		StringBuilder sb = new StringBuilder();
		sb.append("2D Array Start:\n");
		for (float[] sub : array) {
			sb.append("[ ");
			for (float f : sub) {
				sb.append(f).append(", ");
			}
			sb.append("]\n");
		}
		sb.append("2D Array End");
		return sb.toString();
	}
}
