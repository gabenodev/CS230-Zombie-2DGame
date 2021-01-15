package application.Entities;

import application.Level;

/**
 * Super class of the simple enemies that aren't affected by the player.
 * Sub class of the entities class
 *
 * @author Nayan Shrees
 * @version 1.6.0
 */
public abstract class SimpleEnemies extends Entity {
	private static final int UP = 0; /*The direction for going up is set to the value of 0 */
	private static final int RIGHT = 1; /*The direction for going right is set to the value of 1*/
	private static final int DOWN = 2; /*The direction for going down is set to the value of 2*/
	private static final int LEFT = 3; /*The direction for going left is set to the value of 3*/
	private int direction;
	
	/**
	 * Calls the parent classes constructor with the required parameters (it's x and y co-ordinates).
	 *
	 * @param xPos The x co-ordinate of the simple enemy.
	 * @param yPos The y co-ordinate of the simple enemy.
	 */
	public SimpleEnemies(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	/**
	 * Used to return the value of the constant UP.
	 *
	 * @return The constant UP which is the value 0.
	 */
	public static int goUP() {
		return UP;
	}
	
	/**
	 * Used to return the value of the constant RIGHT.
	 *
	 * @return The constant RIGHT which is the value 1.
	 */
	public static int goRIGHT() {
		return RIGHT;
	}
	
	/**
	 * Used to return the value of the constant DOWN.
	 *
	 * @return The constant DOWN which is the value 2.
	 */
	public static int goDOWN() {
		return DOWN;
	}
	
	/**
	 * Used to return the value of the constant LEFT.
	 *
	 * @return The constant LEFT which is the value 3.
	 */
	public static int goLEFT() {
		return LEFT;
	}
	
	/**
	 * Used to get the direction the enemy is moving in.
	 *
	 * @return The directional value of the enemy.
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Sets new direction of the enemy.
	 *
	 * @param direction The new directional value of the enemy.
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	/**
	 * Updates the position of the enemy every time the method is called.
	 *
	 * @param level The current level.
	 */
	public abstract void updatePosition(Level level);
	
	/**
	 * Reverses the direction to the opposite of its current direction.
	 */
	public void reverseDirection() {
		if (direction == UP) {
			direction = DOWN;
		} else if (direction == DOWN) {
			direction = UP;
		} else if (direction == RIGHT) {
			direction = LEFT;
		} else if (direction == LEFT) {
			direction = RIGHT;
		}
	}
	
}
