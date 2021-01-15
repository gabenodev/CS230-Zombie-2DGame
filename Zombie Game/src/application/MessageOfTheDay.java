package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Gets the message of the day from the Swansea Computer Science Website and deciphers the text.
 *
 * @author Gabriel Petcu, Nayan Shrees
 * @version 2.1.0
 */
public class MessageOfTheDay {
	private final String PUZZLE_ADDRESS = "http://cswebcat.swan.ac.uk/puzzle";
	private final String TEST_PUZZLE_ADDRESS = "http://cswebcat.swan.ac.uk/message?solution=";
	
	/**
	 * Shifts the first character one character forward, second character one character backwards, and so on for the whole string.
	 * If the "Z" character needs to shift one character forward, it wraps round so "A" is used.
	 * If the "A" character needs to shift one character backward, it wraps round so "Z" is used.
	 *
	 * @param puzzle The puzzle string that needs to be decoded.
	 * @return The decoded solution shifted to the correct characters.
	 */
	private String decode(String puzzle) {
		String solution = "";
		for (int i = 0; i < puzzle.length(); i++) {
			if (i % 2 == 0) {
				if (puzzle.charAt(i) == 'Z') {
					solution += 'A';
				} else {
					solution += (char) ((int) puzzle.charAt(i) + 1);
				}
			} else {
				if (puzzle.charAt(i) == 'A') {
					solution += 'Z';
				} else {
					solution += (char) ((int) puzzle.charAt(i) - 1);
				}
			}
		}
		return solution;
	}
	
	/**
	 * Sends a GET request to the specified website.
	 *
	 * @param url The url the GET request is being sent to.
	 * @return The result of the GET request.
	 */
	private String getMessage(URL url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String message = reader.readLine();
			reader.close();
			connection.disconnect();
			return message;
		} catch (IOException e) {
			System.out.println("Error getting the message of the day, IO Exception");
			return null;
		}
	}
	
	/**
	 * Sends a GET request to get the puzzle.
	 * Decodes the puzzle.
	 * Sends a GET request to the solution website with the solution.
	 *
	 * @return The message of the day
	 */
	public String getMessage() {
		try {
			String puzzle = getMessage(new URL(PUZZLE_ADDRESS));
			String solution = decode(puzzle);
			String message = getMessage(new URL(TEST_PUZZLE_ADDRESS + solution));
			return message;
		} catch (MalformedURLException e) {
			System.out.println("Error getting the message of the day, Malformed URL");
			return null;
		}
	}
}
