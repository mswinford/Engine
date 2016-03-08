package com.MyJogl.GameObject.terrain;

import org.joml.Vector3f;

public class TerrainVertex {
	private Vector3f position;
	private boolean enabled;
	
	public TerrainVertex() {
		this(new Vector3f(), false);
	}
	
	public TerrainVertex(Vector3f position) {
		this(position, false);
	}
	
	public TerrainVertex(Vector3f position, boolean enabled) {
		setPosition(position);
		setEnabled(enabled);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
