package com.ekstudios.main;

import com.ekstudios.entities.Enemy;
import com.ekstudios.entities.Entity;
import com.ekstudios.world.World;

public class EnemySpawn {

	public int interval = 60*8;
	public int curTime = 0;
	
	public void tick() {
		curTime++;
			if(curTime == interval) {
				curTime = 0;
				int xInitial = Entity.rand.nextInt((World.WIDTH/2) *16 - 16 - 16) + 16;
				Enemy enemy = new Enemy(xInitial,16,16,16,1,Entity.ENEMY1_RIGHT,Entity.ENEMY1_LEFT);
				Game.entities.add(enemy);
			}
	}
	
	
}
