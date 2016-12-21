package photo_renamer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
/**
 * The class in charge of managing the config directory:
 * the master list of tags and name change history.
 *
 */
public class Bookkeeper {
	/* 
	Responsibilities:
	
	export all tags into a a txt.
	exports all image history into a txt for each image
	creat a config directory if there isn't one, and save all those txts in there.
	*/
		
	/**
	 * The default method for reading text files.
	 * the file name must be specified. Only reads in the directory /config/
	 * Returns a list of strings, each string being one line of the text file.
	 * 
	 * @param filename  
	 * 				  The name of the file on the /config/ directory
	 * @return A list of strings, each string a line from the text file.
	 * @throws IOException
	 */
	public static List<String> readFile(String filename) throws IOException {

		List<String> strings = new ArrayList<String>();
		@SuppressWarnings("resource")
		Scanner fileScanner = new Scanner(new File(System.getProperty("user.dir") + "/config/" + filename+".txt"));
		while (fileScanner.hasNextLine()){
		   strings.add(fileScanner.nextLine());
		}
		return strings;

	}
	/**
	 * Given a list of strings, it will write to a text file, each 
	 * string element of the list being one line.
	 * Can completely overwrite the given text file if you so wish.
	 * 
	 * @param filename 
	 * 				  The name of the file we want to write to 
	 * @param input  
	 * 			   The list of strings we write
	 * @param append  
	 *              A boolean which determines whether or not we want to write over the file contents
	 * @throws IOException
	 */
	public static void writeFile(String filename, List<String> input, boolean append) throws IOException{
		
		FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/config/" + filename+".txt", append); 
		for(String str: input) {
		  writer.write(str + "\n");
		}
		writer.close();
	
	}
	
	/**
	 * Verifies the /config/ or /ser/ directories exists. 
	 * Verifies file with the given name exists within the /config/ directory.
	 * If the directory doesnt exist, it creates it.
	 * If the file doesnt exist, it creates it
	 *
	 * @param filename 
	 * 				  The file the method looks for in the /config/ directory
	 * @return
	 */
	public static File checkForConfig(String filename, boolean config_directory){
		
		if (config_directory) {

		//check if the config directory exists
			if (!new File("config").exists()){
			//if not, create it
				File dir = new File("config");
				dir.mkdir();
			}
		//check if the config file for the filename exits
			if (!new File("config", filename+".txt").exists()){
			//if not create it
				File thisFile = new File("config", filename+".txt");
				try {
					thisFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//return the config file for the filename
			return new File("config", filename+".txt");
		}else{
			//now this is for the serialize class
			//make sure the ser directory exists
			if (!new File("ser").exists()){
				//if not, create it
					File dir = new File("ser");
					dir.mkdir();
				}
				return null;
		}	
	}
		
		
	/**
	 * Writes the new filename and the timestamp to the given file's config file. 
	 * This keeps track of all renaming done to the file 
	 * 
	 * @param oldFilename 
	 * 				     the name of the file which is getting a name change
	 * @param newFilename 
	 * 					 the new name of this file
	 */	
	public static void nameLog(String oldFilename, String newFilename){
		Bookkeeper.checkForConfig("NameLog", true);

		List<String> newName = new ArrayList<String>();
		
		//add the time-stamp to the config
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd/HH:mm:ss").format(new Date());
		newName.add(oldFilename + " --> " + newFilename + " time: " + timeStamp);		; // String newFilename;
		try {
			writeFile("nameLog", newName, true); //add the filename to the config
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a new tag to the list of all tags.
	 * If the tag is already in the list of tags, returns "Tag already exists".
	 * 
	 * @param newTag 
	 * 				the new tag being added to the tag list.
	 */
	public static void addNewTag(String newTag){
		Bookkeeper.checkForConfig("Tags", true);
		List<String> Taglist = null;
		try {
			Taglist = readFile("Tags");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(Taglist.contains(newTag)){
			System.out.println("Tag already exists");
		}else{
		
			Taglist.add(newTag);
			try {
				writeFile("Tags", Taglist, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Removes a tag from the list of all tags. 
	 * If the tag is not in the list of all tags, returns 
	 * "Can't remove because no such tag in tagList"
	 * @param oldTag 
	 * 				the tag being removed from the tag list.
	 */
	public static void removeTag(String oldTag){
		Bookkeeper.checkForConfig("Tags", true);
			//read the tag file
		List<String> Taglist = null;
		try {
			Taglist = readFile("Tags");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(Taglist.remove(oldTag)){
			System.out.println(Taglist);
			try {
				writeFile("Tags", Taglist, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Can't remove because no such tag in tagList");
		}
	}
	
	/**
	 * Returns a list of strings of all the tags in the tag list. 
	 * 
	 * @return a list of all the tags.
	 */
	public static List<String> getTagList(){
		Bookkeeper.checkForConfig("Tags", true);
		List<String> Taglist = null;
		try {
			Taglist = readFile("Tags"); //read the tag config file into a string
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Taglist;  
		//return that string
		
		//returns an empty string if no tags are present		
	}		
	
	/*
	public static void main(String args[]){
		
		nameLog("Essam", "@Essam@Thomas@Ben@Canada's Wonderland");
		nameLog("mii", "mii2");

		
		addNewTag("Samantha");
		addNewTag("Me");
		getTagList();
		removeTag("Julie");
		addNewTag("Julie");
		addNewTag("Myself");
		getTagList();
	}
		*/
}