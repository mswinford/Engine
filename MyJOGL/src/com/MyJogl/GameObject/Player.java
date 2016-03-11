package com.MyJogl.GameObject;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;

public class Player extends Character {
	private float moveSpeed = 1.0f;

	public Player(String name) {
		super(name);
	}
	
	public void moveForward() {
		this.translate(new Vector3f(0.0f, 0.0f, moveSpeed).rotate(rotation.invert(new Quaternionf()), new Vector3f()));
		for( GameObject comp : components ) {
			comp.translate(new Vector3f(0.0f, 0.0f, moveSpeed).rotate(comp.getRotation().invert(new Quaternionf()), new Vector3f()));
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
	}
	
	public void moveBackward() {
		this.translate(new Vector3f(0.0f, 0.0f, -moveSpeed).rotate(rotation.invert(new Quaternionf()), new Vector3f()));
		for( GameObject comp : components ) {
			comp.translate( new Vector3f(0.0f, 0.0f, -moveSpeed).rotate(comp.getRotation().invert(new Quaternionf()), new Vector3f()));
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
	}
	
	public void strafeLeft() {
		this.translate(new Vector3f(-moveSpeed, 0.0f, 0.0f).rotate(rotation.invert(new Quaternionf()), new Vector3f()));
		for( GameObject comp : components ) {
			comp.translate(new Vector3f(-moveSpeed, 0.0f, 0.0f).rotate(comp.getRotation().invert(new Quaternionf()), new Vector3f()));
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
	}
	
	public void strafeRight() {
		this.translate(new Vector3f(moveSpeed, 0.0f, 0.0f).rotate(rotation.invert(new Quaternionf()), new Vector3f()));
		for( GameObject comp : components ) {
			comp.translate(new Vector3f(moveSpeed, 0.0f, 0.0f).rotate(comp.getRotation().invert(new Quaternionf()), new Vector3f()));
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
	}
	
	public void moveUp() {
		this.translate(new Vector3f(0.0f, -moveSpeed, 0.0f));
		for( GameObject comp : components ) {
			comp.translate(new Vector3f(0.0f, -moveSpeed, 0.0f));
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
	}
	
	public void moveDown() {
		this.translate(new Vector3f(0.0f, moveSpeed, 0.0f));
		for( GameObject comp : components ) {
			comp.translate(new Vector3f(0.0f, moveSpeed, 0.0f));
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
	}
	
	public void lookLeft() {
		float[] rot = {0.0f, 0.05f, 0.0f};
		this.rotate( rot );
		for( GameObject comp : components ) {
			comp.rotate( rot );
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
	}
	
	public void lookRight() {
		float[] rot = {0.0f, -0.05f, 0.0f};
		this.rotate( rot );
		for( GameObject comp : components ) {
			comp.rotate( rot );
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
	}

	public void lookUp() {
		float[] rot = {0.05f, 0.0f, 0.0f};
		this.rotate( rot );
		for( GameObject comp : components ) {
			comp.rotate( rot );
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
		
	}

	public void lookDown() {
		float[] rot = {-0.05f, 0.0f, 0.0f};
		this.rotate( rot );
		for( GameObject comp : components ) {
			comp.rotate( rot );
			if( comp instanceof Camera ) {
				((Camera) comp).updateView();
			}
		}
		
	}
	
	
}
