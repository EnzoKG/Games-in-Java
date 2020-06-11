package com.ekstudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.ekstudios.main.Game;

public class UI {
	
	public BufferedImage heart = Game.spritesheet.getSprite(0, 49, 7, 7);
	
	public static int seconds = 0;
	public static int minutes = 0;
	public static int frames = 0;
	
	public void tick() {
		frames++;
		if(frames == 60) {
			//Passou 1 segundo.
			frames = 0;
			seconds++;
			if(seconds == 60) {
				//Passou 1 minuto.
				seconds = 0;
				minutes++;
			}
		}
	}

	public void render(Graphics g) {
		if(Game.gameState.equals("NORMAL")) {
			String formatTime = "";
			if(minutes < 10) {
				formatTime+="0"+minutes+":";
			}else {
				formatTime+=minutes+":";
			}
			
			if(seconds < 10) {
				formatTime+="0"+seconds;
			}else {
				formatTime+=seconds;
			}
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 17));
			g.drawString("Score: " + Game.score, 10, 14);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 17));
			g.drawString("Tempo: "+formatTime, 250, 14);
			
			for(int i = 0; i < Game.lifePlayer; i++) {
				g.drawImage(heart, 10, 30 + (i*19), 18, 18, null);
			}
			
		}
	}
	
}
