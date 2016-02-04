package com.MyJogl;

import com.MyJogl.GameObject.PlayerCharacter;
import com.jogamp.newt.event.*;
import java.util.HashMap;

import com.jogamp.newt.event.InputEvent;

public class InputHandler {
	private HashMap<Short, String> eventToAction;
	private PlayerCharacter player;
	
	public InputHandler(PlayerCharacter player) {
		this.player = player;
		
		init();
	}
	
	public void init() {
		eventToAction = new HashMap<Short, String>();
		
		eventToAction.put(KeyEvent.VK_W, "moveForward");
		eventToAction.put(KeyEvent.VK_UP, "moveForward");
	}
	
	public void act(InputEvent e) {
		try {
			if( e instanceof KeyEvent ) {
				if ( eventToAction.get(((KeyEvent)e).getKeyCode()).equals("moveForward")) {
					player.moveForward();
				}
			}
		} catch (java.lang.NullPointerException ex) {
			//do nothing on an invalid input
		}
	}
}
