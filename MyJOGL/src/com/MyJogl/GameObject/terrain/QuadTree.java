package com.MyJogl.GameObject.terrain;

import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;

public class QuadTree {
	private QuadTree[] children; //Q1(bottom left), Q2(bottom right), Q3(top left), Q4(top right)  
	private Vector3f north;
	private Vector3f south;
	private Vector3f east;
	private Vector3f west;
	private Vector3f center;
	private boolean[] enabled; //North, South, East, West, Center
	
	private static int traingles;
	
	public QuadTree(int size) {
		this(size, 0, 0);
	}
	
	public QuadTree(int size, int xOffset, int zOffset) {		
		north = new Vector3f( size/2 + xOffset, 0.0f, size + zOffset );
		south = new Vector3f( size/2 + xOffset, 0.0f, 0.0f );
		east = new Vector3f( size + xOffset, 0.0f, size/2 + zOffset );
		west = new Vector3f( 0.0f, 0.0f, size/2 + zOffset );
		center = new Vector3f( size/2 + xOffset, 0.0f, size/2 + zOffset );
		
		enabled = new boolean[5];
		for( boolean b : enabled ) {
			b = true;
		}
		
		children = new QuadTree[4];
		
		boolean stop = (size <= 50) ? true : false;
		children[0] = stop ? null : new QuadTree(size/2, xOffset, zOffset);
		children[1] = stop ? null : new QuadTree(size/2, size/2 + xOffset, zOffset);
		children[2] = stop ? null : new QuadTree(size/2, xOffset, size/2 + zOffset);
		children[3] = stop ? null : new QuadTree(size/2, size/2 + xOffset, size/2 + zOffset);
		
	}
	
	public void update(Camera camera) {
		Vector3f cPos = camera.getTranslation();
		float dist = Float.MAX_VALUE;
		//south
		dist = cPos.distance(south);
		updateVertex(dist, 1);
		//west
		dist = cPos.distance(south);
		updateVertex(dist, 1);
		//north
		dist = cPos.distance(south);
		updateVertex(dist, 1);
		//east
		dist = cPos.distance(south);
		updateVertex(dist, 1);
		//center
		dist = cPos.distance(south);
		updateVertex(dist, 1);
		
	}
	
	public void draw() {
		
	}
	
	private void updateVertex(float dist, int index) {
		if ( dist >= 30.0f ) {
			enabled[1] = false;
		}
	}
}
