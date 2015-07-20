package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import tileSets.TileSet;
import Game.KeyInput;
import Levels.LoadableLevel;
import Levels.MicroWorld;
import Levels.World;

public class LoreCraft extends Canvas{
	

	private static final long serialVersionUID = 1005836557922160992L;
	public static final int WIDTH = 1600, HEIGHT = (WIDTH / 16) * 9;
	private boolean running = false;
	public static final int GRIDSIZE = 16;
	private static TileSet defaultTileSet = new TileSet("F:\\eclipse\\Workspace\\LoreCraft\\Graphics\\grayscaleHeightmap.png", 16);
	public static World currentLevel = new World(8, 65, defaultTileSet, 0, true);
	//public static LoadableLevel currentLevel = new MicroWorld(0, 0, 65, 65, new TileSet("F:\\eclipse\\Workspace\\LoreCraft\\Graphics\\HeightmapTexture.png", 16));
	public static World cachedLevel;
	
	public static void main(String[] args) {
		new LoreCraft();
	}
	
	public LoreCraft() {
		KeyInput keyinput = new KeyInput();
		this.addKeyListener(keyinput);
		new Handler(keyinput);
		new Window(WIDTH, HEIGHT, "LoreCraft", this);
		this.requestFocusInWindow();
		running=true;
		
		run();
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		long now;
		while (running){
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			render();
			frames++;
			////
			//Runtime runtime = Runtime.getRuntime();
			//long memory = runtime.totalMemory() - runtime.freeMemory();
			//System.out.println(memory);

			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				//System.out.println();
				//double memleft = 100*(Runtime.getRuntime().freeMemory()/Runtime.getRuntime().totalMemory());
				//System.out.println("Memory used: "+Runtime.getRuntime().freeMemory()+"/"+Runtime.getRuntime().totalMemory()+
				//		" ("+memleft+"%)");
				frames = 0;
			}
		}
	}

	private void tick() {
		currentLevel.tick();
		Handler.tick();
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.black);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		
		Handler.render(g2);
		
		g2.dispose(); //what is this
		bs.show(); //what is this
	}
	
}
