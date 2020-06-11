package com.estudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import com.estudios.main.Game;
import com.estudios.world.AStar;
import com.estudios.world.Vector2i;


public class Enemy extends Entity {

	public boolean ghostMode = false;
	public int ghostFrames = 0;
	public int nextTime = Entity.rand.nextInt(1);
	
	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void tick() {
		depth = 0;
		if(ghostMode == false) {
			if (path == null || path.size() == 0) {
				Vector2i start = new Vector2i((int) (x / 16), (int) (y / 16));
				Vector2i end = new Vector2i((int) (Game.player.x / 16), (int) (Game.player.y / 16));
				path = AStar.findPath(Game.world, start, end);
			}

			if (new Random().nextInt(100) < 60)
				followPath(path);

			if (x % 16 == 0 && y % 16 == 0) {
				if (new Random().nextInt(100) < 5) {
					Vector2i start = new Vector2i((int) (x / 16), (int) (y / 16));
					Vector2i end = new Vector2i((int) (Game.player.x / 16), (int) (Game.player.y / 16));
					path = AStar.findPath(Game.world, start, end);
				}
			}
		}
		ghostFrames++;
		if(ghostFrames == nextTime) {

			ghostFrames = 0;
			if(ghostMode == false) {
				ghostMode = true;
			} else {
				ghostMode = false;
			}
		}
		if(isColliding(this, Game.player)) {
			Game.gameState = "GAME_OVER";
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

}
