package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * The Button action listener.
 * This class implements the observer/DEM design pattern. 
 * Its role is to be an observer object. 
 */
public class ButtonListener extends JButton implements ActionListener {

	/**
	 * A representation of an Button listener that contains a test label.
	 * @param label
	 * 			   the name of the button
	 */
	ButtonListener(String label) {
		super(label); 
		this.addActionListener(this); // this button has its own listener.
	}
	/**
	 *The action performed if a button is clicked. This Action performed method is responsible for
	 *all of the buttons present in the GUI: save, exit, the button to exit the program if the directory
	 *has no images, create tag and delete tag, add tag and remove tag from image, and revert name. 
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e) { 
		

		if (e.getActionCommand() == "Save"){
			
			//commit tags
			try{
			Serialize.clean(ListListener.getCurrentlySelectedImage().getImageFile().getName());				
			User.commitTags(ListListener.getCurrentlySelectedImage());
			Serialize.serialize(ListListener.getCurrentlySelectedImage());
			System.out.println("saving image with new tags");
			//refind the directory contents
			FileChooserButtonListener.getDirContents(FileChooserButtonListener.directory);
			MainWindow.buildRightPanel(ListListener.getCurrentlySelectedImage(), FileChooserButtonListener.dirContents);
			} catch (NullPointerException ex){
				System.out.println("No file chosen!");
			}
		}
		
		if (e.getActionCommand() == "Exit" || e.getActionCommand() == 
				"there are no images in the selected directory. click here to exit"){
			System.out.println("Exiting using button!");
			System.exit(0);
		}
		
		if (e.getActionCommand() == "create tag"){
			if(!MainWindow.textField.getText().isEmpty()){
				System.out.println("creating Tag " + MainWindow.textField.getText());
				Bookkeeper.addNewTag(MainWindow.textField.getText());
				MainWindow.buildLeftPanel();
			}
		}
		
		if (e.getActionCommand() == "Remove tag from Tag list" ){ 
			
			if (ComboListener.getComboItem() != null){  
				System.out.println("Deleted tag " + ComboListener.getComboItem() + "from tag list");
				Bookkeeper.removeTag(ComboListener.getComboItem());
				MainWindow.buildLeftPanel();
			}
		}
		
		if (e.getActionCommand() == "Remove selected tag from image" ){  
			try {
				if  (ComboListener.getComboItem() != null){
					System.out.println("Deleting tag " + ComboListener.getComboItem() + "from image");
					User.removeTagFromImage(ListListener.getCurrentlySelectedImage(), ComboListener.getComboItem());
					System.out.println(ListListener.getCurrentlySelectedImage().getTagList());
					MainWindow.buildLeftPanel();
				}
				
			} catch (NullPointerException ex) {
					System.out.println("No file chosen!");
					}	
		}
		
		
		if (e.getActionCommand() == "Add selected tag to image"){
			System.out.println("Adding tag");
			try{
				if (ComboListener.getComboItem() != null){
					System.out.println("Adding '" + ComboListener.getComboItem() + "' tag to image");
					User.addTagToImage(ListListener.getCurrentlySelectedImage(), ComboListener.getComboItem());
					MainWindow.buildLeftPanel();
				}
				}catch (NullPointerException ex) {
				  System.out.println("No file chosen!");
			}
		}
		
		if (e.getActionCommand() == "Revert name"){
			try{
			Serialize.clean(ListListener.getCurrentlySelectedImage().getImageFile().getName());
			User.revertName(ListListener.getCurrentlySelectedImage(), ListListener.getSelectedPastName());
			Serialize.serialize(ListListener.getCurrentlySelectedImage());
			FileChooserButtonListener.getDirContents(FileChooserButtonListener.directory);
			MainWindow.buildRightPanel(ListListener.getCurrentlySelectedImage(), FileChooserButtonListener.dirContents);
			} catch (NullPointerException ex) {
				System.out.println("No Name chosen!");
			}
		}
	}
}