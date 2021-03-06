package com.MyJogl.GameObject.terrain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.MyJogl.Camera.Camera;
import com.MyJogl.GameObject.GameObject;
import com.MyJogl.GameObject.Renderable;
import com.MyJogl.Logger.Logger;
import com.MyJogl.Model.Model;
import com.MyJogl.Util.Util;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;

public class TerrainQT extends GameObject implements Renderable{
	private float[][] heights;
	private QuadTree tree;
	private QTModel model;
	
	
	public TerrainQT() {
		heights = null;
		tree = null;
		model = new QTModel();
	}
	
	public TerrainQT(int size) {
		//create a generic quadtree backed terrain of the given size. Terrain will be a flat square.
		this();
		heights = generateFlatTerrain(size + 1);
		tree = new QuadTree(heights);
	}
	
	public TerrainQT(String filepath) {
		//create a generic quadtree backed terrain of the given size. Terrain will be a flat square.
		this();
		tree = new QuadTree(loadHeights(filepath));
	}
	
	public static float[][] generateFlatTerrain(int size) {
		float[][] heights = new float[size][size];
		for( int z=0; z<size; z++ ) {
			for( int x=0; x<size; x++ ) {
				heights[z][x] = 0.0f;
			}
		}
		return heights;
	}
	
	public static float[][] loadHeights(String filepath) {
		int size;
		float[][] heights;
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filepath));
		} catch (IOException e) {
			Logger.writeToLog("Failed to read heightmap image from file: " + filepath);
		}
		
		size = image.getWidth();
		heights = new float[size][size];
		Logger.writeToLog("heights size", heights.length + " x " + heights[0].length);
		
		//ImageIO reads in the image from top left to bottom right and values go by column then row
		/*
		 * e.g. image storage
		 * 
		 * 			1	2	3
		 * 			4	5	6
		 * 			7	8	9
		 * 
		 * image[0][0] = heights[size][0]
		 * 
		 */
		for( int z=size-1; z>=0; z-- ) {
			for( int x=0; x<size; x++ ) {
				Logger.writeToLog("coordinates", x + ", " + z);
				heights[z][x] = new Color(image.getRGB(x, z)).getRed();
			}
		}
		
		return heights;
	}

	@Override
	public void draw(GL2 gl, Camera camera) {	
		//update the quadtree. returns the number of vertices that need to be drawn along with the number of nodes that are actually going to draw anything
		int[] vals = tree.update(camera);
		
		//set the model's VBO to a new buffer with size equal to the nubmer of vertices * 3
		model.setVBO( Buffers.newDirectFloatBuffer(vals[0] * 3) );
		model.setFirst(new int[vals[1]]);
		model.setCount(new int[vals[1]]);
		
		tree.render(model);
		
		model.draw( gl, calcMVP(camera.getVP()) );
	}

	public float[][] getHeights() {
		return heights;
	}

	public void setHeights(float[][] heights) {
		this.heights = heights;
	}

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public void setModel(Model model) {
		if( model instanceof QTModel ) {
			this.model = (QTModel)model;
		}
		else {
			//invalid model type.
			return;
		}
	}
}