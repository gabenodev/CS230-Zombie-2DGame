package application.Entities;

import application.Cells.Cell;
import application.Level;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Creates an enemy that follows the wall so if possible the enemy will always turn left or right at a junction.
 * Sub class of SimpleEnemies therefore also a Sub class to Entities.
 *
 * @author Nayan Shrees
 * @version 2.5.0
 */
public class WallFollowingEnemy extends SimpleEnemies {
	private static final String IMAGE_LOCATION = "src/resources/WallFollowing.png";
	private boolean followLeft;
	
	/**
	 * Calls the parents constructor to set the x and y position of the wall following enemy.
	 * Also sets the initial direction the enemy should move in when the enemy is instantiated.
	 * Also sets whether the enemy should always follow the left or the right wall.
	 *
	 * @param xPos      The x position of the enemy.
	 * @param yPos      The y position of the enemy.
	 * @param direction The initial direction the level declares for the enemy.
	 */
	public WallFollowingEnemy(int xPos, int yPos, String direction) {
		super(xPos, yPos);
		Random randomiser = new Random();
		if (randomiser.nextInt(1 + 1) == 0) {
			this.followLeft = true;
		} else {
			this.followLeft = false;
		}
		if (direction.equals("UP")) {
			setDirection(goUP());
		} else if (direction.equals("RIGHT")) {
			setDirection(goRIGHT());
		} else if (direction.equals("DOWN")) {
			setDirection(goDOWN());
		} else if (direction.equals("LEFT")) {
			setDirection(goLEFT());
		}
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
	 * Updates the position of the enemy to move in a straight direction unless there is a junction.
	 * If the enemy is set to follow the left wall, at every left junction it will take the left turn.
	 * If the enemy is set to follow the right wall, at every right junction it will take the right turn.
	 * If the opposite junction to the wall is available the enemy will carry on straight.
	 * It will carry on in the same direction if no junction is available until the enemy cannot move in that direction.
	 * In which case the direction will be reversed.
	 * Whether the enemy moves left or right will be kept the same regardless after the enemy is instantiated.
	 *
	 * @param level The current level.
	 */
	@Override
	public void updatePosition(Level level) {
		ArrayList<ArrayList<Cell>> cells = level.getCells();
		if (followLeft) {
			if (!traverseLeft(cells)) {
				if (!traverseStraight(cells)) {
					if (!traverseRight(cells)) {
						super.reverseDirection();
					}
				}
			}
		} else {
			if (!traverseRight(cells)) {
				if (!traverseStraight(cells)) {
					if (!traverseLeft(cells)) {
						super.reverseDirection();
					}
				}
			}
		}
	}
	
	/**
	 * Changes the enemy's position to continue moving in the set direction.
	 *
	 * @param cells 2D Array List of the levels map.
	 * @return If the enemy was successful in moving straight returns true, if the enemy wasn't it returns false.
	 */
	private boolean traverseStraight(ArrayList<ArrayList<Cell>> cells) {
		if (getDirection() == goUP()) {
			if (checkTraversable(cells, getxPos(), getyPos() - 1)) {
				setyPos(getyPos() - 1);
				return true;
			}
		} else if (getDirection() == goRIGHT()) {
			if (checkTraversable(cells, getxPos() + 1, getyPos())) {
				setxPos(getxPos() + 1);
				return true;
			}
		} else if (getDirection() == goDOWN()) {
			if (checkTraversable(cells, getxPos(), getyPos() + 1)) {
				setyPos(getyPos() + 1);
				return true;
			}
		} else if (getDirection() == goLEFT()) {
			if (checkTraversable(cells, getxPos() - 1, getyPos())) {
				setxPos(getxPos() - 1);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Changes the enemy's x and y position to go left of its current direction.
	 * Then sets the direction to the left of its current heading.
	 *
	 * @param cells 2D Array List of the current levels map.
	 * @return If the move to the left was successful returns true, if not it returns false.
	 */
	private boolean traverseLeft(ArrayList<ArrayList<Cell>> cells) {
		if (getDirection() == goUP()) {
			if (checkTraversable(cells, getxPos() - 1, getyPos())) {
				setxPos(getxPos() - 1);
				setDirection(goLEFT());
				return true;
			}
		} else if (getDirection() == goRIGHT()) {
			if (checkTraversable(cells, getxPos(), getyPos() - 1)) {
				setyPos(getyPos() - 1);
				setDirection(goUP());
				return true;
			}
		} else if (getDirection() == goDOWN()) {
			if (checkTraversable(cells, getxPos() + 1, getyPos())) {
				setxPos(getxPos() + 1);
				setDirection(goRIGHT());
				return true;
			}
		} else if (getDirection() == goLEFT()) {
			if (checkTraversable(cells, getxPos(), getyPos() + 1)) {
				setyPos(getyPos() + 1);
				setDirection(goDOWN());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Changes the enemy's x and y position to go right of its current direction.
	 * Then sets the direction to the right of its current heading.
	 *
	 * @param cells 2D Array List of the current levels map.
	 * @return If the move to the right was successful returns true, if not it returns false.
	 */
	private boolean traverseRight(ArrayList<ArrayList<Cell>> cells) {
		if (getDirection() == goUP()) {
			if (checkTraversable(cells, getxPos() + 1, getyPos())) {
				setxPos(getxPos() + 1);
				setDirection(goRIGHT());
				return true;
			}
		} else if (getDirection() == goRIGHT()) {
			if (checkTraversable(cells, getxPos(), getyPos() + 1)) {
				setyPos(getyPos() + 1);
				setDirection(goDOWN());
				return true;
			}
		} else if (getDirection() == goDOWN()) {
			if (checkTraversable(cells, getxPos() - 1, getyPos())) {
				setxPos(getxPos() - 1);
				setDirection(goLEFT());
				return true;
			}
		} else if (getDirection() == goLEFT()) {
			if (checkTraversable(cells, getxPos(), getyPos() - 1)) {
				setyPos(getyPos() - 1);
				setDirection(goUP());
				return true;
			}
		}
		return false;
	}
}
