package application.Cells;

/**
 * Sub Class of Cell
 * default Wall cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class Wall extends Cell {
	private static final String CELLIMAGE1 = "resources/houseNew.png";
	private static final String CELLIMAGE2 = "Tree.png";
	private static final String CELLIMAGE3 = "Mud.png";
	private static final String type = "wall";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell not traversable.
	 */
	public Wall() {
		super(CELLIMAGE1, type);
		this.traversable = false;
	}
}
