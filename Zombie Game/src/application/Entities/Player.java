package application.Entities;

import javafx.scene.image.Image;

/**
 * player class that stores player's inventory
 *
 * @author Tang Zheng
 * @version 1125.3
 */
public class Player extends Entity {
	
	private static Image pUP = new Image("resources/pUP.png");
	private static Image pDOWN = new Image("resources/pDOWN.png");
	private static Image pLEFT = new Image("resources/pLEFT.png");
	private static Image pRIGHT = new Image("resources/pRIGHT.png");
	private static Image[] playerImage = new Image[]{pUP, pDOWN, pLEFT, pRIGHT};
	
	public static boolean hasFlippers = false;
	public static boolean hasFireBoots = false;
	public static boolean hasBat = false;
	public static boolean hasHatchet = false;
	
	public static int tokens;
	
	public static int greenKeys;
	public static int redKeys;
	public static int yellowKeys;
	
	/**
	 * Calls the parent classes constructor and sets the players x and y positions on the map.
	 *
	 * @param x The x position.
	 * @param y The y position.
	 */
	public Player(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Adds an extra token to the players token count.
	 */
	public static void addTokens() {
		tokens++;
	}
	
	/**
	 * Returns the player entity's image.
	 *
	 * @return The sprites of the player looking UP, LEFT, RIGHT, and DOWN.
	 */
	public static Image[] getPlayerImage() {
		return playerImage;
	}
	
	/**
	 * Get the amount of tokens the player entity currently has.
	 *
	 * @return The number of tokens collected.
	 */
	public static int getTokens() {
		return tokens;
	}
	
	/**
	 * Inherited abstract method from entities, however since the images are loaded using the getPlayerImage method.
	 * Always returns null.
	 *
	 * @return null.
	 */
	@Override
	public Image loadEntity() {
		return null;
	}
	
	
	//pick up a key
	
	/**
	 * Depending on which key is on the cell the number of that key is incremented by 1.
	 *
	 * @param type The type of key on the cell.
	 */
	public static void pickKey(String type) {
		switch (type) {
			case "greenKey":
				greenKeys++;
				break;
			case "redKey":
				redKeys++;
				break;
			case "yellowKey":
				yellowKeys++;
				break;
			default:
				break;
		}
	}
	
	/**
	 * Sets the player as having a bat.
	 */
	public static void pickBat() {
		hasBat = true;
	}
	
	/**
	 * Sets the player as not having a bat.
	 */
	public static void dropBat() {
		hasBat = false;
	}
	
	/**
	 * Sets the player as having a hatchet.
	 */
	public static void pickHatchet() {
		hasHatchet = true;
	}
	
	/**
	 * Sets the player as not having a hatchet.
	 */
	public static void dropHatchet() {
		hasHatchet = false;
	}
	
	/**
	 * Sets the player as having flippers.
	 */
	public static void pickFlippers() {
		hasFlippers = true;
	}
	
	/**
	 * Sets the player as having fire boots.
	 */
	public static void pickFireBoots() {
		hasFireBoots = true;
	}
	
	/**
	 * Checks how much of a certain item the player has.
	 *
	 * @param item The item type that is being checked.
	 * @return The number of the certain item the player has.
	 */
	public static int getItemNum(String item) {
		switch (item) {
			case "token":
				return Player.getTokens();
			case "greenKey":
				return Player.greenKeys;
			case "redKey":
				return Player.redKeys;
			case "yellowKey":
				return Player.yellowKeys;
			case "flippers":
				if (hasFlippers) {
					return 1;
				} else {
					return 0;
				}
			case "fireboots":
				if (hasFireBoots) {
					return 1;
				} else {
					return 0;
				}
			case "Lucille":
				if (hasBat) {
					return 1;
				} else {
					return 0;
				}
			case "hatchet":
				if (hasHatchet) {
					return 1;
				} else {
					return 0;
				}
			default:
				return 0;
		}
	}
	
	/**
	 * Reduces the number of certain key used when opening the same coloured door by 1.
	 *
	 * @param type Type of key used to open the door.
	 */
	//loses the key after opening the door
	public static void consumeKey(String type) {
		switch (type) {
			case "greenKey":
				greenKeys--;
				break;
			case "redKey":
				redKeys--;
				break;
			case "yellowKey":
				yellowKeys--;
				break;
			default:
				break;
		}
	}
	
	/**
	 * Checks if the player has a certain key.
	 *
	 * @param type The type of key that is being checked.
	 * @return True if the player has the type of key required, or false if the player doesn't have the key.
	 */
	public static boolean hasKey(String type) {
		switch (type) {
			case "greenKey":
				return greenKeys > 0;
			case "redKey":
				return redKeys > 0;
			case "yellowKey":
				return yellowKeys > 0;
			default:
				return false;
		}
	}
	
	/**
	 * Resets the players inventory to having no keys, tokens and none of the items.
	 */
	public static void reset() {
		hasFireBoots = false;
		hasFlippers = false;
		hasHatchet = false;
		hasBat = false;
		greenKeys = 0;
		yellowKeys = 0;
		redKeys = 0;
		tokens = 0;
	}
	
}
