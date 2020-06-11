package com.estudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.estudios.main.Game;

public class Player extends Entity {

	public int xTarget, yTarget;
	public boolean atacking = false;
	
	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void tick() {
		Enemy enemy = null;
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				int xEnemy = e.getX();
				int yEnemy = e.getY();
				if(Entity.calculateDistance(this.getX(), this.getY(), xEnemy, yEnemy) < 30) {
					enemy = (Enemy)e;
				}
			}
		}
		
		if(enemy != null) {
			atacking = true;
			xTarget = enemy.getX();
			yTarget = enemy.getY();
			if(Entity.rand.nextInt(100) < 30) {
			enemy.life-=Entity.rand.nextDouble();
			}
		} else {
			atacking = false;
		}
		
	}

	public void render(Graphics g) {
		super.render(g);
		//Ataque
		
		if(atacking) {
			g.setColor(Color.RED);
			g.drawLine((int)x+6, (int)y+6, xTarget+6, yTarget+6);
		}
		
	}

}
