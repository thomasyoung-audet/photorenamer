package photo_renamer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * A list of tags associated with an Image. 
 */
public class TagList implements Iterable<Tag>, java.io.Serializable{
	

	private static final long serialVersionUID = 1L;
	
	/**This TagList's list of tags */
	private ArrayList<Tag> tags;
	
	/**
	 * Constructs a new TagList
	 */
	public TagList(){
		this.tags = new ArrayList<>();
	}
	/**
	 * Returns the list of tags tags.
	 * @return the list of tags tags.
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}
	/**
	 * Sets the list of tags as tags.
	 * @param tags
	 * 			the list of tags
	 */			
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	/**
	 * Adds a new tag with name name to the TagList
	 * @param name the name 
	 */
	public void addTag(String name){
		Tag t = new Tag(name);
		tags.add(t);
	}
	/**
	 * Removes a tag with name name from the TagList
	 * @param name the name
	 */
	public void removeTag(String name){
		int nameIndex = -1;
		boolean found = false;
		while(!found){
			for(Tag t: tags){
				if(t.getName().equals(name)){
					nameIndex = tags.indexOf(t);
					found = true;
				}	
			}
			
		}
		if(found){
			tags.remove(nameIndex);
		}
	}
	/**
	 * Returns the amount of tags in this list.
	 *
	 * @return The amount of tags in this list.
	 */
	public int getSize(){
		return tags.size();
	}
	/**
	 * Returns a string representation of a TagList
	 * @return the string representation of a TagList
	 */
	@Override
	public String toString(){
		String tagString = "";
		for (Tag t : tags){
			tagString += "@" + t;
		}
		return tagString;
	}
	/**
	 *Returns an iterator for this TagList
	 *@return an iterator for this TagList
	 */
	@Override
	public Iterator<Tag> iterator() {
		return new TagListIterator();
	}
	/**
	 * An iterator for this TagList
	 */
private class TagListIterator implements Iterator<Tag> {
		
	/** the index of the next Tag to return */
	private int index = 0;
		
	/**
	 * Returns whether there is another Tag to return
	 * @return where this is another Tag to return
	 */
	@Override
	public boolean hasNext() {
		return index < tags.size();
	}
	
	/**
	 * Returns the next tag
	 * @return the next tag
	 */
	@Override
	public Tag next(){
		Tag t;
		try{
			t = tags.get(index);
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
		index += 1;
		return t;
		}
	}
}
