package application.Cells;

/**
 * Sub Class of Cell
 * item YellowKey cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class YellowKey extends Cell {
	private static final String CELLIMAGE1 = "resources/yellowKey.png";
	private static final String type = "yellowKey";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public YellowKey() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
