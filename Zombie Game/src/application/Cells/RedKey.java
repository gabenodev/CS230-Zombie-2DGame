package application.Cells;

/**
 * Sub Class of Cell
 * item RedKey cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class RedKey extends Cell {
	private static final String CELLIMAGE1 = "resources/redKey.png";
	public static final String type = "redKey";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public RedKey() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
