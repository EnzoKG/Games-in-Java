package com.ekstudios.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {

	public static final int WIDTH = 6, HEIGHT = 6;
	public static int[][] TABULEIRO;

	public static int DOCE_0 = 0, DOCE_1 = 1, DOCE_2 = 2;

	public Tabuleiro() {
		TABULEIRO = new int[WIDTH][HEIGHT];

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				TABULEIRO[x][y] = new Random().nextInt(3);
			}
		}
	}

	public void update() {
		ArrayList<Candy> combos = new ArrayList<>();
		for(int yy = 0; yy < HEIGHT; yy++) {
			if(combos.size() == 3) {
				//Tenho um combo
				for(int i = 0; i < combos.size(); i++) {
					int xtemp = combos.get(i).x;
					int ytemp = combos.get(i).y;
					
					TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
				}
				combos.clear();
				return;
			}
			combos.clear();
			for(int xx = 0; xx < WIDTH; xx++) {
				int cor = TABULEIRO[xx][yy];
				
				if(combos.size() == 3) {
					//Tenho um combo
					for(int i = 0; i < combos.size(); i++) {
						int xtemp = combos.get(i).x;
						int ytemp = combos.get(i).y;
						
						TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
					}
					combos.clear();
					return;
				}
				if(combos.size() == 0) {
					combos.add(new Candy(xx, yy, cor));
				} else if(combos.size() > 0) {
					if(combos.get(combos.size() - 1).CANDY_TYPE == cor) {
						combos.add(new Candy(xx, yy, cor));
					} else {
						combos.clear();
						combos.add(new Candy(xx, yy, cor));
					}
				}
			}
		}
		
		combos = new ArrayList<>();
		for(int xx = 0; xx < WIDTH; xx++) {
			if(combos.size() == 3) {
				//Tenho um combo
				for(int i = 0; i < combos.size(); i++) {
					int xtemp = combos.get(i).x;
					int ytemp = combos.get(i).y;
					
					TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
				}
				combos.clear();
				return;
			}
			combos.clear();
			for(int yy = 0; yy < HEIGHT; yy++) {
				int cor = TABULEIRO[xx][yy];
				
				if(combos.size() == 3) {
					//Tenho um combo
					for(int i = 0; i < combos.size(); i++) {
						int xtemp = combos.get(i).x;
						int ytemp = combos.get(i).y;
						
						TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
					}
					combos.clear();
					return;
				}
				if(combos.size() == 0) {
					combos.add(new Candy(xx, yy, cor));
				} else if(combos.size() > 0) {
					if(combos.get(combos.size() - 1).CANDY_TYPE == cor) {
						combos.add(new Candy(xx, yy, cor));
					} else {
						combos.clear();
						combos.add(new Candy(xx, yy, cor));
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				g.setColor(Color.WHITE);
				g.drawRect(x * 48, y * 48, 48, 48);
				int doce = TABULEIRO[x][y];
				if (doce == DOCE_0) {
					g.setColor(Color.RED);
					g.fillRect(x*48 + 12, y*48 + 12, 25, 25);
				}
				if (doce == DOCE_1) {
					g.setColor(Color.GREEN);
					g.fillRect(x*48 + 12, y*48 + 12, 25, 25);
				}
				if (doce == DOCE_2) {
					g.setColor(Color.YELLOW);
					g.fillRect(x*48 + 12, y*48 + 12, 25, 25);
				}
				if(Game.selected) {
					int posx = Game.previousx/48;
					int posy = Game.previousy/48;
					g.setColor(Color.GRAY);
					g.drawRect(posx*48, posy*48, 48, 48);
				}
			}
		}
	}

}
