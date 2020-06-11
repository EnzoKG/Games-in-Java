package com.estudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import javax.imageio.ImageIO;
//import com.estudios.graficos.Spritesheet;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.estudios.world.World;

public class Menu {
	
	public String[] options = {"New Game", "Load Game", "Exit"};
	
	public int curOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;
	
	public static boolean pause = false;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	
	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		} else {
			saveExists = false;
		}
		if(up) {
			up = false;
			curOption--;
			if(curOption < 0) {
				curOption = maxOption;
			}
		}
		if(down) {
			down = false;
			curOption++;
			if(curOption > maxOption) {
				curOption = 0;
			}
		}
		if(enter) {
			Sound.Clips.music.loop();
			enter = false;
			if(options[curOption].equals("New Game") || options[curOption].equals("Continue")) {
				Game.gameState = "NORMAL";
				pause = false;
				file = new File("save.txt");
				file.delete();
			} else if(options[curOption] == "Load Game") {
				file = new File("save.txt");
				if(file.exists()) {
					String saver = loadGame(10);
					applySave(saver);
				}
			} else if(options[curOption].equals("Exit")) {
				System.exit(1);
			}
		}
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0])
			{
				case "level": 
					World.restartGame("level"+spl2[1]+".png");
					Game.gameState = "NORMAL";
					pause = false;
					break;
				
				case "life":
					Game.player.life = Integer.parseInt(spl2[1]);
					break;
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] transition = singleLine.split(":");
						char[] val = transition[1].toCharArray();
						transition[1] = "";
						for(int i = 0; i < val.length;i++) {
							val[i]-=encode;
							transition[1]+=val[i];
						}
						line+=transition[0];
						line+=":";
						line+=transition[1];
						line+="/";
					}
				}catch(IOException e) {}
			
			}catch(FileNotFoundException e) {}
		}
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current+=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++) {
				value[n]+=encode;
				current+=value[n];
			}
			try {
				write.write(current);
				if(i < val1.length - 1) {
					write.newLine();
				}
			}catch(IOException e) {}
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,200));
		//g2.setColor(Color.GRAY.darker());
		g2.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.RED.brighter()); 
		g.setFont(new Font("Times New Roman", Font.BOLD, 40));
		g.drawString("First Game", (Game.WIDTH*Game.SCALE)/2 - 100, (Game.HEIGHT*Game.SCALE)/2 - 150);
		
		//Opções Menu
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		if(pause == false)
			g.drawString("New Game", (Game.WIDTH*Game.SCALE)/2 - 200, 200);
		else
			g.drawString("Continue", (Game.WIDTH*Game.SCALE)/2 - 200, 200);
		g.drawString("Load Game", (Game.WIDTH*Game.SCALE)/2 - 200, 250);
		g.drawString("Exit", (Game.WIDTH*Game.SCALE)/2 - 200, 300);
		if(options[curOption].equals("New Game")) {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 220, 200);
		}else if(options[curOption].equals("Load Game")) {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 220, 250);
		}else if(options[curOption].equals("Exit")) {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 220, 300);
		}
	}
	
}
