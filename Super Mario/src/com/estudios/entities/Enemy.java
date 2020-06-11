package com.estudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.estudios.main.Game;
import com.estudios.world.World;

public class Enemy extends Entity {

	public double gravity = 1;

	public boolean right = true, left = false;

	private int framesAnimation = 0;
	private int maxFrames = 15;
	private int maxSprite = 2;
	private int curSprite = 0;
	
	public int life = 3;
	
	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}

	public void tick() {
		if (World.isFree((int) x, (int) (y + gravity))) {
			y += gravity;
		}
		
		if(right && World.isFree((int) (x+speed), (int)y)) {
			x+=speed;
			if(World.isFree(this.getX() + 16, this.getY() + 1)){
				right = false;
				left = true;
			}
		} else {
			right = false;
			left = true;
		}
		
		if(left && World.isFree((int) (x-speed), (int)y)) {
			x-=speed;
			if(World.isFree(this.getX() - 16, this.getY() + 1)){
				right = true;
				left = false;
			}
		} else {
			right = true;
			left = false;
		}
		
		framesAnimation++;
		if (framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation = 0;
			if (curSprite == maxSprite) {
				curSprite = 0;
			}
		}
	}

	public void render(Graphics g) {
		
		if(right) {
			sprite = Entity.ENEMY_SPRITE2[curSprite];
		} else if(left) {
			sprite = Entity.ENEMY_SPRITE1[curSprite];
		}
		
		if(life == 0) {
			sprite = Entity.ENEMY_DEATH;
			Game.entities.remove(this);
		}
		super.render(g);
	}
	
}
