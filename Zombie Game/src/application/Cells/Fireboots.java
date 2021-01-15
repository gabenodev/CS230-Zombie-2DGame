package application.Cells;

/**
 * Sub Class of Cell
 * item Fireboots cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class Fireboots extends Cell {
	private static final String CELLIMAGE1 = "resources/fireboots.png";
	private static final String type = "fireboots";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Fireboots() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
