package photo_renamer;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The action listener for the Jlists. 
 * This class implements the observer/DEM design pattern. 
 * Its role is to be an observer object.
 */
@SuppressWarnings("serial")
//is abstract because we dont have an actionlistener method. only methods that themselves have actionlisteners.
public abstract class ListListener extends JPanel implements ActionListener{

//The selected item in the list of images.
private static String imageListItem;
//The Image object that corresponds to the selected item in the list of images.
private static Image currentlySelectedImage;
//The item selected in the list of past names.
private static String nameListItem;
	
	/**
	 * The action listener for the list of past names.
	 * @param jlist
	 * 		       the JList being listened to.
	 */
	public static void StringListListener(JList<String> jlist){
		jlist.addListSelectionListener(new ListSelectionListener(){	
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
					if(jlist.getSelectedValue().toString() != "no past names"){
						System.out.println(jlist.getSelectedValue().toString());
				        nameListItem = jlist.getSelectedValue().toString();
					}
				}
			}
		});
	}
	/**
	 * The action listener for the list of directories.
	 * @param imageList
	 * 			   The JList being listened to.
	 */
	public static void FileListListener(JList<String> imageList){
		imageList.addListSelectionListener(new ListSelectionListener(){
		@Override
		public void valueChanged(ListSelectionEvent e) {

			if (!e.getValueIsAdjusting()) {
	            imageListItem = imageList.getSelectedValue();
	            // de-serialize the image if it exists
	            try {
		            currentlySelectedImage = Serialize.deserialize(imageListItem);
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
					currentlySelectedImage = getImage();
				}
	            
	            MainWindow.buildImage(imageList.getSelectedValue());
	            MainWindow.buildLeftPanel();
				MainWindow.buildRightPanel(getCurrentlySelectedImage(), FileChooserButtonListener.dirContents);

				}
			}
		});
	}
	/**
	 * Creates the Image object for the File selected in the List.
	 * @return
	 * 		  Image currentlySelectedImage
	 */
	public static Image getImage(){
		File imageFile = new File(FileChooserButtonListener.directory.getAbsolutePath() + "/" + imageListItem);
		Image currentlySelectedImage = new Image(imageListItem, imageFile);
		return currentlySelectedImage;
	}
	/**
	 * Returns the name selected in the list of past image names. 
	 * @return the name selected in the list of past image names. 
	 */
	public static String getSelectedPastName(){
		return nameListItem;
	}
	/**
	 * Returns  the currently selected image object.
	 * @return the currently selected image object.
	 */
	public static Image getCurrentlySelectedImage(){
		if (currentlySelectedImage == null){
			return null;
		}else
		return currentlySelectedImage;
	}
}
