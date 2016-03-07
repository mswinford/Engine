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
	private static int LOD = 50; //the LOD distance
	private static int triangles = 0;
	
	private QuadTree parent;
	private QuadTree[] children; //Q1(bottom left), Q2(bottom right), Q3(top left), Q4(top right)  
	private TerrainVertex north;
	private TerrainVertex south;
	private TerrainVertex east;
	private TerrainVertex west;
	private TerrainVertex center;
	
	public QuadTree(int size, float[][] heightmap) {
		this(size, (QuadTree)null);
	}

	public QuadTree(int size, QuadTree parent) {
		this.parent = parent;
		
		int xOffset = 0;
		int zOffset = 0;
		
		if(parent != null) {
			Vector3f offset = parent.getNorth().getPosition().add(parent.getEast().getPosition(), new Vector3f());
			xOffset = (int)(offset.x);
			zOffset = (int)(offset.z);
		}
		
		center = new TerrainVertex();
		center.setPosition( new Vector3f(xOffset, 0.0f, zOffset) );
		
		north = new TerrainVertex();
		north.setPosition( new Vector3f(xOffset, 0.0f, zOffset + size/2) );
		
		south = new TerrainVertex();
		south.setPosition( new Vector3f(xOffset, 0.0f, zOffset - size/2) );
		
		east = new TerrainVertex();
		east.setPosition( new Vector3f(xOffset + size/2, 0.0f, zOffset) );
		
		west = new TerrainVertex();
		west.setPosition( new Vector3f(xOffset - size/2, 0.0f, zOffset) );
		
		
		
		children = new QuadTree[4];
		children[0] = null;
		children[1] = null;
		children[2] = null;
		children[3] = null;
	}
	
	public static void loadHeights(int size, String filepath) {		
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		try {
			image = ImageIO.read(new File(filepath));
		} catch (IOException e) {
			Logger.writeToLog("Failed to read heightmap image from file: " + filepath);
		}
		
		float pos = 1.0f / (float)(size-1) ;
		pos = 1.0f;
		for( z=0; z<size; z++) {
			for( x=0; x<size; x++) {
				Color color = new Color(image.getRGB( (int)x, size-1-(int)z));
//				y = clamp(color.getRed()) - 0.5f;
				y = color.getRed();
				int index = ((int)z * size * 3) + ((int)x * 3);
				heightmap.put( index++, x * pos );
				heightmap.put( index++, y- 128);
				heightmap.put( index, z * pos);
			}
		}
		Logger.writeToLog(heightmap.capacity()-1);
		
		
	}
	
	public void update(Camera camera) {
		processVertex(north, camera);
		processVertex(south, camera);
		processVertex(east, camera);
		processVertex(west, camera);
		
		//if any of the side vertices are enabled, then the middle must be enabled too
		if( north.isEnabled() || south.isEnabled() || east.isEnabled() || west.isEnabled() ) {
			center.setEnabled(true);
		}
		else {
			center.setEnabled(false);
		}
		
		//if the center is enabled, then generate the child nodes and update them.
		if( center.isEnabled() ) {
			children[0] = new QuadTree(size, xOffset, zOffset);
		}
		
		//check each child. if the center is enabled, then the sides of this quad that make up the corners of the child must be enabled
		
		
	}
	
	private void processVertex(TerrainVertex v, Camera c) {
		float dist = c.getTranslation().distance(v.getPosition());
		if( dist >= LOD ) {
			v.setEnabled(false);
		}
	}
	
	public void draw(GL2 gl) {
		
		
		Logger.writeToLog(triangles);
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
