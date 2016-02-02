package com.MyJogl;

import org.joml.Vector3f;

import com.MyJogl.GameObject.Character;
import com.MyJogl.Model.Model;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;



public class Game implements GLEventListener, Runnable {
	private volatile boolean isRunning = false;
	private FPSAnimator animator;
	private SceneManager sceneManager;
	private Camera camera;
	
	public Game() {
		Config.initialize();
		sceneManager = new SceneManager();
		camera = new Camera();
	}
	
	public void run() {
		final GLWindow window = GLWindow.create(Config.caps);
		animator = new FPSAnimator(window, 60, Config.vsync);
		animator.setUpdateFPSFrames(3, null);
		
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyNotify(WindowEvent e) {
				window.getAnimator().stop();
				stop();
			}
		});
		
		window.setAnimator(animator);
		animator.start();
        window.addGLEventListener(this);
        window.setSize((int)Config.windowSize.getWidth(), (int)Config.windowSize.getHeight());
        window.setTitle("Game");
        window.setVisible(true);
        isRunning = true;
	}
	
	public void stop() {
		isRunning = false;
		System.out.println("Game Stopped");
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public static void main(String[] args) {
		new Thread(new Game()).run();
    }

	@Override
	public void init(GLAutoDrawable drawable) {			
		shaderID = Util.loadShaders(drawable.getGL().getGL4(), "src/vertex.vp", "src/fragment.fp");
		
		
		//below is for testing
		GL2 gl = drawable.getGL().getGL2();
		model.init(gl);
		model.setShaderID(shaderID);
		model.setMatrixID(gl.glGetUniformLocation(shaderID, "MVP"));
		character.setModel(model);
		scene.add(character);
		sceneManager.setScene(scene);
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
	}
	
	
	private void update() {
		System.out.println((int)animator.getLastFPS());
		
		//character.setScale(character.getScale() + scaleTheta);
		//character.translate(deltaXYZ);
		//character.Rotate(rot);
	}
	
	private void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);	
		
		gl.glUseProgram(shaderID);
		
		
		//below is for testing
		sceneManager.drawScene(gl, camera.getVP());
		
		
	}
	
	int shaderID;
	float scaleTheta = 0.01f;
	Vector3f deltaXYZ = new Vector3f(0.0f, 0.0f, 0.01f);
	float[] rot = {0.0f, 0.01f, 0.01f};
	Model model = new Model();
	Character character = new Character("Test Character");
	Scene scene = new Scene();

}
