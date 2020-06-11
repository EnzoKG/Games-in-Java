package com.estudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.estudios.main.Game;

public class UI {

	public static BufferedImage HEART = Game.spritesheet.getSprite(0, 16, 8, 8);
	
	public void render(Graphics g) {
		for(int i = 0; i < (int)(Game.life_player); i++) {
			g.drawImage(HEART, 10 + (i*37), 10, 36, 36, null);
		}
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.WHITE);
		g.drawString("R$ "+Game.money, (Game.WIDTH * Game.SCALE) - 80, 30);
	}

}
