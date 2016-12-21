package photo_renamer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
/**
 * Creates and shows the GUI for the program.
 * This class implements the observer/DEM design pattern. 
 * Its role is to be an observable object.
 */

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements WindowListener {
	
	public static JTextField textField = new JTextField(30);
	private static JFrame jf = new JFrame("Photo Renamer"); 
	private static JPanel imagePanel = new JPanel();
	private static JPanel leftPanel = new JPanel();
	private static JPanel rightPanel = new JPanel();

	
	/**
	 * Creates a JPanel on the right side of the JFrame containing a Jlist of the past names of an Image
	 * and A Jlist containg the images in the chosen directory.
	 * @param currentImage
	 * 					  the image currently selected by the user.
	 * @param fileList
	 * 				  the list of files in the chosen directory.
	 * @return a JPanel containing two JLists.
	 */
	public static JPanel buildRightPanel(Image currentImage, ArrayList<File> fileList){
		//past name list
		String[] data = new String[1];
		if (currentImage == null || currentImage.getNameHistory().isEmpty()){
			data = new String[]{"no past names"};
		}else{
			data = new String[currentImage.getNameHistory().size()];
			for (int i=0; i<currentImage.getNameHistory().size(); i++ ){
				data[i] = currentImage.getNameHistory().get(i);
			}

		}
		//directory list
		String[] images = new String[fileList.size()];

		for (int i=0; i<fileList.size(); i++ ){
				images[i] = fileList.get(i).getName();
			}
		
		
		JScrollPane nameScrollPane = new JScrollPane();
		JScrollPane imageScrollPane = new JScrollPane();
		JList<String> imageList = new JList<String>(images);
		ListListener.FileListListener(imageList);
		JList<String> nameList = new JList<String>(data);
		ListListener.StringListListener(nameList);
		nameScrollPane.setViewportView(nameList);
		imageScrollPane.setViewportView(imageList);
		
		rightPanel.removeAll();
		rightPanel.add(new JLabel("List of images in the folder:"));
		rightPanel.add(imageScrollPane);
		rightPanel.add(new JLabel("past names of that image:"));
		rightPanel.add(nameScrollPane);
		rightPanel.repaint();
		rightPanel.revalidate();
		
		return rightPanel;
	}
	
	/**
	 * Creates a JPanel on the left side of the GUI with a JButton
	 * with which you can remove an item from the master tag list, a
	 * JComboBox containing all the tags in the master tag list and A Jlabel 
	 * that shows the currently applied tags on an image.
	 * 
	 * @return JPanel with A JComboBox, a JLabel, and a JButton.
	 */
	public static JPanel buildLeftPanel(){
		
		//show currently applied tags to an image
		JLabel appliedTags = new JLabel();
		if (null == ListListener.getCurrentlySelectedImage()){
		}else{
			appliedTags = new JLabel(ListListener.getCurrentlySelectedImage().getTagList().toString());
		}
		
		//build the contents of the drop down
		String[] tagStrings = new String[ Bookkeeper.getTagList().size() + 1];
		tagStrings[0] = "Master Tag List";
		for (int i=0; i<Bookkeeper.getTagList().size(); i++ ){
			tagStrings[i+1] = Bookkeeper.getTagList().get(i);
			}
		//Creating A drop down menu of all the available tags you could apply
		JComboBox<String> mainTagList = new JComboBox<String>(tagStrings);
		//mainTagList.setSelectedIndex(0);
		mainTagList.addItemListener(new ComboListener());
		JButton removeTagButton = new ButtonListener("Remove tag from Tag list");

		leftPanel.removeAll();
		leftPanel.add(removeTagButton);
		leftPanel.add(new JLabel("Master tag list:"));
		leftPanel.add(mainTagList);
		leftPanel.add(new JLabel("currently applied tags:"));
		leftPanel.add(appliedTags);
		leftPanel.repaint();
		leftPanel.revalidate();

		return leftPanel;
	}
	/**
	 * Creates a JPanel containing the image File of the currently selected Image so it can be displayed.
	 * @param imageFile
	 * 				   the FIle corresponding to the selected Image
	 * @return a JPanel with an image in it.
	 */
	public static JLabel buildImage(String imageFile){
		
		//starts building the image
		String IMAGE_PATH = FileChooserButtonListener.directory.getAbsolutePath() + "/" + imageFile;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(IMAGE_PATH));
		} catch (IOException e) {
		//reads the image but hasn't saved it anywhere
		}
		ImageIcon icon = new ImageIcon(img);
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(icon);

		JScrollPane imagePane = new JScrollPane(imageLabel);
		imagePane.setViewportView(imageLabel);
		imagePanel.removeAll();
		imagePanel.add(imagePane);
		imagePanel.repaint();
		imagePanel.revalidate();

		return imageLabel;
	}
	
	/**
	 * Builds the main window of the program, piece by piece. It starts
	 * with the center and then goes clockwise.
	 * @param fileList
	 * 				  the list of files in the directory selected by the user.
	 */
	public static void BuildGUI(ArrayList<File> fileList) { 
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//if the selected directory has no images this creates a panel that only has an exit button
		if (fileList.isEmpty()){
			JButton closeButton = new ButtonListener("there are no images in the selected directory. click here to exit");
			imagePanel.add(closeButton, BorderLayout.CENTER);
			jf.add(imagePanel, BorderLayout.CENTER);
			jf.pack();
		}else{
		//starts to building the window 		
		jf.add(imagePanel, BorderLayout.CENTER); //adding the image panel to the center of the window.
		
		
		
		//Top of the Window
		JPanel upperpanel = new JPanel();
		JButton committagbutton = new ButtonListener("create tag");
		upperpanel.add(textField, BorderLayout.EAST);
		upperpanel.add(committagbutton, BorderLayout.WEST);
		jf.add(upperpanel, BorderLayout.NORTH);
	
		
		
		//Left of the window
		leftPanel.setLayout(new GridLayout(7,1));
		jf.add(buildLeftPanel(), BorderLayout.LINE_START);

		
		
		//Right of the window
		rightPanel.setLayout(new GridLayout(4,1));
		jf.add(buildRightPanel(ListListener.getCurrentlySelectedImage(),fileList), BorderLayout.EAST);

		
		
		//Bottom of the Window. 
		JPanel lowerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JButton deleteTagButton = new ButtonListener("Remove selected tag from image");
		JButton addTagButton = new ButtonListener("Add selected tag to image");
		JButton exitButton = new ButtonListener("Exit");
		JButton saveButton = new ButtonListener("Save");
		JButton revertButton = new ButtonListener("Revert name");
		//Adding a bunch of buttons, then add the buttons to the frame
		buttonPanel.add(deleteTagButton);
		buttonPanel.add(addTagButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(exitButton);
		buttonPanel.add(revertButton);
		//put it all together
		lowerPanel.add(buttonPanel);
		jf.add(lowerPanel, BorderLayout.SOUTH);
		
		//finished creating the window. now pack it up so it fits everything
		jf.pack();
		}
		jf.setVisible(true); // make window visible
		// Ask the window to listen to its own events == we need these for the methods below work.
		MainWindow s = new MainWindow();
		// now how do we make this window interactive. so we attach the windowlistener to the frame.
		jf.addWindowListener((WindowListener) s);
		
	}
	// Below window events are implemented, so the window can respond to actions
	// these methods are unimplemented methods from the interface windowlistener
	public void windowClosing(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}
}