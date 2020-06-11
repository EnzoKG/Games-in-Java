package com.ekstudios;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crab {
	
	public BufferedImage sprite = Game.spritesheet.getSprite(0, 0, 16, 16);
	
	public double x,y,dx,dy;
	public double speed = 1;
	
	public Crab(int x, int y) {
		this.x = x;
		this.y = y;
		//Calculo até o ponto
		double radius = Math.atan2((((Game.HEIGHT*Game.SCALE)/2) - 20) - y, (((Game.WIDTH*Game.SCALE)/2) - 20) - x);
		this.dx = Math.cos(radius);
		this.dy = Math.sin(radius);
	}
	
	public void verifyCollision() {
		if(Game.isPressed) {
			Game.isPressed = false;
			if(Game.mouseX >= x && Game.mouseX <= x + 36) {
				if(Game.mouseY >= y && Game.mouseY <= y + 36) {
					Game.score++;
					Game.smokes.add(new Smoke((int)x, (int)y));
					Game.crabs.remove(this);
					return;
				}
			}
		}
	}

	public void update() {
		x += dx*speed;
		y += dy*speed;
		
		//Verifica colisão com o mouse
		verifyCollision();
		
		if(new Rectangle((int)x, (int)y, 35, 35).intersects(Game.maskCircle)) {
			Game.crabs.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, (int)x, (int)y, 36, 36, null);
	}
	
}
