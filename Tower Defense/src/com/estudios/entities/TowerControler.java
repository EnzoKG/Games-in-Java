package com.estudios.entities;

import java.awt.image.BufferedImage;
import com.estudios.main.Game;
import com.estudios.world.World;

public class TowerControler extends Entity{

	public boolean isPressed = false;
	public int xTarget, yTarget;
	
	public TowerControler(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		if(isPressed) {
			//Criar uma torre no mapa
			isPressed = false;
			boolean released = true;
			int xx = (xTarget/16) * 16;
			int yy = (yTarget/16) * 16;
			Player player = new Player(xx, yy, 16, 16, 0, Game.spritesheet.getSprite(16, 16, 16, 16));
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Player) {
					if(Entity.isColidding(e, player)) {
						released = false;
					}
				}
			}
			
			if(World.isFree(xx, yy)) {
				released = false;
			}
			
			if(released) {
				if(Game.money >= 25) {
					
				Game.entities.add(player);
				Game.money -= 25;
				
				} else {
					//Colocar mensagem que está sem dinheiro
				}
			}
		}
		if(Game.life_player <= 0) {
			//Game over
			System.exit(1);
		}
	}
	
}
