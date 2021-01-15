package application.Cells;

import javafx.scene.image.Image;

/**
 * Cell class and all subclasses
 * Cell type stored as String
 *
 * @author Tang Zheng
 * @version 1125.3
 */
public class Cell {
	public static final int CELL_HEIGHT = 66;
	public static final int CELL_WIDTH = 66;
	private Image cellImage;
	boolean traversable;
	String type;
	
	//constructor sets the cell image and cell type
	
	/**
	 * Constructor for the Super class cell.
	 *
	 * @param imgName Image of the cell
	 * @param type    Type of cell
	 */
	public Cell(String imgName, String type) {
		this.cellImage = new Image(imgName);
		this.type = type;
	}
	
	
	// boolean traversable changes according to the player's inventory
	
	/**
	 * Checks if the cell is traversable.
	 *
	 * @return True if the cell is traversable and false if the cell isn't.
	 */
	public boolean isTraversable() {
		return this.traversable;
	}
	
	/**
	 * This method gets the type of the cell.
	 *
	 * @return String of the cell type.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * This method gets the Image of the cell.
	 *
	 * @return Image of the cell.
	 */
	public Image getCellImage() {
		return this.cellImage;
	}
	
	/**
	 * Sets the token required for a cell, mainly used for the door cells.
	 * @param tokensRequired The number of tokens required.
	 */
	public void setTokensRequired(int tokensRequired) {
	}
}
