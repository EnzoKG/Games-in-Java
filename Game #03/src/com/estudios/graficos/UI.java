package com.estudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.estudios.entities.Player;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(8, 4,50, 8);
		g.setColor(Color.GREEN);
		g.fillRect(8, 4, (int)((Player.life/Player.maxLife)*50), 8);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 8));
		g.drawString((int)Player.life+"/"+(int)Player.maxLife, 18, 10);
	}
	
}
