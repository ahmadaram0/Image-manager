package part01;

import java.time.LocalDate;
import java.util.ArrayList;

// part 2 display the image aswell
public class ImageManager {
	private ArrayList<ImageRecord> imageLibrary = new ArrayList<>(); // Stores all created ImageRecord instances

	/**
	 * adds a reference to an ImageRecord object to the collection.
	 * 
	 * @param image
	 */
	public void addImage(ImageRecord image) {
		if (image != null) {
			imageLibrary.add(image);
		}
	}

	/**
	 * @param id -The id you want to search ImageRecord Object instances for.
	 * @return - Reference to an ImageRecord object (identified by id) or null.
	 */
	public ImageRecord searchId(int id) {
		for (ImageRecord record : this.imageLibrary) { // performs linear search through every ImageRecord created
			if (id == record.getId()) { // compares the provided parameter id with the iterated object's id.
				return record; // returns the ImageRecord if it's id matches the one provided in the parameter/
			}
		}
		return null; // returns null if no id match was found
	}

	/**
	 * @param str - The title you want to search ImageRecord Object instances for.
	 * @return - Reference to an ImageAlbum instance for images with title
	 *         containing the String str.
	 */
	public ImageAlbum searchTitle(String str) {
		ImageAlbum titleAlbum = new ImageAlbum();
		for (ImageRecord record : this.imageLibrary) {
			if (record.getTitle().toLowerCase().contains(str.toLowerCase())) {
				titleAlbum.add(record);
			}
		}
		return titleAlbum;
	}

	/**
	 * 
	 * @param str - The description you want to search ImageRecord Object instances
	 *            for.
	 * @return - Reference to an ImageAlbum instance for images with description
	 *         containing the String str.
	 */
	public ImageAlbum searchDescription(String str) {
		ImageAlbum descriptionAlbum = new ImageAlbum();
		for (ImageRecord record : this.imageLibrary) {
			if (record.getDescription().toLowerCase().contains(str.toLowerCase())) {
				descriptionAlbum.add(record);
			}
		}
		return descriptionAlbum;
	}

	/**
	 * 
	 * @param type - The genre you want to search ImageRecord Object instances for.
	 * @return - Returns a reference to an ImageAlbum instance for images which
	 *         match type.
	 */
	public ImageAlbum searchGenre(ImageType type) {
		ImageAlbum genreAlbum = new ImageAlbum();
		for (ImageRecord record : this.imageLibrary) {
			if (type == record.getType()) {
				genreAlbum.add(record);
			}
		}
		return genreAlbum;
	}

	/**
	 * 
	 * @param start - The starting date you want to search from.
	 * @param end   - The end date that you want to search up to.
	 * @return - ImageAlbum for images taken between start and end dates.
	 */
	public ImageAlbum searchDates(LocalDate start, LocalDate end) {
		ImageAlbum dateAlbum = new ImageAlbum();
		for (ImageRecord record : this.imageLibrary) {
			if (record.getDateTaken().isAfter(start) && record.getDateTaken().isBefore(end)
					|| record.getDateTaken().isEqual(start) || record.getDateTaken().isEqual(end)) {
				dateAlbum.add(record);
			}
		}
		return dateAlbum;
		// my idea prior to learning LocalDate
		// For search functionality split month input based on slashes
		// 13/2/2025 -> .split -> {13,5,2026}
		/*
		 * If year between 2025 n 2026 If year = 2025 month less then 2 if year = 2026
		 * month greater then 5 if year = 2025 month = 2 day < 13 If year = 2026 month =
		 * 5 day > 13 else its valid
		 */
	}

	/**
	 * 
	 * @return - Reference to an ImageAlbum instance for all images managed by an
	 *         ImageManager object.
	 */
	public ImageAlbum getAllImages() {
		return new ImageAlbum(this.imageLibrary);
	}

}
