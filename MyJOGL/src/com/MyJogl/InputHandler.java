package com.MyJogl;

import com.MyJogl.GameObject.Player;
import com.jogamp.newt.event.*;

import java.util.HashMap;

import com.jogamp.newt.event.InputEvent;

public class InputHandler {
	private HashMap<Short, String> eventToAction;
	private Player player;
	
	
	public InputHandler(Player player) {
		this.player = player;
		init();
	}
	
	public void init() {
		eventToAction = new HashMap<Short, String>();
		
		eventToAction.put(KeyEvent.VK_W, "moveForward");
		eventToAction.put(KeyEvent.VK_UP, "moveForward");
		eventToAction.put(KeyEvent.VK_S, "moveBackward");
		eventToAction.put(KeyEvent.VK_DOWN, "moveBackward");
		eventToAction.put(KeyEvent.VK_A, "strafeLeft");
		eventToAction.put(KeyEvent.VK_LEFT, "strafeLeft");
		eventToAction.put(KeyEvent.VK_D, "strafeRight");
		eventToAction.put(KeyEvent.VK_RIGHT, "strafeRight");
	}
	
	public void act(InputEvent e) {
		String action = "";
		try {
			if( e instanceof KeyEvent ) {
				action = eventToAction.get( ((KeyEvent)e).getKeyCode() );
				if ( action.equals("moveForward") ) {
					player.moveForward();
				}
				if ( action.equals("moveBackward") ) {
					player.moveBackward();
				}
				if ( action.equals("strafeLeft") ) {
					player.strafeLeft();
				}
				if ( action.equals("strafeRight") ) {
					player.strafeRight();
				}
			}
			else if ( e instanceof MouseEvent) {
				
			}
			
		} catch (java.lang.NullPointerException ex) {
			
		}
	}
	
	public void setAction(Short keyCode, String action) {
		
	}
	
	public void handleInput() {
		
	}
}
