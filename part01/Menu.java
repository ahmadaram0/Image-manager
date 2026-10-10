package part01;

public class Menu {
	private String items[];
	private String title;

	/**
	 * This constructor takes an array of menu options and stores them in the items
	 * array and takes a title string and stores it in the title String variable.
	 * 
	 * @param title - the title of the menu
	 * @param data  - the String Array of menu options
	 */
	public Menu(String title, String data[]) {
		this.title = title;
		this.items = data;
	}

	public String display() { // Objects should not be dealing with user input and output
		String titleLine = "|   " + title; // We have 4 characters at the start so that the title is in line with the
											// options
		// Depending on titles length is inaccurate for the overall length so line
		// separators should be the longest text length
		// We will also be putting it in a box to make it more asthetically pleasing to
		// the user then just having standard line separators
		int longestLength = titleLine.length() + 1; // We add one because of the box line at the end of the text
		for (int i = 0; i < items.length; i++) {// For every item in items array
			String itemFormat = "|" + i + ". " + items[i];
			if (itemFormat.length() > longestLength) { // Checks a menu items length longer then the longest String
														// length
				// We add
				longestLength = itemFormat.length(); // If it is then it becomes the new longestLength
			}
		}
		String output = ""; // The variable that will store the output result that we'll later return to the
							// user
		// Upper Line
		for (int count = 0; count < longestLength + 1; count++) { // We add one because of the box line at the end of
																	// the text
			output += "_";

		}
		output += "\n";
		if (longestLength - titleLine.length() > 0) {
			output += titleLine + " ".repeat(longestLength - titleLine.length()) + "|\n";
		} else {
			output += titleLine + "|\n";
		}
		// Title and options seperator line
		for (int count = 0; count < longestLength + 1; count++) { // We add one because of the box line at the end of the text
			/**
			 * The line below used to be an overline but was later changed to an equals due to there being
			 * problems on windows that led to the overlines being unevenly sized compared to other characters.
			 * This wasn't a problem on linux which this program was made on.
			 */
			output += "=";
		}
		output += "\n";

		// Menu Options
		for (int option = 1; option <= items.length; option++) {
			String itemLine = "|" + option + ". " + items[option - 1];
			if (longestLength - itemLine.length() > 0) {
				output += itemLine + " ".repeat(longestLength - itemLine.length()) + "|\n";
			} else {
				output += itemLine + "|\n";
			}
		}
		// Bottom line
		for (int count = 0; count < longestLength + 1; count++) { // We add one because of the box line at the end of
																	// the text
			output += "="; // ‾

		}
		output += "\n";
		return output;
	}

	public String validateUserChoice(String userChoice) {
		try {
			int intUserChoice = Integer.parseInt(userChoice);
			if (intUserChoice > 0 && intUserChoice <= items.length) {
				return "";
			} else {
				return String.format(
						"Your Choice: %d is out of range. \n Please use the numbers shown alongside the options.",
						intUserChoice);
			}
		} catch (NumberFormatException nfe) {
			return "Invalid Choice. Please provide a integer value in numeric form for example 1 not one";
		}
	}

	// public int getUserChoice() { We've been taught not to use Scanner or other
	// objects within our objects as it allows Loose Coupling
	// display();
	// System.out.print("Enter Selection: ");
	// int value = input.nextInt(); values should be taken as string then system
	// should try converting so that it doesn't crash
	// String strValue = input.nextLine();
	// try {
	// int value = Integer.parseInt(strValue);
	// return value;
	// }catch(NumberFormatException nfe) {
	// return -1;
	// }
}
