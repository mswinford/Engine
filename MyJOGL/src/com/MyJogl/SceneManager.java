package com.MyJogl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.joml.Matrix4f;

import com.jogamp.opengl.GL2;

public class SceneManager {
	private ArrayList<Scene> scenes;
	private Scene scene;
	
	public SceneManager() {
		scene = new Scene();		
	}
	
	public void drawScene(GL2 gl, Matrix4f vp) {
		scene.draw(gl, vp);
	}
	
	public void initializeScene() {
		
	}

	public void setScene(Scene scene) {
		this.scene = scene;
		
	}
	public Scene getScene() {
		return scene;
	}
	
	
	public void saveScene() {
		try {
			File file = new File("src/" + scene.getSceneName() + ".scene");
			if(!file.exists())
				file.createNewFile();
			FileOutputStream fs = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(scene);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadScene(String sceneName) {
		try {
			FileInputStream fs = new FileInputStream(new File("src/" + sceneName + ".scene"));
			ObjectInputStream is = new ObjectInputStream(fs);
			scene = (Scene)is.readObject();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
