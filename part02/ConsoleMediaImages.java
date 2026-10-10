package part02;

import part01.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import console.Console;

public class ConsoleMediaImages {
	// Below is an array of Menu objects as we will have more then one menu that we
	// will present to users to keep the options organized
	private static final Menu[] MENUS = {
			// Main Menu Iteration 0
			new Menu("Main Menu",
					new String[] { "Display All Images", "Search Image", "Add Image", "Menu Settings",
							"Display Last Image Result", "Exit Application" }),
			// Search Menu Iteration 1
			new Menu("Search Menu",
					new String[] { "Search ID", "Search by Title", "Search By Description", "Search By Genre",
							"Search Date Range", "Return to Main Menu" }),
			// Settings Menu Iteration 3
			new Menu("Settings Menu", new String[] { "Change Text Size", "Change Text Colour", "Change Text Font",
					"Change Background Colour", "Select pre-defined Themes", "Return to Main Menu" }) };
	private static int menuIterator = 0; // The initial menu to be presented to the user
	private static boolean choiceOutOfBounds = false; // If a switch case statement defaults inside of menuNavigator
														// this lets us know
	private static boolean start = true; // Keeps the application running until it's set to false by exit option
//private static Scanner scannerInput = new Scanner(System.in); // The old input method
	private static Console con = new Console(true); // The current input method
	private static ImageManager manageImages = new ImageManager(); // Our Instance of image manager that will be used by
																	// methods
	private static String filePathway = "Images/";
	private static ImageAlbum lastResult = null; // The last Image result or results returned to the user
	private static String lastResultQuery = ""; // The query that was given for the last result
	private static int fontSize = 15;
	private static String fontName = "Monospaced";
	private static String[] fontsArray = null;
	private static int fontStyle = Font.PLAIN;
	private static Color textColour = Color.BLACK;
	private static Color BgColour = new Color(255, 238, 176);
	private static Color[] colours = {Color.BLACK,Color.BLUE,Color.CYAN,Color.DARK_GRAY,Color.GRAY,Color.LIGHT_GRAY,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.RED,Color.WHITE,Color.YELLOW};
	private static String[] colourNames = {"Black","Blue","Cyan","Dark Gray","Gray","Light Gray","Green","Magenta","Orange","Pink","Red","White","Yellow"};
    private static enum StyleConfirmationChoices {
    	EXIT, ACCEPT, REJECT
    };
    private static enum changedStyleElement{
    	FONT,SIZE,COLOUR
    }
	/*
	 * The below enum has been made to improve search result readability as all
	 * search results used to present the entirety of toString but now they will
	 * present ID,title and the field that was searched which will be indicated by
	 * the enum below. for searches by ID or title we'll leave it null to signify no
	 * additional fields will be required.
	 */
	private static enum LastResultFormats {
		DESCRIPTION, GENRE, DATERANGE
	};// to improve readability of search results

	private static LastResultFormats lastResultType = null; // Always good to initialize

	public static void main(String[] args) {
		startConsole();
		try {
			populateWithImages(); // By default, a range of ImageRecord objects are created within the system on startup.
		} catch (Exception e) {
			// The line below prints the error message of any ImageRecord object that may fail construction
			printlnBold("Error unable to Populate table\n" + e.getMessage());
			promptThenClear(); // Ensures the user acknowledges the error.
			
		}
		while (start) {
			con.clear();
			if (choiceOutOfBounds) { // The default value inside Menu based switch statements
				// The line below alerts us if getMenuChoice range validation feature has been bypassed
				printlnBold("Error Choice out of bounds. Implement range validation logic");
				choiceOutOfBounds = false; // Resets the boolean back to false so that it doesn't appear again
				promptThenClear(); // Ensures the user acknowledges the error.
			}
			println("");
			printlnBold("Media Image Collection Manager");
			menuNavigator(getMenuChoice(MENUS[menuIterator], false)); // Sends the choice in integer format
		}
		con.setVisible(start);
	}
/**
 * Displays the console and applies the initial console design which is the same design as Good Old Paper in themes
 */
	private static void startConsole() {
        con.setVisible(start); // Presents the application to the user
        //The below size has been tested on linux and further optimised for java on windows
        con.setSize(700,600); //Maintain this ratio when adjusting size for best UI experience
		con.setFont(new Font(fontName, fontStyle, fontSize)); // Sets initial font
		con.setBgColour(BgColour); // Sets the background colour to the RGB values
		con.setColour(textColour);
		
	}

	/**
	 * Populates the table with pre-defined image records to showcase application
	 * features.
	 * 
	 * @throws Exception - thrown when a ImageRecord has invalid construction
	 *                   parameters such as null or empty string
	 */
	private static void populateWithImages() throws Exception {
		manageImages.addImage(new ImageRecord("The Moon", "Image of the Moon.", ImageType.ASTRONOMY,
				"moon.jpg", LocalDate.of(2023, 1, 1)));
		manageImages.addImage(new ImageRecord("Oslo Opera House", "An image of the Opera House in Oslo.",
				ImageType.ARCHITECTURE, "OsloOperaHouse.jpg", LocalDate.of(2023, 2, 1)));
		manageImages.addImage(new ImageRecord("Norwegian Landscape", "A scenic view captured from Færder National Park.",
				ImageType.LANDSCAPE, "NorwegianLandscape.jpg", LocalDate.of(2023, 4, 1)));
		manageImages.addImage(new ImageRecord("Child of Great Potential", "You should really hire him when he grows up.",
				ImageType.PORTRAIT, "ChildOfPotential.jpg", LocalDate.of(2023, 3, 1)));
		manageImages.addImage(new ImageRecord("Llama", "An aggresive Llama who spat at me.", ImageType.NATURE,
				"llama.png", LocalDate.of(2023, 4, 1)));
		manageImages.addImage(new ImageRecord("Sandefjord", "An overhead view of Sandefjord Norway.",
				ImageType.AERIAL, "Sandefjord.png", LocalDate.of(2023, 5, 1)));
		manageImages.addImage(new ImageRecord("Strawberries", "A bunch of Strawberries.", ImageType.FOOD, "Strawberries.jpg",
				LocalDate.of(2023, 6, 1)));
		manageImages.addImage(new ImageRecord("My favourite hunter", "Kite from Hunter X.", ImageType.OTHER,
				"Kite.jpeg", LocalDate.of(2023, 7, 1)));
	}

	/*
	 * Input/Output methods for easy console migration - Makes code reusable with
	 * many interfaces like scanner or console by simply changing the lines below
	 */
	/**
	 * Prints an object on a new line to an output interface like Eclipse console
	 * 
	 * @param obj - the object that you want to print
	 */
	private static void println(Object obj) {
		con.println(obj);
	}

	/**
	 * Prints an object to an output interface like Eclipse console
	 * 
	 * @param obj - the object that you want to print
	 */
	private static void print(Object obj) {
		con.print(obj);
	}

	private static void printlnBold(Object obj) {
		con.setFont(new Font(fontName, Font.BOLD, fontSize));
		println(obj);
		con.setFont(new Font(fontName, fontStyle, fontSize));
	}

	private static void printlnItalic(Object obj) {
		con.setFont(new Font(fontName, Font.ITALIC, fontSize));
		println(obj);
		con.setFont(new Font(fontName, fontStyle, fontSize));
	}

	/**
	 * Gets user input from the interface like Eclipse console
	 * 
	 * @return - The user's input as a String
	 */
	private static String userInput() {
		return con.readLn();
	}

	/**
	 * The method below determines which menu the user was presented with by running
	 * a switch case on the menuIterator variable's value so that it could then go
	 * to the relevant switch case block for that menu and proceed to switch on the
	 * users input.
	 * 
	 * This combination ensures that the appropriate function is performed for the
	 * current presented menu
	 * 
	 * @param userChoice - the users choice Input from the current iterated menu
	 */
	private static void menuNavigator(int userChoice) {
		switch (menuIterator) {
		case 0: // Main Menu
			switch (userChoice) {
			case 1: // Display All Images Option
				con.clear();
				displayAllImages();
				break;
			case 2: // Search Option
				menuIterator = 1;
				break;
			case 3: // Add Image Option
				con.clear();
				String error = addImage();
				if(!error.isEmpty()) {
					println(error);
					promptThenClear(); // Ensures the user acknowledges the error.
				}
				break;
			case 4: // Menu Settings Option
				menuIterator = 2;
				break;
			case 5: // Display Last Image Result
				con.clear();
				displayLastResult();
				break;
			case 6: // Exit Application Option
				start = false;
				break;
			default:
				// instead of outputting error out of bounds every time this is more organized
				// and easier to implement with console later on.
				choiceOutOfBounds = true; // when the while statement loops again this will give an out of bounds
											// reminder
			}
			break;
		case 1: // Search Menu
			switch (userChoice) {
			case 1: // Search by ID
				searchByID();
				break;
			case 2: // Search by Title
				searchByTitle();
				break;
			case 3: // Search by Description
				searchByDescription();
				break;
			case 4: // Search by Genre
				searchByGenre();
				break;
			case 5: // Search by Date Range
				searchByDateRange();
				break;
			case 6: // Return to Main Menu Option
				menuIterator = 0;
				break;
			default:
				// instead of outputting error out of bounds every time this is more organized
				// and easier to implement with console later on.
				choiceOutOfBounds = true;
				// when the while statement loops again the line above will give an out of
				// bounds reminder.

			}
			break;
		case 2: // Settings Menu
			switch (userChoice) {
			case 1: // Change text size option
				setTextSize();
				break;
			case 2: // Change text colour option (if not background colour)
				setTextColour();
				break;
			case 3: // Change text Font (if monospaced font)
				setTextFont();
				break;
			case 4: // Change background colour option if not text colour
				setBgColour();
				break;
			case 5: // Select pre-defined themes option (Good old sandpaper, Gloomy Gray, Paper
					// White, Dark Mode
				setTheme();
				break;
			case 6:
				menuIterator = 0;
				break;
			default:
				// instead of outputting error out of bounds every time this is more organized
				// and easier to implement with console later on.
				choiceOutOfBounds = true;
				// when the while statement loops again the line above will give an out of
				// bounds reminder.
			}
			break;
		default:
			printlnBold("Error Menu Not Found");
			menuIterator = 0;
		}
	}
/**
 * The method below enables users to select a text size or provide a custom one.
 * It does this by creating a Menu object with the sizes small, medium, large and custom.
 * It then presents this menu to the user through the getMenuChoice method and gets their choice so that
 * it could perform a switch case on it and apply the selected text size. If the user
 * has selected small, medium or large then their choices will be applied without any further question but
 * if they choose the custom option then they will be asked for a text size through the askTextSize method.
 * The askTextSize method then shows them a preview of it and requests their confirmation before returning
 * the text size provided by the user so that we could apply it.
 * 
 * 
 */
	private static void setTextSize() {
		con.clear();
		Menu textSizeMenu = new Menu("Please Choose a text size", new String[] {"Small","Medium","Large","Custom"});
		switch(getMenuChoice(textSizeMenu,false)) {
		case 1: //Small Size option
			fontSize = 12;
			con.setFont(new Font(fontName, fontStyle, fontSize));
			break;
		case 2: //Medium Size option
			fontSize = 15;
			con.setFont(new Font(fontName, fontStyle, fontSize));
			break;
		case 3: //Large size option
			fontSize = 18;
			con.setFont(new Font(fontName, fontStyle, fontSize));
			break;
		case 4: //Custom Size option
			fontSize = askTextSize();
			con.setFont(new Font(fontName, fontStyle, fontSize));
			break;
		default:
			printlnBold("Error. textSizeMenu has unimplemented choice");
			promptThenClear(); // Ensures the user acknowledges the error.
			
			
			
		}

	}
/**
 * This method asks the user for a text size through the askNumber method which ensures that the provided size
 * is a non-negative whole number. Once we have obtained the users number through the askNumber method we 
 * will perform further validation to ensure the chosen size doesn't break the system as that wouldn't be
 * so robust. Our further validation logic checks whether the provided size is less then 9 or above 25 
 * as numbers that are can lead to our application being presented in a broken format. If the number is not 
 * below 9 or greater then 25 we return it as the text size.
 * @return - An integer value of a safe text size between 9 and 25
 */
	private static int askTextSize() {
		int chosenSize = 0; //Initializes a variable to store the provided size
		while(chosenSize<9 || chosenSize >25) { //While our provided size is out of range
			chosenSize = askNumber("Please enter the text size you would like.");
			if(chosenSize<9 || chosenSize >25) {//If it's still out of range then prompt an error
				printlnBold("Error Text Size cannot be less then 9 or bigger then 25");
				promptThenClear(); // Ensures the user acknowledges the error.
			} else { //If it's not out of range then call confirmStyling to showcase text Size
				switch(confirmStyling(chosenSize,changedStyleElement.SIZE)){
					case StyleConfirmationChoices.ACCEPT: 
						//If the user is happy if the chosen size then we go through with it
						break;
					case StyleConfirmationChoices.REJECT:
						//If the user doesn't want their chosen size we clear it to 0 so we can loop again
						chosenSize = 0;
						break;
					case StyleConfirmationChoices.EXIT:
						//If the user changed their mind and doesn't want to modify text size they can exit
						//The line below exits the method and returns the current font size to prevent changes
						return fontSize;
				}
			}
		}
		return chosenSize;
	}
/**
 * The method below asks the user for a text colour by creating a Menu object then presenting it through
 * getMenuChoice which also gets us the users choice number that we will use as an iterator to get the chosen
 * colour object so that we can present a preview of their chosen text colour through confirmStyling which
 * also gets their choice on whether they would like to apply their chosen text colour; reject their chosen text
 * colour and choose another one or exit without having to change the colour. Then the relevant action is perfomed
 * based on the choice and the method eventually returns if the user has chosen to accept their chosen text colour
 * or exit.
 */
	private static void setTextColour() {
		Menu textColourMenu = new Menu("Please choose a text colour",colourNames);
		boolean confirmed = false; //for colour choice confirmation
		while(!confirmed) { //while its unconfirmed
		/*The line below gets the users colour choice offset by -1 since menu choice start from 1 instead of 0
		 * then uses it to iterate through the available colours stored in the colours array.
		 * The colours array and the colourNames array are the same length and have details for the same colour
		 * in every position so we can be sure that the users choice subtracted by 1 based on colourNames
		 *  corressponds to the position where their chosen colour is stored in colours array. 
		 */
		Color chosenColour = colours[getMenuChoice(textColourMenu,false)-1];
		if(!chosenColour.equals(BgColour)) {//If the users chosen text colour isn't the background colour
		/**The line below confirms the users chosen text colour through confirmStyling which also previews their 
		 * chosen style and returns their decision on regarding it. We will 
		 * then perform a switch case statement on the returned StyleConfirmationChoice constant.
		 */
		switch(confirmStyling(chosenColour,changedStyleElement.COLOUR)) {
		case StyleConfirmationChoices.ACCEPT:
			textColour = chosenColour; //store it as the default text colour
			con.setColour(textColour); //apply the chosen text colour
			confirmed = true; //break the while loop since we've confirmed user choice
			break;
		case StyleConfirmationChoices.REJECT:
			//A break is enough to restart the while loop and prompt the user again for a text colour
			break;
		case StyleConfirmationChoices.EXIT:
			//If the user wants to exit we will break the condition of the while loop with the line below
			confirmed = true; //setting it to true breaks the condition !confirmed which returns us from the method
		}} else { //If it is the same as the background colour we'll reject it due to readibility concerns
			println("");
			printlnBold("Error Text Colour can't be the same as background colour");
			promptThenClear(); // Ensures the user acknowledges the error.
		}
		}
		

	}
/**
 * This method showcases the text style chosen by the user whether its a new text font,text size or text colour.
 * It shows the users selected text style and the current system style before prompting the user with a menu and getting
 * their choice on whether they want to either accept their chosen text style; reject their chosen text style or exit out
 * of choosing a text style. Once the user chooses one of these options a constant from the enumerator 
 * StyleConfirmationChoices representing the chosen option is returned so that the calling method could take
 * appropriate action based on the users choice for example if this method returns StyleConfirmationChoices.EXIT
 * then the caller method could reject the chosen text style and stop asking for a text style and If we return
 * StyleConfirmationChoices.REJECT then the caller method could acknowledge the fact that the change is unwanted
 * and prompt the user again to pick a different text style whilst StyleConfirmationChoices.ACCEPT would let
 * the caller method know it can proceed with the text style change as intended.
 * @param givenElement - an Object value for the text Style element you want to showcase 
 * e.g a Color object such as Color.RED for chosen text colour or 
 * a String object such as "Monospaced" for a chosen font.
 * @param elementType - an Object value for the element type of the given element 
 * for example changedStyleElement.COLOUR
 * @return - a constant from the StyleConfirmationChoices enumerator representing the users decision on the style
 */
	private static StyleConfirmationChoices confirmStyling(Object givenElement,changedStyleElement elementType) {
		String changedElement = "";
		String givenFont = fontName;
		Color givenColour = textColour;
		int givenSize = fontSize;
		if(elementType == changedStyleElement.FONT) {
			changedElement = "Font";
			givenFont = (String) givenElement;
			
		} else if(elementType == changedStyleElement.SIZE) {
			changedElement = "Size";
			givenSize = (int) givenElement;
		} else if(elementType == changedStyleElement.COLOUR) {
			changedElement = "Text Colour";
			givenColour = (Color) givenElement;
		}
		while (true) {
			println("");
			println("Chosen "+changedElement+":");
			con.setFont(new Font(givenFont, fontStyle, givenSize));
			con.setColour(givenColour);
			con.println("This is an example message of how your chosen "+changedElement+" looks like.");
			con.setFont(new Font(fontName, fontStyle, fontSize));
			con.setColour(textColour);
			println("");
			println("Previous "+changedElement+":");
			println("This is an example message of how your "+changedElement+" currently looks like");
			Menu fontConfirmation = new Menu("Would you like to apply your chosen "+changedElement+"?",
					new String[] { "Yes, apply my chosen "+changedElement, "No, choose another "+changedElement, "Return to Settings Menu" });
			switch (getMenuChoice(fontConfirmation, true)) {
			case -1:
				promptThenClear(); // Ensures the user acknowledges the error.
				break;
			case 1://Case 1 is the apply my chosen font option
				return StyleConfirmationChoices.ACCEPT;
			case 2: // Choose another font option
				return StyleConfirmationChoices.REJECT;
			case 3:
				return StyleConfirmationChoices.EXIT;
			default:
				printlnBold("Error. method confirmStyling has unimplemented choice");
				promptThenClear(); // Ensures the user acknowledges the error.
			}
			          }
		
	}
	/**
	 * This method gets all the available monospaced fonts that are compatible with our menu design and stores 
	 * them in the String array called fontsArray so that menu objects could present them and so that we can also
	 * iterate through to our chosen choice using the menu choice number minus one since it's offset by +1.
	 * 
	 * This method was created instead of us setting predefined font names as not all fonts are available across 
	 * different systems so we have to present only the available fonts that are compatible with our application 
	 * to the user,
	 */
	private static void getAvailableFonts() {
		if(fontsArray == null) { //If system fonts have not been fetched before
		printlnItalic("Getting Compatible Fonts..."); //Tell the user why they're waiting
		ArrayList<String> fonts = new ArrayList<>(); //Temporary storage for the fetched fonts
		//The line below gets a font from the system's available fonts and stores them as a string in fonts
		for (String font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
			if (font.toLowerCase().contains("mono") && !font.toLowerCase().contains("monotype")) {
				fonts.add(font);
			}
		}
		fonts.add("Return to main menu");
		//The lines below convert the String ArrayList to a String Array
		fontsArray = new String[fonts.size()];
		for (int i = 0; i < fontsArray.length; i++) {
			fontsArray[i] = fonts.get(i);
		}}
		//Converting it to a regular String Array enables us to pass it to Menu objects as options
		//The line below keeps the screen clear once  the operation is over
		con.clear();
	}
/**
 * This method asks the user for a font through the askTextFont method until the 
 * user provides a non empty font that we will apply or the sentinel value "EXIT" where we would then return
 * the user without changing the font or prompting them for a font again.
 * 
 */
	private static void setTextFont() { // Be careful default fallback font breaks styling
		getAvailableFonts();
		String requestMessage = "Please select a font";
		String chosenFont = askTextFont(fontsArray, requestMessage);
		while(chosenFont.isEmpty()) {
			chosenFont = askTextFont(fontsArray, requestMessage);
		}
		if(!chosenFont.equalsIgnoreCase("EXIT")) {
			fontName = chosenFont; // Sets chosen font as the current
			con.setFont(new Font(fontName, fontStyle, fontSize)); //Applies the chosen font
		} else {
			return;
		}
	}
	
/**
 * This method presents a provided font array to the user and gets them to select a font.
 * It then presents a preview of the font through the confirmStyling method before checking if
 * the confirmStyling method has returned the users choice on whether they want to proceed with their
 * chosen font, reject their chosen font and  choose another font or exit back to the main menu. 
 * These choice options are determined by the StyleConfirmationChoices enumerator.
 * 
 * @param fontsArray - an Array of font names that you want to choose from.
 * @param requestMessage - the custom request message you want to display to the user. i.e "Please select a font"
 * @return - A string value of the chosen font name that is obtained from the fontsArray through the users selection
 */
	private static String askTextFont(String[] fontsArray, String requestMessage) {
		Menu fontMenu = new Menu(requestMessage, fontsArray);
		int fontChoiceIterator = getMenuChoice(fontMenu, false) - 1;
		if(fontChoiceIterator == fontsArray.length-1) {
			return "EXIT"; //Sentinel case for exiting text font selection menu
		}
		String chosenFont = fontsArray[fontChoiceIterator];
		switch(confirmStyling(chosenFont,changedStyleElement.FONT)) {
		case StyleConfirmationChoices.ACCEPT:
			break;
		case StyleConfirmationChoices.REJECT:
			chosenFont = ""; //Store an empty string as chose font to show no font has been chosen
			break;
		case StyleConfirmationChoices.EXIT:
			return "EXIT";
		default:
			printlnBold("Error StyleConfirmationChoice has an additional unimplemented constant");
			promptThenClear(); // Ensures the user acknowledges the error.
		}
		return chosenFont;
		
	}
/**
 * This method is responsible for requesting a colour from the user by creating a Menu object called bgColourMenu
 * and then passing it to the getMenuChoice method to attain the users choice which is then subtracted by one since
 * menu choices start at 1 and java iteration starts at 0. After we have subtracted 1 from the users choice we use it
 * to iterate through another array which stores the Color objects in the same position as colourNames stores the
 * string name for them. We then assign this Colour object to the ChosenColour variable so that we can them perform
 * our check. The check ensures that our chosen Background colour stored in chosenColour is not the same as the 
 * text colour as this could impair readability where users would be unable to see text. We then apply
 * the background colour if it's not the same as the text colour and present an error message if it is.
 */
	private static void setBgColour() {
		Menu bgColourMenu = new Menu("Please choose a Background colour",colourNames);
		boolean confirmed = false; //confirmed to be valid variable
		while(!confirmed) { //while validity is unconfirmed
		Color chosenColour = colours[getMenuChoice(bgColourMenu,false)-1]; //use users choice to iterate colours
		if(!chosenColour.equals(textColour)) { //If background colour isn't same as text colour
			BgColour = chosenColour; //Store it as the current background colour
			con.setBgColour(BgColour); //set the new background colour
			confirmed = true; //our if statement confirmed validity so its true
		} else {
			println("");
			printlnBold("Error Text Colour can't be the same as background colour");
			promptThenClear(); // Ensures the user acknowledges the error.
		}
		}

	}
/**
 * This method allows the user to select from pre-defined themes for convenience rather then having to manually
 * adjust text color and background to find a good match. A further project expansion idea would be to enable
 * importing and exporting themes but the time limit for this assessment won't allow it.
 * 
 * The method presents creates a Menu containing all the pre-defined themes they can choose from.
 * This menu is presented to the user through getMenuChoice() which also gets the users choice number from
 * the menu option numbers. These options numbers relate to the order which we presented the options to the menu
 * for example if the first option we provide to the menu is "Good Old Sandpaper" then it's menu option is 1.
 * 
 * Since we know our menu options are numbered by the order we provided them we then perform a switch case statement
 * to perform the relevant operation for each theme which is currently setting the background colour and 
 * text colour for the given theme. 
 */
	private static void setTheme() {
		con.clear();
		Menu themeMenu = new Menu("Theme selection menu",
				new String[] { "Good Old Sandpaper","Lucky Green", "Gloomy Gray", "Light Mode", "Dark Mode",
						"Cyber Cyan","Return to Settings Menu"});
		switch(getMenuChoice(themeMenu,false)) {
		case 1: //Good Old Sandpaper theme
			BgColour = new Color(255, 238, 176); // Sets the background colour to the RGB values
			textColour = Color.BLACK;
			break;
		case 2:
			BgColour = new Color(193,232,153); //Light green
			textColour = new Color(92,67,3); //Dark Brown
			break;
		case 3: //Gloomy Gray theme
			BgColour = Color.LIGHT_GRAY;
			textColour = Color.BLACK;
			break;
		case 4: //Light mode theme
			BgColour = Color.WHITE;
			textColour = Color.BLACK;
			break;
		case 5: //Dark mode theme
			BgColour = Color.BLACK;
			textColour = Color.WHITE;
			break;
		case 6: //Cyber Cyan theme
			BgColour = Color.BLACK;
			textColour = Color.CYAN;
			break;
		case 7: //Return to settings menu option
			break;
		default:
			printlnBold("Error theme menu includes unimplemented themes");
			promptThenClear(); // Ensures the user acknowledges the error.
		}
		con.setBgColour(BgColour);
		con.setColour(textColour);

	}

	/**
	 * The method below gets all the images and gives them to the storeLastResult
	 * method to store along with a String detailing the query and null for the
	 * third field section as we only need the ID and title presented
	 */
	private static void displayAllImages() {
		storeLastResult(manageImages.getAllImages(), "Display All Images", null);
		displayLastResult(); // This line displays all the images after we have stored them
	}

	/**
	 * The method below displays all images from the last result which could be the
	 * result from a previous search or function such as displaying all images. The
	 * query is presented so that users could know what the last result was for and
	 * then the image results are presented for the users to select through a Menu
	 * object and navigate between through the imageNavigator method. It also has
	 * measures to handle instances where there are no previous results for example
	 * if no search function or function such as displayAllImages have been called
	 * and if the function had no Image results. This method does not return until
	 * the user selects the exit option presented through the menu Object so that
	 * they can return once they have browsed through all the images they need to
	 * browse through.
	 */
	private static void displayLastResult() {
		if (lastResult != null) {
			println(""); // White space allows clarity and improves readability as it allows users to
							// notice new prompts
			println(lastResultQuery); // tells the user what the image result query is whether is was a specific
										// search or display all
			println("Result:");
			ArrayList<String> strResults = strFormatImages(lastResult);
			String[] arrayResults = new String[strResults.size() + 1]; // add one more option for returning to menu
			for (int i = 0; i < strResults.size(); i++) {
				arrayResults[i] = strResults.get(i);
			}
			arrayResults[strResults.size()] = "Return to main menu"; // labels that additional option for returning to
																		// menu
			if (strResults.size() == 0) {
				printlnBold("There are no Images for the given Query");
				promptThenClear(); // Ensures the user acknowledges the error.
			} else {
				// Make a menu with strResults have the user select a result
				if (strResults.size() > 1) {
					int userChoice = -1;
					while (userChoice - 1 != strResults.size()) {
						Menu results = new Menu("Please select a result", arrayResults);
						userChoice = getMenuChoice(results, false);
						if (userChoice - 1 != strResults.size()) {
							con.clear();
							imageNavigator(lastResult, userChoice);
						} else {
							printlnItalic("Exiting to main menu...");
						}
						con.clear();
					}
				} else { // if there's just one choice to choose then it chooses it
					con.clear();
					imageNavigator(lastResult, 1);
				}
				// Iterate through the lastResult ImageAlbum for user choice by nexting it
				// without hitting null
				// Display that iterated ImageRecord details and give next previous options
			}
		} else {
			printlnBold("There is no previous Image result.");
			promptThenClear(); // Ensures the user acknowledges the error.
		}
	}

	/**
	 * This method takes an ImageAlbum object and the iteration number provided by
	 * the user to navigate to the selected Image using a for loop on the getNext
	 * method so that it iterates to the next Image until it reaches the iteration
	 * number provided by the user and then it returns the details of that selected
	 * Image whilst presenting users with the option to navigate to the previous
	 * image if there is a previous image and a next image if there is a next image.
	 * It also presents them an option to return back to menu so that they can do so
	 * once they have read the image details.
	 * 
	 * @param album      - The ImageAlbum that has images that you want the user to
	 *                   browse through individually
	 * @param userChoice - The users initial choice which is used to iterate through
	 *                   the images and present the chosen image
	 */
	private static void imageNavigator(ImageAlbum album, int userChoice) {
		ImageRecord image = album.getFirst();
		if (userChoice != (album.getSize() + 1)) {
			ImageRecord result = album.getFirst();
			if (userChoice > 1) {
				for (int i = 1; i < userChoice; i++) { // starts from 1 because we already checked first result
					result = album.getNext();
					if (result == null) {
						printlnBold("System Iteration error. iterated to a null value");
						promptThenClear(); // Ensures the user acknowledges the error.
						break;
					}
				}
				image = result; // Once we've iterated up to the result then store it as the current image
			} // If its not a valid choice greater then one then it has to be the first choice
				// There's no need for an else statement since we've already intialised it with
				// first choice
		}
		while (true) {
			if (userChoice == (album.getSize() + 1)) {
				printlnItalic("Exitting Image Navigator");
				return;
			} else {
				print("");
				ImageIcon imagePreview = new ImageIcon(filePathway + image.getThumbnail());
				if (imagePreview.getIconHeight() == -1) {
					println(String.format("Preview Unavailable please add image to %s directory", filePathway));
				} else {
					double scale = 0;
					int width = con.getWidth() - 25; //-20 allows padding on the sides of the image
					int height = con.getHeight() - fontSize*23; //We need to let 20 lines of text on the screen
					if (((double) width / imagePreview.getIconWidth()) < ((double) height
							/ imagePreview.getIconHeight())) {
						scale = (double) width / imagePreview.getIconWidth();
					} else {
						scale = (double) height / imagePreview.getIconHeight();
					}

					// Width x height height/width = smth
					println(new ImageIcon(
							imagePreview.getImage().getScaledInstance((int) (imagePreview.getIconWidth() * scale),
									(int) (imagePreview.getIconHeight() * scale), Image.SCALE_SMOOTH)));
				}
				if (lastResultType == LastResultFormats.GENRE) {
					println("ID: " + image.getId() + " Title: " + image.getTitlePreview() + " Genre: "
							+ image.getType().name());
				} else if (lastResultType == LastResultFormats.DATERANGE) {
					println("ID: " + image.getId() + " Title: " + image.getTitlePreview() + " Date Taken: "
							+ image.getDateTaken());
				} else if (lastResultType == LastResultFormats.DESCRIPTION) {
					println("ID: " + image.getId() + " Title: " + image.getTitlePreview());
					println(" Description: " + image.getDescriptionPreview());
				} else {
					println("ID: " + image.getId() + " Title: " + image.getTitlePreview());
				}
			}
			println(userChoice - 1 + " Previous Images | Next Images " + (album.getSize() - userChoice));
			// Menu option restrictions
			boolean hasPrevious = true;
			boolean hasNext = true;
			if (userChoice - 1 == 0) {
				hasPrevious = false;
			}
			if ((album.getSize() - userChoice) == 0) {
				hasNext = false;
			}
			String[] options = { "Return to Menu", "Display Image Details", "Previous Image", "Next Image", };
			if (!(hasPrevious && hasNext)) { // If there's not a next or previous option then restrict menu options
				if (!hasPrevious && !hasNext) { // If there is no next and previous image to navigate to
					options = new String[] { "Return to Menu", "Display Image Details" }; // Presents he user with
																							// only a menu option
				} else if (!hasNext) { // else if there's just no next option
					options = new String[] { "Return to Menu", "Display Image Details", "Previous Image" }; // present
																											// the
																											// menu
																											// without
																											// next
					// option
				} else {
					/*
					 * If either the next or previous option is missing and we've confirmed its not
					 * the next option Then It has to be the previous option that's missing
					 */
					options = new String[] { "Return to Menu", "Display Image Details", "Next Image" };
					; // Gets rid of the previous option
				}
			}
			Menu navigate = new Menu("Please select a navigation option", options);
			/*
			 * The we can be certain that the initial value 2 wont lead to a case being
			 * because getMenuChoice forces the user to provide a valid non-empty choice
			 * that would then be assigned to menuChoice variable
			 */

			switch (getMenuChoice(navigate, true)) {
			case -1:
				promptThenClear(); // Ensures the user acknowledges the error.
				break;
			case 1:
				userChoice = (album.getSize() + 1);
				break;
			case 2:
				println(""); // White space allows clarity and improves readability as it allows users to
				printlnBold("Image Details");
				println("");
				println(String.format(
						"ID:%d%nTitle: %s%nDescription: %s%nType: %s%nAbout Type: %s%nThumbnail: %s%nDate Taken: %s",
						image.getId(), image.getTitle(), image.getDescription(), image.getType().name(),
						image.getType(), image.getThumbnail(), image.getDateTaken()));
				promptThenClear(); // Ensures the user acknowledges the error.
				break;
			case 3:
				if (hasPrevious) { // If there is a previous option then perform its function and go to it
					ImageRecord previousResult = album.getPrevious();
					if (previousResult != null) {
						image = previousResult;
						userChoice--;
						con.clear();
					} else {
						printlnBold("There are no previous results");
						promptThenClear(); // Ensures the user acknowledges the error.
					}
					break;
				} // If it doesn't have previous then fall through to the next case as option 2
					// would be next not previous
			case 4:
				// Presents the user with the next image result
				ImageRecord nextResult = album.getNext();
				if (nextResult != null) {
					image = nextResult;
					userChoice++;
					con.clear();
				} else {
					printlnBold("There are no next results");
					promptThenClear(); // Ensures the user acknowledges the error.
				}
				break;
			default:
				printlnBold("System Error. getMenuChoice method failed range validation.");
				promptThenClear(); // Ensures the user acknowledges the error.
			}
		}
	}

	/**
	 * Stores the last result so that users can view it again Single ImageRecord
	 * results have to be converted to a single image ImageAlbum so that the same
	 * logic can be used for single results regardless of whether many results were
	 * expected and one was returned or one result was expected and one result was
	 * returned
	 * 
	 * @param result - The ImageAlbum result from that query.
	 * @param query  - The request query for example display all images or search
	 *               for Title: Big Rock
	 * @param type   - If there was any fields searched in particular then set this
	 *               to display that field
	 */
	private static void storeLastResult(ImageAlbum result, String query, LastResultFormats type) {
		lastResult = result;
		lastResultQuery = "Query: " + query;
		lastResultType = type;
	}

	/**
	 * Provides a String that is well formatted list for each ImageRecord object
	 * stored in the ImageAlbum that's passed to it through the method's parameters.
	 * The string includes the ID and title of each ImageRecord object and a third
	 * optional field that is passed down to it from the methods parameters. This
	 * third field is used by our search functions for instance the
	 * searchByDescription method to display description alongside the ID and Title
	 * so that users can find what they are looking for more easily. Not all search
	 * functions set the third parameter to reference a LastResultFormats enumerator
	 * constant as some functions have it set to null since they don't require a
	 * third additional field for instance searchByID and searchByTitle both use
	 * this method but have the third parameter as null since the default list
	 * containing ID and Title is enough for users to select the correct ImageRecord
	 * object.
	 * 
	 * @param images - The ImageAlbum object with ImageRecords you want to list in a
	 *               String
	 * @return - A list in String format that lists all the ImageRecord objects in
	 *         the provided ImageAlbum
	 */
	private static ArrayList<String> strFormatImages(ImageAlbum images) {
		ArrayList<String> response = new ArrayList<>();
		/*
		 * the third Field will be stored separately so we can align them by measuring
		 * the longest String in response then by adding space at the end of every other
		 * String to make it of equal length so that we can then add the third field
		 */
		ArrayList<String> thirdField = new ArrayList<>();
		ArrayList<String> formattedResponse = new ArrayList<>(); // The final response with the thirdField
		ImageRecord image = images.getFirst();
		if (image == null) {
			// response += "There are no Images for the given Query";
			return response;
		}
		if (lastResultType == null) { // Having the if statement outside the loop
			while (image != null) {
				response.add("ID: " + image.getId() + " TITLE: " + image.getTitlePreview());
				image = images.getNext();
			}
		} else if (lastResultType == LastResultFormats.DESCRIPTION) {
			while (image != null) {
				response.add("ID: " + image.getId() + " TITLE: " + image.getTitlePreview());
				thirdField.add(" DESCRIPTION: " + image.getDescriptionPreview());
				image = images.getNext();
			}
		} else if (lastResultType == LastResultFormats.GENRE) {
			while (image != null) {
				response.add("ID: " + image.getId() + " TITLE: " + image.getTitlePreview());
				thirdField.add(" GENRE: " + image.getType());
				image = images.getNext();
			}
		} else if (lastResultType == LastResultFormats.DATERANGE) {
			while (image != null) {
				response.add("ID: " + image.getId() + " TITLE: " + image.getTitlePreview());
				thirdField.add(" DATE TAKEN: " + image.getDateTaken());
				image = images.getNext();
			}
		}
		if (thirdField.size() > 0) {
			// third Field alignment Phase
			int longestLength = response.get(0).length();
			// Determine the longest line in the response that contains id and title
			for (String line : response) {
				if (line.length() > longestLength) {
					longestLength = line.length();
				}
			}
			// Add spaces to the lines that are shorter then the longest line to make them
			// same length
			for (int i = 0; i < response.size(); i++) {
				/*
				 * Subtracting the length of each text from the longest text length will at most
				 * lead to 0 when it ends up being the longest text length subtracted against
				 * itself. Knowing this fact and that "String".repeat(0) will not add the String
				 * we can be certain that .repeat(neededLength) won't lead to extra spaces being
				 * added to the longest length.
				 */
				int neededLength = longestLength - response.get(i).length();
				formattedResponse.add(response.get(i) + " ".repeat(neededLength) + thirdField.get(i));
			}
			return formattedResponse;
		} else {
			return response; // If there is no third field then there's no need to format the response
		}
	}

	/**
	 * This method searches for images containing the id provided by the user. The
	 * Id is obtained through the askID method that has validation logic inside of
	 * it to make sure a valid ID is provided. Once an ID has been provided is it
	 * then used with the searchId method of manageImages so that It can return an
	 * ImageAlbum with ImageRecord object for the image that has the matching ID.
	 * this ImageAlbum is then passed on to the storeLastResult method alongside the
	 * search query and null to signify that no additional fields apart from title
	 * and ID need to be presented. We then present the results through the
	 * displayLastResult method.
	 */
	private static void searchByID() {
		// returns image record only one
		ImageAlbum albumOfResult = new ImageAlbum();
		int ID = askNumber("Please provide the ID to search images for.");
		albumOfResult.add(manageImages.searchId(ID));
		storeLastResult(albumOfResult, "Search for Images with ID: " + ID, null);
		displayLastResult();
	}

	/**
	 * This method is used to search for images containing the users provided title
	 * as it asks the user for a title through the askTitle method then uses the
	 * provided title to generate a ImageAlbum containing ImageRecord results of the
	 * images that contain the users provided title. This is done by calling the
	 * searchTitle method of manageImages. The ImageAlbum is then passed along with
	 * a query message detailing the search that was performed to the
	 * storeLastResult method so that it could be stored as the last result. The
	 * third parameter we have provided to the storeLastResult method is for the
	 * third field that we want displayed alongside the title and ID which is null
	 * as we don't need anything other then the title and ID shown to the user for
	 * them to decide which result they want to select.
	 */
	private static void searchByTitle() {
		String title = askTitle("Please enter the Title you want to search Images for");
		storeLastResult(manageImages.searchTitle(title), "Search for Images containing Title: " + title, null);
		displayLastResult();
	}

	/**
	 * This method is used to search for images containing the users provided
	 * description as it asks the user for a description through the askDescription
	 * method then uses the provided description to generate a ImageAlbum containing
	 * ImageRecord results of images that contain the users provided description.
	 * This is done by calling the searchDescription method of manageImages. The
	 * ImageAlbum is then passed along with a query message detailing the search
	 * that was performed to the storeLastResult method so that it could be stored
	 * as the last result. The third parameter we have provided to the
	 * storeLastResult method is for the third field that we want displayed
	 * alongside the title and ID which is description in this case as users who
	 * search by description should also be shown the description of the images so
	 * that they can select the right one.
	 */
	private static void searchByDescription() {
		String description = askDescription("Please enter the Description you want to search Images for");
		storeLastResult(manageImages.searchDescription(description),
				"Search for Images containing Description: " + description, LastResultFormats.DESCRIPTION);
		displayLastResult();
	}

	/**
	 * This method is used to search for images by their Genre as it asks the user
	 * for Genre through the askGenre method and then provides that genre to the
	 * searchGenre method of manageImages so that It could return an ImageAlbum
	 * containing ImageRecord object or a ImageRecord objects that have the
	 * specified genre. The returned ImageAlbum is then stored as the last Result
	 * using the storeLastResult method along with a query message stating the type
	 * of search that occurred and the value that was searched so that the user
	 * could have context if they later decide to request the last result again.
	 * After we have stored our search results we display it to the user through
	 * displayLastResult.
	 */
	private static void searchByGenre() {
		ImageType genre = askGenre("Pick a Genre to search");
		storeLastResult(manageImages.searchGenre(genre), "Search for Images with the Genre: " + genre,
				LastResultFormats.GENRE);
		displayLastResult();
	}

	/**
	 * This method is used to search for images between a start and end date. This
	 * method asks for the start date and end date through the askDate method and
	 * then validates whether the start date is before the end date or the same as
	 * the end date and if it's neither then it prints an error message and prompts
	 * then asks for the dates again until the provided date is before the end date
	 * or the same as the end date. Once that has been confirmed, it then used the
	 * start and end date as parameters for calling the searchDates method of
	 * manageImages so that It could store is as the last result through the
	 * storeLastResult method. We have also provided the storeLastResult method with
	 * a formatted string containing the query and dates that we have searched
	 * between so that the user knows the context if he later requests the last
	 * result. The method then calls displayLastResult to print the results from the
	 * date range search.
	 */
	private static void searchByDateRange() {
		boolean valid = false; // This value will help us keep track of whether a valid date range has been
								// provided
		while (!valid) {
			LocalDate startDate = askDate("Please provide the start date to search from");
			LocalDate endDate = askDate("Please provide the end date to search up to");
			if (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
				storeLastResult(manageImages.searchDates(startDate, endDate),
						String.format("Search for Images between %s and %s", startDate, endDate),
						LastResultFormats.DATERANGE);
				displayLastResult();
				valid = true;
			} else {
				printlnBold("Invalid Date Range. Start date cannot be after End Date");
				promptThenClear(); // Ensures the user acknowledges the error.
			}
		}
	}

	/**
	 * Creates an ImageRecord object after asking the user for required parameters
	 * to construct an ImageRecord object using the ask methods to ensure that only
	 * valid input is provided for ImageRecord construction once the ImageRecord
	 * object is constructed it is added to our ImageManager instance called
	 * manageImages using ImageManager.addImage() method which can throw an
	 * exception if we provide it with invalid parameters so I have also surrounded
	 * it in a try catch so that the program doesn't crash if it fails to add the
	 * image record and so that we can also print the error message thrown by the
	 * exception.
	 * 
	 * @return - A String value stating the error message or a message confirming
	 *         that the Image has been added
	 */
	private static String addImage() {
		String response = "";
		String title = askTitle("Please enter the Title you want to set for your Image");
		String description = askDescription("Please enter the Description you want to set for your Image");
		ImageType chosenType = askGenre("Please Select the Genre for your image");
		String thumbnail = askThumbnail("Please enter the Thumbnail you want to set for your Image");
		LocalDate dateTaken = askDate("Please provide the date of when the image was taken");
		try {
			ImageRecord image = new ImageRecord(title, description, chosenType, thumbnail, dateTaken);
			manageImages.addImage(image);
		} catch (Exception e) {
			response += "Error. Unable to construct Image because invalid parameters have been provided \n";
			response += e.getMessage() + "\n";
			return response;
		}
		return "Image Created Successfully";
	}

	/**
	 * The method below asks the user for a title until they provide a valid title
	 * as it protects against empty titles and titles containing only spaces by
	 * running a while loop until the trimmed version of the users input is not
	 * empty. The title provided by the user is also trimmed to prevent accidental
	 * trailing spaces before the title or at the end of the title.
	 * 
	 * @param message - The custom request message that you want displayed.
	 * @return - A string value which is the users provided title once it's
	 *         confirmed to be valid
	 */
	private static String askTitle(String message) {
		String title = ""; // The variable where we will store the users title input
		while (title.isEmpty()) {
			println(""); // White space allows clarity and improves readability as it allows users to
							// notice new prompts
			println(message); // Displays the custom message provided through this methods parameter.
			print("Title: "); // Prints a prompt on the same line where the user would input text to show
								// what's being asked
			title = userInput().trim();// trimmed to prevent trailing spaces before or at the end of the title.
			println(""); // Switches to a new line from the one containing the user's input
			if (!title.isEmpty()) {
				// The line below provides interactive feedback letting the user know their
				// title has been accepted
				printlnItalic("Title Accepted");
			} else {// If it still is empty then the user should know why they're being asked again
					// for a title
				printlnBold("You have not provided a title."); // Lets the user know their mistake.
				promptThenClear(); // Ensures the user acknowledges the error.
			}
		}
		return title;
	}

	/**
	 * The method below asks the user for a description until they provide a valid
	 * description as it protects against empty descriptions and descriptions
	 * containing only spaces by running a while loop until the trimmed version of
	 * the users input is not empty. The description provided by the user is also
	 * trimmed to prevent accidental trailing spaces before the description or at
	 * the end of the description.
	 * 
	 * @param message - The custom request message that you want displayed.
	 * @return - A string value which is the users provided description once it's
	 *         confirmed to be valid
	 */
	private static String askDescription(String message) {
		String description = ""; // The variable where we will store the users description input
		while (description.isEmpty()) {
			println(""); // White space allows clarity and improves readability as it allows users to
							// notice new prompts
			println(message); // Displays the custom message provided through this methods parameter.
			print("Description:"); // Prints a prompt on the same line where the user would input text to show
									// what's being asked
			description = userInput().trim(); // trimmed to prevent trailing spaces before or at the end of the
												// Description.
			println(""); // Switches to a new line from the one containing the user's input
			if (!description.isEmpty()) { // Checks if the description variable is no longer empty
				// The line below provides interactive feedback letting the user know their
				// description has been accepted
				printlnItalic("Description Accepted");
			} else { // If it still is empty then the user should know why they're being asked again
						// for a description
				printlnBold("You have not provided a Description."); // Lets the user know their mistake.
				promptThenClear(); // Ensures the user acknowledges the error.
			}
		}
		return description;
	}

	/**
	 * Asks the user for a thumbnail and ensures a valid thumbnail has been provided
	 * by making sure the result is not empty once is has been trimmed from leading
	 * and trailing spaces. This ensures when a user enters only spaces that it
	 * doesn't count as a non empty input and circumvent our validation procedure.
	 * It also performs other checks such as ensuring that every provided thumbnail
	 * as a non empty extension as the end of it.
	 * 
	 * @param message - The custom request message that the user will be presented
	 *                with
	 * @return - A String value of a valid thumbnail provided by the user
	 */
	private static String askThumbnail(String message) {
		String thumbnail = ""; // The variable where we will store the users thumbnail input
		boolean valid = false;
		while (!valid) {
			println(""); // White space allows clarity and improves readability as it allows users to
							// notice new prompts
			println(message); // Displays the custom message provided through this methods parameter.
			print("Thumbnail: "); // Prints a prompt on the same line where the user would input text to show
									// what's being asked
			thumbnail = userInput(); // I didn't trim this input because it has to be an exact match to the image
										// file name
			println(""); // Switches to a new line from the one containing the user's input
			if (!thumbnail.trim().isEmpty()) {
				// After debugging I realized that I have to do \\. for java to not confuse
				// splitting on periods for a regex wildcard
				String[] splitThumbnail = thumbnail.split("\\.");
				/*
				 * The line below checks if if the thumbnail has an extension by splitting the
				 * provided thumbnail on each period and counting if there is more then one part
				 * which would signify an extension. we also check if the final extension part
				 * is not empty to make sure the user hasnt just placed a dot without providing
				 * the extension for example Andromeda. instead of Andromeda.png
				 */
				if (splitThumbnail.length > 1 && !splitThumbnail[splitThumbnail.length - 1].isEmpty()) {
					ImageIcon image = new ImageIcon(filePathway + thumbnail);
					if (image.getIconHeight() == -1) { // If the image is not found
						printlnBold("Thumbnail does not lead to an Image."); // Lets the user know
						// The line below lets them know of a possible mistake.
						println(String.format("Please make sure your image is added to the %s directory", filePathway));
						println("");
						// The line and menu below ensure that the user has typed the thumbnail
						// correctly
						println("Also make sure you have typed the thumbnail correctly. \n You Wrote:" + thumbnail);
						String title = String.format("Your thumbnail '%s'. Is this correct?", thumbnail); // Menu Title
						Menu confirmMenu = new Menu(title,
								new String[] { "Yes,It's correct", "No,I need to write it again." });
						switch (getMenuChoice(confirmMenu, false)) { // Gets the users menu choice to confirm thumbnail
						case 1: // If the user has confirmed it's correct then we will let them know it's been
								// accepted
							printlnItalic("Thumbnail Accepted");
							valid = true; // We set valid to true so that the thumbnail can be returned
							break;
						case 2: // If the user denied it being correct we continue the while not valid loop.
							break;
						default: // It's impossible to select out of range options but its not impossible to
									// select unimplemented ones
							printlnBold("Error thumbnail confirmation menu has more options then available.");
							promptThenClear(); // Ensures the user acknowledges the error.
						}
						// Menu confirmMenu = new Menu(,false);

					} else {
						printlnItalic("Thumbnail Accepted");
						valid = true; // Lets the while loop know it's time to exit because we've set the thumbnail
										// variable
					}
				} else {
					printlnBold(
							"Please include the extension at the end of your file name \n For example andromedia.png");
					println("Where png is the file type.");
					promptThenClear(); // Ensures the user acknowledges the error.
				}
			} else {
				printlnBold("You have not provided a Thumbnail.");
				promptThenClear(); // Ensures the user acknowledges the error.
				
			}
		}
		return thumbnail;
	}

	/**
	 * Asks the user for a Genre until they provide a valid Genre then it returns
	 * the ImageType of that Genre.
	 * 
	 * This is done by presenting a guide to the user detailing every genre by
	 * showing it's description so that they could make a better informed decision
	 * when we then create a Menu and present them with All the valid genre choices
	 * to pick from. We can be sure that Invalid choices won't be picked as our
	 * getMenuChoice method ensures that a valid menu choice is provided which we
	 * then use to iterate through an Array of ImageType constants called
	 * typesOfImage then selecting the chosen one instead of directly taking a
	 * String input from the user and returning it as a genre. Using the enumerator
	 * and enumarator array of enum constants we can be certain that only a valid
	 * genre will be picked.
	 * 
	 * @param message - Custom request message that you want to display to the user
	 * @return - Returns a ImageType of the chosen Genre
	 */
	private static ImageType askGenre(String message) {
		ImageType[] typesOfImage = ImageType.values();
		String[] strTypesOfImage = new String[ImageType.values().length];
		println(""); // White space allows clarity and improves readability as it allows users to
						// notice new prompts
		/**
		 * Below is a section for displaying each genre name along with it's description
		 * so that users can make a more informed decision when presented with the
		 * choice menu.
		 * 
		 * It also stores each of the genre names inside of strTypesOfImage array so
		 * that they could later be presented as choices in the Menu object that we will
		 * later create.
		 */
		printlnBold("The Genre Guide"); // The Title for the genre guide section
		for (int i = 0; i < typesOfImage.length; i++) {
			strTypesOfImage[i] = typesOfImage[i].name();
			printlnBold(typesOfImage[i].name());
			println(typesOfImage[i].toString() + "\n");
		}
		// End of Genre Guide section that details every genre we have
		/**
		 * The line below creates a Menu object with our custom request message as the
		 * title along with a reminder telling the user to scroll up for the guide.
		 */
		Menu imageTypeMenu = new Menu(message + " [Scroll Up for Guide]", strTypesOfImage);
		// The line below uses the menu choice to iterate through the ImageType values
		// in typesOfImage and return it
		// Our menu choices start at 1 but java iteration starts at index 0 so we
		// subtract 1
		return typesOfImage[getMenuChoice(imageTypeMenu, false) - 1];

	}

	/**
	 * Similar to the depreciated getUserChoice method of Menu; this method gets the
	 * users choice for a given menu that has been passed to it through it's
	 * parameters. It also protects against bad input when taking the users choice
	 * by passing each choice to the validateUserChoice method and only returning
	 * the users choice in Integer format if the validateUserChoice message doesn't
	 * return any error message. A while loop is also in place for it to keep
	 * looping until a valid integer is returned.
	 * 
	 * acceptsSentinel is to be used in cases where there is already a loop in place
	 * that prompts the user again with the menu so that the menu doesn't get
	 * printed again by itself skipping out other page information.
	 * 
	 * @param givenMenu       - The menu that you want to get the user's choice for
	 * @param acceptsSentinel - If set to True it will return -1 instead of
	 *                        re-prompting for a valid input
	 * @return - The integer format of the users choice if it's valid choice.
	 */
	private static int getMenuChoice(Menu givenMenu, boolean acceptsSentinel) {
		while (true) {
			println(givenMenu.display());
			print("Choice: "); // Prints a prompt on the same line where the user would input text to show
								// what's being asked
			String choiceNumber = userInput().trim();
			String errorMessage = givenMenu.validateUserChoice(choiceNumber);
			if (errorMessage.isEmpty()) {
				printlnItalic("Choice Accepted.");
				return Integer.parseInt(choiceNumber);
			} else {
				printlnBold(errorMessage);
				if (acceptsSentinel) {
					return -1;
				}
				promptThenClear(); // Ensures the user acknowledges the error.
			}
		}
	}

	/**
	 * Asks the user for a whole number until they provide a parsable integer 
	 * that's not a negative number below 0 since we only assign ID's starting from 0 
	 * and upwards and Set text font sizes from zero and upwards. Our system doesn't require negative numbers.
	 * This method is not to be used for ID assignment during image creation as user assigned
	 * ID's would break the system when our auto incrementing variable idIncrement
	 * reaches the same number that was manually assigned as this would then lead to
	 * duplicate IDs for different image record objects.
	 * 
	 * The Current use case for this method is to return an non-negative integer provided by the user
	 * so that we can search for ImageRecord objects that match the provided ID or set the text font size.
	 * 
	 * Current validation includes preventing numbers that are negative or not a
	 * whole number along with preventing other characters apart from numbers from
	 * being entered by the user such as letters or special characters.
	 * 
	 * @param message - The custom reason of request message displayed to the user
	 * @return - An Integer value of the non-negative whole Number provided by the user.
	 */
	private static int askNumber(String message) {
		int providedNumber = -1; // we'll store the users number here once validating it and later return this variable
		boolean valid = false; // lets us know whether a valid Number has been provided
		while (!valid) {
			println("");// White space allows clarity and improves readability as it allows users to
						// notice new prompts
			printlnBold(message);// Displays the custom message provided through this methods parameter.
			println("Make sure its a whole number in numeric form that's not negative. These are numbers from 0 and upwards.");
			println("");
			print("Input: "); // Prints a prompt on the same line where the user would input text to show
							// what's being asked
			String strNumber = userInput().trim(); // trimmed user input to prevent accidental spaces
			if (!strNumber.isEmpty()) { // If the Number provided is not empty
				if (isInteger(strNumber)) { // If the Number provided is a parsable integer
					int intNumber = Integer.parseInt(strNumber); // Parse it as an integer and store it in intNumber
					if (intNumber >= 0) { // Valid ID's start from 0 and go upwards so do Font sizes so we make sure its not negative
						providedNumber = intNumber; /// If its a non negative integer we assign it to our providedNumber variable
						valid = true; // Lets the while loop know it's time to exit because we've set providedNumber
						printlnItalic("Value accepted"); // (Interactive feedback) alerts the user that their Number has been accepted
					} else { // If it's a negative number below 0 we print the error message below.
						printlnBold(
								"Invalid Input. Your Number can't be a negative number and must be a value from 0 and upwards.");
						promptThenClear();
					}
				} else { // If user input is not a parsable integer we print the error message below
					printlnBold(
							"Invalid Input. you have not provided an integer value which is a whole number in numeric form \n for example 3 not three");
					promptThenClear();
					
				}
			} else {// If user input is an empty string we print the error message below
				printlnBold("You have not provided an Number.");
				promptThenClear();
			}
		}
		return providedNumber; // Once the boolean variable valid is true then our while loop ends and ID is
							// returned
	}

	/**
	 * Asks the user for a date until they provide a valid date that could be used
	 * to construct a LocalDate object. Current validation includes preventing date
	 * inputs that don't contain exactly 3 forward slashes that separate day, month
	 * and year along with Character
	 * 
	 * @param message - The custom message displayed to the user with the reason of
	 *                date request
	 * @return - A LocalDate object constructed with the user's provided date
	 *         information once it's confirmed to be valid
	 */
	private static LocalDate askDate(String message) {
		con.clear();
		LocalDate date = null; // A LocalDate variable for storing the valid date provided
		boolean valid = false; // A boolean value indicating whether a valid date has been provided
		while (!valid) { // Runs while valid data has not been provided which is indicated by the boolean
							// valid
			println(""); // White space allows clarity and improves readability as it allows users to
							// notice new prompts
			printlnBold(message); // Displays the custom message provided through this methods parameter.
			println("");
			println("use the following format DD/MM/YYYY \n For example 10th Febuary 2020 would be 10/02/2020");
			print("");
			print("Date: "); // Prints a prompt on the same line where the user would input text to show
								// what's being asked
			String providedDate = userInput().trim(); // trimmed user input to prevent accidental spaces
			//The line below has -1 which ensures that empty splits don't count
			if (providedDate.split("/",-1).length == 3) { // Checks if there's exactly 3 fields in date
				// The line below splits the provided date on forward slashes to separate
				// day,month and year then stores it in splitDate Array
				String splitDate[] = providedDate.split("/");
				boolean parsableInt = true; // this value tells us whether splitDate is a parsable integer
				for (String field : splitDate) {// Iterate through the 3 fields of date stored in splitDate
					if (!isInteger(field)) {// If the current iterated field is not a parsable integer
						parsableInt = false; // sets parsableInt to false so that once the for loops over the date wont be used
						println("");
						printlnBold("Unexpected Value: " + field);
					}
				} /*
					 * Should the validation for loop above run without setting our parsableInt to
					 * false then we can be sure that we have been provided with nothing other then
					 * integer values seperated by slashes. So if parsableInt is still in its true
					 * state then the following if statement will run
					 */
				if (parsableInt) {
					/*
					 * The lines below set each part of the splitDate array into a labeled integer
					 * variable so that we can better understand them before creating a LocalDate
					 * object with it
					 */
					int day = Integer.parseInt(splitDate[0]);
					int month = Integer.parseInt(splitDate[1]);
					int year = Integer.parseInt(splitDate[2]);
					if (validDate(day, month, year).isEmpty()) { // Ensures whether our date is logically correct for
																	// example not being in the future.
						date = LocalDate.of(year, month, day); // LocalDate Variable
						valid = true; // Lets the while loop know it's time to exit because we've set the date
										// variable
					} else {
						printlnBold(validDate(day, month, year) + "\n"); 
						promptThenClear();												
					}
				} else {
					println("");
					printlnBold("Please use only Integers for the date fields and make sure their not empty.");
					println("The are whole numbers in numeric form \n For example 20 instead of twenty");
					promptThenClear();
				}
			} else {
				if(!providedDate.isEmpty()) {
				println("");
				printlnBold("Invalid Date Format. Please use forward slashes to seperate day,month and year.");
				println("");
				println("Do not include slashes at the start or end of the date and only between the day, month and year");
				promptThenClear();
				} else {
					printlnBold("Error. You have not Provided a date");
					promptThenClear(); // Ensures the user acknowledges the error.
				}
			}
		}
		return date; // Once valid has been set to true we can safely return date
	}
/**
 * The method below prompts the user to enter any key before clearing the menu and is used to give users the time
 * to acknowledge error messages
 */
	private static void promptThenClear() {
		println("");
		println("Press Enter to continue");
		userInput();
		con.clear();
		
	}

	/**
	 * This method validates dates in a way that aims to provide a more helpful
	 * response message then the LocalDateException message so that users can better
	 * understand their mistakes.
	 * 
	 * @param day   - The Day of the Date
	 * @param month - The Month of the Date
	 * @param year  - The Year of the Date
	 * @return - Any Error messages from invalid dates or an empty string to signify
	 *         validity
	 */
	private static String validDate(int day, int month, int year) {
		int[] daysPerMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// Leap Year check
		if (isLeapYear(year)) { // Checks if the provided year is a leap year
			daysPerMonth[1] = 29; // February is 29 days in a leap year
		}
		// End of Leap Year check
		if (month <= 12 && month > 0) { // Checks if month is between 1 and 12
			if (daysPerMonth[month - 1] >= day && day > 0) { // Checks if day is between 1 and the number of days in
																// that month
				// This try catch has been implemented as a fail safe method in case our
				// validation logic lacks perfection or is flawed
				try {
					LocalDate testDate = LocalDate.of(year, month, day); // Checks if LocalDate object be created with
																			// given date
					if (testDate.isAfter(LocalDate.now())) { // Checks if the date is before the current date
						return "Invalid Date. A Photo can't be taken in the future";
					} else if (year < 1) {
						return "Invalid Year, Please provide a year thats 1 or greater";
					} else {
						return ""; // Returning empty String indicates all validity checks were successful
					}
				} catch (DateTimeException dte) {
					return dte.getMessage(); // Returns the thrown error message
				}
			} else {
				if (month == 2 && !isLeapYear(year) && day == 29) { // Checks If the user has provided 29th february for
																	// a non leap year
					// If the user has then they should know that It's not a leap year
					return String.format(
							"Invalid Date. Day cannot be less than 1 or greater then %d for month %d %n This is because %d is not a Leap Year.",
							daysPerMonth[month - 1], month, year);
				}
				return String.format("Invalid Date. Day cannot be less than 1 or greater then %d for month %d",
						daysPerMonth[month - 1], month);
			}
		} else { /*
					 * If the month is not between 1 and 12 inclusive then we have to ensure our
					 * potentially diverse user is aware that we are going by the gregorian calendar
					 * and not other calendars such as the Hijri calendar or Chinese Lunar calendar.
					 * It also enables them to research the type of calendar that's being used.
					 */
			return "Invalid Date. Month cannot be greater then 12 or less than 1 for the gregorian calendar";
		}
	}

	/**
	 * Checks If the provided year is a leap year
	 * 
	 * @param year - The year you want to check
	 * @return - True if its a leap year or false if its not
	 */
	private static boolean isLeapYear(int year) {
		if ((year % 4 == 0 && (year % 100) > 0) || year % 400 == 0) { // Is the year evenly divisible by 4 and not 100
																		// or evenly divisible by 400?
			return true; // Returns true if its a leap year
		} else {
			return false; // Returns false if its not
		}
	}

	/**
	 * Checks if the provided value is a parsable integer.
	 * 
	 * @param number - The supposed number you want to check
	 * @return - True if it's a parsable integer or false if it's not
	 */
	private static boolean isInteger(String number) {
		try {
			Integer.parseInt(number); // Attempts to parse the provided String into an Integer
			return true; // Returns true if it parsed successfully
		} catch (NumberFormatException nfe) {
			return false; // Returns false if the attempt failed to parse
		}
	}

}
