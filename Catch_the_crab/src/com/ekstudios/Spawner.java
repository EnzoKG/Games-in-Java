package com.ekstudios;

import java.util.Random;

public class Spawner {
	
	public int curTime = 0, interval = 60*2;
	public Random rand;
	
	public Spawner() {
		rand = new Random();
	}
	
	public void update() {
		curTime++;
		if(curTime == interval) {
			curTime = 0;
			if(rand.nextInt(100) < 50) {
				Game.crabs.add(new Crab(rand.nextInt(Game.WIDTH*Game.SCALE - 40), -40));
			} else {
				Game.crabs.add(new Crab(rand.nextInt(Game.WIDTH*Game.SCALE - 40), Game.HEIGHT*Game.SCALE-40));
			}
		}
	}
	
}
