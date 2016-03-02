package com.MyJogl.GameObject;

import org.joml.Vector3f;

public class Guard extends Character {
	
	Vector3f from;
	Vector3f to;
	
	public Guard(String name) {
		super(name);
		from = new Vector3f(this.getTranslation());
		to = new Vector3f(from);
		this.setSpeed(speed);
	}
	
	public void patrol() {
		this.move( to.sub(from, null) );
	}

}
