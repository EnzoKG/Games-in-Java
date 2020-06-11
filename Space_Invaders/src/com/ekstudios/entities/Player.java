package com.ekstudios.entities;

import java.awt.image.BufferedImage;
import com.ekstudios.main.Game;

public class Player extends Entity {

	public boolean right, left;

	public boolean isShooting = false;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void tick() {
		depth = 1;
		if (right) {
			x += speed + 0.5;
		} else if (left) {
			x -= speed + 0.5;
		}

		if (x >= Game.WIDTH) {
			x = -16;
		} else if (x + 16 < 0) {
			x = Game.WIDTH;
		}

		// Sistema de tiro
		if (isShooting) {
			isShooting = false;
			int xx = this.getX() + 6;
			int yy = this.getY();

			Bullet bullet = new Bullet(xx, yy, 3, 3, 3.5, null);
			Game.entities.add(bullet);

		}

		if (Game.lifePlayer <= 0) {
			Game.gameState = "GAME_OVER";
		}

		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Enemy) {
				if (Entity.isColidding(this, e)) {
					if (Game.lifePlayer == 1) {
						ExplosionPlayer boom = new ExplosionPlayer(x, y, 16, 16, 0, null);
						Game.entities.add(boom);
						return;
					}
				}
				break;
			}
		}

	}
}
