package TextAdventureGame;

//April 24, 2023
//Naoroj Farhan
//Item class


//using weapons drains mana

//note: not using a hashmap<string,Item> for item list because I want to be able to update the instance of the item when player 
//		chooses to upgrade their item with the altar

class Item{

	private String name = "";
	private String description = "";
	private int weight;
	
	private double damage = 0;				//only for weapons
	private double healthMultiplier = 0;	//only for armor
	
	private int level = 0;					//weapons or armor
	
	//health and mana potions just get player to maxHealth
	
	Item(String itemName){
		name = itemName;
		if (name == "sword") {
			description = "a sharp steel sword for fighting the monsters of the ruined city.";
			damage = 10;
			weight = 2;
			level = 0;
		}
		if (name == "crossbow") {
			description = "a crossbow to fight the monsters from a distance without taking damage.";
			damage = 5;
			weight = 2;
			level = 0;
		}
		if (name == "leather armor") {
			description = "Leather padded armor, not very strong.";
			healthMultiplier = 1.25;
			weight = 3;
			level = 0;
		}
		if (name == "chainmail armor") {
			description = "Chainmail armor, it is stronger than leather.";
			healthMultiplier = 1.5;
			weight = 5;
			level = 0;
		}
		
		if (name == "plate armor") {
			description = "Steel plate armor, foes tremble before the warrior clad in plate.";
			weight = 7;
			healthMultiplier = 2.0;
			level = 0;
		}
		if (name == "healing potion") {
			description = "Healing potions will get you back to max health.";
			weight = 2;
		}
		if (name == "mana potion") {
			description = "Mana potions will get you back to max stamina.";
			weight = 2;
		}
		if (name == "key") {
			description = "Looks like it can be used to open locked doors.";
			weight = 1;
		}
	}
	
	String getName(){return name;}
	String getDesc() {return description;}
	
	int getWeight() {return weight;}
	
	double getDamage() {return damage;}
	
	double getHealthMultiplier() {return healthMultiplier;}
	
	void itemProperties() {
		switch(name) {
		case "sword":case "crossbow":
			System.out.printf("level: %d, damage: %.2f, weight: %d", level, damage, weight);
			break;
		case "leather armor": case "chainmail armor": case "plate armor":
			System.out.printf("level: %d, health multiplier: %.2f, weight: %d", level, healthMultiplier, weight);
			break;
		case "healing potion": case "mana potion": case "key":
			System.out.printf("weight: %d", weight);
		}
	}
	
	//if you have enough souls, you can upgrade item at anvil
	void upgradeItem() {
		switch (name) {
		case "sword":case "crossbow":
			level++;
			damage+=3;
			break;
		case "leather armor": case "chainmail armor": case "plate armor":
			level++;
			healthMultiplier+=0.2;
			break;
		case "healing potion": case "mana potion": case "key":
			System.out.printf("You cannot upgrade that.");
		}
	}
	
}