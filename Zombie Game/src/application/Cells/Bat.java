package application.Cells;

/**
 * Sub Class of Cell
 * Weapon item bat
 *
 * @author Tang Zheng
 * @version 2
 */
public class Bat extends Cell {
	private static final String CELLIMAGE1 = "resources/Lucille.png";
	private static final String type = "bat";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Bat() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
