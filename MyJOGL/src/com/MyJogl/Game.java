package com.MyJogl;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;
import com.MyJogl.Camera.FreeFlyCamera;
import com.MyJogl.Debug.ThreadDebug;
import com.MyJogl.GameConsole.GameConsole;
import com.MyJogl.Logger.Logger;
import com.MyJogl.Model.Model;
import com.MyJogl.Model.RenderMode;
import com.MyJogl.Util.ShaderUtil;
import com.MyJogl.GameObject.Character;
import com.MyJogl.GameObject.Player;
import com.MyJogl.GameObject.terrain.QuadTree;
import com.MyJogl.GameObject.terrain.Terrain;
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
	private String name;
	private volatile boolean isRunning = false;
	private Animator animator;
	private GLWindow window;
	private SceneManager sceneManager;
	
	private float dt = 0;
	
	private Matrix4f projection;
	
	private GameConsole console;
	
	//indefinite global variables
	private Player player;
	private InputHandler input;
	
	
	public static void main(String[] args) {		
		Thread gameThread = new Thread(Game.createGame("Test Game"));
		gameThread.run();
    }
	
	private Game(String name) {
		this.name = name;
		Logger.initilalizeLogger();
		Config.initialize();
		sceneManager = new SceneManager();
		console = new GameConsole();
		input = new InputHandler(null);
	}
	
	public static Game createGame(String name) {
		return new Game(name);
	}
	
	public void run() {
		window = GLWindow.create(Config.caps);
		window.setAutoSwapBufferMode(false); //disable the automatic excution of swapBuffers for double buffering. must call swapBuffers manually after each render call.
		
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyNotify(WindowEvent e) {
				stop();
			}
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				pause();
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				resume();
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
					stop();
				}
			}
		});
		
		animator = new Animator(window);
		animator.setUpdateFPSFrames(5, null);
		animator.start();
        window.addGLEventListener(this);
        window.setPosition(500, 500);
        window.setSize((int)Config.windowSize.getWidth(), (int)Config.windowSize.getHeight());
        window.setFullscreen(Config.fullscreen);
        window.setTitle(this.name);
        window.setVisible(true);
        isRunning = true;
	}
	
	public void stop() {
		ThreadDebug.printAllThreads();
		window.getAnimator().stop();
		window.lockSurface();
		isRunning = false;
		Logger.writeToLog("Active Threads: ");
		Logger.writeToLog("Game Stopped");
		Logger.dispose();
	}
	
	public void pause() {
		this.animator.pause();
	}
	
	public void resume() {
		this.animator.resume();
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void init(GLAutoDrawable drawable) {	
		//enable or disable vsync
		int vsync = Config.vsync ? 1 : 0;
		drawable.getGL().setSwapInterval(vsync);

		//this will move later when dynamic shader loading is implemented
		shaderID = ShaderUtil.loadShaders(drawable.getGL().getGL4(), "src/assets/shaders/vertex.vp", "src/assets/shaders/fragment.fp");
		shaderID2 = ShaderUtil.loadShaders(drawable.getGL().getGL4(), "src/assets/shaders/vertex.vp", "src/assets/shaders/fragment2.fp");
		terrainShaderID = ShaderUtil.loadShaders(drawable.getGL().getGL4(), "src/assets/shaders/TerrainVertex.vp", "src/assets/shaders/TerrainFragment.fp");
		
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
//		gl.glCullFace(GL.GL_CCW);
//		gl.glEnable(GL.GL_CULL_FACE);
		
		//below is for testing	
		intializeTestScene(gl);
		
		player = new Player("Player");
		FreeFlyCamera ffc = new FreeFlyCamera(0.1f);
		ffc.setName("camera");
		ffc.setProjection(projection);
		player.addComponent(ffc);
		input.setPlayer(player);

		//sceneManager.saveScene();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		dt = (float)((double)animator.getLastFPSPeriod() / 1000.0D);
		System.out.println( 1.0f / dt );
		console.setFPS(animator.getLastFPS());
		update();
		render(drawable);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		Config.windowSize.setSize(width, height);
		Config.aspectRatio = ((float)width)/((float)height);
		
		projection = new Matrix4f().setPerspective((float)Math.toRadians(Config.FOV), Config.aspectRatio, Config.zNear, Config.zFar);
//		projection.identity().setOrtho(-width/2, width/2, -height/2, height/2, Config.zNear, Config.zFar);
		projection.scale(-1.0f, 1.0f, 1.0f);
		Logger.writeToLog(projection);
		
		//testing
		((Camera)(player.getComponent("camera"))).setProjection(projection);
	}
	
	
	private void update() {		
//		Runtime rt = Runtime.getRuntime();
//		long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
//		Logger.writeToLog("MB used: " + usedMB);
		
//		character.setScale(character.getScale() + (scaleTheta * dt));
//		character.translate(deltaXYZ.mul(dt, new Vector3f()));
//		character.rotate(rot);
	}
	
	private void render(GLAutoDrawable drawable) {		
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		Matrix4f vp = ((Camera)(player.getComponent("camera"))).getVP();
		
		//below is for testing		
		sceneManager.drawScene(gl, vp);
		
		drawable.swapBuffers();
		gl.glFlush();
	}
	
	
	
	/////////////////////////////////////////////////////DELETE!!!!/////////////////////////////////////////////////
	
	
	
	int shaderID, shaderID2, terrainShaderID;
	float scaleTheta = 0.0f;
	Vector3f deltaXYZ = new Vector3f(0.01f, 0.0f, 0.0f);
	float[] rot = {0.0f, 0.0f, 0.0f};
	Model model = new Model();
	Model model2 = new Model();
	Character character = new Character("Test Character");
	Scene scene = new Scene("Test Scene");
		
	public void intializeTestScene( GL2 gl ) {
		Logger.writeToLog("initializing test scene");
		
		model.load(gl);
		model.setShaderID(shaderID);
		model.setMatrixID(gl.glGetUniformLocation(shaderID, "MVP"));
		
		model2.load(gl);
		model2.setShaderID(shaderID2);
		model2.setMatrixID(gl.glGetUniformLocation(shaderID2, "MVP"));
		
//		character.setModel(model);
//		character.setScale(0.25f);
		Character c1 = new Character("");
		c1.setModel(model);
		c1.setName("c1");
		c1.setScale(new Vector3f(0.1f));
		c1.translate(new Vector3f(0.0f, 0.0f, 1.0f));
		Character c2 = new Character("");
		c2.setModel(model2);
		c2.setTransparent(true);
		c2.setName("c2");
		c2.setScale(new Vector3f(0.1f));
		c2.translate(new Vector3f(0.0f, 1.5f, 1.0f));
		Character c3 = new Character("");
		c3.setModel(model);
		c3.setName("c3");
		c3.setScale(new Vector3f(0.1f));
		c3.translate(new Vector3f(1.0f, 1.5f, 1.0f));
		Character c4 = new Character("");
		c4.setModel(model2);
		c4.setName("c4");
		c4.setScale(new Vector3f(0.1f));
		c4.translate(new Vector3f(0.0f, -1.0f, -5.0f));
		
//		scene.add(c1);
//		scene.add(c2);
//		scene.add(c3);
//		scene.add(c4);
//		scene.add(character);
		
		Terrain t = new Terrain(512);
		t.load(gl, "src/assets/mountains512.png");
		t.getModel().setShaderID(terrainShaderID);
		t.getModel().setMatrixID(gl.glGetUniformLocation(terrainShaderID, "MVP"));
		t.getModel().setRenderMode(RenderMode.WIREFRAME);
//		t.setModel(tm);
//		t.setScale(1.0f);
		t.setScale(new Vector3f(2.0f, 1.0f, 2.0f));
		
		Terrain t2 = new Terrain(512);
		t2.setModel(t.getModel());
		t2.translate(new Vector3f(-t2.getSize(), 0.0f, 0.0f));
		Terrain t3 = new Terrain(512);
		t3.setModel(t.getModel());
		t3.translate(new Vector3f(-t3.getSize(), 0.0f, t3.getSize()));
		Terrain t4 = new Terrain(512);
		t4.setModel(t.getModel());
		t4.translate(new Vector3f(0.0f, 0.0f, t4.getSize()));
		
		scene.add(t);
//		scene.add(t2);
//		scene.add(t3);
//		scene.add(t4);
		
		
		
//		QuadTree qt = new QuadTree(128);
//		Logger.writeToLog(qt.toString());
		
		
		
		scene.add(player);
		
		sceneManager.setScene(scene);
		
		
	}

}