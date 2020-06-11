package com.estudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.estudios.main.Game;

public class UI {
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString("Apple: "+Game.fruit_atual+"/"+Game.contFruit, 30, 22);
	}
	
}
