package application.Entities;

import application.Cells.Cell;
import application.Level;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Creates an enemy that uses path finding to move closer to the player if the enemy has a path to the player.
 * If the enemy doesn't have a path to the player, it will move a random but valid direction.
 *
 * @author Nayan Shrees
 * @version 4.6.0
 */
public class SmartTargetingEnemy extends TargetEnemies {
	private static final String IMAGE_LOCATION = "src/resources/SmartTarget.png";
	
	/**
	 * Calls the parents constructor and sets the enemy's x and y position.
	 *
	 * @param xPos The x position of the enemy.
	 * @param yPos The y position of the enemy.
	 */
	public SmartTargetingEnemy(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	/**
	 * Loads the enemy's sprite.
	 *
	 * @return Image with the enemy's sprite loaded or null if the enemy's sprite fails to load.
	 */
	@Override
	public Image loadEntity() {
		try {
			Image entityImage = new Image(new FileInputStream(IMAGE_LOCATION));
			return entityImage;
		} catch (FileNotFoundException e) {
			System.out.println("Image file not found");
			return null;
		}
	}
	
	/**
	 * Updates the enemy's position closer to the players if a path to the player is possible.
	 * Does this by using the flood fill algorithm.
	 * If there is no path to the player due to obstacles the smart enemy will make a random but valid move.
	 *
	 * @param level  The current level of the game.
	 * @param player The current player in the game.
	 */
	@Override
	public void updatePosition(Level level, Player player) {
		ArrayList<ArrayList<Cell>> cells = level.getCells();
		int[][] map = new int[cells.get(0).size()][cells.size()];
		map = fillMap(map, cells);
		if (!checkFloodFill(map, getxPos(), getyPos(), player.getxPos(), player.getyPos(), cells.get(0).size(), cells.size())) {
			map = fillMap(map, cells);
			randomDirection(cells);
		} else {
			map = fillMap(map, cells);
			map = floodFill(map, getxPos(), getyPos(), player.getxPos(), player.getxPos(), cells.get(0).size(), cells.size(), 10);
			tracepath(map, player.getxPos(), player.getyPos(), player.getxPos(), player.getyPos(), cells.get(0).size(), cells.size());
		}
	}
	
	/**
	 * Creates a map of the current level in a 2D array.
	 * If the cell is a ground cell the value of that cell is set to 0 on the 2D array.
	 * If it is any other cell the value of that cell is set to 1 on the 2D array.
	 *
	 * @param map   A 2D array the same size as the map of the level.
	 * @param cells A 2D ArrayList of the map of the level.
	 * @return A 2D array that is filled with 0's and 1's representing the ground and not ground cells respectively.
	 */
	private int[][] fillMap(int[][] map, ArrayList<ArrayList<Cell>> cells) {
		for (int y = 0; y < cells.size(); y++) {
			for (int x = 0; x < cells.get(y).size(); x++) {
				if (cells.get(y).get(x).getType().equals("ground")) {
					map[x][y] = 0;
				} else {
					map[x][y] = 1;
				}
			}
		}
		return map;
	}
	
	/**
	 * Checks if there is a path to the player using flood fill and recurrence.
	 *
	 * @param map     The map as a 2D array.
	 * @param x       The x co-ordinates that are being tested to see if there is a path to the player.
	 * @param y       The y co-ordinates that are being tested to see if there is a path to the player.
	 * @param xTarget The players x co-ordinates.
	 * @param yTarget The players y co-ordinates.
	 * @param rows    The length of rows in the level.
	 * @param columns The length of columns in the level.
	 * @return If there is a path to the player return true, if the path is blocked by obstacles returns false.
	 */
	private boolean checkFloodFill(int[][] map, int x, int y, int xTarget, int yTarget, int rows, int columns) {
		final int FILL_COLOUR = 5;
		if (x < 0 || x > rows || y < 0 || y > columns) {
			return false;
		}
		if (x == xTarget && y == yTarget) {
			return true;
		}
		map[x][y] = FILL_COLOUR;
		if (map[x][y + 1] == 0) {
			if (checkFloodFill(map, x, y + 1, xTarget, yTarget, rows, columns)) {
				return true;
			}
		}
		if (map[x + 1][y] == 0) {
			if (checkFloodFill(map, x + 1, y, xTarget, yTarget, rows, columns)) {
				return true;
			}
		}
		if (map[x][y - 1] == 0) {
			if (checkFloodFill(map, x, y - 1, xTarget, yTarget, rows, columns)) {
				return true;
			}
		}
		if (map[x - 1][y] == 0) {
			if (checkFloodFill(map, x - 1, y, xTarget, yTarget, rows, columns)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Fills the 2D array with values incrementing as it gets further away from the enemies source position until it reaches the player.
	 *
	 * @param map     The map of the level as a 2D array.
	 * @param x       The x co-ordinate that is having its score updated.
	 * @param y       The y co-ordinate that is having its score updated.
	 * @param xTarget The players x co-ordinate.
	 * @param yTarget The players y co-ordinate.
	 * @param rows    The length of the maps rows.
	 * @param columns The length of the maps columns.
	 * @param score   The distance the cell is away from the enemy's x and y co-ordinates.
	 * @return A 2D array of the map of the level with a score for each cell from the enemy's position to the players position.
	 */
	private int[][] floodFill(int[][] map, int x, int y, int xTarget, int yTarget, int rows, int columns, int score) {
		if (x < 0 || x > rows || y < 0 || y > columns) {
			return map;
		}
		map[x][y] = score;
		if (x == xTarget && y == yTarget) {
			return map;
		}
		if (map[x][y + 1] == 0 || (map[x][y + 1] != 1 && map[x][y] > score)) {
			floodFill(map, x, y + 1, xTarget, yTarget, rows, columns, score + 10);
		}
		if (map[x + 1][y] == 0 || (map[x + 1][y] != 1 && map[x + 1][y] > score)) {
			floodFill(map, x + 1, y, xTarget, yTarget, rows, columns, score + 10);
		}
		if (map[x][y - 1] == 0 || (map[x][y - 1] != 1 && map[x][y - 1] > score)) {
			floodFill(map, x, y - 1, xTarget, yTarget, rows, columns, score + 10);
		}
		if (map[x - 1][y] == 0 || (map[x - 1][y] != 1 && map[x - 1][y] > score)) {
			floodFill(map, x - 1, y, xTarget, yTarget, rows, columns, score + 10);
		}
		return map;
	}
	
	/**
	 * Traces a path from the players position back to the enemy's position using recursion.
	 * Then moves the enemy's position one cell closer to the players.
	 *
	 * @param map     2D array of the map of the level.
	 * @param x       The current x position tracing back.
	 * @param y       The current y position tracing back.
	 * @param xParent The previous x position.
	 * @param yParent The previous y position.
	 * @param rows    The length of the maps rows.
	 * @param columns The length of the maps columns.
	 */
	private void tracepath(int[][] map, int x, int y, int xParent, int yParent, int rows, int columns) {
		if (x < 0 || x > rows || y < 0 || y > columns) {
			return;
		}
		
		if (map[x][y] == 10) {
			setxPos(xParent);
			setyPos(yParent);
			return;
		}
		if ((map[x][y - 1] != 0 && map[x][y - 1] != 1) && map[x][y - 1] < map[x][y]) {
			tracepath(map, x, y - 1, x, y, rows, columns);
		}
		if ((map[x + 1][y] != 0 && map[x + 1][y] != 1) && map[x + 1][y] < map[x][y]) {
			tracepath(map, x + 1, y, x, y, rows, columns);
		}
		if ((map[x][y + 1] != 0 && map[x][y + 1] != 1) && map[x][y + 1] < map[x][y]) {
			tracepath(map, x, y + 1, x, y, rows, columns);
		}
		if ((map[x - 1][y] != 0 && map[x - 1][y] != 1) && map[x - 1][y] < map[x][y]) {
			tracepath(map, x - 1, y, x, y, rows, columns);
		}
		return;
	}
	
	/**
	 * Moves the enemy's position in a random direction up, down, left, or right if its possible to move that direction.
	 *
	 * @param cells 2D ArrayList of the cells of the map of the current level.
	 */
	private void randomDirection(ArrayList<ArrayList<Cell>> cells) {
		final int UP = 0;
		final int RIGHT = 1;
		final int DOWN = 2;
		final int LEFT = 3;
		final int MAX_TRAVERSABLE_DIRECTIONS = 4;
		int direction = -1;
		boolean[] traversableDirections = new boolean[MAX_TRAVERSABLE_DIRECTIONS];
		if (checkTraversable(cells, getxPos(), getyPos() - 1)) {
			traversableDirections[UP] = true;
		}
		if (checkTraversable(cells, getxPos() + 1, getyPos())) {
			traversableDirections[RIGHT] = true;
		}
		if (checkTraversable(cells, getxPos(), getyPos() + 1)) {
			traversableDirections[DOWN] = true;
		}
		if (checkTraversable(cells, getxPos() - 1, getyPos())) {
			traversableDirections[LEFT] = true;
		}
		while (direction == -1) {
			int randomDirection = new Random().nextInt(MAX_TRAVERSABLE_DIRECTIONS);
			if (traversableDirections[randomDirection]) {
				direction = randomDirection;
			} else {
				direction = -1;
			}
		}
		if (direction == UP) {
			setyPos(getyPos() - 1);
		} else if (direction == RIGHT) {
			setxPos(getxPos() + 1);
		} else if (direction == DOWN) {
			setyPos(getyPos() + 1);
		} else if (direction == LEFT) {
			setxPos(getxPos() - 1);
		}
	}
}
