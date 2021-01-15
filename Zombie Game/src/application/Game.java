package application;

import application.Cells.Cell;
import application.Cells.*;
import application.Entities.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static javafx.scene.layout.BackgroundSize.DEFAULT;
import static sceneController.ProfileManagerController.getCurrentProfile;
import static sceneController.ProfileManagerController.saveProfileList;

/**
 * This is the class that launches the game and draw the game
 * also implements two scenes and handles player and enemies
 * <p>
 * 11.26 Implemented an Inventory UI
 *
 * @author Tang Zheng, Sam
 * @version 1128.1
 */
public class Game extends Main {
	final static String GAME_TITLE = "Zed Dead Redemption";
	private static final String SAVE_PATH = "src/resources/savefile.txt";
	
	private Stage curStage;
	
	private Canvas mapCanvas;
	
	private final int[] COUNT = {0};
	
	private ParallelCamera camera = new ParallelCamera();
	
	private String levelName;
	private Level level;
	
	private int playerColumn;
	private int playerRow;
	
	private int playerPixelX;
	private int playerPixelY;
	
	private Player curPlayer = new Player(playerColumn, playerRow);
	private String prevPlayerMove;
	
	//all the cell&tile images
	private Image[] player = Player.getPlayerImage();
	private Image curPlayerImage = player[1];
	private Image straight = new StraightLineEnemy(0, 0, "LEFT").loadEntity();
	private Image wallFollowing = new WallFollowingEnemy(0, 0, "DOWN").loadEntity();
	private Image dumb = new DumbTargetingEnemy(0, 0).loadEntity();
	private Image smart = new SmartTargetingEnemy(0, 0).loadEntity();
	
	private Image ground = new Ground().getCellImage();
	private Image ground2 = new Image("resources/ground2.png");
	private Image ground3 = new Image("resources/ground3.png");
	private Image wall = new Wall().getCellImage();
	private Image wall2 = new Image("resources/houseNew2.png");
	private Image wall3 = new Image("resources/houseNew3.png");
	private Image water = new Water().getCellImage();
	private Image fire = new Fire().getCellImage();
	private Image goal = new Goal().getCellImage();
	private Image goal2 = new Image("resources/goal2.png");
	private Image goal3 = new Image("resources/goal3.png");
	private Image greenDoor = new GreenDoor().getCellImage();
	private Image greenDoor2 = new Image("resources/greenDoor2.png");
	private Image greenDoor3 = new Image("resources/greenDoor3.png");
	private Image redDoor = new RedDoor().getCellImage();
	private Image redDoor2 = new Image("resources/redDoor2.png");
	private Image redDoor3 = new Image("resources/redDoor3.png");
	private Image yellowDoor = new YellowDoor().getCellImage();
	private Image yellowDoor2 = new Image("resources/yellowDoor2.png");
	private Image yellowDoor3 = new Image("resources/yellowDoor3.png");
	
	private Image sideBar = new Image("resources/sideBarBG.png");
	private Image diary = new Image("resources/logBG.png");
	
	private Image redKey = new RedKey().getCellImage();
	private Image redKey2 = new Image("resources/redKey2.png");
	private Image redKey3 = new Image("resources/redKey3.png");
	private Image greenKey = new GreenKey().getCellImage();
	private Image greenKey2 = new Image("resources/greenKey2.png");
	private Image greenKey3 = new Image("resources/greenKey3.png");
	private Image yellowKey = new YellowKey().getCellImage();
	private Image yellowKey2 = new Image("resources/yellowKey2.png");
	private Image yellowKey3 = new Image("resources/yellowKey3.png");
	private Image token = new Token().getCellImage();
	private Image token2 = new Image("resources/token2.png");
	private Image token3 = new Image("resources/token3.png");
	private Image flippers = new Flippers().getCellImage();
	private Image flippers2 = new Image("resources/flippers2.png");
	private Image flippers3 = new Image("resources/flippers3.png");
	private Image fireBoots = new Fireboots().getCellImage();
	private Image fireBoots2 = new Image("resources/fireboots2.png");
	private Image fireBoots3 = new Image("resources/fireboots3.png");
	private Image teleporter = new Teleporter().getCellImage();
	private Image teleporter2 = new Image("resources/teleporter2.png");
	private Image teleporter3 = new Image("resources/teleporter3.png");
	private Image tokenDoor;
	private Image tokenDoor2 = new Image("resources/tokenDoor2.png");
	private Image tokenDoor3 = new Image("resources/tokenDoor3.png");
	private Image lucille = new Bat().getCellImage();
	private Image lucille2 = new Image("resources/Lucille2.png");
	private Image lucille3 = new Image("resources/Lucille3.png");
	private Image hatchet = new Hatchet().getCellImage();
	private Image hatchet2 = new Image("resources/hatchet2.png");
	private Image hatchet3 = new Image("resources/hatchet3.png");
	
	private MediaPlayer mediaPlayer;
	private MediaPlayer sfxPlayer;
	
	/**
	 * Constructing inventory UI
	 */
	private ObservableList<String> items = FXCollections.unmodifiableObservableList(
			FXCollections.observableArrayList(
					"token",
					"greenKey",
					"redKey",
					"yellowKey",
					"fireboots",
					"flippers",
					"Lucille",
					"hatchet"
			)
	);
	
	private Map<String, Image> itemImageCollection;
	
	private Pane inventory = buildInventoryUI();
	private Scene invScene = new Scene(inventory, WINDOW_WIDTH, WINDOW_HEIGHT);
	
	/**
	 * Constructing map UI
	 */
	
	private int mapCanvasWidth;
	private int mapCanvasHeight;
	
	private Pane map;
	private Scene mapScene;
	private GraphicsContext gc;
	
	/**
	 * This is the constructor of the Game.
	 *
	 * @param levelFileName name of the current level txt file.
	 */
	public Game(String levelFileName) {
		this.levelName = levelFileName;
		level = new Level(levelName);
		this.playerColumn = level.getPlayerStartX();
		this.playerRow = level.getPlayStartY();
		this.playerPixelX = playerColumn * 66;
		this.playerPixelY = playerRow * 66;
		this.tokenDoor = new TokenDoor(level.getTokensRequired()).getCellImage();
		this.mapCanvasWidth = level.getMapWidth() * Cell.CELL_WIDTH;
		this.mapCanvasHeight = level.getMapHeight() * Cell.CELL_HEIGHT;
		this.map = buildMap();
		this.mapScene = new Scene(map, 726, 726);
		this.gc = mapCanvas.getGraphicsContext2D();
	}
	
	/**
	 * This method starts up the game, initializing the basic functionalities that supports the game.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mapScene.setCamera(camera);
		moveCamera();
		
		primaryStage.setTitle(GAME_TITLE);
		primaryStage.getIcons().add(new Image("resources/icon.png"));
		
		// Event handlers for the two scenes
		mapScene.addEventFilter(KeyEvent.KEY_PRESSED, this::processKeyEventMap);
		invScene.addEventFilter(KeyEvent.KEY_PRESSED, this::processKeyEventInventory);
		
		drawGame();
		int delay = 0;
		int period = 1000;
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				COUNT[0]++;
			}
		}, delay, period);
		
		curStage = primaryStage;
		
		setStage(mapScene);
		
		setMusic();
		mediaPlayer.play();
		
		curStage.show();
	}
	
	/**
	 * This method sets the scene for the current stage.
	 *
	 * @param scene the scene that will be bind to the stage.
	 */
	private void setStage(Scene scene) {
		curStage.setScene(scene);
	}
	
	/**
	 * This method builds the map pane and canvas.
	 *
	 * @return a built pane of the map.
	 */
	private Pane buildMap() {
		BorderPane root = new BorderPane();
		
		mapCanvas = new Canvas(mapCanvasWidth, mapCanvasHeight);
		root.setCenter(mapCanvas);
		
		return root;
	}
	
	/**
	 * This method builds the UI of the inventory.
	 *
	 * @return a built pane of the Inventory.
	 */
	private Pane buildInventoryUI() {
		BorderPane root = new BorderPane();
		
		//VBox on the right for the menu buttons
		VBox toolbar = new VBox();
		toolbar.setSpacing(10);
		toolbar.setPadding(new Insets(20, 20, 20, 20));
		toolbar.setPrefWidth(200);
		root.setRight(toolbar);
		
		BackgroundImage sideBarBackground = new BackgroundImage(sideBar, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, DEFAULT);
		Background sideBarBG = new Background(sideBarBackground);
		toolbar.setBackground(sideBarBG);
		
		Lighting lighting = new Lighting();
		Light.Distant light = new Light.Distant();
		light.setColor(Color.LIGHTGRAY);
		lighting.setLight(light);
		
		Button resumeButton = new Button("RESUME");
		resumeButton.setTextFill(Color.RED);
		resumeButton.setFont(Font.font("Chiller", FontWeight.BOLD, 26));
		resumeButton.setPrefSize(200, 40);
		resumeButton.setEffect(lighting);
		toolbar.getChildren().add(resumeButton);
		
		Button musicSwitchButton = new Button("MUSIC ON/OFF");
		musicSwitchButton.setTextFill(Color.RED);
		musicSwitchButton.setFont(Font.font("Chiller", FontWeight.BOLD, 26));
		musicSwitchButton.setPrefSize(200, 40);
		musicSwitchButton.setEffect(lighting);
		toolbar.getChildren().add(musicSwitchButton);
		
		// SAVE BUTTON
		// UPDATED BY GABE
		
		Button saveButton = new Button("SAVE");
		saveButton.setTextFill(Color.RED);
		saveButton.setFont(Font.font("Chiller", FontWeight.BOLD, 26));
		saveButton.setEffect(lighting);
		saveButton.setPrefSize(200, 40);
		toolbar.getChildren().add(saveButton);
		
		
		Button leaderBoardButton = new Button("LEADERBOARD");
		leaderBoardButton.setTextFill(Color.RED);
		leaderBoardButton.setFont(Font.font("Chiller", FontWeight.BOLD, 26));
		leaderBoardButton.setEffect(lighting);
		leaderBoardButton.setPrefSize(200, 40);
		toolbar.getChildren().add(leaderBoardButton);
		
		Button achievementButton = new Button("ACHIEVEMENTS");
		achievementButton.setTextFill(Color.RED);
		achievementButton.setFont(Font.font("Chiller", FontWeight.BOLD, 26));
		achievementButton.setEffect(lighting);
		achievementButton.setPrefSize(200, 40);
		toolbar.getChildren().add(achievementButton);
		
		Button backToMenuButton = new Button("MAIN MENU");
		backToMenuButton.setTextFill(Color.RED);
		backToMenuButton.setFont(Font.font("Chiller", FontWeight.BOLD, 26));
		backToMenuButton.setEffect(lighting);
		backToMenuButton.setPrefSize(200, 40);
		toolbar.getChildren().add(backToMenuButton);
		
		Button quitButton = new Button("QUIT GAME");
		quitButton.setTextFill(Color.RED);
		quitButton.setFont(Font.font("Chiller", FontWeight.BOLD, 26));
		quitButton.setEffect(lighting);
		quitButton.setPrefSize(200, 40);
		toolbar.getChildren().add(quitButton);
		
		resumeButton.setOnAction(e -> resumeGame());
		musicSwitchButton.setOnAction(e -> musicSwitch());
		backToMenuButton.setOnAction(e -> backToMenu());
		quitButton.setOnAction(e -> System.exit(0));
		saveButton.setOnAction(e -> Save());
		
		//listView in the center for inventory
		itemImageCollection = items.stream().collect(
				Collectors.toMap(
						items -> items,
						items -> new Image("resources/" + items + "3.png")
				)
		);
		
		ListView<String> itemList = new ListView<>(items);
		itemList.setCellFactory(param -> new ItemCell());
		itemList.setPrefWidth(650);
		itemList.setPrefHeight(792);
		
		root.setCenter(itemList);
		
		
		// Text flow on the left for diary/log
		ScrollPane logPane = new ScrollPane();
		logPane.setPrefWidth(300);
		logPane.setPrefHeight(792);
		
		File story = new File("src/resources/story.txt");
		Scanner in = null;
		try {
			in = new Scanner(story);
		} catch (FileNotFoundException e) {
			System.out.println("Story file not found");
			System.exit(0);
		}
		
		String inputText = in.nextLine();
		in.close();
		
		TextFlow log = new TextFlow();
		
		BackgroundImage diaryBackground = new BackgroundImage(diary, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, DEFAULT);
		Background diaryBG = new Background(diaryBackground);
		log.setBackground(diaryBG);
		
		Text text1 = new Text("Road to Redemption\n");
		Text text2 = new Text(inputText);
		text1.setFont(Font.font("Chiller", FontWeight.EXTRA_BOLD, 40));
		text1.setFill(Color.RED);
		text2.setFont(Font.font("Papyrus", FontWeight.BOLD, 15));
		
		
		log.getChildren().addAll(text1, text2);
		log.setTextAlignment(TextAlignment.CENTER);
		log.setPadding(new Insets(10, 20, 10, 20));
		log.setLineSpacing(20D);
		log.setPrefWidth(300);
		log.setPrefHeight(792);
		
		logPane.setContent(log);
		
		
		root.setLeft(logPane);
		
		
		return root;
	}
	
	private class ItemCell extends ListCell<String> {
		private ImageView imageView = new ImageView();
		
		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			
			if (empty || item == null) {
				imageView.setImage(null);
				
				setGraphic(null);
				setText(null);
			} else {
				imageView.setImage(
						itemImageCollection.get(
								item)
				);
				
				setText(item.toUpperCase() + " X " + Player.getItemNum(item));
				setGraphic(imageView);
			}
		}
	}
	
	/**
	 * This method sets the back ground music of the current game.
	 */
	void setMusic() {
		String musicFile = "src/resources/sample1.wav";
		Media sound = new Media((new File(musicFile)).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setMute(false);
		mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
	}
	
	/**
	 * This method sets the sound effect of events to their respective sounds.
	 *
	 * @param sfxName It is a String that determines the file name of the sound effect.
	 */
	private void setSFX(String sfxName) {
		String sfxFile = "src/resources/sfx/" + sfxName + ".wav";
		Media sound = new Media((new File(sfxFile)).toURI().toString());
		sfxPlayer = new MediaPlayer(sound);
		mediaPlayer.setMute(false);
	}
	
	/**
	 * This method controlls the camera in a 11x11 field of view, it checks the corners/edges of the map and-
	 * makes sure that the camera stays inside the map.
	 * 66 is the width and height of a cell/tile
	 */
	private void moveCamera() {
		if (((mapCanvasHeight / 66) - playerRow) > ((mapCanvasHeight / 66) - 5)) { //Check Top edge
			if (((mapCanvasWidth / 66) - playerColumn) <= 5) { //Check Top right edge
				camera.relocate(mapCanvasWidth - 11 * 66, 0);
			} else if (((mapCanvasWidth / 66) - playerColumn) > ((mapCanvasWidth / 66) - 5)) { //Check Top left edge
				camera.relocate(0, 0);
			} else { //Its Top
				camera.relocate((playerColumn * 66) - (5 * 66), 0);
			}
		} else if (((mapCanvasHeight / 66) - playerRow) <= 5) { //Check Bottom
			if (((mapCanvasWidth / 66) - playerColumn) > ((mapCanvasWidth / 66) - 5)) { //Check Bottom left edge
				camera.relocate(0, mapCanvasHeight - 11 * 66);
			} else if (((mapCanvasWidth / 66) - playerColumn) <= 5) { //Check Bottom right edge
				camera.relocate(mapCanvasWidth - 11 * 66, mapCanvasHeight - 11 * 66);
			} else { //Its bottom
				camera.relocate((playerColumn * 66) - (5 * 66), mapCanvasHeight - 11 * 66);
			}
		} else if (((mapCanvasWidth / 66) - playerColumn) <= 5) { //Check right edge
			camera.relocate(mapCanvasWidth - 11 * 66, (playerRow * 66) - (5 * 66));
		} else if (((mapCanvasWidth / 66) - playerColumn) > ((mapCanvasWidth / 66) - 5)) { //Check left edge
			camera.relocate(0, (playerRow * 66) - (5 * 66));
		} else { //Camera is not touching edges
			camera.relocate((playerColumn * 66) - (5 * 66), (playerRow * 66) - (5 * 66));
		}
	}
	
	/**
	 * This method calls three different drawing methods to draw different levels according to player's selection
	 */
	private void drawGame() {
		
		gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
		
		drawGrass();
		
		if (getCurrentProfile().getHighestLevel() == 2) {
			drawSnow();
		}
		
		if (getCurrentProfile().getHighestLevel() == 3) {
			drawSand();
		}
		
		gc.drawImage(curPlayerImage, playerPixelX, playerPixelY);
		drawEnemies();
	}
	
	/**
	 * This method uses the graphics context obtained from the map and the level1 data from a level class to draw-
	 * the current level of the game, drawing the tiles/cells one by one in column of each row, and moving to next row-
	 * while finished current row with a double for loop.
	 * 66 is the width and height of a cell/tile
	 */
	private void drawGrass() {
		for (int row = 0; row < this.level.getLayout().size(); ++row) {
			for (int i = 0; i < this.level.getLayout().get(row).size(); ++i) {
				char n = this.level.getLayout().get(row).get(i);
				switch (n) {
					case ' ':
						gc.drawImage(ground, i * 66, row * 66);
						break;
					case '#':
						gc.drawImage(wall, i * 66, row * 66);
						break;
					case '~':
						gc.drawImage(water, i * 66, row * 66);
						break;
					case '&':
						gc.drawImage(fire, i * 66, row * 66);
						break;
					case '$':
						gc.drawImage(goal, i * 66, row * 66);
						break;
					case 'G':
						gc.drawImage(greenDoor, i * 66, row * 66);
						break;
					case 'R':
						gc.drawImage(redDoor, i * 66, row * 66);
						break;
					case 'Y':
						gc.drawImage(yellowDoor, i * 66, row * 66);
						break;
					case 'B':
						gc.drawImage(fireBoots, i * 66, row * 66);
						break;
					case 'F':
						gc.drawImage(flippers, i * 66, row * 66);
						break;
					case 'L':
						gc.drawImage(greenKey, i * 66, row * 66);
						break;
					case 'H':
						gc.drawImage(redKey, i * 66, row * 66);
						break;
					case 'A':
						gc.drawImage(yellowKey, i * 66, row * 66);
						break;
					case '!':
						gc.drawImage(token, i * 66, row * 66);
						break;
					case '@':
					case '?':
						gc.drawImage(teleporter, i * 66, row * 66);
						break;
					case '%':
						gc.drawImage(tokenDoor, i * 66, row * 66);
						break;
					case 'N':
						gc.drawImage(lucille, i * 66, row * 66);
						break;
					case 'P':
						gc.drawImage(hatchet, i * 66, row * 66);
						break;
					default:
						++i;
						break;
				}
			}
		}
	}
	
	/**
	 * This method uses the graphics context obtained from the map and the level2 data from a level class to draw-
	 * the current level of the game, drawing the tiles/cells one by one in column of each row, and moving to next row-
	 * while finished current row with a double for loop.
	 * 66 is the width and height of a cell/tile
	 */
	private void drawSnow() {
		for (int row = 0; row < this.level.getLayout().size(); ++row) {
			for (int i = 0; i < this.level.getLayout().get(row).size(); ++i) {
				char n = this.level.getLayout().get(row).get(i);
				switch (n) {
					case ' ':
						gc.drawImage(ground2, i * 66, row * 66);
						break;
					case '#':
						gc.drawImage(wall2, i * 66, row * 66);
						break;
					case '~':
						gc.drawImage(water, i * 66, row * 66);
						break;
					case '&':
						gc.drawImage(fire, i * 66, row * 66);
						break;
					case '$':
						gc.drawImage(goal2, i * 66, row * 66);
						break;
					case 'G':
						gc.drawImage(greenDoor2, i * 66, row * 66);
						break;
					case 'R':
						gc.drawImage(redDoor2, i * 66, row * 66);
						break;
					case 'Y':
						gc.drawImage(yellowDoor2, i * 66, row * 66);
						break;
					case 'B':
						gc.drawImage(fireBoots2, i * 66, row * 66);
						break;
					case 'F':
						gc.drawImage(flippers2, i * 66, row * 66);
						break;
					case 'L':
						gc.drawImage(greenKey2, i * 66, row * 66);
						break;
					case 'H':
						gc.drawImage(redKey2, i * 66, row * 66);
						break;
					case 'A':
						gc.drawImage(yellowKey2, i * 66, row * 66);
						break;
					case '!':
						gc.drawImage(token2, i * 66, row * 66);
						break;
					case '@':
					case '?':
						gc.drawImage(teleporter2, i * 66, row * 66);
						break;
					case '%':
						gc.drawImage(tokenDoor2, i * 66, row * 66);
						break;
					case 'N':
						gc.drawImage(lucille2, i * 66, row * 66);
						break;
					case 'P':
						gc.drawImage(hatchet2, i * 66, row * 66);
						break;
					default:
						++i;
						break;
				}
			}
		}
	}
	
	/**
	 * This method uses the graphics context obtained from the map and the level3 data from a level class to draw-
	 * the current level of the game, drawing the tiles/cells one by one in column of each row, and moving to next row-
	 * while finished current row with a double for loop.
	 * 66 is the width and height of a cell/tile
	 */
	private void drawSand() {
		for (int row = 0; row < this.level.getLayout().size(); ++row) {
			for (int i = 0; i < this.level.getLayout().get(row).size(); ++i) {
				char n = this.level.getLayout().get(row).get(i);
				switch (n) {
					case ' ':
						gc.drawImage(ground3, i * 66, row * 66);
						break;
					case '#':
						gc.drawImage(wall3, i * 66, row * 66);
						break;
					case '~':
						gc.drawImage(water, i * 66, row * 66);
						break;
					case '&':
						gc.drawImage(fire, i * 66, row * 66);
						break;
					case '$':
						gc.drawImage(goal3, i * 66, row * 66);
						break;
					case 'G':
						gc.drawImage(greenDoor3, i * 66, row * 66);
						break;
					case 'R':
						gc.drawImage(redDoor3, i * 66, row * 66);
						break;
					case 'Y':
						gc.drawImage(yellowDoor3, i * 66, row * 66);
						break;
					case 'B':
						gc.drawImage(fireBoots3, i * 66, row * 66);
						break;
					case 'F':
						gc.drawImage(flippers3, i * 66, row * 66);
						break;
					case 'L':
						gc.drawImage(greenKey3, i * 66, row * 66);
						break;
					case 'H':
						gc.drawImage(redKey3, i * 66, row * 66);
						break;
					case 'A':
						gc.drawImage(yellowKey3, i * 66, row * 66);
						break;
					case '!':
						gc.drawImage(token3, i * 66, row * 66);
						break;
					case '@':
					case '?':
						gc.drawImage(teleporter3, i * 66, row * 66);
						break;
					case '%':
						gc.drawImage(tokenDoor3, i * 66, row * 66);
						break;
					case 'N':
						gc.drawImage(lucille3, i * 66, row * 66);
						break;
					case 'P':
						gc.drawImage(hatchet3, i * 66, row * 66);
						break;
					default:
						++i;
						break;
				}
			}
		}
	}
	
	/**
	 * This method draws all Enemies in the map so the player could know where the enemies are, the drawing-
	 * is based on enemy types, which it uses four for statements to differentiate.
	 * 66 is the width and height of a cell/tile
	 */
	private void drawEnemies() {
		for (StraightLineEnemy a : level.getStraightEnemies()) {
			gc.drawImage(straight, a.getxPos() * 66, a.getyPos() * 66);
		}
		for (WallFollowingEnemy a : level.getWallEnemies()) {
			gc.drawImage(wallFollowing, a.getxPos() * 66, a.getyPos() * 66);
		}
		for (DumbTargetingEnemy a : level.getDumbEnemies()) {
			gc.drawImage(dumb, a.getxPos() * 66, a.getyPos() * 66);
		}
		for (SmartTargetingEnemy a : level.getSmartEnemies()) {
			gc.drawImage(smart, a.getxPos() * 66, a.getyPos() * 66);
		}
	}
	
	/**
	 * This method updates the position of enemies by calling methods of the enemy class.
	 */
	private void moveEnemies() {
		for (StraightLineEnemy a : level.getStraightEnemies()) {
			a.updatePosition(level);
		}
		for (WallFollowingEnemy a : level.getWallEnemies()) {
			a.updatePosition(level);
		}
		for (DumbTargetingEnemy a : level.getDumbEnemies()) {
			a.updatePosition(level, curPlayer);
		}
		for (SmartTargetingEnemy a : level.getSmartEnemies()) {
			a.updatePosition(level, curPlayer);
		}
	}
	
	/**
	 * This method resumes the game from the main menu.
	 */
	private void resumeGame() {
		curStage.setScene(mapScene);
	}
	
	/**
	 * This method turns music on or off, and is accessed in the inventory screen.
	 */
	private void musicSwitch() {
		if (mediaPlayer.isMute()) {
			mediaPlayer.setMute(false);
		} else {
			mediaPlayer.setMute(true);
		}
	}
	
	/**
	 * This method resets player to the start location of the map they started playing with.
	 * 66 is the width and height of a cell/tile
	 */
	private void restartGame() {
		level = new Level(levelName);
		mapCanvasWidth = level.getMapWidth() * Cell.CELL_WIDTH;
		mapCanvasHeight = level.getMapHeight() * Cell.CELL_HEIGHT;
		mapCanvas.setWidth(mapCanvasWidth);
		mapCanvas.setHeight(mapCanvasHeight);
		mapCanvas.resize(mapCanvasWidth, mapCanvasHeight);
		gc = mapCanvas.getGraphicsContext2D();
		Player.reset();
		playerPixelX = level.getPlayerStartX() * Cell.CELL_WIDTH;
		playerPixelY = level.getPlayStartY() * Cell.CELL_HEIGHT;
		playerColumn = playerPixelX / 66;
		playerRow = playerPixelY / 66;
		moveCamera();
		drawGame();
	}
	
	/**
	 * This method sends the player back to the main menu when accessed through the inventory screen.
	 */
	private void backToMenu() {
		mediaPlayer.stop();
		Stage s = new Stage();
		try {
			new Main().start(s);
			curStage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creates a save file that saves the current state of the level.
	 */
	private void Save() {
		try {
			File f = new File(SAVE_PATH);
			if (!f.exists())
				f.createNewFile();
			Writer we = new FileWriter(f);
			we.write(new Integer(level.getMapWidth()).toString() + "," + new Integer(level.getMapHeight()).toString());
			for (int i = 0; i < level.getMapHeight(); i++) {
				we.write(System.getProperty("line.separator"));
				for (int j = 0; j < level.getMapWidth(); j++) {
					
					level.getCells().get(i).get(j).getType();
					we.write(getSymbol(i, j));
					
				}
			}
			
			we.write(System.getProperty("line.separator"));
			we.write(new Integer(playerColumn).toString() + "," + new Integer(playerRow).toString() + "," + "START");
			we.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method translates the current state of the level in game back to symbols for the save file.
	 *
	 * @param i x position
	 * @param j y position
	 * @return a string symbol that represents the cell.
	 */
	private String getSymbol(int i, int j) {
		String n = level.getCells().get(i).get(j).getType();
		switch (n) {
			case "fire":
				return "&";
			case "water":
				return "~";
			case "wall":
				return "#";
			case "ground":
				return " ";
			case "greenDoor":
				return "G";
			case "redDoor":
				return "R";
			case "yellowDoor":
				return "Y";
			case "redKey":
				return "H";
			case "yellowKey":
				return "A";
			case "greenKey":
				return "L";
			case "teleporter":
			case "teleporter2":
				return "@";
			case "token":
				return "!";
			case "flippers":
				return "F";
			case "fireboots":
				return "B";
			case "goal":
				return "$";
			case "tokenDoor":
				return "%";
			case "bat":
				return "N";
			case "hatchet":
				return "P";
			default:
				break;
		}
		return n;
	}
	
	//time for animation timeline
	private int currentTime = 1;
	private int duration = 11;
	private int speed = 6;
	
	private void performAnimationR() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(duration), ae -> onTimeR()));
		timeline.setCycleCount(duration);
		timeline.play();
	}
	
	private void onTimeR() {
		currentTime++;
		gc.clearRect(0, 0, mapCanvasWidth, mapCanvasHeight);
		playerPixelX += speed;
		drawGame();
	}
	
	private void performAnimationL() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(duration), ae -> onTimeL()));
		timeline.setCycleCount(duration);
		timeline.play();
	}
	
	private void onTimeL() {
		currentTime++;
		gc.clearRect(0, 0, mapCanvasWidth, mapCanvasHeight);
		playerPixelX -= speed;
		drawGame();
	}
	
	private void performAnimationU() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(duration), ae -> onTimeU()));
		timeline.setCycleCount(duration);
		timeline.play();
	}
	
	private void onTimeU() {
		currentTime++;
		gc.clearRect(0, 0, mapCanvasWidth, mapCanvasHeight);
		playerPixelY -= speed;
		drawGame();
	}
	
	private void performAnimationD() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(duration), ae -> onTimeD()));
		timeline.setCycleCount(duration);
		timeline.play();
	}
	
	private void onTimeD() {
		currentTime++;
		gc.clearRect(0, 0, mapCanvasWidth, mapCanvasHeight);
		playerPixelY += speed;
		drawGame();
	}
	
	/**
	 * This method handles all events that are related to key pressing by the player/user in game, including-
	 * events where player steps on cells and items.
	 *
	 * @param event The input key event of the player while playing the game.
	 */
	private void processKeyEventMap(KeyEvent event) {
		
		boolean traversableR = level.getCells().get(playerRow).get(playerColumn + 1).isTraversable();
		boolean traversableL = level.getCells().get(playerRow).get(playerColumn - 1).isTraversable();
		boolean traversableU = level.getCells().get(playerRow - 1).get(playerColumn).isTraversable();
		boolean traversableD = level.getCells().get(playerRow + 1).get(playerColumn).isTraversable();
		
		int prevPosColumn = playerColumn;
		int prevPosRow = playerRow;
		
		switch (event.getCode()) {
			case RIGHT:
				curPlayerImage = player[3];
				if (traversableR) {
					performAnimationR();
					++playerColumn;
					prevPlayerMove = "RIGHT";
				}
				break;
			case LEFT:
				curPlayerImage = player[2];
				if (traversableL) {
					performAnimationL();
					--playerColumn;
					prevPlayerMove = "LEFT";
				}
				break;
			case UP:
				curPlayerImage = player[0];
				if (traversableU) {
					performAnimationU();
					--playerRow;
					prevPlayerMove = "UP";
				}
				break;
			case DOWN:
				curPlayerImage = player[1];
				if (traversableD) {
					performAnimationD();
					++playerRow;
					prevPlayerMove = "DOWN";
				}
				break;
			case I:
				inventory = buildInventoryUI();
				invScene = new Scene(inventory, 726, 726);
				invScene.addEventFilter(KeyEvent.KEY_PRESSED, this::processKeyEventInventory);
				curStage.setScene(invScene);
				break;
			default:
				break;
		}
		
		Cell curCell = level.getCells().get(playerRow).get(playerColumn);
		
		if (curCell.getType().equals("goal")) {
			updateLeaderBoard(COUNT[0], getCurrentProfile().getProfileName(), "src/resources/leaderboard" + getCurrentProfile().getHighestLevel() + ".txt");
			//Checks if the player is playing on their highest level
			if (getCurrentProfile().getHighestLevelFileName().equals(this.levelName)) {
				getCurrentProfile().progressToNextLevel();
				setLevel(getCurrentProfile().getHighestLevelFileName());
				String nextLevelString = this.levelName.replaceAll("[^0-9]", "");
				int nextLevelInt = Integer.parseInt(nextLevelString) + 1;
				this.levelName = "level" + nextLevelInt + ".txt";
			} else {
				String nextLevelString = this.levelName.replaceAll("[^0-9]", "");
				int nextLevelInt = Integer.parseInt(nextLevelString);
				nextLevelInt = nextLevelInt + 1;
				System.out.println("level " + nextLevelInt);
				setLevel("level" + nextLevelInt + ".txt");
			}
			saveProfileList("profiles.txt");
			mapCanvasWidth = level.getMapWidth() * Cell.CELL_WIDTH;
			mapCanvasHeight = level.getMapHeight() * Cell.CELL_HEIGHT;
			mapCanvas.setWidth(mapCanvasWidth);
			mapCanvas.setHeight(mapCanvasHeight);
			mapCanvas.resize(mapCanvasWidth, mapCanvasHeight);
			gc = mapCanvas.getGraphicsContext2D();
			playerColumn = level.getPlayerStartX();
			playerRow = level.getPlayStartY();
			switch (prevPlayerMove) {
				case "UP":
					playerPixelX = playerColumn * 66;
					playerPixelY = playerRow * 66 + 66;
					break;
				
				case "DOWN":
					playerPixelX = playerColumn * 66;
					playerPixelY = playerRow * 66 - 66;
					break;
				
				case "LEFT":
					playerPixelX = playerColumn * 66 + 66;
					playerPixelY = playerRow * 66;
					break;
				
				case "RIGHT":
					playerPixelX = playerColumn * 66 - 66;
					playerPixelY = playerRow * 66;
					break;
				
				default:
					break;
			}
			
			drawGame();
		}
		
		//obtains a token
		if (curCell.getType().equals("token")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);  // pop up message can get rid of it when we add sound effects
			alert.setTitle("You got a token");
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setHeaderText("PACK OF CIGARETTE COLLECTED");
			alert.setContentText("Collect more cigarettes to pass Gangster Hideouts");
			alert.show();
			Player.addTokens();
			
			level.getCells().get(playerRow).set(playerColumn, new Ground()); // replace the cell with a 'ground' cell once collected
			level.getLayout().get(playerRow).set(playerColumn, ' ');
		}
		
		if (curCell.getType().equals("tokenDoor")) {
			TokenDoor temp = (TokenDoor) curCell;
			if (temp.getlockedStatus()) {
				if (!(Player.getTokens() >= temp.getTokensRequired())) {
					setSFX("lockedDoor");
					sfxPlayer.play();
					int posDiffColumn = playerColumn - prevPosColumn;
					int posDiffRow = playerRow - prevPosRow;
					
					if (posDiffColumn == 1) {
						performAnimationL();
					} else if (posDiffColumn == -1) {
						performAnimationR();
					}
					
					if (posDiffRow == 1) {
						performAnimationU();
					} else if (posDiffRow == -1) {
						performAnimationD();
					}
					playerColumn = prevPosColumn;
					playerRow = prevPosRow;
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
					dialogStage.getIcons().add(new Image("resources/icon.png"));
					alert.setTitle("Congrats!");
					alert.setHeaderText("You have enough tokens to pass");
					alert.show();
					temp.unlocked();
				}
			}
			//could add a opened door image
		}
		
		if (curCell.getType().equals("bat")) {
			setSFX("negan");
			sfxPlayer.play();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setTitle("You've acquired a kickass weapon");
			alert.setHeaderText("'LUCILLE' COLLECTED");
			alert.setContentText("Now go bash some zombie heads");
			alert.show();
			Player.pickBat();
			level.getCells().get(playerRow).set(playerColumn, new Ground()); // replace the cell with a 'ground' cell once collected
			level.getLayout().get(playerRow).set(playerColumn, ' ');
		}
		
		if (curCell.getType().equals("hatchet")) {
			setSFX("rick");
			sfxPlayer.play();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setTitle("You've acquired a kickass weapon");
			alert.setHeaderText("'RICK'S HATCHET' COLLECTED");
			alert.setContentText("Now show your 'mercy' to the Zeds");
			alert.show();
			Player.pickHatchet();
			level.getCells().get(playerRow).set(playerColumn, new Ground()); // replace the cell with a 'ground' cell once collected
			level.getLayout().get(playerRow).set(playerColumn, ' ');
		}
		
		//obtains a green key
		if (curCell.getType().equals("greenKey")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setTitle("You got a one-time military pass");
			alert.setHeaderText("One-Time Military Pass COLLECTED");
			alert.setContentText("You can now pass a military gate with it");
			alert.show();
			Player.pickKey("greenKey");
			level.getCells().get(playerRow).set(playerColumn, new Ground()); // replace the cell with a 'ground' cell once collected
			level.getLayout().get(playerRow).set(playerColumn, ' ');
		}
		
		//after opening a greendoor, you lose a greenkey
		if (curCell.getType().equals("greenDoor")) {
			GreenDoor temp = (GreenDoor) curCell;
			if (temp.getlockedStatus()) {
				if (!Player.hasKey("greenKey")) {
					setSFX("lockedDoor");
					sfxPlayer.play();
					int posDiffColumn = playerColumn - prevPosColumn;
					int posDiffRow = playerRow - prevPosRow;
					
					if (posDiffColumn == 1) {
						performAnimationL();
					} else if (posDiffColumn == -1) {
						performAnimationR();
					}
					
					if (posDiffRow == 1) {
						performAnimationU();
					} else if (posDiffRow == -1) {
						performAnimationD();
					}
					playerColumn = prevPosColumn;
					playerRow = prevPosRow;
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
					dialogStage.getIcons().add(new Image("resources/icon.png"));
					alert.setTitle("You were stopped by the 'military'");
					alert.setHeaderText("You used a military pass to get through");
					alert.show();
					Player.consumeKey("greenKey");
					temp.unlocked();
				}
			}
			//could add a opened door image
		}
		
		if (curCell.getType().equals("redKey")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setTitle("You got a one-time Red Cross Gang pass");
			alert.setHeaderText("One-time Red Cross Gang pass COLLECTED");
			alert.setContentText("You can now get pass a Red Cross hideout with it");
			alert.show();
			Player.pickKey("redKey");
			level.getCells().get(playerRow).set(playerColumn, new Ground()); // replace the cell with a 'ground' cell once collected
			level.getLayout().get(playerRow).set(playerColumn, ' ');
		}
		
		if (curCell.getType().equals("redDoor")) {
			RedDoor temp = (RedDoor) curCell;
			if (temp.getlockedStatus()) {
				if (!Player.hasKey("redKey")) {
					setSFX("lockedDoor");
					sfxPlayer.play();
					int posDiffColumn = playerColumn - prevPosColumn;
					int posDiffRow = playerRow - prevPosRow;
					
					if (posDiffColumn == 1) {
						performAnimationL();
					} else if (posDiffColumn == -1) {
						performAnimationR();
					}
					
					if (posDiffRow == 1) {
						performAnimationU();
					} else if (posDiffRow == -1) {
						performAnimationD();
					}
					playerColumn = prevPosColumn;
					playerRow = prevPosRow;
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
					dialogStage.getIcons().add(new Image("resources/icon.png"));
					alert.setTitle("You were stopped by the 'Red Cross Gang'");
					alert.setHeaderText("You used a Red Cross pass to get through");
					alert.show();
					Player.consumeKey("redKey");
					temp.unlocked();
				}
			}
			//could add a opened door image
		}
		
		if (curCell.getType().equals("yellowKey")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setTitle("You got a one-time Yellow Snake Gang pass");
			alert.setHeaderText("One-time Yellow Snake Gang pass COLLECTED");
			alert.setContentText("You can now get pass a Yellow Snake hideout with it");
			alert.show();
			Player.pickKey("yellowKey");
			level.getCells().get(playerRow).set(playerColumn, new Ground()); // replace the cell with a 'ground' cell once collected
			level.getLayout().get(playerRow).set(playerColumn, ' ');
		}
		
		if (curCell.getType().equals("yellowDoor")) {
			YellowDoor temp = (YellowDoor) curCell;
			if (temp.getlockedStatus()) {
				if (!Player.hasKey("yellowKey")) {
					setSFX("lockedDoor");
					sfxPlayer.play();
					int posDiffColumn = playerColumn - prevPosColumn;
					int posDiffRow = playerRow - prevPosRow;
					
					if (posDiffColumn == 1) {
						performAnimationL();
					} else if (posDiffColumn == -1) {
						performAnimationR();
					}
					
					if (posDiffRow == 1) {
						performAnimationU();
					} else if (posDiffRow == -1) {
						performAnimationD();
					}
					playerColumn = prevPosColumn;
					playerRow = prevPosRow;
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
					dialogStage.getIcons().add(new Image("resources/icon.png"));
					alert.setTitle("You were stopped by the 'Yellow Snake Gang'");
					alert.setHeaderText("You used a Yellow Snake pass to get through");
					alert.show();
					Player.consumeKey("yellowKey");
					temp.unlocked();
				}
			}
			//could add a opened door image
		}
		
		if (curCell.getType().equals("flippers")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setTitle("You got a pair of flippers");
			alert.setHeaderText("FLIPPERS COLLECTED");
			alert.setContentText("You can now walk on water");
			alert.show();
			Player.pickFlippers();
			level.getCells().get(playerRow).set(playerColumn, new Ground()); // replace the cell with a 'ground' cell once collected
			level.getLayout().get(playerRow).set(playerColumn, ' ');
		}
		
		if (curCell.getType().equals("water")) {
			if (!Player.hasFlippers) {
				setSFX("drowning");
				mediaPlayer.setMute(true);
				sfxPlayer.play();
				drawGame();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("You drowned");
				Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
				dialogStage.getIcons().add(new Image("resources/icon.png"));
				alert.setHeaderText("Choose more wisely next time");
				alert.setContentText("Do you have the courage to try again?");
				
				ButtonType one = new ButtonType("Yes");
				ButtonType two = new ButtonType("No, I'm a pussy, I quit");
				
				alert.getButtonTypes().setAll(one, two);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == one) {
					sfxPlayer.stop();
					mediaPlayer.setMute(false);
					restartGame();
				} else {
					System.exit(0);
				}
			}
		}
		
		if (curCell.getType().equals("fireboots")) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setTitle("You got a pair of fireboots");
			alert.setHeaderText("FIREBOOTS COLLECTED");
			alert.setContentText("You can now walk on fire");
			alert.show();
			Player.pickFireBoots();
			level.getCells().get(playerRow).set(playerColumn, new Ground()); // replace the cell with a 'ground' cell once collected
			level.getLayout().get(playerRow).set(playerColumn, ' ');
		}
		
		if (curCell.getType().equals("fire")) {
			if (!Player.hasFireBoots) {
				drawGame();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("You were burned to death");
				Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
				dialogStage.getIcons().add(new Image("resources/icon.png"));
				alert.setHeaderText("Chose more wisely next time");
				alert.setContentText("Do you have the courage to try again?");
				
				ButtonType one = new ButtonType("Yes");
				ButtonType two = new ButtonType("No, I'm a pussy, I quit");
				
				alert.getButtonTypes().setAll(one, two);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == one) {
					restartGame();
				} else {
					System.exit(0);
				}
			}
		}
		
		if (curCell.getType().equals("teleporter")) {
			drawGame();
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("You entered a teleporter");
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setHeaderText("This will take you to some magical places");
			alert.setContentText("Do you want to go?");
			
			ButtonType one = new ButtonType("Yes");
			ButtonType two = new ButtonType("No, I need to think twice");
			
			alert.getButtonTypes().setAll(one, two);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == one) {
				for (int i = 0; i < level.getCells().size(); i++) {
					for (int j = 0; j < level.getCells().get(i).size(); j++) {
						if (level.getCells().get(i).get(j).getType().equals("teleporter2")) {
							if (level.getCells().get(i).get(j + 1).getType().equals("ground")) {
								playerColumn = j + 1;
								playerRow = i;
								playerPixelX = playerColumn * 66;
								playerPixelY = playerRow * 66;
								moveCamera();
								drawGame();
							} else if (level.getCells().get(i).get(j - 1).getType().equals("ground")) {
								playerColumn = j - 1;
								playerRow = i;
								playerPixelX = playerColumn * 66;
								playerPixelY = playerRow * 66;
								moveCamera();
								drawGame();
							} else if (level.getCells().get(i - 1).get(j).getType().equals("ground")) {
								playerColumn = j;
								playerRow = i - 1;
								playerPixelX = playerColumn * 66;
								playerPixelY = playerRow * 66;
								moveCamera();
								drawGame();
							} else if (level.getCells().get(i + 1).get(j).getType().equals("ground")) {
								playerColumn = j;
								playerRow = i + 1;
								playerPixelX = playerColumn * 66;
								playerPixelY = playerRow * 66;
								moveCamera();
								drawGame();
							}
							break;
						}
					}
				}
			} else {
				int posDiffColumn = playerColumn - prevPosColumn;
				int posDiffRow = playerRow - prevPosRow;
				
				if (posDiffColumn == 1) {
					performAnimationL();
				} else if (posDiffColumn == -1) {
					performAnimationR();
				}
				
				if (posDiffRow == 1) {
					performAnimationU();
				} else if (posDiffRow == -1) {
					performAnimationD();
				}
				playerColumn = prevPosColumn;
				playerRow = prevPosRow;
			}
		}
		
		if (curCell.getType().equals("teleporter2")) {
			drawGame();
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("You entered a teleporter");
			Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
			dialogStage.getIcons().add(new Image("resources/icon.png"));
			alert.setHeaderText("This will take you to some magical places");
			alert.setContentText("Do you want to go?");
			
			ButtonType one = new ButtonType("Yes");
			ButtonType two = new ButtonType("No, I need to think twice");
			
			alert.getButtonTypes().setAll(one, two);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == one) {
				for (int i = 0; i < level.getCells().size(); i++) {
					for (int j = 0; j < level.getCells().get(i).size(); j++) {
						if (level.getCells().get(i).get(j).getType().equals("teleporter")) {
							if (level.getCells().get(i).get(j + 1).getType().equals("ground")) {
								playerColumn = j + 1;
								playerRow = i;
								playerPixelX = playerColumn * 66;
								playerPixelY = playerRow * 66;
								moveCamera();
								drawGame();
							} else if (level.getCells().get(i).get(j - 1).getType().equals("ground")) {
								playerColumn = j - 1;
								playerRow = i;
								playerPixelX = playerColumn * 66;
								playerPixelY = playerRow * 66;
								moveCamera();
								drawGame();
							} else if (level.getCells().get(i - 1).get(j).getType().equals("ground")) {
								playerColumn = j;
								playerRow = i - 1;
								playerPixelX = playerColumn * 66;
								playerPixelY = playerRow * 66;
								moveCamera();
								drawGame();
							} else if (level.getCells().get(i + 1).get(j).getType().equals("ground")) {
								playerColumn = j;
								playerRow = i + 1;
								playerPixelX = playerColumn * 66;
								playerPixelY = playerRow * 66;
								moveCamera();
								drawGame();
							}
							break;
						}
					}
				}
			} else {
				int posDiffColumn = playerColumn - prevPosColumn;
				int posDiffRow = playerRow - prevPosRow;
				
				if (posDiffColumn == 1) {
					performAnimationL();
				} else if (posDiffColumn == -1) {
					performAnimationR();
				}
				
				if (posDiffRow == 1) {
					performAnimationU();
				} else if (posDiffRow == -1) {
					performAnimationD();
				}
				playerColumn = prevPosColumn;
				playerRow = prevPosRow;
			}
		}
		
		updateCurrentPlayer(playerColumn, playerRow);
		
		moveCamera();
		checkEaten();
		drawGame();
		moveEnemies();
		drawGame();
		checkEaten();
		event.consume();
	}
	
	/**
	 * This method checks if the player is in contact with an enemy
	 *
	 * @return The boolean value true if player is in contact with an enemy entity, else false.
	 */
	private boolean meetEnemy() {
		boolean meetEnemy = false;
		for (StraightLineEnemy a : level.getStraightEnemies()) {
			if (a.getxPos() == playerColumn && a.getyPos() == playerRow) {
				meetEnemy = true;
				break;
			}
		}
		for (WallFollowingEnemy a : level.getWallEnemies()) {
			if (a.getxPos() == playerColumn && a.getyPos() == playerRow) {
				meetEnemy = true;
				break;
			}
		}
		for (DumbTargetingEnemy a : level.getDumbEnemies()) {
			if (a.getxPos() == playerColumn && a.getyPos() == playerRow) {
				meetEnemy = true;
				break;
			}
		}
		for (SmartTargetingEnemy a : level.getSmartEnemies()) {
			if (a.getxPos() == playerColumn && a.getyPos() == playerRow) {
				meetEnemy = true;
				break;
			}
		}
		return meetEnemy;
	}
	
	/**
	 * This method checks if the player should be dead by being in contact with an enemy, kills the player-
	 * if yes, and plays the death sound, then asks the player if they want to retry. Death event is cancelled-
	 * if the player has a weapon item, removes the weapon from player after used.
	 */
	private void checkEaten() {
		//dies if on the same cell with enemy
		if (meetEnemy()) {
			if (!Player.hasBat && !Player.hasHatchet) {
				setSFX("eaten");
				mediaPlayer.setMute(true);
				sfxPlayer.play();
				drawGame();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("You died");
				Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
				dialogStage.getIcons().add(new Image("resources/icon.png"));
				alert.setHeaderText("Chose more wisely next time");
				alert.setContentText("Do you have the courage to try again?");
				
				ButtonType one = new ButtonType("Yes");
				ButtonType two = new ButtonType("No, I'm a pussy, I quit");
				
				alert.getButtonTypes().setAll(one, two);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == one) {
					mediaPlayer.setMute(false);
					restartGame();
				} else {
					System.exit(0);
				}
			} else {
				setSFX("eaten");
				sfxPlayer.play();
				drawGame();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
				dialogStage.getIcons().add(new Image("resources/icon.png"));
				alert.setTitle("A Zed got to you!");
				alert.setHeaderText("Luckily you have a weapon!");
				alert.setContentText("You escaped but sadly lost the weapon...");
				alert.show();
				if (Player.hasHatchet) {
					Player.dropHatchet();
				} else {
					Player.dropBat();
				}
			}
		}
	}
	
	/**
	 * This method updates the player position in the current player class.
	 *
	 * @param xPos the X coordinate of the player.
	 * @param yPos the Y coordinate of the player.
	 */
	private void updateCurrentPlayer(int xPos, int yPos) {
		curPlayer.setxPos(xPos);
		curPlayer.setyPos(yPos);
	}
	
	/**
	 * This method updates the leader board and registers it with the finishing time of players.
	 *
	 * @param time     the finishing time of the player.
	 * @param user     the profile name of the player.
	 * @param filePath the file path of the leader board.
	 */
	private void updateLeaderBoard(double time, String user, String filePath) {
		
		double time1;
		double time2;
		double time3;
		
		String name1;
		String name2;
		String name3;
		
		File input = new File(filePath);
		try {
			Scanner in = new Scanner(input).useDelimiter(",");
			
			time1 = in.nextDouble();
			name1 = in.nextLine().substring(1);
			
			time2 = in.nextDouble();
			name2 = in.nextLine().substring(1);
			
			time3 = in.nextDouble();
			name3 = in.nextLine().substring(1);
			
			Double[] result = new Double[3];
			String[] names = new String[3];
			
			if (time1 < time) {
				result[0] = time1;
				names[0] = name1;
				if (time2 < time) {
					result[1] = time2;
					names[1] = name2;
					if (time3 < time) {
						result[2] = time3;
						names[2] = name3;
					} else {
						result[2] = time;
						names[2] = user;
					}
				} else {
					result[1] = time;
					names[1] = user;
					result[2] = time2;
					names[2] = name2;
				}
			} else {
				result[0] = time;
				names[0] = user;
				result[1] = time1;
				names[1] = name1;
				result[2] = time2;
				names[2] = name2;
			}
			
			for (int i = 0; i < 3; i++) {
				System.out.println(names[i]);
			}
			
			in.close();
			
			PrintWriter printWriter = new PrintWriter(filePath);
			for (int i = 0; i < 3; i++) {
				printWriter.print(result[i] + ",");
				printWriter.println(names[i]);
			}
			
			printWriter.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("leader board file not found!!");
			System.exit(0);
		}
	}
	
	/**
	 * event handler for inventory UI
	 *
	 * @param event input named event.
	 */
	private void processKeyEventInventory(KeyEvent event) {
		switch (event.getCode()) {
			case ESCAPE:
			case I:
				curStage.setScene(mapScene);
				break;
			case M:
				musicSwitch();
				break;
			default:
				break;
		}
	}
	
	/**
	 * This method changes the current level to a new one.
	 *
	 * @param levelFileName name of the new level file.
	 */
	public void setLevel(String levelFileName) {
		this.level = new Level(levelFileName);
	}
}
