package sceneController;

import application.Entities.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.*;

import static sceneController.ProfileManagerController.*;

/**
 * This class acts as a controller for newProfile.fxml
 * It allows the user to create a new user and saves it to the file
 *
 * @author Jordan Holbrook
 */

public class NewProfileController {
    @FXML Button createButton;
    @FXML TextField nameTextField;

    public void initialize() {
        nameTextField.setStyle("-fx-background-color: transparent;");
        createButton.setStyle("-fx-background-color: transparent;");
        createButton.setOnMouseEntered(e -> createButton.setTextFill(Color.RED));
        createButton.setOnMouseExited(e -> createButton.setTextFill(Color.BLACK));
    }

    public void handleCreateButton(ActionEvent event) {
        /**
         * Loops through the profiles array to check if a profile already exists with the entered name.
         * If the profile doesn't exist then the profile is added to the the array.
         * Scene is closed if new Profile is created successfully.
         */
        boolean profileExists = false;
        for (Profile p:profiles) {
            if (p.getProfileName().equals(nameTextField.getText())){
                profileExists = true;
            }
        }
        if (profileExists == false) {
            String profileName = nameTextField.getText();
            profiles.add(new Profile(profileName, 1));
            try {
                saveProfileList(getProfileFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((Node)event.getTarget()).getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Cannot create profile");
            alert.setHeaderText(null);
            alert.setContentText("A survivor with this name already exists.");
            alert.showAndWait();
            nameTextField.setText("");
        }
    }
}
