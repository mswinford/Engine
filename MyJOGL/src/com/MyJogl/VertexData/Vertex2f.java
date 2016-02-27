package com.MyJogl.VertexData;

public class Vertex2f implements Vertex {
	final int STRIDE = 2;
	
	private float x, y;
	
	public Vertex2f() {
		this(0.0f);
	}
	public Vertex2f(float val) {
		this(val, val);
	}
	public Vertex2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public Vertex2f(Vertex2f v) {
		this.x = v.getX();
		this.y = v.getY();
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

	public int getSTRIDE() {
		return STRIDE;
	}
	@Override
	public int getSize() {
		return STRIDE;
	}
	
	
}
