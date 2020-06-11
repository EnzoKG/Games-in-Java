package com.estudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.estudios.entities.Player;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Coins: "+Player.curCoins+"/"+Player.maxCoins, 10, 35);
	}

}
