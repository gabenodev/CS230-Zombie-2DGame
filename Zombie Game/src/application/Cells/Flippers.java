package application.Cells;

/**
 * Sub Class of Cell
 * item flipper cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class Flippers extends Cell {
	private static final String CELLIMAGE1 = "resources/flippers.png";
	private static final String type = "flippers";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Flippers() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
