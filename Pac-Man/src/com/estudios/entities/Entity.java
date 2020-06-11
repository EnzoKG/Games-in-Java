package com.estudios.entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.estudios.main.Game;
import com.estudios.world.Camera;
import com.estudios.world.Node;
import com.estudios.world.Vector2i;
import com.estudios.world.World;

public class Entity {
	
	public static BufferedImage MACA = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage ENEMY1 = Game.spritesheet.getSprite(16, 16, 16, 16);
	public static BufferedImage ENEMY2 = Game.spritesheet.getSprite(32, 16, 16, 16);
	
	public double x;
	public double y;
	protected int width;
	protected int height;
	protected double speed;
	
	public int depth;
	
	protected List<Node> path;
	
	private BufferedImage sprite;
	
	public static Random rand = new Random();
	
	public Entity(double x, double y, int width, int height,double speed, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0, Entity n1) {
			
			if(n1.depth < n0.depth)
				return + 1;
			if(n1.depth > n0.depth)
				return - 1;
			return 0;
		
		}
		
	};
	
	public int getX() {
		return (int)this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0,  World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y,null);	
	}

	public void tick() {
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void followPath(List<Node> path) {
		if(path!=null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				if(x < target.x * 16) {
					x++;
				}else if(x > target.x * 16) {
					x--;
				}
				
				if(y < target.y * 16) {
					y++;
				}else if(y > target.y *16) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
			}
		}
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	
}
