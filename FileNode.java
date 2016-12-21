package photo_renamer;

import java.util.Map;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * The root of a tree representing a directory structure.
 */
public class FileNode{

	/** The name of the file or directory this node represents. */
	private String name;
	/** Whether this node represents a file or a directory. */
	private FileType type;
	/** This node's parent. */
	private FileNode parent;
	/**
	 * This node's children, mapped from the file names to the nodes. If type is
	 * FileType.FILE, this is null.
	 */
	private Map<String, FileNode> children;

	/**
	 * A node in this tree.
	 *
	 * @param name
	 *            the file
	 * @param parent
	 *            the parent node.
	 * @param type
	 *            file or directory
	 * @see buildFileTree
	 */
	public FileNode(String name, FileNode parent, FileType type) {
		this.name = name;
		this.parent = parent;
		this.type = type;
		if  (this.isDirectory()){
			this.children = new HashMap<String, FileNode>();
			}
	}

	/**
	 * Build the tree of nodes rooted at file in the file system; note curr is
	 * the FileNode corresponding to file, so this only adds nodes for children
	 * of file to the tree if those children are directories or image files.
	 * Precondition: file represents a directory.
	 * 
	 * @param file
	 *            the file or directory we are building
	 * @param curr
	 *            the node representing file
	 */
	public static void buildTree(File file, FileNode curr) {
		for(File f : file.listFiles())
			if (f.isFile() && (f.getName().contains(".jpg") || f.getName().contains(".png") || 
					f.getName().contains(".jpeg"))){
				curr.addChild(f.getName(), new FileNode(f.getName(), curr, FileType.FILE));
			}
		}
	/**
	 * needs to be redone
	 * 
	 * Build an ArrayList of FileNodes that are children of fileNode.
	 *
	 * @param fileNode
	 *            the root of the subtree. 
	 * @param contents
	 *            the arraylist to be returned
	 */
	public static void buildDirectoryContents(FileNode fileNode, ArrayList<FileNode> contents) {
		if (fileNode.isDirectory()){
			for(FileNode childNode : fileNode.getChildren()){
				contents.add(childNode);
				buildDirectoryContents(childNode, contents);
			}
		}
	}
	/**
	 * Find and return a child node named name in this directory tree, or null
	 * if there is no such child node.
	 *
	 * @param name
	 *            the file name to search for
	 * @return the node named name
	 */
	public FileNode findChild(String name) {
		//base case
		if(this.getName().equals(name)){
			return this;
			}
		//recursive step
		if(this.isDirectory()){
			for(FileNode key : this.getChildren()){
				if (key.getName().equals(name)){
					return key.findChild(name);
				}
				if(key.isDirectory()){
					return key.findChild(name);
				}	
			}
		}
		return null;
		}

	/**
	 * Return the name of the file or directory represented by this node.
	 *
	 * @return name of this Node
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name of the current node
	 *
	 * @param name
	 *            of the file/directory
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the child nodes of this node.
	 *
	 * @return the child nodes directly underneath this node.
	 */
	public Collection<FileNode> getChildren() {
		return this.children.values();
	}

	/**
	 * Return this node's parent.
	 * @return 
	 * 
	 * @return the parent
	 */
	public FileNode getNodeParent() {
		return parent;
	}

	/**
	 * Set this node's parent to p.
	 * 
	 * @param p
	 *            the parent to set
	 */
	public void setParent(FileNode p) {
		this.parent = p;
	}

	/**
	 * Add childNode, representing a file or directory named name, as a child of
	 * this node.
	 * 
	 * @param name
	 *            the name of the file or directory
	 * @param childNode
	 *            the node to add as a child
	 */
	public void addChild(String name, FileNode childNode) {
		this.children.put(name, childNode);
	}

	/**
	 * Return whether this node represents a directory.
	 * 
	 * @return whether this node represents a directory.
	 */
	public boolean isDirectory() {
		return this.type == FileType.DIRECTORY;
	}
	

}
