package photo_renamer;
/**
 * A name used to describe who is in an image.
 *
 */
public class Tag implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L; //eclipse said to put this here
	/** This Tag's name. */ 
	private String name;
	
	/**
	 * Constructs a new Tag with name name. 
	 * @param name
	 * 			the name that this tag represents.
	 */
	public Tag(String name){
		this.name = name;
	}
	/**
	 * Returns this Tag's name
	 * @return the name 
	 * 
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set's this Tag's name to name
	 * @param name
	 * 			the name to set
	 */			
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Returns a string representation of this tag.
	 * @return the name associated with this tag. 
	 */
	@Override
	public String toString(){
		return getName();
	}
}
