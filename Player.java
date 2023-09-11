package TextAdventureGame;

import java.util.ArrayList;

//April 24, 2023
//Naoroj Farhan
//player class

public class Player{
	
	Player(){
		
	}
	
	ArrayList<Item> inventory = new ArrayList<Item>();			
	
	private int inventoryWeight = 0;							//maxweight is just 15
	
	//player stats
	double maxHealth = 20;
	private double currHealth = 20;
	
	double maxMana = 20;
	private double currMana = 20;
	
	//the weapon and armor the player has equipped from their inventory
	Item equippedWeapon;
	Item equippedArmor;
	
	//currency of the game, use it to interact with altars, bonfires, and anvils, etc
	private int soulsCollected = 0;
	
	void setInventoryWeight (int newWeight) {inventoryWeight = newWeight;}
	
	int getInventoryWeight () {return inventoryWeight;}
	
	void setHealth(double newHealth) {currHealth = newHealth;}
	double getHealth() {return currHealth;}
	
	void setMana(double newMana) {currMana = newMana;}
	double getMana() {return currMana;}
	
	void setSouls(int newSouls) { soulsCollected = newSouls;}
	int getSouls() {return soulsCollected;}
	
	void getStats() {
		System.out.printf("max health: %.1f, current health: %.1f\n", maxHealth, currHealth);
		System.out.printf("max mana: %.1f, current mana: %.1f\n", maxMana, currMana);
		System.out.println("souls collected: "+soulsCollected);
		
		if (equippedWeapon == null) {
			System.out.println("equipped weapon: fist");
		}
		else {
			System.out.println("equipped weapon: "+equippedWeapon.getName());
		}
		
		if (equippedArmor == null) {
			System.out.println("equipped armor: none");
		}
		else {
			System.out.println("equipped armor: "+equippedArmor.getName());
		}
	}
}