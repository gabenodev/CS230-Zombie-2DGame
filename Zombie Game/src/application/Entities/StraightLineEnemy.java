package application.Entities;

import application.Cells.Cell;
import application.Level;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Creates an enemy that moves in a straight direction and reverses the direction when hitting an object.
 * Sub Class of SimpleEnemies therefore also a sub class of Entities.
 *
 * @author Nayan Shrees
 * @version 2.1.0
 */
public class StraightLineEnemy extends SimpleEnemies {
	private static final String IMAGE_LOCATION = "src/resources/StraightLine.png";
	
	/**
	 * Calls the parents constructor setting the x and y position and sets the initial direction of the enemy.
	 *
	 * @param xPos      The x position.
	 * @param yPos      The y position.
	 * @param direction The string with the initial direction of the enemy from the level file.
	 */
	public StraightLineEnemy(int xPos, int yPos, String direction) {
		super(xPos, yPos);
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
	 * Updates the enemy's position to travel in one straight direction.
	 * Reverses the direction if the enemy is about to collide with a cell that isn't a ground cell.
	 *
	 * @param level The current level.
	 */
	@Override
	public void updatePosition(Level level) {
		ArrayList<ArrayList<Cell>> cells = level.getCells();
		if (getDirection() == goUP()) {
			if (checkTraversable(cells, getxPos(), getyPos() - 1)) {
				setyPos(getyPos() - 1);
			} else {
				super.reverseDirection();
			}
		} else if (getDirection() == goRIGHT()) {
			if (checkTraversable(cells, getxPos() + 1, getyPos())) {
				setxPos(getxPos() + 1);
			} else {
				super.reverseDirection();
			}
		} else if (getDirection() == goDOWN()) {
			if (checkTraversable(cells, getxPos(), getyPos() + 1)) {
				setyPos(getyPos() + 1);
			} else {
				super.reverseDirection();
			}
		} else if (getDirection() == goLEFT()) {
			if (checkTraversable(cells, getxPos() - 1, getyPos())) {
				setxPos(getxPos() - 1);
			} else {
				super.reverseDirection();
			}
		}
	}
}
