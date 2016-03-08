package com.MyJogl.GameObject.terrain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import javax.imageio.ImageIO;

import org.joml.Matrix4f;

import com.MyJogl.Camera.Camera;
import com.MyJogl.GameObject.GameObject;
import com.MyJogl.GameObject.Renderable;
import com.MyJogl.Logger.Logger;
import com.MyJogl.Model.Model;
import com.MyJogl.Model.TerrainModel;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;

public class Terrain extends GameObject implements Renderable {
	FloatBuffer heightmap;
	int size;
	Model model;
	
	private Terrain() {
		super();
	}
	public Terrain(int size) {	
		this();
		this.size = size;
		heightmap = Buffers.newDirectFloatBuffer(size * size * 3);
	}
	
	public void load(GL2 gl, String filepath) { //load heightmap from various types of files. Right now only PNG is supported.
		loadVerticesFromHeightmap(filepath);
		
		model = new TerrainModel();
		((TerrainModel)model).load(gl, heightmap, size);
	}
	
	private void loadVerticesFromHeightmap(String filepath) {
		float x, y, z; //x and z are the width and height of the image. Y is the greyscale value
		
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
	
	private float clamp(int a) {
		return ((float)a) / 255.0f;
	}
	public void setModel(Model model) {
		//this.model = (TerrainModel)model;
		this.model = model;
	}
	public Model getModel() {
		return model;
	}
	
	@Override
	public void draw(GL2 gl, Camera camera) {
		if(model != null) {
			model.draw(gl, calcMVP(camera.getVP()));
		}
		else 
			//Logger.writeToLog(name + " has no model");
		
		for (GameObject comp : components) {
			if(comp != null && comp instanceof Renderable)
				((Renderable)comp).draw(gl, camera);
		}
	}
	
	public int getSize() {
		return size;
	}
	
	
}
