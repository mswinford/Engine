package com.MyJogl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;



public class Game implements GLEventListener {
	private boolean isRunning = false;
	private SceneGraph gameGraph;
	private Camera camera;
	private Config settings;
	
	public Game() {
		settings = new Config();
		gameGraph = new SceneGraph();
		camera = new Camera(settings.FOV, settings.aspectRatio, settings.drawDistance);
	}
	
	public void run() {
		GLWindow window = GLWindow.create(Config.caps);
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyNotify(WindowEvent e) {
				stop();
			}
		});
		
        window.addGLEventListener(this);
        window.setSize(300, 300);
        window.setTitle("Game");
        window.setVisible(true);
        isRunning = true;
	}
	
	public void stop() {
		//TODO save the game state before closing
		
		isRunning = false;		
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public static void main(String[] args) {
		(new Game()).run();
    }

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub        
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {		
		// TODO Auto-generated method stub
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		update();
		render(drawable);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		
		render(drawable);
		
	}
	
	
	private void update() {
		
	}
	
	private void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);	
		
		gl.glUseProgram(shaderID);
		
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBuffer.get(0));
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 0, 0);
		
		gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3);
		gl.glDisableVertexAttribArray(0);
			
	}
	
	private FloatBuffer fb;
	private IntBuffer vertexBuffer;
	int shaderID;
	
	private float[] triangle = {-1, -1, 0,
 			1, -1, 0,
 			0,  1, 0,
 			};
	
	private float[] square = {
		      -1.0f,-1.0f,-1.0f, // triangle 1 : begin
		      -1.0f,-1.0f, 1.0f,
		      -1.0f, 1.0f, 1.0f, // triangle 1 : end
		      1.0f, 1.0f,-1.0f, // triangle 2 : begin
		      -1.0f,-1.0f,-1.0f,
		      -1.0f, 1.0f,-1.0f, // triangle 2 : end
		     1.0f,-1.0f, 1.0f,
		     -1.0f,-1.0f,-1.0f,
		     1.0f,-1.0f,-1.0f,
		     1.0f, 1.0f,-1.0f,
		     1.0f,-1.0f,-1.0f,
		     -1.0f,-1.0f,-1.0f,
		     -1.0f,-1.0f,-1.0f,
		     -1.0f, 1.0f, 1.0f,
		     -1.0f, 1.0f,-1.0f,
		     1.0f,-1.0f, 1.0f,
		     -1.0f,-1.0f, 1.0f,
		     -1.0f,-1.0f,-1.0f,
		     -1.0f, 1.0f, 1.0f,
		     -1.0f,-1.0f, 1.0f,
		     1.0f,-1.0f, 1.0f,
		     1.0f, 1.0f, 1.0f,
		     1.0f,-1.0f,-1.0f,
		     1.0f, 1.0f,-1.0f,
		     1.0f,-1.0f,-1.0f,
		     1.0f, 1.0f, 1.0f,
		     1.0f,-1.0f, 1.0f,
		     1.0f, 1.0f, 1.0f,
		     1.0f, 1.0f,-1.0f,
		     -1.0f, 1.0f,-1.0f,
		     1.0f, 1.0f, 1.0f,
		     -1.0f, 1.0f,-1.0f,
		     -1.0f, 1.0f, 1.0f,
		     1.0f, 1.0f, 1.0f,
		     -1.0f, 1.0f, 1.0f,
		     1.0f,-1.0f, 1.0f
		 };

}
