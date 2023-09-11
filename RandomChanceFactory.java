package TextAdventureGame;

import java.util.ArrayList;
import java.util.HashMap;

//April 24, 2023
//Naoroj Farhan
//a class to generate random chance of chest spawn, enemy stats, what is inside a chest, etc

public class RandomChanceFactory{
	
	//random tip if you read book in the libary
	static void randomTip() {
		int randomNum = (int)(6*Math.random())+1;
		
		if (randomNum == 1) {
			System.out.println("There is a decripit old man in the marketplace selling health and mana potions.");
		}
		if (randomNum == 2) {
			System.out.println("There is a decripit old man who will let you enter a tunnel leading back to the beach for a 1 soul tax.");
		}
		if (randomNum == 3) {
			System.out.println("You can upgrade your weapons or armor at the armory in addition to the sanctuary.");
		}
		if (randomNum == 4) {
			System.out.println("The tavern has a bonfire for you to rest in addition to the sanctuary.");
		}
		if (randomNum == 5) {
			System.out.println("The only place you can level up is at the altar and the cost is 10 souls");
		}
		if (randomNum == 6) {
			System.out.println("There are three type of enemies in the ruined city: militia, swordsman, and knight. The more difficult the opponent, the more souls they are worth.");
		}
	}
	
	static boolean spawnChest() {							//method to decide if a chest spawns in this room
		
		//50% chance that a chest will spawn in a room
		int randomNum = (int)(2*Math.random())+1;
		if (randomNum == 2) {
			System.out.println("You spot a chest in the " +MainGame.roomList.get(MainGame.currentRoom).getTitle());
			return true;
		}
		return false;
	}
	
	static boolean spawnEnemy() {
		
		int randomNum = (int)(2*Math.random())+1;
		
		if (randomNum == 2) {
			return true;
		}
		return false;
	}
	
	static Enemy addEnemy() {
		int randomNum = (int)(10*Math.random())+1;
		
		if (randomNum<=5) {
			System.out.println("There is a militia in the "+MainGame.roomList.get(MainGame.currentRoom).getTitle());
			Enemy y = new Enemy ("militia");
			return y;
		}
		if (randomNum<=8) {
			System.out.println("There is a swordsman in the "+MainGame.roomList.get(MainGame.currentRoom).getTitle());
			Enemy y = new Enemy ("swordsman");
			return y;
		}
		else {
			System.out.println("There is a knight in the "+MainGame.roomList.get(MainGame.currentRoom).getTitle());
			Enemy y = new Enemy ("knight");
			return y;
		}
	}
	
	static Item openChest(){							//method to choose what item drops from chest randomly
		
		int randomNum = (int)(50*Math.random())+1;
		if (randomNum<=5) {
			//dropped item is sword
			System.out.println("You open the chest. A sword is inside.");
			Item z = new Item("sword");
			return z;
			
		}
		else if (randomNum<=10) {
			//dropped item is crossbow
			System.out.println("You open the chest. a crossbow is inside.");
			Item z = new Item("crossbow");
			return z;
		}
		else if (randomNum<=15) {
			//dropped item is leather armor
			System.out.println("You open the chest. Leather armor is inside.");
			Item z = new Item("leather armor");
			return z;
		}
		else if (randomNum<=17) {
			//dropped item is chainmail armor
			System.out.println("You open the chest. Chainmail armor is inside.");
			Item z = new Item("chainmail armor");
			return z;
		}
		else if (randomNum<=18) {
			//dropped item is plate armor
			System.out.println("You open the chest. Plate armor is inside.");
			Item z = new Item("plate armor");
			return z;
		}
		else if (randomNum<=24) {
			//dropped item is healing potion
			System.out.println("You open the chest. A healing potion is inside.");
			Item z = new Item("healing potion");
			return z;
		}
		else if (randomNum<=30) {
			//dropped item is mana potion
			System.out.println("You open the chest. A mana potion is inside.");
			Item z = new Item("mana potion");
			return z;
		}
		else if (randomNum<=45) {
			//dropped item is key
			System.out.println("You open the chest. A key is inside.");
			Item z = new Item ("key");
			return z;
		}
		else {
			//no item is dropped
			System.out.println("You open the chest. Nothing is inside.");
			return null;
		}
	}
	
	static boolean dodge() {				//crossbow gives a chance of dodging when attacking enemy
		int randomNum = (int)(2*Math.random())+1;
		
		if (randomNum == 2) {
			return false;
		}
		return true;
	}
	
}