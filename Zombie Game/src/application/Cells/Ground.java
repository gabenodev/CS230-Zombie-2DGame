package application.Cells;

/**
 * Sub Class of Cell
 * default Ground cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class Ground extends Cell {
	private static final String CELLIMAGE1 = "resources/ground.png";
	private static final String type = "ground";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Ground() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
