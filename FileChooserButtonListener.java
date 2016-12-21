package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The listener for the button to choose a directory. 
 */
public class FileChooserButtonListener implements ActionListener {

	/** The window the button is in. */
	private JFrame directoryFrame;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;
	/** The contents of the chosen directory */
	public static ArrayList<FileNode> contents = new ArrayList<FileNode>();
	/** the directory contents but as Files*/
	public static ArrayList<File> dirContents = new ArrayList<File>();
	/**The chosen directory*/
	public static File directory = new File("emptyfile");
	
	
	
	/**
	 * An action listener for window dirFrame, displaying a file path on
	 * dirLabel, using fileChooser to choose a file.
	 *
	 *
	 * @param dirFrame
	 *            the main window
	 * @param dirLabel
	 *            the label for the directory path
	 * @param fileChooser
	 *            the file chooser to use
	 */
	public FileChooserButtonListener(JFrame dirFrame, JLabel dirLabel, JFileChooser fileChooser) {
		this.directoryFrame = dirFrame;
		this.directoryLabel = dirLabel;
		this.fileChooser = fileChooser;
		
	}
	/**
	 * Creates a list of the files inside a given directory, excluding other directories.
	 * 
	 * @param file
	 * 			  the directory in which we are looking for all the files.
	 */
	public static void getDirContents(File file){
		
		contents = new ArrayList<FileNode>();
		dirContents = new ArrayList<File>();

		// Make the root.
		FileNode fileTree = new FileNode(file.getName(), null, FileType.DIRECTORY);
		FileNode.buildTree(file, fileTree);

		// getting all the image files
		FileNode.buildDirectoryContents(fileTree, contents);
		
		//removing anything that isn't a directory
		List<FileNode> list = new ArrayList<FileNode>();
		for (FileNode node: contents){
				if (node.isDirectory()){
					list.add(node);
				}
			}
		contents.removeAll(list);
		// creating File objects from the FileNodes and the selected directory
		for(FileNode node: contents){
			dirContents.add( new File(file + "/" + node.getName()));
		}
	}

	/**
	 * Handle the user clicking on the open button.
	 *
	 * @param e
	 *            the event object
	 * @return 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		int returnVal = fileChooser.showOpenDialog(directoryFrame.getContentPane());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			directory = fileChooser.getSelectedFile();
			if (directory.exists()) {
				directoryLabel.setText("Selected File" + directory.getAbsolutePath());
				getDirContents(directory);
			}
		} 
		DirectoryExplorer.buildWindow().setVisible(false);
		DirectoryExplorer.buildWindow().dispose();
		MainWindow.BuildGUI(FileChooserButtonListener.dirContents);
	}
}


