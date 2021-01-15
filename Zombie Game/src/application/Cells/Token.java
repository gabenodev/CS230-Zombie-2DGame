package application.Cells;

/**
 * Sub Class of Cell
 * item Token Cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class Token extends Cell {
	private static final String CELLIMAGE1 = "resources/token.png";
	private static final String type = "token";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Token() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
