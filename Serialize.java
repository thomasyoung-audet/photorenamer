package photo_renamer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Serializes and deserializes the Image objects
 */
public class Serialize {
	
	//code partly taken from https://www.tutorialspoint.com/java/java_serialization.htm
	/**
	 * Serializes an given image to a .ser file. 
	 * @param e
	 * 			the image to be serialized. 
	 */
	public static void serialize(Image e){
		
	//makes sure the ser directory exists	
	Bookkeeper.checkForConfig(null, false);
	
	try {
        FileOutputStream fileOut =
        new FileOutputStream(System.getProperty("user.dir") + "/ser/"+ 
        e.getImageFile().getName()+".ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(e);
        out.close();
        fileOut.close();
        System.out.printf("Serialized data is saved in /ser/" + e.getImageFile().getName() + ".ser");
     }catch(IOException i) {
        i.printStackTrace();
     }
  }
	/**
	 * Deserializes an Image object
	 * @param imageName 
	 * 			the name of the image file being sought after.
	 * @return the Image 
	 * 
	 * @throws FileNotFoundException If the file being sought after does not exist
	 */

	public static Image deserialize(String imageName) throws FileNotFoundException{
	        //does nothing if there is no file by that name to deserialize
	        Image e = null;
			if (!new File(System.getProperty("user.dir") + "/ser/"+ imageName + ".ser").exists()){
	        	 throw new FileNotFoundException("File doesn't exist");
	         }else{
	        	 try {
	    	         FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") 
	    	        		 + "/ser/"+ imageName +".ser");	
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         e = (Image)in.readObject();
		         in.close();
		         fileIn.close();
		         System.out.println("Deserialized Image...");
			     System.out.println("Name: " + e.getName());
			     System.out.println("ImageFile: " + e.getImageFile());
			     System.out.println("History: " + e.getNameHistory());
			     System.out.println("Tags: " + e.getTagList());
		         return e;
		         }catch(IOException i) {
		         i.printStackTrace();
		         return e;
		         }catch(ClassNotFoundException c) {
		         System.out.println("Image class not found");
		         c.printStackTrace();
		         return e;
	      }
	      }
	}
	/**
	 * Deletes the .ser file of the image.
	 * @param imageName
	 * 				   name of the image we want to delete the .ser file of.
	 */
	public static void clean(String imageName){
		if (new File(System.getProperty("user.dir") + "/ser/"+ imageName + ".ser").exists()){
			new File(System.getProperty("user.dir") + "/ser/"+ imageName + ".ser").delete();
		}
		
	}
	
}
