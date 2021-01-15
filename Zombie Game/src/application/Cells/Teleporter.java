package application.Cells;

/**
 * Sub Class of Cell
 * Teleporter cell
 *
 * @author Tang Zheng, Zade
 * @version 2
 */
public class Teleporter extends Cell {
	private static final String CELLIMAGE1 = "resources/teleporter.png";
	private static final String type = "teleporter";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Teleporter() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
