package com.estudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import com.estudios.entities.Entity;
import com.estudios.entities.Player;
import com.estudios.graficos.Spritesheet;
import com.estudios.graficos.UI;
import com.estudios.world.World;

public class Game extends Canvas implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;

	public static JFrame jframe;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	private BufferedImage image; 
	
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	
	public UI ui;
	
	public static int contFruit = 0;
	public static int fruit_atual = 0;
	
	public static String gameState = "NORMAL";
	
	private static boolean restartGame = false;
	private static boolean exitGame = false;
	private static boolean showMessage = false;
	private static int framesMessage = 0;
	
	public Game() {
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		//Objetos do jogo
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(0, 0, 16, 16, 2, spritesheet.getSprite(32, 0, 16, 16));
		world = new World("/level1.png");
		ui = new UI();
		
		entities.add(player);
	}
	
	public void initFrame() {
		jframe = new JFrame("Pac-Man Game by: Enzo Kozoroski");
		jframe.add(this);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		if(gameState.equals("NORMAL")) {
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
		} else if(gameState.equals("GAME_OVER") || gameState.equals("WIN")) {
			framesMessage++;
			if(framesMessage == 30) {
				framesMessage = 0;
				if(showMessage)
					showMessage = false;
				else
					showMessage = true;
			}
			if(restartGame) {
				restartGame = false;
				gameState = "NORMAL";
				World.restartGame("/level1.png");
			}
			if(exitGame) {
				System.exit(1);
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		Collections.sort(entities, Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		g.dispose();
		g = bs.getDrawGraphics(); 
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		ui.render(g);
		
		if(gameState.equals("GAME_OVER")) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,220));
			g2.fillRect(0,0, (WIDTH*SCALE), (HEIGHT*SCALE)); 
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.setColor(Color.WHITE);
			g.drawString("RESTART GAME ?", (WIDTH*SCALE)/2 - 175, (HEIGHT*SCALE)/2);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(Color.LIGHT_GRAY.brighter().darker());
			if(showMessage) {
				g.drawString(" Pressione Enter para reiniciar ", (WIDTH*SCALE)/2 - 220, (HEIGHT*SCALE)/2+200);
			}
		} else if(gameState.equals("WIN")) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,220));
			g2.fillRect(0,0, (WIDTH*SCALE), (HEIGHT*SCALE)); 
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.setColor(Color.WHITE);
			g.drawString("YOU WIN !", (WIDTH*SCALE)/2 - 100, (HEIGHT*SCALE)/2);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(Color.LIGHT_GRAY.brighter().darker());
			if(showMessage) {
				g.drawString(" Pressione E para sair do jogo",(WIDTH*SCALE)/2 - 220, (HEIGHT*SCALE)/2+170);
			} else {
				g.drawString(" Pressione Enter para reiniciar ", (WIDTH*SCALE)/2 - 220, (HEIGHT*SCALE)/2+200);
			}
		}
		
		bs.show(); 
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime(); 
		double amountOfTick = 60.0;	
		double ns = 1000000000/amountOfTick; 
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis(); 
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta = delta + (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+frames);
				frames = 0;
				timer = timer + 1000;
			}
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState.equals("GAME_OVER") || gameState.equals("WIN")) {
				restartGame = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) {
			if(gameState.equals("WIN")) {
				exitGame = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || 
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT || 
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || 
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN || 
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || 
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT || 
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || 
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN || 
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}

