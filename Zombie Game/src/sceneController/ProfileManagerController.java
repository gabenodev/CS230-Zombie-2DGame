package sceneController;

import application.Entities.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acts as a controller for the 'profileManager.fxml'.
 * Displays the existing profiles in a listview.
 * It allows the user to select and delete existing profiles.
 *
 * @author Jordan Holbrook
 */
public class ProfileManagerController {

    /**
     * Constant variables.
     */
    private static final String profileFile = "src/resources/profiles.txt";
    public static List<Profile> profiles = new ArrayList<Profile>();
    private static Profile currentProfile;
    @FXML ListView<String> profileList;
    @FXML Button newProfileButton;
    @FXML Button selectButton;
    @FXML Button deleteButton;

    public void initialize() {

        loadProfiles();

        /**
         * FXML commands to assign transparent backgrounds to the buttons.
         */
        profileList.setStyle("-fx-background-color: transparent;");
        newProfileButton.setStyle("-fx-background-color: transparent;");
        selectButton.setStyle("-fx-background-color: transparent;");
        deleteButton.setStyle("-fx-background-color: transparent;");
        /**
         * FXML commands to make the text change to red when the users mouse hovers over.
         */
        newProfileButton.setOnMouseEntered(e -> newProfileButton.setTextFill(Color.RED));
        selectButton.setOnMouseEntered(e -> selectButton.setTextFill(Color.RED));
        deleteButton.setOnMouseEntered(e -> deleteButton.setTextFill(Color.RED));
        /**
        * FXML commands to make the text change back to black
         * once the mouse stops hovering over the text.
        */
        newProfileButton.setOnMouseExited(e -> newProfileButton.setTextFill(Color.BLACK));
        selectButton.setOnMouseExited(e -> selectButton.setTextFill(Color.BLACK));
        deleteButton.setOnMouseExited(e -> deleteButton.setTextFill(Color.BLACK));
        // Display the profiles
        refreshProfileList();
    }
    /**
     * Reads the array from 'profileFile' into the profiles array
    */
    private void loadProfiles() {
        try {
            FileInputStream fis = new FileInputStream(profileFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            profiles = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            return;
        }
    }
    /**
     * Loads the Profiles in the arraylist 'profiles' into the list view
     */
    private void refreshProfileList() {

        // Clear the displayed list
        profileList.getItems().clear();

        // Add each profile to the displayed list

        for (Profile p : profiles) {
            profileList.getItems().add(p.toString());
        }

    }
    /**
     * Loads the 'NewProfile.fxml' scene
     * @param event The button pressed
     */
    public void handleNewProfileButton(ActionEvent event) {

        try {
            // Load the main scene.
            String filePathString = "src/scene/newProfile.fxml";

            File f = new File(filePathString);
            if(f.exists()) {
                System.out.println("File exists");
                System.out.println(f.getAbsoluteFile());
            }

            VBox root = (VBox) FXMLLoader.load(f.toURI().toURL());
            Scene scene = new Scene(root, 480, 607);

            // Place the main scene on stage and show it.
            Stage newProfileStage = new Stage();
            newProfileStage.setScene(scene);
            newProfileStage.getIcons().add(new Image("resources/icon.png"));
            newProfileStage.setTitle("NewProfilePage");
            newProfileStage.initModality(Modality.APPLICATION_MODAL);
            newProfileStage.showAndWait();
            refreshProfileList();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    /**
     * Removes the selected profile from the array then saves
     * the array list to the file.
     * @param event The button pressed
     */
    public void handleDeleteButtonAction(ActionEvent event) {
        // Get the index of the selected item in the displayed list
        int selectedIndex = profileList.getSelectionModel().getSelectedIndex();

        // Check if user selected an item
        if (selectedIndex < 0) {
            // Show a message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Cannot delete profile");
            alert.setHeaderText(null);
            alert.setContentText("Cannot delete profile as no profile is selected. Please select a profile first.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure " +
                    "you want to delete this profile?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                // Remove the profile at the selected index.
                profiles.remove(selectedIndex);
                saveProfileList(profileFile);
            } else {
                return;
            }
        }
        // We might have deleted a profile - so we must refresh the displayed list
        refreshProfileList();
    }
    /**
     * Assigns the selected profile to currentProfile
     * If successful the scene is closed
     * @param event The button pressed
     */
    public void handleSelectButtonAction(ActionEvent event) {
        //Assigns the selected profile on the list to currentProfile, which is used around the program

        int selectedIndex = profileList.getSelectionModel().getSelectedIndex();

        if (selectedIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Cannot select profile");
            alert.setHeaderText(null);
            alert.setContentText("You have not selected a survivor");
            alert.showAndWait();
        } else {
            currentProfile = profiles.get(selectedIndex);

            //This below is just for seeing the output
            System.out.println(currentProfile.getProfileName());
            System.out.println(currentProfile.getHighestLevel());
            System.out.println(currentProfile.getHighestLevelFileName());

            ((Node)event.getTarget()).getScene().getWindow().hide();
        }
    }
    /**
     * @return the currentProfile.
     */
    public static Profile getCurrentProfile() {
        return currentProfile;
    }
    /**
     * @return the ProfileFile.
     */
    public static String getProfileFile() {
        return profileFile;
    }

    /**
     * Run this method below when the player progress onto a new level
     * @param profileFile the user profiles.
     */
    public static void saveProfileList(String profileFile) {
        try {
            FileOutputStream f = new FileOutputStream(new File(profileFile));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(profiles);

            o.close();
            f.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
