package com.ekstudios;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, MouseListener {

	private static final long serialVersionUID = 1L;

	public static int WIDTH = 240;
	public static int HEIGHT = 240;
	public static int SCALE = 2;

	public static List<Crab> crabs;
	public static List<Smoke> smokes;
	public Spawner spawner;

	public static Spritesheet spritesheet;

	public static Rectangle maskCircle;
	
	public static int mouseX, mouseY;
	public static boolean isPressed = false;
	
	public static int score = 0;

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.addMouseListener(this);
		crabs = new ArrayList<>();
		smokes = new ArrayList<>();
		spawner = new Spawner();
		maskCircle = new Rectangle(((WIDTH * SCALE) / 2) - 35, ((HEIGHT * SCALE) / 2) - 35, 70, 70);
		spritesheet = new Spritesheet("/spritesheet.png");
		
	}

	public void update() {
		spawner.update();
		for (int i = 0; i < crabs.size(); i++) {
			crabs.get(i).update();
		}
		for (int i = 0; i < smokes.size(); i++) {
			smokes.get(i).update();
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(255, 229, 102));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

		g.setColor(Color.BLACK);
		g.fillOval(((WIDTH * SCALE) / 2) - 35, ((HEIGHT * SCALE) / 2) - 35, 70, 70);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Score: "+score, 10, 20);

		for (int i = 0; i < crabs.size(); i++) {
			crabs.get(i).render(g);
		}
		for (int i = 0; i < smokes.size(); i++) {
			smokes.get(i).render(g);
		}

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame jframe = new JFrame();
		jframe.setTitle("Catch the crab!");
		jframe.add(game);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		new Thread(game).start();
	}

	@Override
	public void run() {
		double fps = 144.0;
		while (true) {
			update();
			render();
			try {
				Thread.sleep((int) (1000 / fps));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
		isPressed = true;
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
