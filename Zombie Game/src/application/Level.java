package application;

import application.Cells.*;
import application.Entities.DumbTargetingEnemy;
import application.Entities.SmartTargetingEnemy;
import application.Entities.StraightLineEnemy;
import application.Entities.WallFollowingEnemy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class reads the level txt file
 * and stores the level information in two arrarylists
 * one stores the ASCII symbols for drawing the map
 * the other stores the cell Classes(objects) for interaction
 *
 * @author Tang Zheng
 * @version 1125.3
 */
public class Level {
	private ArrayList<ArrayList<Character>> layout;
	private ArrayList<ArrayList<Cell>> cells;
	
	private int mapWidth;
	private int mapHeight;
	
	private int playerStartX;
	private int playerStartY;
	
	private ArrayList<StraightLineEnemy> straightLineEnemies = new ArrayList<>();
	private ArrayList<WallFollowingEnemy> wallFollowingEnemies = new ArrayList<>();
	private ArrayList<DumbTargetingEnemy> dumbTargetingEnemies = new ArrayList<>();
	private ArrayList<SmartTargetingEnemy> smartTargetingEnemies = new ArrayList<>();
	
	private String enemyType;
	private String enemyDirection;
	
	private ArrayList[] mapInfo;
	
	private int tokensRequired;
	
	/**
	 * This is the constructor of the level class.
	 *
	 * @param levelFileName current name of the level txt file.
	 */
	public Level(String levelFileName) {
		this.mapInfo = readLevelFile(levelFileName);
		this.layout = mapInfo[0];
		this.cells = mapInfo[1];
	}
	
	/**
	 * This method gets the x coordinate of the start position of the player.
	 *
	 * @return X coordinate of start position of player.
	 */
	int getPlayerStartX() {
		return playerStartX;
	}
	
	/**
	 * This method gets the y coordinate of the start position of the player.
	 *
	 * @return Y coordinate of start position of player.
	 */
	int getPlayStartY() {
		return playerStartY;
	}
	
	/**
	 * This method gets the enemies that belongs to the type straight.
	 *
	 * @return the instances of straight enemies in an arraylist.
	 */
	ArrayList<StraightLineEnemy> getStraightEnemies() {
		return straightLineEnemies;
	}
	
	/**
	 * This method gets the enemies that belongs to the type wall.
	 *
	 * @return the instances of wall enemies in an arraylist.
	 */
	ArrayList<WallFollowingEnemy> getWallEnemies() {
		return wallFollowingEnemies;
	}
	
	/**
	 * This method gets the enemies that belongs to the type dumb.
	 *
	 * @return the instances of dumb enemies in an arraylist.
	 */
	ArrayList<DumbTargetingEnemy> getDumbEnemies() {
		return dumbTargetingEnemies;
	}
	
	/**
	 * This method gets the enemies that belongs to the type smart.
	 *
	 * @return the instances of smart enemies in an arraylist.
	 */
	ArrayList<SmartTargetingEnemy> getSmartEnemies() {
		return smartTargetingEnemies;
	}
	
	/**
	 * This method gets the width of the map.
	 *
	 * @return width of the map in int.
	 */
	int getMapWidth() {
		return mapWidth;
	}
	
	/**
	 * This method gets the Height of the map.
	 *
	 * @return height of the map in int.
	 */
	int getMapHeight() {
		return mapHeight;
	}
	
	/**
	 * This method gets the amount of tokens required by the token doors in that level.
	 *
	 * @return amount of token required to unlock token doors in int.
	 */
	int getTokensRequired() {
		return tokensRequired;
	}
	
	/**
	 * This method gets the full map of char that represents cells.
	 *
	 * @return an arraylist of arraylist of chars.
	 */
	public ArrayList<ArrayList<Character>> getLayout() {
		return layout;
	}
	
	/**
	 * This method gets the full map of cells
	 *
	 * @return an arraylist of arraylist of cells.
	 */
	public ArrayList<ArrayList<Cell>> getCells() {
		return cells;
	}
	
	/**
	 * This method reads in a txt level file when called with the supplied String fileName and returns the level layout.
	 *
	 * @param fileName name of the txt file in String.
	 * @return actual level reading method that holds the raw full layout of the level in a Arraylist of list.
	 */
	private ArrayList[] readLevelFile(String fileName) {
		File inputFile = new File("src/resources/" + fileName);
		
		Scanner in;
		try {
			in = new Scanner(inputFile);
		} catch (FileNotFoundException var5) {
			in = null;
			System.out.println(fileName);
			System.out.println("txt File not found");
			System.exit(0);
		}
		
		return readLevelFile(in);
	}
	
	/**
	 * This method processes the raw level data from the other readLevelFile method and translates it into cells and a char layout.
	 *
	 * @param in a Scanner that contains the raw level data.
	 * @return an ArrayList of list that contains the map info.
	 */
	private ArrayList[] readLevelFile(Scanner in) {
		
		ArrayList<ArrayList<Character>> layout = new ArrayList<>();
		ArrayList<ArrayList<Cell>> cells = new ArrayList<>();
		
		setMapDimensions(in);
		
		for (int y = 0; y < mapHeight; y++) {
			String curLine = in.nextLine();
			ArrayList<Character> tokens = new ArrayList<>();
			ArrayList<Cell> xCells = new ArrayList<>();
			
			for (int x = 0; x < mapWidth; ++x) {
				char curToken = curLine.charAt(x);
				tokens.add(curToken);
				switch (curToken) {
					case ' ':
						xCells.add(new Ground());
						break;
					case '#':
						xCells.add(new Wall());
						break;
					case '~':
						xCells.add(new Water());
						break;
					case '&':
						xCells.add(new Fire());
						break;
					case '$':
						xCells.add(new Goal());
						break;
					case 'G':
						xCells.add(new GreenDoor());
						break;
					case 'R':
						xCells.add(new RedDoor());
						break;
					case 'Y':
						xCells.add(new YellowDoor());
						break;
					case 'B':
						xCells.add(new Fireboots());
						break;
					case 'F':
						xCells.add(new Flippers());
						break;
					case 'L':
						xCells.add(new GreenKey());
						break;
					case 'H':
						xCells.add(new RedKey());
						break;
					case 'A':
						xCells.add(new YellowKey());
						break;
					case '!':
						xCells.add(new Token());
						break;
					case '@':
						xCells.add(new Teleporter());
						break;
					case '?':
						xCells.add(new Teleporter2());
						break;
					case '%':
						xCells.add(new TokenDoor(tokensRequired));
						break;
					case 'N':
						xCells.add(new Bat());
						break;
					case 'P':
						xCells.add(new Hatchet());
						break;
					default:
						xCells.add(new Cell("resources/ground.png", "asd"));
						break;
				}
			}
			layout.add(tokens);
			cells.add(xCells);
		}
		
		setMapExtraInfo(in);
		for (ArrayList<Cell> a : cells) {
			for (Cell b : a) {
				if (b.getType().equals("tokenDoor")) {
					b.setTokensRequired(this.tokensRequired);
				}
			}
		}
		
		in.close();
		
		ArrayList[] mapInfo = new ArrayList[2];
		mapInfo[0] = layout;
		mapInfo[1] = cells;
		
		return mapInfo;
	}
	
	/**
	 * This method sets the Map Dimensions mapWidth and mapHeight, according to the level data in the scanner.
	 *
	 * @param in a Scanner that contains the raw level data.
	 */
	private void setMapDimensions(Scanner in) {
		String firstLine = in.nextLine();
		Scanner line = new Scanner(firstLine);
		line.useDelimiter(",");
		mapWidth = line.nextInt();
		mapHeight = line.nextInt();
		line.close();
	}
	
	/**
	 * This method sets the ExtraData such as enemy, player start position and tokens needed for doors according to the scanner.
	 *
	 * @param in a Scanner that contains the raw level data.
	 */
	private void setMapExtraInfo(Scanner in) {
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine);
			line.useDelimiter(",");
			if (curLine.contains("START")) {
				playerStartX = line.nextInt();
				playerStartY = line.nextInt();
			} else if (curLine.contains("ENEMY")) {
				int enemyStartX = line.nextInt();
				int enemyStartY = line.nextInt();
				enemyType = line.next();
				enemyDirection = line.next();
				if (enemyType.equals("straight")) {
					straightLineEnemies.add(new StraightLineEnemy(enemyStartX, enemyStartY, enemyDirection));
				} else if (enemyType.equals("wall")) {
					wallFollowingEnemies.add(new WallFollowingEnemy(enemyStartX, enemyStartY, enemyDirection));
				} else if (enemyType.equals("dumb")) {
					dumbTargetingEnemies.add(new DumbTargetingEnemy(enemyStartX, enemyStartY));
				} else if (enemyType.equals("smart")) {
					smartTargetingEnemies.add(new SmartTargetingEnemy(enemyStartX, enemyStartY));
				}
			} else if (curLine.contains("TOKEN")) {
				tokensRequired = line.nextInt();
			}
			line.close();
		}
	}
}
