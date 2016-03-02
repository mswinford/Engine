package com.MyJogl.VertexData;

import java.util.Hashtable;

public class VertexData {
	protected Hashtable<String, Vertex> attributes;
	int stride = 0;
	
	public VertexData() {
		
	}
	
	public Hashtable<String, Vertex> getAttributes() {
		return attributes;
	}

	public int getStride() {
		return stride;
	}
	
}
