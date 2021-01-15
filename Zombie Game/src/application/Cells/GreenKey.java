package application.Cells;

/**
 * Sub Class of Cell
 * item GreenKey cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class GreenKey extends Cell {
	private static final String CELLIMAGE1 = "resources/greenKey.png";
	private static final String type = "greenKey";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public GreenKey() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
