package TextAdventureGame;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Arrays;



//April 24, 2023
//Naoroj Farhan (template sourced from Harwood brightspace)
//main class for text adventure game

public class MainGame {

	static HashMap<String,Room> roomList = new HashMap<String, Room>();
	static String currentRoom;
	static boolean isPlaying = true;
	static Player player = new Player();	
	
	
	
	static void setup() {
		
		Room.setupRooms(roomList);
		currentRoom = "Beach";  //where you start
	}
	
	
	public static void main(String[] args) {
		
		setup();
		
		System.err.print("========================================================    SOULS HUNTER    ========================================================\n\n");
		
		System.out.println("You wash up on a remote island, you were on a diplomatic mission to end a war between two "
				+ "kingdoms but got caught in a violent storm. \nIn the distance, you see ruined town, at it heart lies a castle.\n");
		
		System.out.println("Ambition rises in you as you realize you can become king if you storm the castle and seize it.\n");
		System.out.println("type help for for advice");
		
		lookAtRoom(true);
			
		
		while(isPlaying) {
	
			parseCommand(" "+getCommand()+" ");		//parse the string we get
			
			//if hp <= 0 -> game ends
			// if mp <= 0 -> in battle= die, else pass out from fatigue, lose 10 hp, mana upto full again
			
			if (player.getHealth() <= 0) {								//no health left
				System.err.println("YOU ARE VANQUISHED. GAME OVER.");
				System.exit(0);
			}
			
			if (currentRoom.equals("Castle")) {					//win condition
				System.err.println("YOU WIN");
				System.exit(0);
			}
			
			if (player.getMana() <= 0) {						//no mana left
				System.out.println("You drop from fatique, losing 10 health. You wake up with full stamina");
				player.setHealth(player.getHealth()-10);
				player.setMana(player.maxMana);
			}
			
			if (player.getHealth() <=5) {								//dying
				System.out.println("You are on the verge of death.");
			}
			
			if (player.getMana() <= 5) {								//exhausted
				System.out.println("You are beginning to feel tired.");
			}
			
			
		}
	}
	
	//method to get movements from player
	static Scanner sc = new Scanner(System.in);
	static String getCommand() {
		
		System.out.println();
		System.out.print("=> ");		
		String text = sc.nextLine();
		System.out.println();
		if (text.length() == 0) text = "qwerty"; //default command		
		return text;
	}
	
	
	//parse player input
	static void parseCommand(String text) {	
		
		
		//replace with synonyms
		text = text.replaceAll(" pick up "," take ");
		text = text.replaceAll(" search ", " take ");
		text = text.replaceAll(" armour ", " armor ");
		text = text.replaceAll(" examine ", " look at ");
		
		text= text.toLowerCase().trim();
		String[] command = text.split(" ");	
		
		Room rm = roomList.get(currentRoom);
		
		switch(command[0]) {
		case "help":
			System.out.println("use commands like north, south, west, east to move around"
					+ ", inventory to open your inventory, open/unlock to open chests or rooms, \n"
					+ "attack when fighting enemies, stats to view player stats. The souls you collect from defeating"
					+ " enemies can be used to buy items or use altars,\nbonfires, or anvils."
					+ " To win, you must enter the castle and take your place as the new king.");
			break;
		
		case "north":case "west":case "east":case "south":case "n": case "s": case "w": case "e":
			moveToRoom(command[0].charAt(0));
			break;
			
		case "inventory": case "i":
			openInventory();
			break;
		
		case "open": case "unlock":
			
			if (command.length == 1) {System.out.println("open what?"); break;}
				
			if (command[1].equals("chest")) {
				if (roomList.get(currentRoom).getHasChest()) {
					openChest();
				}
				else {
					System.out.println("There is no chest here.");
				}
			}
			
			else {
				if (command.length == 3) {command[1] += " "+command[2];}
				unlockRoom (command[1]);
			}
			break;
			
		case "take":
			if (command.length == 1) {System.out.println("take what?"); break;}
			
			if (roomList.get(currentRoom).roomItem == null) {System.out.println("There is no item here to pick up."); break;}
			
			if (command.length == 3) {command[1]+=" "+command[2];}					//covers cases like leather armor
			
			if (command[1].equals( roomList.get(currentRoom).roomItem.getName())) {
				
				takeItem(roomList.get(currentRoom).roomItem);
				break;
				
			}
			else {
				System.out.println("You cannot take "+command[1]);
			}
			break;
			
		case "remove": case "drop":
			if (command.length == 1) {System.out.println("remove what?"); break;}
			
			if (command.length == 3) {command[1]+=" "+command[2];}					//covers cases like leather armor
			
			removeItem(command[1]);
			break;
			
			
		case "use":
			if (command.length == 1) {System.out.println("use what?"); break;}
			
			if (command[1].equals("anvil")) {
				useAnvil();
			}
			else if (command[1].equals("altar")) {
				useAltar();
			}
			else if (command[1].equals("bonfire")) {
				useBonfire();
			}
			else if (command[1].equals("healing")) {
				usePotion("healing potion");
			}
			else if (command[1].equals("mana")) {
				usePotion("mana potion");
			}
			else {
				System.out.println(command[1]+" cannot be used at this time.");
			}
			break;
			
		case "equip": case "wear":
			if (command.length == 1) {System.out.println("equip what?"); break;}
			if (command.length == 3) { command[1] += " "+command[2];}
			
			equipItem(command[1]);
			break;
			
		case "buy":
			if (command.length == 1) {System.out.println("buy what?");break;}
			
			if (roomList.get(currentRoom).getTitle().equals("Marketplace")) {
				if (player.getSouls() >= 3 && player.getInventoryWeight() + 2 <=15) {
					if (command[1].equals("healing")) {
						player.inventory.add(new Item("healing potion"));
						player.setSouls(player.getSouls()-3);
						player.setInventoryWeight(player.getInventoryWeight()+2);
						System.out.println("Bought healing potion.");
					}
					else if (command[1].equals("mana")) {
						player.inventory.add(new Item("mana potion"));
						player.setSouls(player.getSouls()-3);
						player.setInventoryWeight(player.getInventoryWeight()+2);
						System.out.println("Bought mana potion.");
					}
					else {
						System.out.println("You cannot buy "+command[1]);
					}
				}
				else {
					System.out.println("Either your inventory is too full or you do not have enough souls to buy anything");
				}
			}
			else {
				System.out.println("You cannot buy anything here.");
			}
			break;
			
		case "stats":
			player.getStats();
			break;
		
		case "attack":
			if (rm.getHasEnemy()) {
				attack();
			}
			else {
				System.out.println("There is no enemy to attack.");
			}
			break;
			
		case "read":
			if (rm.getTitle().equals("Library")) {
				RandomChanceFactory.randomTip();
				break;
			}
			
		case "look":
			
			//if we are examining an object
			if (command.length >= 2) {
				if (command[1].equals("at")) {
					if (command.length == 2) {System.out.println("Look at what?");break;}
					if (command.length == 4) {
						command[2] += " "+command[3];
					}
					lookAtItem(command[2]);
				}
				
				break;
			}
			
			lookAtRoom(true);
			if (rm.getHasChest()) {
				System.out.println("You spot a chest in the " +rm.getTitle());
			}
			if (rm.getHasEnemy()) {
				System.out.println("There is a "+ rm.roomEnemy.getName()+" in the "+rm.getTitle());
			}
			break;
			
		default:
			System.out.println("I do not understand this command.");
			break;
		
		}
		
		
	}
	
	//crashes if move to room that does not exist
	static void moveToRoom(char dir) {
		
		String newRoom = roomList.get(currentRoom).getExit(dir);
		
		if (newRoom.length()==0) {
			System.out.println("You can't go that way.");
			return;
		}
		
		//cannot enter room if locked
		if (roomList.get(newRoom).getRoomLock() && !newRoom.equals("Castle")) {
			System.out.println("the "+newRoom+" is locked, you need to OPEN the room with a key");	
			return;
		}
		
		//can't enter castle without having 100 souls
		if (newRoom.equals("Castle")) {
			if (player.getSouls() < 100) {
				System.out.println("The spirits of the island will not let you become king until you have collected 100 souls.");
				return;
			}
		}
		//can't enter tunnels if you can't pay souls tax
		if (newRoom.equals("Southwest Tunnel") || newRoom.equals("Southeast Tunnel")) {
			if (player.getSouls()<1) {
				System.out.println("You do not have enough souls to pay the decripit old man's tax.");
				return;
			}
			player.setSouls(player.getSouls()-1);
		}
		
		roomList.get(currentRoom).roomItem = null;
		roomList.get(currentRoom).setHasChest(false);		//removing chest from currentRoom
		roomList.get(currentRoom).setHasEnemy(false);
		
		currentRoom = newRoom;								//you are now in the new room
		player.setMana(player.getMana()-1);
		lookAtRoom(false);
		

		
		//chests will not spawn in some rooms, enemy guaranteed to spawn in rooms other than the rooms below
		if (!currentRoom.equals( "Sanctuary") &&
			!currentRoom.equals ("Castle") &&
			!currentRoom.equals("Southwest Tunnel") &&
			!currentRoom.equals("Southeast Tunnel" )) {
			roomList.get(currentRoom).setHasChest(RandomChanceFactory.spawnChest());
			roomList.get(currentRoom).setHasEnemy(RandomChanceFactory.spawnEnemy());
		}
		
		if (roomList.get(currentRoom).getHasEnemy()) {
			roomList.get(currentRoom).roomEnemy = RandomChanceFactory.addEnemy();
		}
		
	}
	
	//open chest in room
	private static void openChest() {
		Item addItem = RandomChanceFactory.openChest();					//if item inside chest, add item to room inventory
		roomList.get(currentRoom).roomItem = addItem;
		roomList.get(currentRoom).setHasChest(false);
	}

	//pick up item
	private static void takeItem(Item pickUpItem) {
		
		if (player.getInventoryWeight() + roomList.get(currentRoom).roomItem.getWeight() <= 15 ) {//can player add item to inventory?
			
			System.out.println("picked up "+ roomList.get(currentRoom).roomItem.getName());
			player.inventory.add(roomList.get(currentRoom).roomItem);
			player.setInventoryWeight(player.getInventoryWeight() + roomList.get(currentRoom).roomItem.getWeight());
			roomList.get(currentRoom).roomItem = null;
		}
		else {
			System.out.println("Your inventory is too full to add more items. REMOVE an item from your inventory first.");
		}
	}
	
	//remove item from inventory
	private static void removeItem(String itemName) {
		
		boolean removed = false;												//flag just checks if there is an item to be removed
		for (int i = 0; i < player.inventory.size(); i++) {
			if (itemName.equals(player.inventory.get(i).getName())) {
				
				player.setInventoryWeight(player.getInventoryWeight() - player.inventory.get(i).getWeight());
				
				if (player.equippedWeapon != null) {
					if (player.equippedWeapon.getName().equals(itemName)) {
						player.equippedWeapon = null;
					}
				}
				if (player.equippedArmor != null) {
					if (player.equippedArmor.getName().equals(itemName)) {
						player.equippedArmor = null;
					}
				}
				player.inventory.remove(i);
				System.out.println("Removed "+itemName);
				removed = true;
				break;
			}
		}
		if (!removed) {
			System.out.println(itemName + " does not exist in your inventory.");
		}
	}
	
	//look at item
	private static void lookAtItem(String itemName) {
		
		boolean looked = false;												//flag just checks if there is an item to be checked
		for (int i = 0; i < player.inventory.size(); i++) {
			if (itemName.equals(player.inventory.get(i).getName())) {
				System.out.println(player.inventory.get(i).getDesc());
				player.inventory.get(i).itemProperties();
				System.out.println();
				looked = true;
				break;
			}
		}
		if (!looked) {
			System.out.println(itemName + " does not exist in your inventory.");
		}
	}
	
	private static void usePotion(String potionName) {				//method for using potion if in inventory
		
		for (int i = 0; i < player.inventory.size(); i++) {
			
			if (player.inventory.get(i).getName().equals(potionName)) {
				player.setInventoryWeight(player.getInventoryWeight() - player.inventory.get(i).getWeight());
				player.inventory.remove(i);
				
				if (potionName.equals("healing potion")) {player.setHealth(player.maxHealth);}
				if (potionName.equals("mana potion")) {player.setMana(player.maxMana);}
				
				System.out.println("Used "+potionName);
				return;
			}
		}
		System.out.println("You do not have this potion in your inventory.");
	}
	
	private static void equipItem(String equipItem) {
		
		//equipping or unequipping armor augments health, no effect on mana 
		
		int equipItemIndex = -1;
		
		for (int i = 0; i < player.inventory.size(); i++) {
			if (equipItem.equals(player.inventory.get(i).getName())) {
				equipItemIndex = i;
				break;
			}
		}
		
		if (equipItemIndex != -1) {							//if this val is not -1, it does exist
			
			if (equipItem.equals("sword") || equipItem.equals("crossbow")) {			//weapon equip
				player.equippedWeapon = player.inventory.get(equipItemIndex);
				System.out.println("You have equipped a " +equipItem);
			}
			else if (equipItem.equals("leather armor") || equipItem.equals("chainmail armor") || equipItem.equals("plate armor")) {
				//armor equip, changes max health stats
				
				if (player.equippedArmor != null) {
					player.maxHealth /=player.equippedArmor.getHealthMultiplier();
				}
				player.equippedArmor = player.inventory.get(equipItemIndex);
				player.maxHealth *= player.equippedArmor.getHealthMultiplier();
				System.out.println("You have equipped "+equipItem);
			}
			else {
				System.out.println("You cannot equip a "+ equipItem);
			}
		}
		else {
			System.out.println("You cannot equip that.");
		}
	
	}
	
	//unlock room if you have a key and you are adjacen to the room
	private static void unlockRoom(String newRoom) {

		boolean validRoom = false;
		
		Room r = roomList.get(currentRoom);
		
		if (r.getExit('n') != null) {
			if (newRoom.equals(r.getExit('n').toLowerCase())) {
				validRoom = true;
				newRoom = r.getExit('n');							// accounts for case specifity in roomList hashmap
			}
		}
		if (r.getExit('s') != null) {
			if (newRoom.equals(r.getExit('s').toLowerCase())) {
				validRoom = true;
				newRoom = r.getExit('s');							// accounts for case specifity in roomList hashmap
			}
		}
		if (r.getExit('w') != null) {
			if (newRoom.equals(r.getExit('w').toLowerCase())) {
				validRoom = true;
				newRoom = r.getExit('w');							// accounts for case specifity in roomList hashmap
			}
		}
		if (r.getExit('e') != null) {
			if (newRoom.equals(r.getExit('e').toLowerCase())) {
				validRoom = true;
				newRoom = r.getExit('e');							// accounts for case specifity in roomList hashmap
			}
		}
		
		if (!validRoom) {
			System.out.println("You cannot unlock "+newRoom+".");
			return;
		}
			
		boolean usedKey = false;
		
		for (int i = 0; i < player.inventory.size(); i++) {
			if (player.inventory.get(i).getName().equals("key")) {
				
				usedKey = true;
				roomList.get(newRoom).setRoomLock(false);
				System.out.println("You have unlocked the "+ newRoom);
				player.inventory.remove(i);
				player.setInventoryWeight(player.getInventoryWeight()-1);
				break;
			}
		}
		if (!usedKey) {
			
			System.out.println("You do not have a key to open "+newRoom+".");
		}
		
	}
	
	//open player inventory
	private static void openInventory() {
		
		if (player.inventory.size() == 0) {
			System.out.println("You have no items in your inventory.");
		}
		else {
			for (int i = 0; i < player.inventory.size(); i++) {
				System.out.print(player.inventory.get(i).getName()+"; ");
				player.inventory.get(i).itemProperties();
				System.out.println();
			}
		}
	}
	
	//look at room and what is in the room
	private static void lookAtRoom(boolean b) {
		Room rm = roomList.get(currentRoom);
		System.out.println("\n== " + rm.getTitle() + " ==");
		System.out.println(rm.getDesc());	
		
	}
	
	//use anvil to level up weapons/armor with souls
	private static void useAnvil() {
		
		//can only use in sanctuary or armory
		
		if (roomList.get(currentRoom).getTitle().equals("Sanctuary") ||
			roomList.get(currentRoom).getTitle().equals("Armory")) {
			
			System.out.println("Upgrading weapons or armor requires 5 souls. Type the name of the item in your inventory "
					+ "you want to upgrade.");
			
			String upgradeItem = getCommand().toLowerCase();
			int itemIndex = -1;
			for (int i = 0; i < player.inventory.size(); i++) {
				if (player.inventory.get(i).getName().equals(upgradeItem) && !upgradeItem.equals("healing potion") && !upgradeItem.equals("mana potion")) {
					itemIndex = i;
					break;
				}
			}
			
			if (itemIndex != -1 && player.getSouls() >= 5) {
				player.inventory.get(itemIndex).upgradeItem();
				System.out.println("You have successfully upgraded "+player.inventory.get(itemIndex).getName());
				player.setSouls(player.getSouls()-5);
			}
			else {
				System.out.println("You cannot upgrade the item because it either does not exist or you have not collected enough souls");
			}
			
			
		}
		else {
			System.out.println("The room you are in does not have an anvil to upgrade with.");
		}
	}
	
	//use bonfire to heal to max health and mana
	private static void useBonfire() {
		//can only use in sanctuary or tavern
		
		if (roomList.get(currentRoom).getTitle().equals("Sanctuary") ||
			roomList.get(currentRoom).getTitle().equals("Tavern")) {
			
			if (player.getSouls() >= 2) {
				System.out.println("You throw 2 souls into the bonfire, you regain all your lost health and mana.");
				player.setSouls(player.getSouls()-2);
				player.setHealth(player.maxHealth);
				player.setMana(player.maxMana);
			}
			else {
				System.out.println("You need two souls to throw to the bonfire, you do not have enough.");
			}
		}
		else {
			System.out.println("There is no bonfire here.");
		}
		
	}
	
	//use altar to heal to level up by using souls
	private static void useAltar() {
		// can only use in sanctuary
		
		if (roomList.get(currentRoom).getTitle().equals("Sanctuary")) {
			if (player.getSouls() >= 10) {
				System.out.println("You sacrifice 10 souls to the altar, and you level up.");
				player.setSouls(player.getSouls()-10);
				player.maxHealth +=5;
				player.maxMana +=5;
			}
			else {
				System.out.println("You need 10 souls to sacrifice to the altar to level up, you do not have enough.");
			}
		}
		else {
			System.out.println("There is no altar here.");
		}
	}
	
	//attack enemies if they are in the room
	private static void attack() {
		Room rm = roomList.get(currentRoom);
		player.setMana(player.getMana()-1);
		if (player.equippedWeapon == null) {
			
			System.out.println("You deal 1 damage to "+rm.roomEnemy.getName());
			rm.roomEnemy.setHealth(rm.roomEnemy.getHealth()-1);
			System.out.printf("%s attacks back and deals %d damage.\n",rm.roomEnemy.getName(),rm.roomEnemy.getDamage());
			player.setHealth(player.getHealth()-rm.roomEnemy.getDamage());
			
			if (rm.roomEnemy.getHealth() <= 0) {
				System.out.printf("You have defeated %s, you collect %d souls\n", rm.roomEnemy.getName(), rm.roomEnemy.getSoulVal());
				player.setSouls(player.getSouls()+rm.roomEnemy.getSoulVal());
				rm.roomEnemy = null;
			}
			return;
		}
		else {
			System.out.printf("You deal %.1f damage to %s,\n",player.equippedWeapon.getDamage(),rm.roomEnemy.getName());
			rm.roomEnemy.setHealth(rm.roomEnemy.getHealth()-player.equippedWeapon.getDamage());
		}
		
		if (rm.roomEnemy.getHealth() <= 0) {
			System.out.printf("You have defeated the %s, you collect %d souls\n", rm.roomEnemy.getName(), rm.roomEnemy.getSoulVal());
			player.setSouls(player.getSouls()+rm.roomEnemy.getSoulVal());
			rm.roomEnemy = null;
			rm.setHasEnemy(false);
			return;
		}
		
		if (player.equippedWeapon.getName().equals("sword")) {
			System.out.printf("%s attacks back and deals %d damage.\n",rm.roomEnemy.getName(),rm.roomEnemy.getDamage());
			player.setHealth(player.getHealth()-rm.roomEnemy.getDamage());
		}
		else {		//crossbow
			if (RandomChanceFactory.dodge()) {
				System.out.println(rm.roomEnemy.getName()+" tries to attack but cannot reach you because of you use the crossbow.");
			}
			else {
				System.out.printf("%s attacks back and deals %d damage.\n",rm.roomEnemy.getName(),rm.roomEnemy.getDamage());
				player.setHealth(player.getHealth()-rm.roomEnemy.getDamage());
			}
		}
	}
}