package com.ekstudios.entities;

import java.awt.image.BufferedImage;

import com.ekstudios.main.Game;

public class Enemy extends Entity {
	
	public int life = 3;
	
	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void tick() {
		y+=speed;
		if(y >= Game.WIDTH + 22) {
			ExplosionEnemy boom = new ExplosionEnemy(x, y, 16, 16, 0, null);
			Game.entities.add(boom);
			Game.entities.remove(this);
			Game.lifePlayer -= 1;
			return;
		}
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Bullet) {
				if(Entity.isColidding(this, e)) {
					Game.entities.remove(i);
					life--;
					if(life == 0) {
						ExplosionEnemy boom = new ExplosionEnemy(x, y, 16, 16, 0, null);
						Game.entities.add(boom);
						Game.score+=25;
						Game.entities.remove(this);
						return;
					}
					break;
				}
			}
			
		}
	}
	
}
