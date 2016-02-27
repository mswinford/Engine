package com.MyJogl.VertexData;

public class Vertex3f implements Vertex {
	final int STRIDE = 3;

	private float x, y, z;
	
	public Vertex3f() {
		this(0.0f);
	}
	public Vertex3f(float val) {
		this(val, val, val);
	}
	public Vertex3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vertex3f(Vertex3f v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}
	
	public int getSTRIDE() {
		return STRIDE;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	@Override
	public int getSize() {
		return STRIDE;
	}
	
	
}
