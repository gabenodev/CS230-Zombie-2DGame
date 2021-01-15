package application.Cells;

/**
 * Sub Class of Cell
 * TokenDoor cell
 *
 * @author Tang Zheng, Sam
 * @version 3.9
 */
public class TokenDoor extends Cell {
	private static final String CELLIMAGE1 = "resources/tokenDoor.png";
	private static final String type = "tokenDoor";
	private int tokensRequired;
	boolean locked = true;
	
	/**
	 * Calls the cells parent constructor setting the image and type while making the cell traversable.
	 * Also sets the token required to open the door.
	 * @param tokensRequired number of tokens required to open the specific door.
	 */
	public TokenDoor(int tokensRequired) {
		super(CELLIMAGE1, type);
		this.traversable = true;  //Player.tokens >= this.tokenRequired;
		this.tokensRequired = tokensRequired;
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
	
	/**
	 * Sets the number of tokens required to open the door.
	 *
	 * @param tokensRequired The number of tokens required.
	 */
	public void setTokensRequired(int tokensRequired) {
		this.tokensRequired = tokensRequired;
	}
	
	/**
	 * Gets the number of tokens required to open the door.
	 *
	 * @return The number of tokens required to open the door.
	 */
	public int getTokensRequired() {
		return tokensRequired;
	}
}
