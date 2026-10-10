package part01;

import java.util.ArrayList;

public class ImageAlbum {
	private ArrayList<ImageRecord> imageCollection; // The array of images sorted by date with the earliest first
	// The line below is initialised the sentinel case of -1
	private int lastImageAccessed = -1; // the iterator for the last image accessed

	/**
	 * A standard ImageAlbum constructor that only initializes the imageCollection
	 * arrayList using this constructor enables you to add imageRecord objects
	 * individually to the imageCollection ArrayList.
	 */
	public ImageAlbum() {
		imageCollection = new ArrayList<>();
	}

	/**
	 * An ImageAlbum constructor that takes a complete ArrayList and stores it in
	 * date order with the earliest date first.
	 * 
	 * @param imageResults - the ArrayList you'd like to store in date order inside
	 *                     the imageAlbum object.
	 */
	public ImageAlbum(ArrayList<ImageRecord> imageResults) {
		imageCollection = new ArrayList<>();
		for (ImageRecord record : imageResults) {
			add(record);
		}
	}

	/**
	 * Adds an imageRecord to the imageCollection ArrayList in date order with the
	 * earliest date first.
	 * 
	 * @param record - the imageRecord object you want to store.
	 */
	public void add(ImageRecord record) {
		if (record != null) {
			boolean added = false;
			for (int i = 0; i < imageCollection.size(); i++) {
				if (record.getDateTaken().isBefore(imageCollection.get(i).getDateTaken())) {
					imageCollection.add(i, record);
					added = true;
					break;
				}
			}
			if (!added) {
				imageCollection.add(record);
			}
		}
	}

	/**
	 * 
	 * @return - Reference to an ImageRecord instance – the first or ‘earliest’
	 *         image in an ImageAlbum or null if there are no images in the album.
	 */
	public ImageRecord getFirst() {
		if (imageCollection.size() > 0) {
			lastImageAccessed = 0;
			return imageCollection.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return - Reference to an ImageRecord instance of the image ‘next’ to the
	 *         last image accessed
	 * @return - null if no images in the album or no ‘next’ image exists or no
	 *         images previously accessed.
	 */
	public ImageRecord getNext() {
		if (lastImageAccessed == -1 || lastImageAccessed == imageCollection.size() - 1) {
			return null;
		} else {
			return imageCollection.get(++lastImageAccessed);
		}
	}

	/**
	 * 
	 * @return - reference to an ImageRecord instance of the image ‘previous’ to the
	 *         last image accessed.
	 * @return - null if no images in the album or no ‘previous’ image exists or no
	 *         images previously accessed.
	 */
	public ImageRecord getPrevious() {
		if (lastImageAccessed < 1) {
			return null;
		} else {
			return imageCollection.get(--lastImageAccessed);
		}
	}

	/**
	 * Useful for knowing how many Image Records are in the Image Album
	 * 
	 * @return - The number of ImageRecord objects in the Album
	 */
	public int getSize() {
		return imageCollection.size();
	}

}
