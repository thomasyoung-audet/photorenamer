package photo_renamer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * The main control center that manipulates the Image objects
 * by creating or deleting tags, and by applying tags to images. 
 *
 */
public class User {
  /* 
   * Responsibilities:
   * rename image file
   * add tag to image
   * delete tag from image
   * create tag
   * view all tags (reads the .txt file)
   */
	
	//this method actually commits the tags to the Image and the Image's associated file
	/**
	 * Commits the proposed tags for a given Image and 
	 * sends a request to retag the Image
	 * 
	 * @param img
	 * 			the image to be changed
	 */			
	public static void commitTags(Image img){
		if (img != null){
			String newName = "";
			for (Tag tag: img.getTagList()){
				newName += "@" + tag.getName();
			}
			try {
				img.retag(newName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//these next two methods add/remove tags to/from the Image's taglist
	/**
	 * Sends a tag to be added to the given Image.
	 * 
	 * @param img
	 * 			the Image being given the tag
	 * @param tag
	 * 			the tag to be applied to the image. 
	 */			
	public static void addTagToImage(Image img, String tag){
		if (img != null){
		img.getTagList().addTag(tag);
		}
	}
	/**
	 * Sends a tag to be removed from the given Image.
	 * 
	 * @param img
	 * 			the Image to have the tag removed.
	 * @param tag
	 * 			the tag to be removed from the Image.
	 */			
    public static void removeTagFromImage(Image img, String tag){
		if(img != null){
			img.getTagList().removeTag(tag);
		}
	}	

	
    /**
     * Changes an Image's name, filename and tags to an older configuration. 
     * @param img
     * 			the Image being changed.
     * @param oldName
     * 			the name being applied to the Image. 
     */
    public static void revertName(Image img, String oldName){
	    int i = oldName.lastIndexOf('.');
	   	//checking if we're going back to the original file name. 
	   	if (!oldName.equals(img.getName())){
	    	String[] oldTags = oldName.substring(0, i).split("@");
	    	ArrayList<String> oldTagsArray = new ArrayList<String>(Arrays.asList(oldTags));
	   		oldTagsArray.toArray(oldTags);    		
	   		oldTagsArray.remove(0);
	   		TagList newTagList = new TagList();
	   		for(String name: oldTagsArray){
	    		newTagList.addTag(name);
	   		}
	   		img.setTagList(newTagList);
	   		commitTags(img);
	    }else{
	   		TagList newTagList = new TagList();
	   		img.setTagList(newTagList);
	    	try {
				img.retag("");
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
    }
}

