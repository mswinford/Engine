package com.MyJogl.GameObject;

import com.MyJogl.Model.Model;

public class Character extends GameObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9095140582144742999L;
	public Character(String name) {
		super(name);
	}
	public Character(Model model){
		super(model);
	}
}
