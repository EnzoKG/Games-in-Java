package com.ekstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.ekstudios.entities.Entity;
import com.ekstudios.entities.Player;
import com.ekstudios.graficos.Spritesheet;
import com.ekstudios.graficos.UI;
import com.ekstudios.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 120;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	private BufferedImage image;
	private BufferedImage game_bg;
	private BufferedImage game_bg2;
	
	public int backY = 0;
	public int backY2 = 160;
	public int backSpeed = 1;
	
	public static World world;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Player player;
	public static Menu menu;
	
	public EnemySpawn enemySpawn;
	
	public static int score = 0;
	public static int lifePlayer = 5;

	public UI ui;
	
	public static String gameState = "MENU";
	
	public static int curMessage = 0;
	public static boolean showMessage = true;
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(WIDTH/2,HEIGHT - 16,16,16,1,spritesheet.getSprite(0, 0, 16, 16));
		//world = new World();
		ui = new UI();
		enemySpawn = new EnemySpawn();
		menu = new Menu();
		
		try {
			game_bg = ImageIO.read(getClass().getResource("/bg_game_space_invaders.png"));
			game_bg2 = ImageIO.read(getClass().getResource("/bg_game_space_invaders.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		entities.add(player);
		
	}
	
	public void initFrame(){
		frame = new JFrame("Space Invaders");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
		if(gameState.equals("NORMAL")) {
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			ui.tick();
			enemySpawn.tick();
			
			backY-=backSpeed;
			if(backY + 160 <= 0) {
				backY = 160;
			}
			
			backY2-=backSpeed;
			if(backY2 + 160 <= 0) {
				backY2 = 160;
			}
			
		}
		
		if(gameState.equals("MENU")) {
			menu.tick();
		}
		
		curMessage++;
		if(curMessage == 30) {
			curMessage = 0;
			if(showMessage) {
				showMessage = false;
			} else {
				showMessage = true;
			}
		}
		
	}
	


	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		g.drawImage(game_bg, 0, backY, null);
		g.drawImage(game_bg2, 0, backY2, null);
		
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		//world.render(g);
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		if(gameState.equals("GAME_OVER")) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0,0, (WIDTH*SCALE), (HEIGHT*SCALE)); 
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.setColor(Color.WHITE);
			g.drawString("YOU DIED", (WIDTH*SCALE)/2 - 145, (HEIGHT*SCALE)/2 - 175);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.setColor(Color.LIGHT_GRAY.brighter().darker());
			if(showMessage) {
				g.drawString("> ESC para sair <", (WIDTH*SCALE)/2 - 166, (HEIGHT*SCALE)/2-100);
			}
		} else if(gameState.equals("MENU")) {
			menu.render(g);
		}

		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(gameState.equals("MENU")) {
				menu.down = true;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(gameState.equals("MENU")) {
				menu.up = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState.equals("MENU")) {
				menu.enter = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isShooting = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(gameState.equals("GAME_OVER")) {
				System.exit(1);
			}
			if(lifePlayer > 1) {
				gameState = "MENU";
				Menu.pause = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}
