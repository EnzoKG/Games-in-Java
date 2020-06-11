package com.ekstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 288;
	public static final int HEIGHT = 288;
	public static final int SCALE = 2;

	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	public Tabuleiro tabuleiro;

	public static boolean selected = false;
	public static int previousx = 0, previousy = 0;
	public static int nextx = -1, nexty = -1;

	public Game() {

		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		addMouseListener(this);
		addMouseListener(this);
		tabuleiro = new Tabuleiro();
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
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

	public static void main(String args[]) {
		frame = new JFrame("Candy Crush");
		Game game = new Game();
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
		new Thread(game).start();
	}

	public void update() {
		
		tabuleiro.update();
		
		if (selected && nextx != -1 && nexty != -1) {
			int posx = previousx / 48;
			int posy = previousy / 48;

			int pos2x = nextx / 48;
			int pos2y = nexty / 48;

			if (pos2x == posx + 1 || pos2x == posx - 1 || pos2x == posx + 1 && pos2y == posy + 1 || pos2y == posy - 1 || pos2y == posy + 1) {
				
				if((pos2x >= posx + 1 || pos2x <= posx - 1) &&
						(pos2y >= posy + 1 || pos2y <= posy - 1)	) {
					
					System.out.println("nao pode mover");
					return;
				}
				
				int val = Tabuleiro.TABULEIRO[pos2x][pos2y];
				int val2 = Tabuleiro.TABULEIRO[posx][posy];
				
				Tabuleiro.TABULEIRO[posx][posy] = val;
				Tabuleiro.TABULEIRO[pos2x][pos2y] = val2;
				
				nextx = -1;
				nexty = -1;
				selected = false;
				
				System.out.println("mouveu");
				
			} else {
				
			}
		} else {
			
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		//
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//
		tabuleiro.render(g);
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

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
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}

		}

		stop();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (selected == false) {
			selected = true;
			previousx = e.getX() / 2;
			previousy = e.getY() / 2;
		} else {
			nextx = e.getX() / 2;
			nexty = e.getY() / 2;
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
