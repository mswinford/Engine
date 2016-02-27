package com.MyJogl.GameObject;

<<<<<<< master
=======
import org.joml.Matrix4f;
import org.joml.Vector3f;

>>>>>>> a4246be Big update. Changes to testing. Began Terrain implementation.
import com.MyJogl.Model.Model;

<<<<<<< master
public class Character extends GameObject {
=======
public class Character extends GameObject implements Renderable, Movable {
	
	protected Model model;
	protected boolean transparent;
	protected float speed;
>>>>>>> a4246be Big update. Changes to testing. Began Terrain implementation.
	
	public Character(String name, Model model) {
		super(name);
		this.name = name;
		this.model = model;
	}
	public Character(String name) {
		this(name, null);
	}
	public Character(Model model) {
		this("", model);
	}
	public Character() {
		this("", null);
	}
<<<<<<< master
	public Character(Model model){
		super(model);
=======
	
	@Override
	public void move(Vector3f move) {
		this.translate(move);
>>>>>>> a4246be Big update. Changes to testing. Began Terrain implementation.
	}
<<<<<<< master
=======
	
	@Override
	public void draw(GL2 gl, Matrix4f vp) {
		if(model != null) {
			model.draw(gl, calcMVP(vp));
		}
		else 
			//Logger.writeToLog(name + " has no model");
		
		for (GameObject comp : components) {
			if(comp != null && comp instanceof Renderable)
				((Renderable)comp).draw(gl, vp);
		}
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public boolean isTransparent() {
		return transparent;
	}

	@Override
	public void setTransparent(boolean trans) {
		this.transparent = trans;
		
	}
	@Override
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	@Override
	public float getSpeed() {
		return speed;		
	}
	
>>>>>>> a4246be Big update. Changes to testing. Began Terrain implementation.
}
