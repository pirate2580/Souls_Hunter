package TextAdventureGame;

import java.util.ArrayList;
import java.util.HashMap;

//April 24, 2023
//Naoroj Farhan (template sourced from Harwood brightspace)
//room class

class Room {
	

	private String title;									//Title of room
	private String description;								//description of room qualities
	private String N,S,E,W;									//Directions to go
	Item roomItem;
	Enemy roomEnemy;
	
	//decide whether chest spawns or not
	private boolean hasChest = false;			// get/set has chest
	
	private boolean hasEnemy = false;			// get/set has enemy
	
	private boolean isLocked = false;			//we want to be able to get/set isLocked, but the rooms only become unlocked
	
	Room(String t, String d) {
		title = t;
		description = d;
	}
	
	private void setExits(String N, String S, String W, String E) {
		this.N = N;
		this.S = S;
		this.W = W;
		this.E = E;		
	}
	
	String getExit(char c) {
		switch (c) {
		case 'n': return this.N;
		case 's': return this.S;
		case 'w': return this.W;
		case 'e': return this.E;
		default: return null;
		}
	}
	
	String getTitle() {return title;}
	String getDesc() {return description;}
	
	void setHasChest(boolean doesChestSpawn) { hasChest = doesChestSpawn;}
	boolean getHasChest() {return hasChest;}
	
	void setHasEnemy (boolean doesEnemySpawn) {hasEnemy = doesEnemySpawn;}
	boolean getHasEnemy() {return hasEnemy;}
	
	void setRoomLock (boolean newState) {isLocked = newState;}
	boolean getRoomLock () {return isLocked;}
	
	
	
	//ONLY done at the beginning of the game
	static void setupRooms(HashMap<String,Room> roomList) {
		// N S W E
		
		//Beach
		Room r = new Room("Beach"," The waves wash up against the shore of the beach, carrying away the remains of your ship. You see a small sanctuary to the north.");
		r.setExits("Sanctuary","","","");
		roomList.put("Beach", r);
		
		//Sanctuary
		r = new Room("Sanctuary", " You enter a small sanctuary; there is an anvil to level up weapons/armor, an altar to level up yourself (you doubt you will find another \naltar in such a place), and a bonfire to rest and regain health, you can USE any of them. Outside the sanctuary towards north, you see\nthe castle gates.");
		r.setExits("Castle Gate", "Beach", "", "");
		roomList.put("Sanctuary", r);
		
		//Castle gate
		r = new Room("Castle Gate", "The castle gates lie in front of you, you hear the moans of undead warriors inside the castle. In the north direction, you see the main courtyard.");
		r.setExits("Court Yard 1", "Sanctuary", "", "");
		roomList.put("Castle Gate", r);
		
		//Court Yard 1
		r = new Room("Court Yard 1", "You enter a narrow courtyard. To your west, you see the marketplace, and to your east you see the tavern. The courtyard extends north.");
		r.setExits("Court Yard 3", "Castle Gate", "Marketplace", "Tavern");
		r.hasEnemy = true;
		roomList.put("Court Yard 1", r);
		
		//Court Yard 3
		r = new Room("Court Yard 3", "You enter a narrow courtyard. The courtyard extends in all directions.");
		r.setExits("Court Yard 5","Court Yard 1","Court Yard 2", "Court Yard 4");
		roomList.put("Court Yard 3", r);
		
		
		//Marketplace
		r = new Room("Marketplace", "The marketplace is deserted except for a decripit old man who asks you if you want to buy health or mana potions. In the north and east directions, \nthere are courtyards.");
		r.setExits("Court Yard 2","","","Court Yard 1");
		r.isLocked = true;
		roomList.put("Marketplace", r);
		
		//Tavern
		r = new Room("Tavern", "You hope to find ale to quench your thirst, but all the ale barrels are empty. Fortunately, the tavern has a bonfire to rest. In the north and west \ndirections, there are courtyards.");
		r.setExits("Court Yard 4","","Court Yard 1","");
		r.isLocked = true;
		roomList.put("Tavern", r);
		
		//Court Yard 2
		r = new Room("Court Yard 2","You enter a narrow courtyard. To your north you see a library, to your west you see a tower, to your south is the marketplace, \nand to your east lies more courtyard.");
		r.setExits("Library", "Marketplace", "Southwest Tower", "Court Yard 3");
		roomList.put("Court Yard 2", r);
		
		//Court Yard 4
		r = new Room("Court Yard 4","You enter a narrow courtyard. To your north you see an armory, to your west lies more courtyard, to your south is the tavern, and to your east\n you see a tower.");
		r.setExits("Armory","Tavern","Court Yard 3","Southeast Tower");
		roomList.put("Court Yard 4", r);
		
		//Southwest Tower
		r = new Room("Southwest Tower","You enter the southwest tower, you see a decripit old man who will tariff 1 soul from you if you enter the tunnel. The tunnel is in the south direction.");
		r.setExits("", "Southwest Tunnel", "", "Court Yard 2");
		r.isLocked = true;						
		roomList.put("Southwest Tower", r);
		
		//Southeast Tower
		r = new Room("Southeast Tower","You enter the southeast tower, you see a decripit old man who will tariff 1 soul from you if you enter the tunnel. The tunnel is in the south direction.");
		r.setExits("", "Southeast Tunnel", "", "Court Yard 4");
		r.isLocked = true;					
		roomList.put("Southeast Tower", r);
		
		//Southwest Tunnel
		r = new Room("Southwest Tunnel","The tunnel is damp, towards the east, the tunnel leads to the beach.");
		r.setExits("Southwest Tower", "", "", "Beach");
		roomList.put("Southwest Tunnel", r);
		
		//Southeast Tunnel
		r = new Room("Southeast Tunnel","The tunnel is damp, towards the west, the tunnel leads to the beach.");
		r.setExits("Southeast Tower", "", "Beach", "");
		roomList.put("Southeast Tunnel", r);
		
		//Court Yard 5
		r = new Room("Court Yard 5","You enter a narrow courtyard. To your north lies the castle, the doors are locked and need two keys to open. To your west is the library, to your south\n lies more courtyard, and to your east is the armory.");
		r.setExits("Castle","Court Yard 3","Library","Armory");
		roomList.put("Court Yard 5", r);
		
		//Library
		r = new Room("Library","You enter a dusty libary. You see a book on the ground, you wish to read it. To your east and south, there lies courtyard.");
		r.setExits("","Court Yard 2","","Court Yard 5");
		r.isLocked = true;
		r.hasEnemy = true;
		roomList.put("Library", r);
		
		//Armory
		r = new Room("Armory","You enter the armory. You see an anvil to repair your weapons and armor. To your west and south, there lies courtyard.");
		r.setExits("","Court Yard 4","Court Yard 5","");
		r.isLocked = true;
		roomList.put("Armory", r);
		
		//Castle
		r = new Room("Castle","After unlocking the castle door, you head in. You take your place as the king of the ruined city.");
		r.setExits("","Court Yard 5","","");
		r.isLocked = true;
		roomList.put("Castle", r);
	}
}