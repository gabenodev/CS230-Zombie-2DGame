package application.Cells;

/**
 * Sub Class of Cell
 * Water cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class Water extends Cell {
	private static final String CELLIMAGE1 = "resources/water.png";
	private static final String type = "water";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Water() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
