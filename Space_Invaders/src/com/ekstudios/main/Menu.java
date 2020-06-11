package com.ekstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	
	public String[] options = {"New Game",  "Exit"};
	
	public int curOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;
	
	public static boolean pause = false;
	
	public void tick() {
		if(up) {
			up = false;
			curOption--;
			if(curOption < 0) {
				curOption = maxOption;
			}
		}
		if(down) {
			down = false;
			curOption++;
			if(curOption > maxOption) {
				curOption = 0;
			}
		}
		if(enter) {
			enter = false;
			if(options[curOption].equals("New Game") || options[curOption].equals("Continue")) {
				Game.gameState = "NORMAL";
				pause = false;
			} else if(options[curOption].equals("Exit")) {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,200));
		g2.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.BLUE.brighter()); 
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawString("Space Invaders", (Game.WIDTH*Game.SCALE)/2 - 160, (Game.HEIGHT*Game.SCALE)/2 - 200);
		
		//Opções Menu
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 9));
		if(pause == false)
			g.drawString("New Game", (Game.WIDTH*Game.SCALE)/2 - 150, (Game.HEIGHT*Game.SCALE)/2 - 150);
		else
			g.drawString("Continue", (Game.WIDTH*Game.SCALE)/2 - 150, (Game.HEIGHT*Game.SCALE)/2 - 150);
		g.drawString("Exit", (Game.WIDTH*Game.SCALE)/2 - 150, (Game.HEIGHT*Game.SCALE)/2 - 130);
		if(options[curOption].equals("New Game")) {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 160, (Game.HEIGHT*Game.SCALE)/2 - 150);
		}else if(options[curOption].equals("Exit")) {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 160, (Game.HEIGHT*Game.SCALE)/2 - 130);
		}
	}
	
}
