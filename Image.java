package photo_renamer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;


/**
 * The representation of the image file being manipulated
 *
 */
public class Image implements java.io.Serializable {
	
	
	private static final long serialVersionUID = 1L;
	//the images original filename
	private String name;
	//the images history of names which will need to be read from its config 
	private ArrayList<String> nameHistory;
	//the current tags applied to this image
	private TagList tagList;
	//the file associated with this Image
	private File imageFile;


	//Serialize constructor
	/**
	 * A representation of an image file that contains its original filename name,
	 * its filename history nameHistory, and the contains its current tags in tagList  
	 * 
	 * @param name  
	 * 			the files original name
	 * @param imageFile
	 * 			the image file being represented
	 * @param nameHistory
	 * 			the collection of past file name this Image has had
	 * @param tagList
	 * 			the current tags applied to this Image.
	 */
	public Image (String name, File imageFile, ArrayList<String> nameHistory, TagList tagList){
		this.name = name;
		this.imageFile = imageFile;
		this.nameHistory = nameHistory;
		this.tagList = tagList;
	}
	//new file constructor

	/**
	 * A representation of an image file that contains its original filename name,
	 * and represents the file imageFile.
	 * @param name
	 * 			the files original name
	 * @param imageFile
	 * 			the image file being represented
	 */			
	public Image(String name, File imageFile){
		this.name = name; 
		this.imageFile = imageFile;
		this.nameHistory = new ArrayList<String>(); 
		this.tagList = new TagList();
				
	}

		
	/**
	 * Applies new tags to the Image object from newTags, 
	 * adds the old name to the nameHistory, and sends a request to
	 * log the change of name to Bookkeeper. 
	 * 
	 * @param newTags
	 * 			the new tags to be applied to the Image.
	 * @throws IOException
	 * 			If an input or output exception occurred
	 */
	public void retag(String newTags) throws IOException{
		String newPath = "";

		int i = imageFile.getAbsolutePath().lastIndexOf('/');
		int j = name.lastIndexOf('.');
		newPath = imageFile.getAbsolutePath().substring(0, i+1);
		
		//add the old name to the renaming log
		Bookkeeper.nameLog(imageFile.getName(), name + newTags);
		//add the old name to the Images own history
		this.nameHistory.add(imageFile.getName());
		//rename the imageFile
		File destFile = new File(newPath + name.substring(0, j) + newTags + name.substring(j));
		Files.move(imageFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		setImageFile(destFile);
			
	}

	/**
	 * Returns this Image's tagList
	 * 
	 * @return the Image's tagList
	 */
	public TagList getTagList(){
		return this.tagList;
	}

	/**
	 * Sets the tagList of this Image.
	 * 
	 * @param tagList
	 * 			the list of tags to be applied to this Image
	 */
	
	public void setTagList(TagList tagList){
		this.tagList = tagList;
	}

	/**
	 * Returns the Image's nameHistory
	 * 
	 * @return the Image's nameHistory
	 */
	public ArrayList<String> getNameHistory() {
		return this.nameHistory;
	}
	

	/**
	 * Sets the nameHistory of this Image
	 * 
	 * @param nameHistory
	 * 			the history to be applied to this Image.
	 */			
	public void setNameHistory(ArrayList<String> nameHistory) {
		this.nameHistory = nameHistory;
	}
	/**
	 * Returns the image file associated with this Image.
	 * 
	 * @return the image file associated with this Image.
	 */
	public File getImageFile(){
		return this.imageFile;
	}
	/**
	 * Sets the image file for this Image.
	 * 
	 * @param imageFile
	 * 			the image file to be associated with this Image.
	 */
	public void setImageFile(File imageFile){
		this.imageFile = imageFile;
	}
	/**
	 * Returns the original file name of this Image.
	 * 
	 * @return the original file name of this Image.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the original file name for this Image.
	 * 
	 * @param name
	 * 			the original file name. 
	 */
	public void setName(String name) {
		this.name = name;
	}
}