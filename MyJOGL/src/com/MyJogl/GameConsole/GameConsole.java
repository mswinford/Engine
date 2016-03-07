package com.MyJogl.GameConsole;

import java.awt.Color;
import java.awt.Font;

import com.jogamp.opengl.util.awt.TextRenderer;

public class GameConsole {
	TextRenderer tr;
	Color color;
	
	//items that will be rendered
	private float fps;
	
	public GameConsole() {
		tr = new TextRenderer(new Font("sansSerif", Font.PLAIN, 18));
		color = Color.WHITE;
	}

	public float getFPS() {
		return fps;
	}

	public void setFPS(float fps) {
		this.fps = fps;
	}
	
	public void draw(int width, int height) {		
		tr.begin3DRendering();
		drawFPS();
		tr.end3DRendering();
	}
	
	private void drawFPS() {
		if( fps >= 60.0f ) {
			color = Color.GREEN;
		}
		else if( fps < 60.0f && fps >= 30 ) {
			color = Color.YELLOW;
		}
		else {
			color = Color.RED;
		}
		
		tr.setColor(color);
		tr.draw3D(Float.toString(fps), 0.0f, 0.0f, 1.0f, 1.0f);
	}

}
