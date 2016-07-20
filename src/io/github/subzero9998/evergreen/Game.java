package io.github.subzero9998.evergreen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int width = 300; //sets the width of the game
	public static int height = width / 16 * 9; //sets the height of the game to 16:9
	public static int scale = 3; //scales the window up 3x
	
	private Thread thread; //initializes the game thread
	private JFrame frame;
	private boolean running = false; //boolean to determine when running
	
	public Game() {
		Dimension size = new Dimension(width *scale, height * scale);
		setPreferredSize(size);
		
		frame = new JFrame();
	}
	
	//method to start the game
	public synchronized void start() {
		running = true; //sets the running variable true
		thread = new Thread(this, "Display"); //starts the thread
		thread.start();
		
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(running) {
			update(); //logic (game update) restricted to 60 per second
			render(); //graphics rendering, unlimited
		}
	}
	
	public void update() {
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Evergreen");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}
	
}
