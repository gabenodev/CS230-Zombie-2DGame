package application.Entities;

import sceneController.ProfileManagerController;

import java.io.Serializable;

/**
 * Represents a profile which can be used throughout the program.
 * A profile can be created and stored in a file.
 *
 * @author Jordan Holbrook
 * @version 3.5.0
 */
public class Profile implements Serializable {
	// Persistent Fields:
	private static final long serialVersionUID = 1L;
	private String profileName;
	private Integer highestLevel;
	private String highestLevelFileName;
	
	/**
	 * Creates a new Profile with the entered name.
	 * highestLevel will always be set to 1 when created.
	 *
	 * @param profileName  name of the profile in string.
	 * @param highestLevel the highest level reached of the current profile.
	 */
	public Profile(String profileName, Integer highestLevel) {
		this.profileName = profileName;
		this.highestLevel = highestLevel;
		setHighestLevelFileName(highestLevel);
	}
	
	/**
	 * @param profileName profileName to set (String)
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	/**
	 * @param highestLevel highestLevel to set (Integer)
	 */
	public void setHighestLevel(Integer highestLevel) {
		this.highestLevel = highestLevel;
	}
	
	/**
	 * @param highestLevel highestLevelFileName to set (String)
	 */
	public void setHighestLevelFileName(int highestLevel) {
		this.highestLevelFileName = "level" + highestLevel + ".txt";
	}
	
	/**
	 * @return this Profile's name.
	 */
	public String getProfileName() {
		return this.profileName;
	}
	
	/**
	 * @return this Profile's highestLevel.
	 */
	public Integer getHighestLevel() {
		return this.highestLevel;
	}
	
	/**
	 * @return this Profile's highestLevelFileName.
	 */
	public String getHighestLevelFileName() {
		return highestLevelFileName;
	}
	
	/**
	 * Used to increase the profiles highestLevel
	 */
	public void progressToNextLevel() {
		this.highestLevel++;
		Player.hasFlippers = false;
		Player.hasFireBoots = false;
		setHighestLevelFileName(highestLevel);
		ProfileManagerController.saveProfileList(ProfileManagerController.getProfileFile());
	}
	
	/**
	 * Used to print out the Profile class as a string
	 *
	 * @return String The profile name and its highest level.
	 */
	@Override
	public String toString() {
		return String.format(profileName + " - level " + highestLevel);
	}
}