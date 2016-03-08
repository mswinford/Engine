package com.MyJogl.GameObject.terrain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;
import com.MyJogl.Logger.Logger;
import com.jogamp.opengl.GL2;

public class QuadTree {
	private static int LOD = 10; //the LOD distance
	
	private QuadTree parent;
	private QuadTree[] children; //Q1(bottom left), Q2(bottom right), Q3(top left), Q4(top right)  
	private TerrainVertex north;
	private TerrainVertex south;
	private TerrainVertex east;
	private TerrainVertex west;
	private TerrainVertex center;
	
	public QuadTree(float[][] heights) {
		this(heights, (QuadTree)null, 0, heights.length-1, 0, heights.length-1);
	}

	public QuadTree(float[][] heights, QuadTree parent, int minX, int maxX, int minZ, int maxZ) {
		this.parent = parent;
		
		int halfX = (maxX - minX) / 2;
		int halfZ = (maxZ - minZ) / 2;
		
		float y = 0.0f;
		int x = 0, z = 0;
		
		x = minX + halfX;
		z = minZ + halfZ;
		y = heights[z][x];
		center = new TerrainVertex();
		center.setPosition( new Vector3f(x, y, z) );
		
		//x is the same as the center vertex but z is maxZ
		//x = minX + halfX;
		z = maxZ;
		y = heights[z][x];
		north = new TerrainVertex();
		north.setPosition( new Vector3f(x, y, z) );
		
		//x is the same as the center vertex but z is minZ
		//x = minX + halfX;
		z = minZ;
		y = heights[z][x];
		south = new TerrainVertex();
		south.setPosition( new Vector3f(x, y, z) );
		
		//z is the same as the center vertex but x is maxX
		x = maxX;
		z = minZ + halfZ;
		y = heights[z][x];
		east = new TerrainVertex();
		east.setPosition( new Vector3f(x, y, z) );
		
		//z is the same as the center vertex but x is minX
		x = minX;
		//z = minZ + halfZ;
		y = heights[z][x];
		west = new TerrainVertex();
		west.setPosition( new Vector3f(x, y, z) );
		
		children = null;
		
		if( center.getPosition().distance(north.getPosition()) > 1.0f ) {
			//Create the child nodes. Else this is a leaf node, so don't create children.
			children = new QuadTree[4];
			/* Quadrants:
			 *				Q3	Q4
			 * 				Q1	Q2
			 */
			//Q1
			children[0] = new QuadTree(heights, this, minX, (int)(center.getPosition().x), minZ, (int)(center.getPosition().z));
			//Q2
			children[1] = new QuadTree(heights, this, (int)(center.getPosition().x), maxX, minZ, (int)(center.getPosition().z));
			//Q3
			children[2] = new QuadTree(heights, this, minX, (int)(center.getPosition().x), (int)(center.getPosition().z), maxZ);
			//Q4
			children[3] = new QuadTree(heights, this, (int)(center.getPosition().x), maxX, (int)(center.getPosition().z), maxZ);
		}
		
	}
	
	public int update(Camera camera) {
		int numPolys = 0;
		
		//all base vertices must be enabled or the terrain will be missing whole quadrants at the top level
		if(parent == null) {
			north.setEnabled(true);
			south.setEnabled(true);
			east.setEnabled(true);
			west.setEnabled(true);
		}
		else {
			processVertex(north, camera);
			processVertex(south, camera);
			processVertex(east, camera);
			processVertex(west, camera);
		}
		
		//if any of the side vertices are enabled, then the middle must be enabled too
		if( north.isEnabled() || south.isEnabled() || east.isEnabled() || west.isEnabled() ) {
			center.setEnabled(true);
		}
		else {
			center.setEnabled(false);
		}
		
		//if children is null, then this is a leaf node and there are no children below to update.
		//also return if the center is not enabled because if it isn't enabled then none of the sides are enabled
		if( children != null && center.isEnabled() ) {
			numPolys += 4;
		
			//if the center is enabled, update the child nodes
			numPolys += children[0].update(camera);
			numPolys += children[1].update(camera);
			numPolys += children[2].update(camera);
			numPolys += children[3].update(camera);
			
			//check each child. if the center is enabled, then the sides of this quad that make up the corners of the child must be enabled
			if( children[0].getCenter().isEnabled() ) {
				west.setEnabled(true);
				south.setEnabled(true);
			}
			if( children[1].getCenter().isEnabled() ) {
				east.setEnabled(true);
				south.setEnabled(true);
			}
			if( children[2].getCenter().isEnabled() ) {
				west.setEnabled(true);
				north.setEnabled(true);
			}
			if( children[3].getCenter().isEnabled() ) {
				east.setEnabled(true);
				north.setEnabled(true);
			}
		}
		
		return numPolys;
	}
	
	private void processVertex(TerrainVertex v, Camera c) {
		float dist = c.getTranslation().distance(v.getPosition());
		if( dist >= LOD ) {
			v.setEnabled(false);
		}
		else {
			v.setEnabled(true);
		}
	}
	
	public void render() {
		//render via triangle fans.
		//TRAVERSE THE TREE. Check each child node starting with Q1 and going counter-clockwise.
		//if the child's center is enabled, 
		//then set the starting vertex for the fan to it's most counter-clockwise edge and the ending vertex to the most clockwise edge if it hasn't already been set.
		TerrainVertex start = null;
		TerrainVertex end = null;
		
		if( children[0].getCenter().isEnabled() ) {
			if( end == null ) end = west;
			start = south;
		}
		if( children[1].getCenter().isEnabled() ) {
			if( end == null ) end = south;
			start = east;
		}
		if( children[2].getCenter().isEnabled() ) {
			if( end == null ) end = east;
			start = north;
		}
		if( children[3].getCenter().isEnabled() ) {
			if( end == null ) end = north;
			start = west;
		}
		
		//if the starting vertex is the same as the end vertex then all drawing is handled in the child nodes, so just return;
		if( start == end ) return;
		
		//now render all the child nodes
		children[0].render();
		children[1].render();
		children[2].render();
		children[3].render();
		
		
		
	}

	public QuadTree[] getChildren() {
		return children;
	}

	public void setChildren(QuadTree[] children) {
		this.children = children;
	}

	public TerrainVertex getNorth() {
		return north;
	}

	public void setNorth(TerrainVertex north) {
		this.north = north;
	}

	public TerrainVertex getSouth() {
		return south;
	}

	public void setSouth(TerrainVertex south) {
		this.south = south;
	}

	public TerrainVertex getEast() {
		return east;
	}

	public void setEast(TerrainVertex east) {
		this.east = east;
	}

	public TerrainVertex getWest() {
		return west;
	}

	public void setWest(TerrainVertex west) {
		this.west = west;
	}

	public TerrainVertex getCenter() {
		return center;
	}

	public void setCenter(TerrainVertex center) {
		this.center = center;
	}
}
