package com.estudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.estudios.main.Game;
import com.estudios.world.World;

public class Player extends Entity{
	
	public boolean right, left, up, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	
	public boolean moved = false;
	public BufferedImage[] rightPlayer, leftPlayer, upPlayer, downPlayer;
	public int frames = 0, maxFrames = 10, index = 0, maxIndex = 1;
	
	public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		for(int i = 0; i < 2; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		for(int i = 0; i < 2; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(64 + (i*16), 0, 16, 16);
		}
		for(int i = 0; i < 2; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(96 + (i*16), 0, 16, 16);
		}
		for(int i = 0; i < 2; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(128 + (i*16), 0, 16, 16);
		}
	}	
	
	public void tick() {
		
		depth = 1;
	
		moved = false;
		if(right && World.isFree((int)(x+speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		if(down && World.isFree(this.getX(),(int)(y+speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;	
		}
		else if(up && World.isFree(this.getX(), (int)(y-speed))) {
			moved = true;
			dir = up_dir;
			y-=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		verifyCatchesFruit();
		
		if(Game.contFruit == Game.fruit_atual) {
			
			//Sistema pro player ganhar
			Game.gameState = "WIN";
			
		}
		
	}
	
	public void verifyCatchesFruit() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Fruit) {
				if(Entity.isColliding(this, current)) {
					Game.fruit_atual++;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
		} else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
		} else if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX(), this.getY(), null);
		} else if(dir == down_dir) {
			g.drawImage(downPlayer[index], this.getX(), this.getY(), null);
		} 
	}
	
}
