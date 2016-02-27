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
		eventToAction.put(KeyEvent.VK_UP, "moveUp");
		eventToAction.put(KeyEvent.VK_S, "moveBackward");
		eventToAction.put(KeyEvent.VK_DOWN, "moveDown");
		eventToAction.put(KeyEvent.VK_A, "strafeLeft");
		eventToAction.put(KeyEvent.VK_LEFT, "lookLeft");
		eventToAction.put(KeyEvent.VK_D, "strafeRight");
		eventToAction.put(KeyEvent.VK_RIGHT, "lookRight");
		eventToAction.put(MouseEvent.EVENT_MOUSE_DRAGGED, "look");
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
				if ( action.equals("moveUp") ) {
					player.moveUp();
				}
				if ( action.equals("moveDown") ) {
					player.moveDown();
				}
				if ( action.equals("lookLeft") ) {
					player.lookLeft();
				}
				if ( action.equals("lookRight") ) {
					player.lookRight();
				}
			}
			else if ( e instanceof MouseEvent) {
				action = eventToAction.get( ((MouseEvent)e).getEventType() );
				if( action.equals("look") ) {
					int r = ((MouseEvent)e).getY();
					if( r > 0 ) {
						player.lookRight();
					}
					if( r < 0 ) {
						player.lookLeft();
					}
				}
			}
			
		} catch (java.lang.NullPointerException ex) {
			
		}
	}
	
	public void setAction(Short keyCode, String action) {
		
	}
	
	public void handleInput() {
		
	}
}
