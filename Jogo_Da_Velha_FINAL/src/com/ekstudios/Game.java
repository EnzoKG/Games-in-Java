package com.ekstudios;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, MouseListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 300, HEIGHT = 300;
	public int PLAYER = 1, OPONENTE = -1, CURRENT = OPONENTE;

	public BufferedImage PLAYER_SPRITE, OPONENTE_SPRITE;
	public int[][] TABULEIRO = new int[3][3];

	public static int mouseX, mouseY;
	public boolean isPressed = false;

	public int framesMessage = 0;
	public boolean showMessage = true;

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addMouseListener(this);
		try {
			PLAYER_SPRITE = ImageIO.read(getClass().getResource("/player.png"));
			OPONENTE_SPRITE = ImageIO.read(getClass().getResource("/oponente.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		resetTab();
	}

	public void resetTab() {
		for (int xx = 0; xx < TABULEIRO.length; xx++) {
			for (int yy = 0; yy < TABULEIRO.length; yy++) {
				TABULEIRO[xx][yy] = 0;
			}
		}
	}

	public int checkVictory() {
		// Verificar se o player ganhou

		// Player

		// Horizontal
		if (TABULEIRO[0][0] == PLAYER && TABULEIRO[1][0] == PLAYER && TABULEIRO[2][0] == PLAYER) {
			// O player ganhou na horizontal na primeira linha
			return PLAYER;
		}
		if (TABULEIRO[0][1] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[2][1] == PLAYER) {
			// O player ganhou na horizontal na segunda linha
			return PLAYER;
		}
		if (TABULEIRO[0][2] == PLAYER && TABULEIRO[1][2] == PLAYER && TABULEIRO[2][2] == PLAYER) {
			// O player ganhou na horizontal na terceira linha
			return PLAYER;
		}

		// Vertical
		if (TABULEIRO[0][0] == PLAYER && TABULEIRO[0][1] == PLAYER && TABULEIRO[0][2] == PLAYER) {
			// O player ganhou na vertical na primeira linha
			return PLAYER;
		}
		if (TABULEIRO[1][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[1][2] == PLAYER) {
			// O player ganhou na vertical na segunda linha
			return PLAYER;
		}
		if (TABULEIRO[2][0] == PLAYER && TABULEIRO[2][1] == PLAYER && TABULEIRO[2][2] == PLAYER) {
			// O player ganhou na vertical na terceira linha
			return PLAYER;
		}

		// Diagonal
		if (TABULEIRO[0][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[2][2] == PLAYER) {
			// O player ganhou na diagonal na primeira linha
			return PLAYER;
		}
		if (TABULEIRO[2][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[0][2] == PLAYER) {
			// O player ganhou na diagonal na segunda linha
			return PLAYER;
		}

		// Oponente

		// Horizontal
		if (TABULEIRO[0][0] == OPONENTE && TABULEIRO[1][0] == OPONENTE && TABULEIRO[2][0] == OPONENTE) {
			// O OPONENTE ganhou na horizontal na primeira linha
			return OPONENTE;
		}
		if (TABULEIRO[0][1] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[2][1] == OPONENTE) {
			// O OPONENTE ganhou na horizontal na segunda linha
			return OPONENTE;
		}
		if (TABULEIRO[0][2] == OPONENTE && TABULEIRO[1][2] == OPONENTE && TABULEIRO[2][2] == OPONENTE) {
			// O OPONENTE ganhou na horizontal na terceira linha
			return OPONENTE;
		}

		// Vertical
		if (TABULEIRO[0][0] == OPONENTE && TABULEIRO[0][1] == OPONENTE && TABULEIRO[0][2] == OPONENTE) {
			// O OPONENTE ganhou na vertical na primeira linha
			return OPONENTE;
		}
		if (TABULEIRO[1][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[1][2] == OPONENTE) {
			// O OPONENTE ganhou na vertical na segunda linha
			return OPONENTE;
		}
		if (TABULEIRO[2][0] == OPONENTE && TABULEIRO[2][1] == OPONENTE && TABULEIRO[2][2] == OPONENTE) {
			// O OPONENTE ganhou na vertical na terceira linha
			return OPONENTE;
		}

		// Diagonal
		if (TABULEIRO[0][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[2][2] == OPONENTE) {
			// O OPONENTE ganhou na diagonal na primeira linha
			return OPONENTE;
		}
		if (TABULEIRO[2][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[0][2] == OPONENTE) {
			// O OPONENTE ganhou na diagonal na segunda linha
			return OPONENTE;
		}

		// Empate
		int curScore = 0;
		for (int xx = 0; xx < TABULEIRO.length; xx++) {
			for (int yy = 0; yy < TABULEIRO.length; yy++) {
				if (TABULEIRO[xx][yy] != 0) {
					curScore++;
				}
			}
		}

		if (curScore == TABULEIRO.length * TABULEIRO[0].length) {
			return 0;
		}

		// Ninguém ganhou
		return -10;
	}

	public void tick() {
		if (CURRENT == PLAYER) {
			if (isPressed) {
				isPressed = false;
				mouseX /= 100;
				mouseY /= 100;
				if (TABULEIRO[mouseX][mouseY] == 0) {
					TABULEIRO[mouseX][mouseY] = PLAYER;
					CURRENT = OPONENTE;
				}
			}
		} else if (CURRENT == OPONENTE) {
			for (int xx = 0; xx < TABULEIRO.length; xx++) {
				for (int yy = 0; yy < TABULEIRO.length; yy++) {
					if (TABULEIRO[xx][yy] == 0) {
						Node bestMove = getBestMove(xx, yy, 0, OPONENTE);
						TABULEIRO[bestMove.x][bestMove.y] = OPONENTE;
						CURRENT = PLAYER;
						return;
					}
				}
			}
		}

		framesMessage++;
		if (framesMessage == 30) {
			framesMessage = 0;
			if (showMessage) {
				showMessage = false;
			} else {
				showMessage = true;
			}
		}

	}

	public Node getBestMove(int x, int y, int depth, int turno) {

		if (checkVictory() == PLAYER) {
			return new Node(x, y, depth - 10, depth);
		} else if (checkVictory() == OPONENTE) {
			return new Node(x, y, 10 - depth, depth);
		} else if (checkVictory() == 0) {
			return new Node(x, y, 0, depth);
		}

		List<Node> nodes = new ArrayList<>();
		for (int xx = 0; xx < TABULEIRO.length; xx++) {
			for (int yy = 0; yy < TABULEIRO.length; yy++) {
				if (TABULEIRO[xx][yy] == 0) {
					Node node;
					if (turno == PLAYER) {
						TABULEIRO[xx][yy] = PLAYER;
						node = getBestMove(xx, yy, depth+1, OPONENTE);
						TABULEIRO[xx][yy] = 0;
					} else {
						TABULEIRO[xx][yy] = OPONENTE;
						node = getBestMove(xx, yy, depth+1, PLAYER);
						TABULEIRO[xx][yy] = 0;
					}
					nodes.add(node);
				}
			}
		}
		
		Node finalNode = nodes.get(0);
		
		for(int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			if(turno == PLAYER) {
				if(n.score > finalNode.score) {
					finalNode = n;
				}
			} else {
				if(n.score < finalNode.score) {
					finalNode = n;
				}
			}
		}
		
		return finalNode;

	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for (int xx = 0; xx < TABULEIRO.length; xx++) {
			for (int yy = 0; yy < TABULEIRO.length; yy++) {
				g.setColor(Color.black);
				g.drawRect(xx * 100, yy * 100, 100, 100);
				if (TABULEIRO[xx][yy] == PLAYER) {
					g.drawImage(PLAYER_SPRITE, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
				} else if (TABULEIRO[xx][yy] == OPONENTE) {
					g.drawImage(OPONENTE_SPRITE, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
				}
			}
		}

		if (checkVictory() == PLAYER) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 210));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 20));

			if (showMessage) {
				g.drawString("Player ganhou", 80, 150);
			}

		} else if (checkVictory() == OPONENTE) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 210));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			if (showMessage) {
				g.drawString("Oponente ganhou", 80, 150);
			}

		} else if (checkVictory() == 0) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 210));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			if (showMessage) {
				g.drawString("EMPATE", 80, 150);
			}

		}

		g.dispose();
		bs.show();
	}

	public static void main(String args[]) {
		Game game = new Game();
		JFrame frame = new JFrame("Tic Tac Toe");
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		new Thread(game).start();
	}

	@Override
	public void run() {

		while (true) {
			tick();
			render();
			try {
				Thread.sleep(1000 / 60);
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
