package TextAdventureGame;

class Enemy{ 
	
	private String name = "";
	private double health = 0;
	private int damage = 0;
	private int soulVal = 0;
	
	
	Enemy(String enemyName){
		name = enemyName;
		switch(name) {
		case "militia":
			health = 15;
			damage = 5;
			soulVal = 2;
			break;
		case "swordsman":
			health = 20;
			damage = 10;
			soulVal = 5;
			break;
		case "knight":
			health = 30;
			damage = 15;
			soulVal = 15;
			break;
		}
	}
	
	String getName() {return name;}
	
	int getDamage() {return damage;}
	
	void setHealth(double newHealth) {health = newHealth;}
	
	double getHealth() {return health;}
	
	int getSoulVal() {return soulVal;}
	
}