package com.ekstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ekstudios.main.Game;

public class ExplosionPlayer extends Entity{

	private int frames = 0;
	private int targetFrames = 4;
	private int maxFrames = 5;
	private int curFrames = 0;
	
	public BufferedImage[] explosionSprites = new BufferedImage[6];
	
	public ExplosionPlayer(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		explosionSprites[0] = Game.spritesheet.getSprite(0, 16, 16, 16);
		explosionSprites[1] = Game.spritesheet.getSprite(16, 16, 16, 16);
		explosionSprites[2] = Game.spritesheet.getSprite(32, 16, 16, 16);
		explosionSprites[3] = Game.spritesheet.getSprite(48, 16, 16, 16);
		explosionSprites[4] = Game.spritesheet.getSprite(64, 16, 16, 16);
		explosionSprites[5] = Game.spritesheet.getSprite(80, 16, 16, 16);
	}
	
	public void tick() {
		depth = 2;
		frames++;
		if(frames == targetFrames) {
			frames = 0;
			curFrames++;
			if(curFrames > maxFrames) {
				Game.entities.remove(this);
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(explosionSprites[curFrames], this.getX(), this.getY(), null);
	}
	
}
