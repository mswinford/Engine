package com.MyJogl.Util;

import org.joml.Vector3i;

public class Face {
	private Vector3i vertexIndex;
	private Vector3i uvIndex;
	private Vector3i normalIndex;
	
	private boolean hasUVs;
	private boolean hasNormals;
	
	private int stride;
	
	private Face(Vector3i v, Vector3i u, Vector3i n) {
		this.vertexIndex = v;
		this.uvIndex = u;
		this.normalIndex = n;
		
		if ( uvIndex == null )
			hasUVs = false;
		else {
			hasUVs = true;
			setStride(getStride() + 3);
		}
		
		if( normalIndex == null )
			hasNormals = false;
		else {
			hasNormals = true;
			setStride(getStride() + 3);
		}
	}
	
	public static Face createFace(Vector3i v, Vector3i u, Vector3i n) {
		return new Face(v, u, n);
	}
	public static Face createFace() {
		return new Face(new Vector3i(), new Vector3i(), new Vector3i());
	}
	
	
	public Vector3i getVertexIndex() {
		return vertexIndex;
	}

	public void setVertexIndex(Vector3i vertexIndex) {
		this.vertexIndex = vertexIndex;
	}

	public Vector3i getUVIndex() {
		return uvIndex;
	}

	public void setUVIndex(Vector3i uvIndex) {
		this.uvIndex = uvIndex;
	}

	public Vector3i getNormalIndex() {
		return normalIndex;
	}

	public void setNormalIndex(Vector3i normalIndex) {
		this.normalIndex = normalIndex;
	}

	public boolean hasUVs() {
		return hasUVs;
	}

	public void setHasUVs(boolean hasUVs) {
		this.hasUVs = hasUVs;
	}

	public boolean hasNormals() {
		return hasNormals;
	}

	public void setHasNormals(boolean hasNormals) {
		this.hasNormals = hasNormals;
	}

	public int getStride() {
		return stride;
	}

	public void setStride(int stride) {
		this.stride = stride;
	}
	
	public void setVertexIndex(int i1, int i2, int i3) {
		this.vertexIndex = new Vector3i(i1, i2, i3);
	}
	public void setUVIndex(int i1, int i2, int i3) {
		this.uvIndex = new Vector3i(i1, i2, i3);
	}
	public void setNormalIndex(int i1, int i2, int i3) {
		this.normalIndex = new Vector3i(i1, i2, i3);
	}
	
	
}
