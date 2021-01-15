package application;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import sceneController.ProfileManagerController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static javafx.scene.layout.BackgroundSize.DEFAULT;
import static sceneController.ProfileManagerController.getCurrentProfile;

/**
 * Acts as the Main Menu of the program.
 * Launches the program and displays Menu scene.
 *
 * @author Gabriel Petcu
 * @version 2.5.0
 */
public class Main extends Application {
	
	/**
	 * Constant variables.
	 */
	private GameMenu gameMenu;
	static final int WINDOW_WIDTH = 992;
	static final int WINDOW_HEIGHT = 792;
	private Stage curStage;
	private MediaPlayer mediaPlayer;
	private static int profileRank = 0;
	
	private Map<String, Image> profileImageCollection;
	
	private ObservableList<String> profiles = FXCollections.observableList(FXCollections.observableArrayList());
	
	/**
	 * Shows the main menu screens to the player.
	 *
	 * @param primaryStage The primary stage for this application, onto which the application scene can be set. The primary stage will be embedded in the browser if the application was launched as an applet. Applications may create other stages, if needed, but they will not be primary stages and will not be embedded in the browser.
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		setMusic();
		mediaPlayer.play();
		
		Pane root = new Pane();
		root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		InputStream is = Files.newInputStream(Paths.get("src/resources/bg-image.jpg"));
		Image img = new Image(is);
		is.close();
		
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(WINDOW_HEIGHT);
		imgView.setFitWidth(WINDOW_WIDTH);
		
		gameMenu = new GameMenu();
		gameMenu.setVisible(true);
		
		root.getChildren().addAll(imgView, gameMenu);
		
		Scene scene = new Scene(root);
		scene.setOnKeyPressed(event -> {
			
			if (event.getCode() == KeyCode.ESCAPE) {
				if (!gameMenu.isVisible()) {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
					ft.setFromValue(1);
					ft.setToValue(1);
					
					gameMenu.setVisible(true);
					ft.play();
				} else {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
					ft.setFromValue(1);
					ft.setToValue(0);
					ft.setOnFinished(evt -> gameMenu.setVisible(false));
					ft.play();
				}
			}
		});
		
		curStage = primaryStage;
		
		curStage.setTitle(Game.GAME_TITLE);
		curStage.getIcons().add(new Image("resources/icon.png"));
		curStage.setScene(scene);
		curStage.show();
	}
	
	/**
	 * Sets the background music for the menu.
	 */
	void setMusic() {
		String musicFile = "src/resources/sample2.wav";
		Media sound = new Media((new File(musicFile)).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setMute(false);
		mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
	}
	
	/**
	 * This class is used to create the scene for the main menu.
	 * Creates all the buttons and assigns them events.
	 */
	private class GameMenu extends Parent {
		public GameMenu() {
			VBox menu0 = new VBox(10);
			VBox menu1 = new VBox(10);
			VBox menu2 = new VBox(10);
			VBox menu3 = new VBox(10);
			VBox menu4 = new VBox(10);
			
			menu0.setTranslateX(100);
			menu0.setTranslateY(200);
			
			menu2.setTranslateX(100);
			menu2.setTranslateY(200);
			
			menu1.setTranslateX(100);
			menu1.setTranslateY(200);
			
			menu3.setTranslateX(100);
			menu3.setTranslateY(200);
			
			menu4.setTranslateX(100);
			menu4.setTranslateY(200);
			
			final int offset = 400;
			
			menu1.setTranslateX(offset);
			
			
			MenuButton btnLboard = new MenuButton("LEADERBOARDS");
			btnLboard.setOnMouseClicked(event -> {
				getChildren().add(menu2);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt.setToX(menu0.getTranslateX() - offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu2);
				tt1.setToX(menu0.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt -> {
					getChildren().remove(menu0);
				});
				
			});
			
			MenuButton btnMenu = new MenuButton("BACK");
			btnMenu.setOnMouseClicked(event -> {
				getChildren().add(menu0);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
				tt.setToX(menu2.getTranslateX() + offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
				tt1.setToX(menu2.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt -> {
					getChildren().remove(menu2);
				});
			});
			
			MenuButton btnBackToLboard = new MenuButton("BACK");
			btnBackToLboard.setOnMouseClicked(event -> {
				getChildren().add(menu2);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu4);
				tt.setToX(menu2.getTranslateX() + offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu2);
				tt1.setToX(menu4.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt -> {
					getChildren().remove(menu4);
				});
			});
			
			MenuButton btnLboard1 = new MenuButton("LEVEL 1");
			btnLboard1.setOnMouseClicked(event -> {
				profileRank = 0;
				ListView<String> profileList = getListView(1);
				profileList.setPrefWidth(650);
				profileList.setPrefHeight(225);
				menu4.getChildren().clear();
				menu4.getChildren().addAll(btnBackToLboard, profileList);
				getChildren().add(menu4);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
				tt.setToX(menu2.getTranslateX() - offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu4);
				tt1.setToX(menu2.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt -> {
					getChildren().remove(menu2);
				});
			});
			
			MenuButton btnLboard2 = new MenuButton("LEVEL 2");
			btnLboard2.setOnMouseClicked(event -> {
				profileRank = 0;
				ListView<String> profileList = getListView(2);
				profileList.setPrefWidth(650);
				profileList.setPrefHeight(225);
				menu4.getChildren().clear();
				menu4.getChildren().addAll(btnBackToLboard, profileList);
				getChildren().add(menu4);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
				tt.setToX(menu2.getTranslateX() - offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu4);
				tt1.setToX(menu2.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt -> {
					getChildren().remove(menu2);
				});
			});
			
			MenuButton btnLboard3 = new MenuButton("LEVEL 3");
			btnLboard3.setOnMouseClicked(event -> {
				profileRank = 0;
				ListView<String> profileList = getListView(3);
				profileList.setPrefWidth(650);
				profileList.setPrefHeight(225);
				menu4.getChildren().clear();
				menu4.getChildren().addAll(btnBackToLboard, profileList);
				getChildren().add(menu4);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
				tt.setToX(menu2.getTranslateX() - offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu4);
				tt1.setToX(menu2.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt -> {
					getChildren().remove(menu2);
				});
			});
			
			MenuButton btnStart = new MenuButton("START");
			btnStart.setOnMouseClicked(event -> {
				if (ProfileManagerController.getCurrentProfile() == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error: No survivor selected");
					alert.setHeaderText(null);
					alert.setContentText("You have not selected a survivor");
					alert.showAndWait();
				} else {
					//
					getChildren().add(menu3);
					
					TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
					tt.setToX(menu0.getTranslateX() - offset);
					
					TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
					tt1.setToX(menu0.getTranslateX());
					
					tt.play();
					tt1.play();
					
					tt.setOnFinished(evt -> {
						getChildren().remove(menu0);
					});
				}
			});
			
			MenuButton btnBackFromPlay = new MenuButton("BACK");
			btnBackFromPlay.setOnMouseClicked(event -> {
				getChildren().add(menu0);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
				tt.setToX(menu3.getTranslateX() + offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
				tt1.setToX(menu3.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt -> {
					getChildren().remove(menu3);
				});
			});
			
			MenuButton btnLevel1 = new MenuButton("Chapter 1");
			btnLevel1.setOnMouseClicked(event -> {
				Stage s = new Stage();
				try {
					new Game("level1.txt").start(s);
					mediaPlayer.setMute(true);// launch new game with current profile
					curStage.close();
				} catch (NullPointerException e) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Failed to verify your identification");
					Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
					dialogStage.getIcons().add(new Image("resources/icon.png"));
					alert.setHeaderText("Please select a profile");
					alert.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			MenuButton btnLevel2 = new MenuButton(("Chapter 2"));
			btnLevel2.setOnMouseClicked(event -> {
				if (getCurrentProfile().getHighestLevel() >= 2) {
					Stage s = new Stage();
					try {
						new Game("level2.txt").start(s);
						mediaPlayer.setMute(true);// launch new game with current profile
						curStage.close();
					} catch (NullPointerException e) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Failed to verify your identification");
						Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
						dialogStage.getIcons().add(new Image("resources/icon.png"));
						alert.setHeaderText("Please select a profile");
						alert.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("You haven't unlocked Chapter 2");
					Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
					dialogStage.getIcons().add(new Image("resources/icon.png"));
					alert.setHeaderText("This is no shortcut in life");
					alert.setContentText("please finish Chapter 1 first");
					alert.show();
				}
			});
			
			MenuButton btnLevel3 = new MenuButton(("Chapter 3"));
			btnLevel3.setOnMouseClicked(event -> {
				if (getCurrentProfile().getHighestLevel() >= 3) {
					Stage s = new Stage();
					try {
						new Game("level3.txt").start(s);
						mediaPlayer.setMute(true);// launch new game with current profile
						curStage.close();
					} catch (NullPointerException e) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Failed to verify your identification");
						Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
						dialogStage.getIcons().add(new Image("resources/icon.png"));
						alert.setHeaderText("Please select a profile");
						alert.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("You haven't unlocked Chapter 3");
					Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
					dialogStage.getIcons().add(new Image("resources/icon.png"));
					alert.setHeaderText("This is no shortcut in life");
					alert.setContentText("please finish Chapter 1 & 2 first");
					alert.show();
				}
			});
			/*
			  Loads the profile page
			 */
			MenuButton btnProfile = new MenuButton("PROFILE");
			btnProfile.setOnMouseClicked(event -> {
				try {
					// Load the main scene.
					
					String filePathString = "src/scene/profileManager.fxml";
					
					File f = new File(filePathString);
					if (f.exists()) {
						System.out.println("File exists");
						System.out.println(f.getAbsoluteFile());
					}
					
					VBox root = (VBox) FXMLLoader.load(f.toURI().toURL());
					Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
					
					// Place the main scene on stage and show it.
					Stage newProfileStage = new Stage();
					newProfileStage.setScene(scene);
					newProfileStage.setTitle("ProfilePage");
					newProfileStage.getIcons().add(new Image("resources/icon.png"));
					newProfileStage.initModality(Modality.APPLICATION_MODAL);
					newProfileStage.showAndWait();
					
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
				
			});
			
			MenuButton btnResume = new MenuButton("RESUME");
			btnResume.setOnMouseClicked(event -> {
				FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setOnFinished(evt -> setVisible(false));
				ft.play();
				Stage s = new Stage();
				try {
					new Game("savefile.txt").start(s);
					mediaPlayer.setMute(true);// launch new game with current profile
					curStage.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			//Opens the options page
			MenuButton btnOptions = new MenuButton("OPTIONS");
			btnOptions.setOnMouseClicked(event -> {
				getChildren().add(menu1);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt.setToX(menu0.getTranslateX() - offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
				tt1.setToX(menu0.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt -> {
					getChildren().remove(menu0);
				});
				
			});
			//Closes the program
			MenuButton btnExit = new MenuButton("EXIT");
			btnExit.setOnMouseClicked(event -> {
				System.exit(0);
			});
			
			MenuButton btnBack = new MenuButton("BACK");
			btnBack.setOnMouseClicked(event -> {
				getChildren().add(menu0);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
				tt.setToX(menu1.getTranslateX() + offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
				tt1.setToX(menu1.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt -> {
					getChildren().remove(menu1);
				});
			});
			
			MenuButton btnSound = new MenuButton("MUSIC ON/OFF");
			btnSound.setOnMouseClicked(event -> {
				if (mediaPlayer.isMute()) {
					mediaPlayer.setMute(false);
				} else {
					mediaPlayer.setMute(true);
				}
				
			});
			MenuButton btnVideo = new MenuButton("VIDEO");
			MenuButton btnCredits = new MenuButton("CREDITS");
			
			MessageOfTheDay mainMenuMsg = new MessageOfTheDay();
			Image msgBGImage = new Image("resources/msgOfTheDayBG.png");
			BackgroundImage msgBackground = new BackgroundImage(msgBGImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, DEFAULT);
			Background msgBG = new Background(msgBackground);
			
			
			HBox msgOfTheDay = new HBox(10);
			msgOfTheDay.setPrefSize(580, 250);
			msgOfTheDay.setPadding(new Insets(10, 20, 10, 20));
			
			TextFlow msgBox = new TextFlow();
			msgBox.setPadding(new Insets(10, 0, 10, 20));
			Text spacing = new Text("\n\n\n");
			Text title = new Text("Message for all survivors out there: \n");
			Text msg = new Text(mainMenuMsg.getMessage());
			title.setFont(Font.font("Unispace", 16));
			title.setFill(Color.BLACK);
			msg.setFont(Font.font("Unispace", 14));
			msg.setFill(Color.BLACK);
			msgOfTheDay.setBackground(msgBG);
			
			
			msgBox.getChildren().addAll(spacing, title, msg);
			msgBox.setTextAlignment(TextAlignment.LEFT);
			msgBox.setPadding(new Insets(10, 10, 10, 10));
			msgBox.setLineSpacing(20D);
			
			msgOfTheDay.getChildren().add(msgBox);
			
			//Building leader board UI
			
			menu0.getChildren().addAll(btnStart, btnResume, btnProfile, btnLboard, btnOptions, btnExit, msgOfTheDay);
			menu1.getChildren().addAll(btnBack, btnSound, btnVideo, btnCredits);
			
			menu2.getChildren().addAll(btnMenu, btnLboard1, btnLboard2, btnLboard3);
			menu3.getChildren().addAll(btnBackFromPlay, btnLevel1, btnLevel2, btnLevel3);
			
			Rectangle bg = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT);
			bg.setFill(Color.GREY);
			bg.setOpacity(0.4);
			
			getChildren().addAll(bg, menu0);
			
		}
	}
	
	/**
	 * Shows a list view of all the levels along with players and their times.
	 *
	 * @param level leader-board for each level
	 */
	private ListView<String> getListView(int level) {
		ArrayList<String> leaderBoardName1 = loadLeaderBoardName(1);
		ArrayList<String> leaderBoardName2 = loadLeaderBoardName(2);
		ArrayList<String> leaderBoardName3 = loadLeaderBoardName(3);
		
		if (level == 1) {
			profiles.clear();
			for (String a : leaderBoardName1) {
				profiles.add(a);
				System.out.println(profiles);
			}
			
			profileImageCollection = profiles.stream().collect(
					Collectors.toMap(
							profiles -> profiles,
							profiles -> new Image("resources/" + getImage() + ".png")
					)
			);
			
			ListView<String> profileList1 = new ListView<>(profiles);
			profileList1.setCellFactory(param -> new application.Main.ItemCell1());
			
			return profileList1;
		} else if (level == 2) {
			profiles.clear();
			for (String a : leaderBoardName2) {
				profiles.add(a);
				System.out.println(profiles);
			}
			
			profileImageCollection = profiles.stream().collect(
					Collectors.toMap(
							profiles -> profiles,
							profiles -> new Image("resources/" + getImage() + ".png")
					)
			);
			
			ListView<String> profileList2 = new ListView<>(profiles);
			profileList2.setCellFactory(param -> new application.Main.ItemCell2());
			
			return profileList2;
		} else {
			profiles.clear();
			for (String a : leaderBoardName3) {
				profiles.add(a);
				System.out.println(profiles);
			}
			
			profileImageCollection = profiles.stream().collect(
					Collectors.toMap(
							profiles -> profiles,
							profiles -> new Image("resources/" + getImage() + ".png")
					)
			);
			
			ListView<String> profileList3 = new ListView<>(profiles);
			profileList3.setCellFactory(param -> new application.Main.ItemCell3());
			
			return profileList3;
		}
	}
	
	private int getImage() {
		if (profileRank < 3) {
			profileRank++;
		}
		return profileRank;
	}
	
	/**
	 * Method used to fine the file storing the times for a certain level.
	 *
	 * @param level the level the leaderboard belongs to
	 * @return leaderBoardName
	 */
	private ArrayList<String> loadLeaderBoardName(int level) {
		File leaderboardFile = new File("src/resources/leaderboard" + level + ".txt");
		Scanner in = null;
		
		try {
			in = new Scanner(leaderboardFile);
		} catch (FileNotFoundException e) {
			System.out.println("didnt find leaderboard file while loading from main menu");
			System.exit(0);
		}
		
		ArrayList<String> leaderBoardName = new ArrayList<>();
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine).useDelimiter(",");
			line.nextDouble();
			leaderBoardName.add(line.nextLine().substring(1));
		}
		
		return leaderBoardName;
	}
	
	/**
	 * Gets the times for each level and the profile names.
	 *
	 * @param name  name of the profile
	 * @param level the level which the times belong to
	 * @return times for each player/level
	 */
	private double findLeaderBoardTime(String name, int level) {
		File leaderboardFile = new File("src/resources/leaderboard" + level + ".txt");
		Scanner in = null;
		double time = 0;
		
		try {
			in = new Scanner(leaderboardFile);
		} catch (FileNotFoundException e) {
			System.out.println("didnt find leaderboard file while loading from main menu");
			System.exit(0);
		}
		
		while (in.hasNextLine()) {
			String curLine = in.nextLine();
			Scanner line = new Scanner(curLine).useDelimiter(",");
			if (curLine.contains(name)) {
				time = line.nextDouble();
			}
		}
		
		return time;
	}
	
	/**
	 * Method to define ItemCell1
	 */
	private class ItemCell1 extends ListCell<String> {
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
						profileImageCollection.get(
								item)
				);
				
				setText(item + "      :      " + findLeaderBoardTime(item, 1) + " seconds");
				setGraphic(imageView);
			}
		}
	}
	
	/**
	 * Method to define ItemCell2
	 */
	private class ItemCell2 extends ListCell<String> {
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
						profileImageCollection.get(
								item)
				);
				
				setText(item + "      :      " + findLeaderBoardTime(item, 2) + " seconds");
				setGraphic(imageView);
			}
		}
	}
	
	/**
	 * Method to define ItemCell3
	 */
	private class ItemCell3 extends ListCell<String> {
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
						profileImageCollection.get(
								item)
				);
				
				setText(item + "      :      " + findLeaderBoardTime(item, 3) + " seconds");
				setGraphic(imageView);
			}
		}
	}
	
	/**
	 * Method to define MenuButton
	 */
	private static class MenuButton extends StackPane {
		
		private Text text;
		
		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(text.getFont().font(20));
			text.setFill(Color.WHITE);
			
			Rectangle bg = new Rectangle(200, 30);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			
			bg.setEffect(new GaussianBlur(3.5));
			
			this.setAlignment(Pos.CENTER_LEFT);
			setRotate(-0.5);
			getChildren().addAll(bg, text);
			
			setOnMouseEntered(event -> {
				bg.setTranslateX(10);
				text.setTranslateX(10);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});
			
			setOnMouseExited(Event -> {
				bg.setTranslateX(0);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
			
			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());
			
			setOnMousePressed(event -> setEffect(drop));
			setOnMouseReleased(event -> setEffect(null));
			
		}
	}
	
	/**
	 * Starts the program.
	 *
	 * @param args The command line arguments passed to the application. An application may get these parameters using the getParameters() method.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
