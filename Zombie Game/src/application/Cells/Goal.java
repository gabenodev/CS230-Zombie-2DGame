package application.Cells;

/**
 * Sub Class of Cell
 * Goal cell
 *
 * @author Tang Zheng
 * @version 2
 */
public class Goal extends Cell {
	private static final String CELLIMAGE1 = "resources/goal.png";
	private static final String type = "goal";
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public Goal() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
}
