package application.Cells;

/**
 * Sub Class of Cell
 * fire cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class Fire extends Cell {
	private static final String CELLIMAGE1 = "resources/fire.gif";
	private static final String type = "fire";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Fire() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
