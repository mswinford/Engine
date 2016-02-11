package com.MyJogl;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;
import com.MyJogl.Camera.FreeFlyCamera;
import com.MyJogl.GameObject.Character;
import com.MyJogl.GameObject.Player;
import com.MyJogl.Logger.Logger;
import com.MyJogl.Model.Model;
import com.MyJogl.Util.ShaderUtil;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.Animator;



public class Game implements GLEventListener, Runnable {
	private volatile boolean isRunning = false;
	private Animator animator;
	private SceneManager sceneManager;
	private Camera camera;
	
	private long dt = 0;
	
	private Matrix4f projection;
	
	//indefinite global variables
	private Player player;
	private InputHandler input;
	
	
	public static void main(String[] args) {
		new Thread(Game.createGame()).run();
    }
	
	private Game() {
		Logger.initilalizeLogger();
		Config.initialize();
		sceneManager = new SceneManager();
		camera = new Camera();
		camera.getView().setLookAt(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f));
		projection = new Matrix4f().setPerspective((float)Math.toRadians(Config.FOV), Config.aspectRatio, Config.zNear, Config.zFar);
		
		player = new Player("Player");
		player.setCamera(new FreeFlyCamera(0.01f));
		input = new InputHandler(player);
	}
	
	public static Game createGame() {
		return new Game();
	}
	
	public void run() {
		final GLWindow window = GLWindow.create(Config.caps);
		animator = new Animator();
		animator.setUpdateFPSFrames(5, null);
		
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyNotify(WindowEvent e) {
				window.getAnimator().stop();
				window.lockSurface();
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
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					window.getAnimator().stop();
					stop();
				}
			}
		});
		
		animator.add(window);
		animator.start();
        window.addGLEventListener(this);
        window.setPosition(500, 500);
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

	@Override
	public void init(GLAutoDrawable drawable) {	
		//enable or disable vsync
		int vsync = Config.vsync ? 1 : 0;
		drawable.getGL().setSwapInterval(vsync);
		
		
		shaderID = ShaderUtil.loadShaders(drawable.getGL().getGL4(), "src/assets/shaders/vertex.vp", "src/assets/shaders/fragment.fp");
		
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
		
		//below is for testing	
		intializeTestScene(gl);
		

		//sceneManager.saveScene();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		dt = animator.getLastFPSPeriod();
		System.out.println((int)animator.getLastFPS());
		update();
		render(drawable);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		Config.windowSize.setSize(width, height);
		Config.aspectRatio = ((float)width)/((float)height);
		projection.setPerspective((float)Math.toRadians(Config.FOV), Config.aspectRatio, Config.zNear, Config.zFar);
		
	}
	
	
	private void update() {		
//		Runtime rt = Runtime.getRuntime();
//		long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
//		Logger.writeToLog("MB used: " + usedMB);
		
		character.setScale(character.getScale() + scaleTheta);
		character.translate(deltaXYZ);
		character.rotate(rot);
	}
	
	private void render(GLAutoDrawable drawable) {		
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		gl.glUseProgram(shaderID);
		
		//below is for testing		
		Matrix4f vp = new Matrix4f().set(projection).mul(player.getCamera().getView());
		sceneManager.drawScene(gl, vp);
	}
	
	
	
	
	
	
	
	int shaderID;
	float scaleTheta = 0.0f;
	Vector3f deltaXYZ = new Vector3f(0.0f, 0.0f, 0.0f);
	float[] rot = {0.0f, 0.0f, 0.0f};
	Model model = new Model();
	Character character = new Character("Test Character");
	Scene scene = new Scene("Test Scene");
		
	public void intializeTestScene( GL2 gl ) {
		Logger.writeToLog("initializing test scene");
		
		model.load(gl);
		model.setShaderID(shaderID);
		model.setMatrixID(gl.glGetUniformLocation(shaderID, "MVP"));
		
		character.setModel(model);
		character.setScale(0.25f);
		Character c1 = new Character("");
		c1.setModel(model);
		c1.setName("c1");
		c1.setScale(0.25f);
		//c1.translate(new Vector3f(-0.5f, 0.5f, 0.0f));
		Character c2 = new Character("");
		c2.setName("c2");
		c2.setScale(0.25f);
		c2.translate(new Vector3f(0.5f, 0.5f, 0.0f));
		Character c3 = new Character("");
		c3.setName("c3");
		c3.setScale(0.25f);
		c3.translate(new Vector3f(0.5f, -0.5f, 0.0f));
		Character c4 = new Character("");
		c4.setName("c4");
		c4.setScale(0.25f);
		c4.translate(new Vector3f(-0.5f, -0.5f, 0.0f));
		scene.add(c1);
//		scene.add(c2);
//		scene.add(c3);
//		scene.add(c4);
//		scene.add(character);
		
		//create lots of triangles (10,000) to test limits
//		float x = -1.0f;
//		float y = 1.0f;
//		for(int i=0; i<10; i++) {
//			for(int j=0; j<10; j++) {
//				Character c = new Character(model);
//				c.setScale(0.25f);
//				c.translate(new Vector3f(x, y, 0.0f));
//				scene.add(c);
//				x += 0.5f;
//			}
//			y -= 0.5f;
//			x = -1.0f;
//		}
		
		scene.add(player);
		sceneManager.setScene(scene);
		
		
		
		
	}

}
