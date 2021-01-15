package application.Cells;

/**
 * Sub Class of Cell
 * GreenDoor Cell
 *
 * @author Tang Zheng, Sam
 * @version 3.9
 */
public class GreenDoor extends Cell {
	private static final String CELLIMAGE1 = "resources/greenDoor.png";
	private static final String type = "greenDoor";
	boolean locked = true;
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 */
	public GreenDoor() {
		super(CELLIMAGE1, type);
		this.traversable = true;
	}
	
	/**
	 * Checks whether the door is locked or not.
	 *
	 * @return True if the door is locked, false if the door is open.
	 */
	public boolean getlockedStatus() {
		return this.locked;
	}
	
	/**
	 * Unlocks the door.
	 */
	public void unlocked() {
		this.locked = false;
	}
	
	
}
