package com.estudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.estudios.main.Game;
import com.estudios.world.AStar;
import com.estudios.world.Vector2i;
import com.estudios.world.World;

public class Enemy extends Entity {

	public double gravity = 1;

	public boolean right = true, left = false;

//	private int framesAnimation = 0;
//	private int maxFrames = 15;
//	private int maxSprite = 2;
//	private int curSprite = 0;

	public double life = 30;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		path = AStar.findPath(Game.world, new Vector2i(World.xInitial, World.yInitial), new Vector2i(World.xFinal, World.yFinal));
	}

	public void tick() {
		followPath(path);
		if(x >= Game.WIDTH) {
			//Perder vida
			Game.life_player -= Entity.rand.nextDouble();
			Game.entities.remove(this);
			return;
		}
		if(life <= 0) {
			Game.entities.remove(this);
			Game.money+=25;
			return;
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.RED);
		g.fillRect((int)(x-6), (int)(y-5), 30, 6);
		
		g.setColor(Color.GREEN);
		g.fillRect((int)(x-6), (int)(y-5), (int)((life/30)*30), 6);
	}

}
