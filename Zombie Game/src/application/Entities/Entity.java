package application.Entities;

import application.Cells.Cell;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Super class of all entities classes.
 *
 * @author Nayan Shrees
 * @version 1.1.0
 */

public abstract class Entity {
	private int xPos;
	private int yPos;
	
	/**
	 * Creates an entity with a specified x and y co-ordinate.
	 *
	 * @param xPos The x co-ordinate.
	 * @param yPos The y co-ordinate.
	 */
	public Entity(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/**
	 * Get the x co-ordinate of the entity.
	 *
	 * @return The x co-ordinate.
	 */
	public int getxPos() {
		return xPos;
	}
	
	/**
	 * Set the x co-ordinate of the entity.
	 *
	 * @param xPos The new x co-ordinate.
	 */
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	
	/**
	 * Get the y co-ordinate of the entity.
	 *
	 * @return The y co-ordinate.
	 */
	public int getyPos() {
		return yPos;
	}
	
	/**
	 * Set the new y co-ordinate of the entity.
	 *
	 * @param yPos The new y co-ordinate.
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	
	/**
	 * Loads the entities sprite.
	 *
	 * @return An image type with the sprite of the entity loaded in the image.
	 */
	public abstract Image loadEntity();
	
	/**
	 * Mainly used for the enemy classes and it checks if a certain cell is a ground cell.
	 *
	 * @param cells The map of the level as a 2D ArrayList.
	 * @param x     The x value of the co-ordinate that is being checked.
	 * @param y     The y value of the co-ordinate that is being checked.
	 * @return True if the cell is a ground cell, false if it's any other type of cell.
	 */
	public boolean checkTraversable(ArrayList<ArrayList<Cell>> cells, int x, int y) {
		if (cells.get(y).get(x).getType().equals("ground")) {
			return true;
		}
		return false;
	}
}
