package part01;

import java.time.LocalDate;

public class ImageRecord implements ImageRecordInterface {
	private static int idIncrement = 0; // A class integer variable that's incremented after being assigned to an id
										// instance variable
	private final int id; // The unique Identifier for ImageRecord object Instances.
	private String title; // The title of the image;
	private String description; // The image's description
	private ImageType type; // The image's type which is also refered to as the Genre.
	private String thumbnail; // the filename of the picture
	private LocalDate dateTaken; // The date at which the picture was taken

	/**
	 * Constructs an image record object whilst ensuring that the provided instance
	 * data is valid and throwing an exception with a detailed error message if any
	 * of the provided parameters are invalid.
	 * 
	 * @param title       = the title of the image
	 * @param description - a description of the image
	 * @param type        = type of the Image from the predefined Imagetype
	 *                    enumerator
	 * @param thumbnail   - the image file's name e.g Homer.png
	 * @param dateTaken   - the date of when the image was taken
	 * @throws Exception - triggered if any provided parameters are null or if
	 *                   string parameters are empty
	 */
	public ImageRecord(String title, String description, ImageType type, String thumbnail, LocalDate dateTaken)
			throws Exception {
		String errorMessage = ""; // used for concatenating error messages from setters that have failed to set.
		if (!setTitle(title)) {
			errorMessage += "Error Title should not be empty or null.\n";
		}
		if (!setDescription(description)) {
			errorMessage += "Error Description should not be empty or null. \n";
		}
		if (!setType(type)) {
			errorMessage += "Error type cannot be null and must be an enum constant from the enum Imagetype. \n";
		}
		if (!setThumbnail(thumbnail)) {
			errorMessage += "Error thumbnail should not be empty or null. \n";
		}
		if (!setDateTaken(dateTaken)) {
			errorMessage += "Error dateTaken cannot be null.";
		}
		if (!errorMessage.equals("")) {
			throw new Exception(errorMessage); // throws the concatenated error message
		}
		this.id = idIncrement++;
	}

// Instance data getters
	/**
	 * @return a integer value of the Id variable
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return a String value of the title variable
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * @return - a String value of a 20 character long preview of the title or the title if it's shorter.
	 */
	public String getTitlePreview() {
		if(this.title.length() > 20) { //If the length is longer then 20 then limit it to 20 with trailing periods
			return this.title.substring(0,20)+"..."; //The periods at the end signify the message is longer
		} else {
		return this.title;
		}
	}

	/**
	 * @return a String value of the description variable
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * @return - a String value of a 20 character long preview of the description or the description if it's shorter.
	 */
	public String getDescriptionPreview() {
		if(this.description.length() > 20) { //If the length is longer then 20 then limit it to 20 with trailing periods
			return this.description.substring(0,20)+"..."; //The periods at the end signify the message is longer
		} else {
		return this.description;
		}
	}

	/**
	 * @return = a ImageType value of the type variable
	 */
	public ImageType getType() {
		return this.type;
	}

	/**
	 * @return = String value of the thumbnail variable
	 */
	public String getThumbnail() {
		return this.thumbnail;
	}

	/**
	 * @return = a LocalDate value of the dateTaken variable
	 */
	public LocalDate getDateTaken() {
		return this.dateTaken;
	}

	/**
	 * @return - formatted String containing the Title,Description,Type,Thumbnail
	 *         and Date Taken.
	 */
	public String toString() {
		LocalDate dateTaken = getDateTaken();
		return String.format("ID:%d Title: %s Description: %s Type: %s Thumbnail: %s Date Taken: %s", getId(),
				getTitle(), getDescription(), getType().name(), getThumbnail(), dateTaken);
	}

//Instance Data Setters (where appropriate)

	/**
	 * A String setter for the title instance variable. It ensures that the provided
	 * title is valid by making sure it isn't an empty string or null.
	 * 
	 * @param title - the String value you'd want to set for the instance variable
	 *              called title.
	 */
	public boolean setTitle(String title) {
		if (title != null && !title.trim().equals("")) {
			this.title = title;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A String setter for the description instance variable. It ensures that the
	 * provided Description is valid by making sure it isn't an empty string or
	 * null.
	 * 
	 * @param description - The description you'd want to set.
	 * @return - a boolean value indicating whether the set operation was successful
	 *         by returning true if it was and false if it wasn't.
	 */
	public boolean setDescription(String description) {
		if (description != null && !description.trim().equals("")) {
			this.description = description;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A setter for the type instance variable. It ensures that the provided type is
	 * valid by only allowing one of the pre-defined Imagetype enumerator constants
	 * to be set and by making sure that the provided type isn't null.
	 * 
	 * @param type = The ImageType value you'd want to set for the instance variable
	 *             called type.
	 * @return = Boolean value indicating whether the set operation was successful
	 *         by returning true if it was and false if it wasn't.
	 */
	public boolean setType(ImageType type) {
		if (type != null) {
			this.type = type;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A setter for the dateTaken instance variable. It ensures that the provided
	 * dateTaken is valid by only allowing LocalDate objects to be set and by making
	 * sure that the provided dateTaken isn't null.
	 * 
	 * @param dateTaken = Set the provided LocalDate object as the dateTaken
	 *                  instance variable.
	 * @return - Boolean value indicating whether the set operation was successful
	 *         by returning true if it was and false if it wasn't.
	 */
	public boolean setDateTaken(LocalDate dateTaken) {
		if (dateTaken != null) {
			this.dateTaken = dateTaken;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A String setter for the thumbnail instance variable. It ensures that the
	 * provided thumbnail is valid by making sure it isn't an empty string or null.
	 * 
	 * @param thumbnail - The string value you'd want to set for the instance
	 *                  variable called thumbnail.
	 * @return - Boolean value indicating whether the set operation was successful
	 *         by returning true if it was and false if it wasn't.
	 */
	public boolean setThumbnail(String thumbnail) {
		if (thumbnail != null && !thumbnail.trim().equals("")) {
			this.thumbnail = thumbnail;
			return true;
		} else {
			return false;
		}
	}

}
