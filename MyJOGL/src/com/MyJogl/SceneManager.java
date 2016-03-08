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
	private Scene activeScene;
	
	public SceneManager() {
		activeScene = new Scene();		
	}
	
	public void drawScene(GL2 gl) {
		activeScene.draw(gl);
	}
	
	public void initializeScene() {
		
	}

	public void setScene(Scene scene) {
		this.activeScene = scene;
		
	}
	public Scene getScene() {
		return activeScene;
	}
	
	
	public void saveScene() {
		try {
			File file = new File("src/" + activeScene.getSceneName() + ".scene");
			if(!file.exists())
				file.createNewFile();
			FileOutputStream fs = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(activeScene);
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
			activeScene = (Scene)is.readObject();
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
