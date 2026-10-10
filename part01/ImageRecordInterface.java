package part01;

public interface ImageRecordInterface {
	// Our Required Getters
	// According to the specification every instance data value must have a getter
	public int getId();

	public String getTitle();

	public String getDescription();

	public ImageType getType();

	// Our Required Setters
	// According to the specification only appropriate instance data values must
	// have a setter
	// Setter for Id has been excluded as it is not appropriate to manually assign
	// Id's
	// Manually assigning an Id would lead to conflicts if idIncrement increments to
	// a manually assigned Id value
	public boolean setTitle(String title);

	public boolean setDescription(String Description);

	public boolean setType(ImageType type);

	// As stated in the specification, we must have a toString method in Image
	// Record
	public String toString();

}
