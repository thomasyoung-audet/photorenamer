package photo_renamer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * This test case only tests the part of this class that manages the 
 * Master TagList. Using this test WILL delete the existing master tag list.
 * 
 */
public class BookkeeperTest {
	
	private static Scanner fileScanner1;
	private static File testTagFile = new File(System.getProperty("user.dir") + "/config/" + "Tags.txt");
	private static ArrayList<String> testTags1 = new ArrayList<String>();
	private static ArrayList<String> testTags2 = new ArrayList<String>();
	private static FileWriter writer;
	/**
	 * Deletes the existing tag file and creates a new blank one, and creates our test tags list.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if(testTagFile.exists()){
			testTagFile.delete();
		}
		
		testTagFile.createNewFile();
		testTags1.add("Apple");
		testTags1.add("Mango");
		testTags1.add("Banana");
		testTags2.add("Bob");
		testTags2.add("Jim");
		testTags2.add("Lucy");
		
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		fileScanner1.close();
		
	}

	/**
	 * Creates a brand new tag list file,FileWriter, and Scanner for every test
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		testTagFile.createNewFile();
		fileScanner1 = new Scanner(new File(System.getProperty("user.dir") + "/config/" + "Tags.txt"));
		try {
			writer = new FileWriter(System.getProperty("user.dir") + "/config/" + "Tags.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Closes the file scanner and deletes the tag file. 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		testTagFile.delete();
		
	}
	/**
	 * Tests Adding tags to the tags file.
	 */
	@Test
	public void testAddNewTag() {
		ArrayList<String> strings = new ArrayList<String>();
		for(String tag: testTags1){
			Bookkeeper.addNewTag(tag);
		}
		while(fileScanner1.hasNextLine()){
			strings.add(fileScanner1.nextLine());
		}
		assertEquals(testTags1, strings);
	}
	/**
	 * Tests removing tags from the tags file. 
	 * @throws IOException
	 */
	@Test
	public void testRemoveTag() throws IOException {
		ArrayList<String> strings = new ArrayList<String>();
		writer = new FileWriter(System.getProperty("user.dir") + "/config/" + "Tags.txt", true);
		
		for(String tag: testTags2){
			writer.write(tag + "\n");
		}
		writer.close();
		
		testTags2.remove("Lucy");
		Bookkeeper.removeTag("Lucy");
		while(fileScanner1.hasNextLine()){
			strings.add(fileScanner1.nextLine());
		}
		assertEquals(testTags2, strings);
	}
	/**
	 * Tests retrieving the tag file as a list of strings.
	 * @throws IOException
	 */
	@Test
	public void testGetTagList() throws IOException {
		List<String> strings = new ArrayList<String>();
		writer = new FileWriter(System.getProperty("user.dir") + "/config/" + "Tags.txt", true);
		for(String tag: testTags1){
			writer.write(tag + "\n");
		}
		writer.close();

		strings = Bookkeeper.getTagList();
		assertEquals(testTags1, strings);
	}

}
