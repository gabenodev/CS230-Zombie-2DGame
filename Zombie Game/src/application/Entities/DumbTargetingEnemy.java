package application.Entities;

import application.Cells.Cell;
import application.Level;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Creates an enemy that will always try and get closer to the player regardless if there is an obstacle in the way.
 *
 * @author Nayan Shrees
 * @version 2.9.0
 */
public class DumbTargetingEnemy extends TargetEnemies {
	private static final String IMAGE_LOCATION = "src/resources/DumbTarget.png";
	
	/**
	 * Calls the parent classes constructor with the x and y position of the enemy.
	 *
	 * @param xPos The x position of the enemy.
	 * @param yPos The y position of the enemy.
	 */
	public DumbTargetingEnemy(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	/**
	 * Loads the enemy's sprite.
	 *
	 * @return Image with the enemy's sprite loaded or null if the enemy's sprite fails to load.
	 */
	@Override
	public Image loadEntity() {
		try {
			Image entityImage = new Image(new FileInputStream(IMAGE_LOCATION));
			return entityImage;
		} catch (FileNotFoundException e) {
			System.out.println("Image file not found");
			return null;
		}
	}
	
	/**
	 * Checks which of the x or y positions is greater in difference between the player and the enemy.
	 * Whichever of the x and y is greater the enemy moves one cell closer if possible.
	 * This can lead to the enemy being stuck on obstacles which is intended.
	 * If the difference between the player and enemy is equal on both the x and y co-ordinates,
	 * the enemy prefers to move in the y axis.
	 *
	 * @param level  The current level of the game.
	 * @param player The current player in the game.
	 */
	@Override
	public void updatePosition(Level level, Player player) {
		ArrayList<ArrayList<Cell>> cells = level.getCells();
		if (Math.abs(player.getyPos() - getyPos()) >= Math.abs(player.getxPos() - getxPos())) {
			if (player.getyPos() > getyPos()) {
				checkMovePos(cells, getxPos(), getyPos() + 1);
			} else {
				checkMovePos(cells, getxPos(), getyPos() - 1);
			}
		} else if (Math.abs(player.getxPos() - getxPos()) > Math.abs(player.getyPos() - getyPos())) {
			if (player.getxPos() > getxPos()) {
				checkMovePos(cells, getxPos() + 1, getyPos());
			} else {
				checkMovePos(cells, getxPos() - 1, getyPos());
			}
		}
	}
	
	/**
	 * Checks if the enemy can move into the cell x and y, and if it is possible will move to position x and y.
	 * If it's not possible to move it does nothing.
	 *
	 * @param cells The 2D ArrayList of the current level.
	 * @param x     The x co-ordinate that is being checked/the enemy is moving to.
	 * @param y     The y co-ordinate that is being checked/the enemy is moving to.
	 */
	private void checkMovePos(ArrayList<ArrayList<Cell>> cells, int x, int y) {
		if (super.checkTraversable(cells, x, y)) {
			setxPos(x);
			setyPos(y);
		}
	}
}
