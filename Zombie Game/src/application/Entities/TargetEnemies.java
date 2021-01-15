package application.Entities;

import application.Level;

/**
 * Super Class of the enemies that target the player.
 * Sub Class of the Entity class.
 *
 * @author Nayan Shrees
 * @version 1.2.0
 */
public abstract class TargetEnemies extends Entity {
	
	/**
	 * Calls the parents classes constructor with the x and y co-ordinates.
	 *
	 * @param xPos The x co-ordinate.
	 * @param yPos The y co-ordinate.
	 */
	public TargetEnemies(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	/**
	 * Updates the position of the enemy every time the method is called.
	 *
	 * @param level  The current level of the game.
	 * @param player The current player in the game.
	 */
	public abstract void updatePosition(Level level, Player player);
}
