package com.ekstudios;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Smoke {
	
	public int x, y;
	public BufferedImage[] sprite;
	public int curFrames = 0, maxFrames = 20, curAnim = 0, maxAnim = 2;
	
	public Smoke(int x, int y) {
		this.x = x;
		this.y = y;
		sprite = new BufferedImage[2];
		sprite[0] = Game.spritesheet.getSprite(16, 0, 16, 16);
		sprite[1] = Game.spritesheet.getSprite(32, 0, 16, 16);
	}
	
	public void update() {
		curFrames++;
		if(curFrames == maxFrames) {
			curAnim++;
			if(curAnim == maxAnim) {
				curAnim = 0;
				Game.smokes.remove(this);
			}
			curFrames = 0;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite[curAnim], x, y, 36, 36, null);
	}
	
}
