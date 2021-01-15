package application.Cells;

/**
 * Sub Class of Cell
 * Weapon item Hatchet cell
 *
 * @author Tang Zheng
 * @version 1
 */
public class Hatchet extends Cell {
	private static final String CELLIMAGE1 = "resources/hatchet.png";
	private static final String type = "hatchet";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Hatchet() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
