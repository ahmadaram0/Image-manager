package part01;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class testscript {
private static Scanner userInput = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(Arrays.toString(Imagetype.values()));
		//testImagetype();
		//System.out.println(ImageType.FOOD);
		//testImageRecordId();
		//testLocalDate();
		//System.out.println("Message");
		//System.out.println("Hi".repeat(0));
		//String name = "  i d k    ";
		//System.out.println(name);
		//System.out.println(name.trim());

	}
	/**
	 * Prints the name and description of each enum constant from the Imagetype enumerator.
	 * This helps us ensure that our enumerator is fully functional and that it contains 
	 * all the Image types mentioned in the brief along with their descriptions.
	 * 
	 */
	
	public static void testImagetype() {
		for(ImageType img: ImageType.values()) {
			System.out.println(img.name()+"\n"+img+"\n");
		}
	}
	/**
	 * This test ensures that each ImageRecord object get's a unique identifier derived from the idIncrement Variable
	 * Uniqueness will be achieved by incrementing the idIncrement class variable each time after assigning it to on object
	 * so that the next object gets a different id.
	 *  No longer working due to null parameters leading to a rejected construction
	 */
	public static void testImageRecordId(){
		try {
		ImageRecord test = new ImageRecord(null, null, null, null, null);
		System.out.println(test.getId());
		ImageRecord test2 = new ImageRecord(null, null, null, null, null);
		System.out.println(test2.getId());
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}
	/**
	 * Experimenting with LocalDate behaviour to better understand it's use case and how it handles bad data 
	 */
	public static void testLocalDate() {
		boolean exit = false;
		while(!exit) {
			try {
		System.out.println("Day:");
		int day = userInput.nextInt();
		userInput.nextLine();
		System.out.println("Month: ");
		int month = userInput.nextInt();
		userInput.nextLine();
		System.out.println("Year: ");
		int year = userInput.nextInt();
		userInput.nextLine();
		LocalDate date = LocalDate.of(year, month, day);
		System.out.println("The date is:");
		System.out.println(date);
			}catch(DateTimeException dte) {
				System.out.println(dte.getLocalizedMessage());
				System.out.println("Next");
				System.out.println(dte.getMessage());
				System.out.println("Next");
				System.out.println(dte.toString());
				System.out.println("The Cause: ");
				System.out.println(dte.getCause());
			}
		System.out.println("Enter e to exit \n Input: ");
		if(userInput.nextLine().equalsIgnoreCase("e")) {
			exit = true;
		}
		}
	}

}
