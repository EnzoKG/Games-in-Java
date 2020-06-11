package com.estudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.estudios.main.Game;

public class Npc extends Entity {

	public String[] frases = new String[3];
	public boolean showMessage = false;
	public boolean show = false;

	public int curIndexMessage = 0;
	public int fraseIndex = 0;

	public int time = 0;
	public int maxTime = 5;

	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] = "Olá, seja muito bem vindo ao jogo!";
		frases[1] = "Destrua os inimigos recolhendo a arma";
		frases[2] = "Para atirar pressione X";
	}

	public void tick() {
		depth = 2;
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();

		int xNpc = (int) x;
		int yNpc = (int) y;

		if (Math.abs(xPlayer - xNpc) < 20 && Math.abs(yPlayer - yNpc) < 20) {
			if (show == false) {
				showMessage = true;
				show = true;
			}
		} else {
			// showMessage = false;
		}

		this.time++;
		if (showMessage) {
			if (this.time >= this.maxTime) {
				this.time = 0;
				if (curIndexMessage < frases[fraseIndex].length()) {
					curIndexMessage++;
				} else {
					if (fraseIndex < frases.length - 1) {
						fraseIndex++;
						curIndexMessage = 0;
					}
				}
			}
		}
	}

	public void render(Graphics g) {
		super.render(g);
		if (showMessage) {
			g.setColor(Color.WHITE);
			g.fillRect(9, 9, Game.WIDTH - 18, Game.HEIGHT - 18);
			g.setColor(Color.BLACK);
			g.fillRect(10, 10, Game.WIDTH - 20, Game.HEIGHT - 20);
			g.setFont(Game.newFont.deriveFont(20f));
			g.setColor(Color.WHITE);
			g.drawString(frases[fraseIndex].substring(0, curIndexMessage), (int) x, (int) y);

			g.drawString("> Press Enter to Exit <", (int) x + 10, (int) y + 18);
		}
	}

}
