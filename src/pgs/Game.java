package pgs;


import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import pgs.gfx.Asset;

public class Game implements Runnable {
	private Display display;
	public int width,height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	
	
	public Game(String title, int width, int height) {
		
		this.title = title;
		this.width = width;
		this.height = height;
		
	}
	
	
	private void init() {
		display = new Display(title, width, height);
		Asset.init();
		
	}
	
	private void update() {
		//System.out.println("Hlw");
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs==null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		
		
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		
		//Draw Here
		g.drawImage(Asset.grass,10,10,null);
		//End drawing
		bs.show();
		g.dispose();
	}
	public void run() {
		init();
		
		while(running) {
			update();
			render();
		}
		
		stop();
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()  {
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
}
