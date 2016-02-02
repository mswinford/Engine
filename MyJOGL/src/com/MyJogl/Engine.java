package com.MyJogl;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

public class Engine implements GLEventListener {
	private Frame window;
	private GLCanvas canvas;
	
	private Game game;
	
	public Engine() {		
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		
		
		window = new Frame("Engine");
		window.setLayout(new GridLayout(2, 1));
		window.add(canvas);
		window.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
				
			}
			
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		window.setSize(200, 200);
		initialize();
		window.setVisible(true);
		
	}	

	private void initialize() {
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("File");
		MenuItem item1 = new MenuItem("Run...");
		item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runGame();
			}
		});
		menu.add(item1);
		menuBar.add(menu);
		window.setMenuBar(menuBar);
		
		JButton runButton = new JButton("Run...");
		runButton.setSize(new Dimension(30, 20));
		runButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				runGame();				
			}
		});
		window.add(runButton);
		
	}

	public static void main(String[] args) {
		new Engine();
		
	}

	public void runGame(){
		if (game != null && game.isRunning()) {
			System.out.println("Game is alreadyRunning");
			return;
		}
		game = new Game();
		(new Thread(game)).run();
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
		// TODO Auto-generated method stub		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
