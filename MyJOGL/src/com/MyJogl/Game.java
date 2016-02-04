package com.MyJogl;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;
import com.MyJogl.Camera.FreeFlyCamera;
import com.MyJogl.GameObject.Character;
import com.MyJogl.GameObject.PlayerCharacter;
import com.MyJogl.Logger.Logger;
import com.MyJogl.Model.Model;
import com.MyJogl.Utils.ShaderUtils;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
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
	
	private Matrix4f projection;
	
	//indefinite global variables
	private PlayerCharacter player;
	private InputHandler input;
	
	public Game() {
		Logger.initilalizeLogger();
		Config.initialize();
		sceneManager = new SceneManager();
		camera = new Camera();
		projection = new Matrix4f()
				.setPerspective((float)Math.toRadians(Config.FOV), (float)(Config.windowSize.getWidth()/Config.windowSize.getHeight()), Config.zNear, Config.zFar);
		
		player = new PlayerCharacter(new Model());
		player.setName("Player");
		player.setCamera(new FreeFlyCamera(0.01f));
		input = new InputHandler(player);
	}
	
	public void run() {
		final GLWindow window = GLWindow.create(Config.caps);
		animator = new FPSAnimator(window, 60, Config.vsync);
		animator.setUpdateFPSFrames(5, null);
		
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyNotify(WindowEvent e) {
				window.getAnimator().stop();
				stop();
			}
		});
		
		window.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				input.act(e);
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
		Logger.writeToLog("Game Stopped");
		Logger.dispose();
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public static void main(String[] args) {
		new Thread(new Game()).run();
    }

	@Override
	public void init(GLAutoDrawable drawable) {			
		shaderID = ShaderUtils.loadShaders(drawable.getGL().getGL4(), "src/vertex.vp", "src/fragment.fp");
		
		
		//below is for testing
		GL2 gl = drawable.getGL().getGL2();
			
		Logger.writeToLog("initializing test scene");
		model.init(gl);
		model.setShaderID(shaderID);
		model.setMatrixID(gl.glGetUniformLocation(shaderID, "MVP"));
		player.setModel(model);
		Character c1 = new Character(model);
		c1.setName("c1");
		c1.setScale(0.25f);
		c1.translate(new Vector3f(-0.5f, 0.5f, 0.0f));
		Character c2 = new Character(model);
		c2.setName("c2");
		c2.setScale(0.25f);
		c2.translate(new Vector3f(0.5f, 0.5f, 0.0f));
		Character c3 = new Character(model);
		c3.setName("c3");
		c3.setScale(0.25f);
		c3.translate(new Vector3f(0.5f, -0.5f, 0.0f));
		Character c4 = new Character(model);
		c4.setName("c4");
		c4.setScale(0.25f);
		c4.translate(new Vector3f(-0.5f, -0.5f, 0.0f));
		scene.add(c1);
		scene.add(c2);
		scene.add(c3);
		scene.add(c4);
		scene.add(player);
		sceneManager.setScene(scene);
		

		//sceneManager.saveScene();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		update();
		render(drawable);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		Config.windowSize.setSize(width, height);
		Config.aspectRatio = width/height;
	}
	
	
	private void update() {
		System.out.println((int)animator.getLastFPS());
		
		character.translate(new Vector3f(0.01f, 0.0f, 0.0f));
		Logger.writeToLog(character.getTranslation().toString());
		//character.setScale(character.getScale() + scaleTheta);
		//character.translate(deltaXYZ);
		//character.Rotate(rot);
	}
	
	private void render(GLAutoDrawable drawable) {		
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);	
		
		gl.glUseProgram(shaderID);
		
		//below is for testing
		sceneManager.drawScene(gl, projection.mul(player.getCamera().getView()));
	}
	
	int shaderID;
	float scaleTheta = 0.01f;
	Vector3f deltaXYZ = new Vector3f(0.01f, 0.01f, 0.0f);
	float[] rot = {0.0f, 0.01f, 0.01f};
	Model model = new Model();
	Character character = new Character("Test Character");
	Scene scene = new Scene("Test Scene");

}
