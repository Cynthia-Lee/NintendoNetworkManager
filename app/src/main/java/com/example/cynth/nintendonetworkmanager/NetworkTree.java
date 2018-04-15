package com.example.cynth.nintendonetworkmanager; /**
 * Class that is a tree manager for the NetworkNode objects
 * 
 * CSE 214-R12 Recitation, CSE 214-02 Lecture
 * @author Cynthia Lee
 * e-mail: cynthia.lee.2@stonybrook.edu
 * Stony Brook ID: 111737790
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class NetworkTree {
	// serve as the tree manager for your NetworkTree. This must hold references
	// into a tree (the root and cursor), as well as be able to generate and save
	// the tree to and from a file.
	private static NetworkNode root;
	private NetworkNode cursor;
	private NetworkNode removedTree = null;

	/**
	 * Default constructor that creates a NetworkTree with no nodes
	 */
	public NetworkTree() {
		root = null;
		cursor = null;
	}

	/**
	 * Sets the cursor to the root of the NetworkTree
	 */
	public void cursorToRoot() {
		// sets the cursor to the root of the NetworkTree
		cursor = root;
	}

	// public NetworkNode getCursor() { // if the cursor is null, then the method
	// should return null as well
	// if (cursor == null) { // cursor does not reference something
	// return null;
	// }
	// return cursor;
	// }

	/**
	 * Removes the child at the specified index of the NetworkTree
	 * @return
	 * Node that was removed from the tree
	 * @throws NoTreeException
	 * If there is no tree it will throw an exception because there is no NetworkNode to cut
	 */
	public NetworkNode cutCursor() throws NoTreeException {
		// removes the child at the specified index of the NetworkTree, *as well as all
		// of its children*
		NetworkNode parent = null;
		removedTree = cursor;
		int indexOfCursor = 0;
		if (cursor != root) {
			// cursor.getParent().setChildren(c);
			parent = cursor.getParent();
			// loop through the parent's children array until find cursor, shift everything
			// over
			// find which child you have to remove and remove it
			// have to shift things over
			for (int c = 0; c <= parent.getNumberOfChildren(); c++) {
				if (parent.getChildren()[c] == cursor) { // until find where the cursor is
					indexOfCursor = c;
					break;
				}
			} // have index of the cursor in the parent's children array
				// remove the specific child
				// remove the cursor's child and set all of it's children to null

			// cursor.setChildren(null);
			// shift the right element over into the left
			for (int i = indexOfCursor; i < parent.getMaxChildrenNumber() - 1; i++) {
				parent.getChildren()[i] = parent.getChildren()[i + 1];
			}
			// set last child to null
			parent.getChildren()[parent.getMaxChildrenNumber() - 1] = null;
			// cursor.setChildren(null);
			// cursor.getParent().removeChild(cursor);
			cursor = parent;// cursor goes to parent
			cursor.setNumberOfChildren(cursor.getNumberOfChildren() - 1);
		} else if (root == null) { // no tree
			throw new NoTreeException();
		} else if (cursor == root) {// cutting root clears the tree
			root = null;
			cursor = null; //
		}
		return removedTree;
	}

	/**
	 * Adds a child node to the NetworkTree
	 * @param index
	 * Integer index that the child will be added to
	 * @param node
	 * Node that will be added to the tree
	 * @throws HoleInArrayException
	 * If adding the child node will make a hole in the array
	 * @throws InvalidArgumentException
	 * If the index entered is not valid
	 * @throws FullTreeException
	 * If the tree is full, cannot add node
	 * @throws CannotAddChildException
	 * If the node is a Nintendo node (Nintendo nodes cannot have children)
	 */
	public void addChild(int index, NetworkNode node)
			throws HoleInArrayException, InvalidArgumentException, FullTreeException, CannotAddChildException {
		NetworkNode parent = cursor;
		// append the node to the end of the parent's children
		if (cursor == null) {
			root = node;
			cursor = root;
		} else if (cursor.getIsNintendo()) { // is a Nintendo node, so you shouldn't add children to it
			throw new CannotAddChildException();
		} else if (index < 0) {
			throw new InvalidArgumentException();
		} else if (cursor.getNumberOfChildren() == cursor.getMaxChildrenNumber()) {
			throw new FullTreeException();
		} else if (cursor.getNumberOfChildren() - 1 >= index) { // # 3 0,1,2 can add in index
			// at an index with a node in it, shift the nodes over
			// Adds the given node to the corresponding index of the children array.
			// add at the end of tree
			// add and shift nodes down
			for (int i = cursor.getNumberOfChildren(); i > index; i--) {
				cursor.getChildren()[i] = cursor.getChildren()[i - 1];
			}
			// now have room to put the new child
			cursor.getChildren()[index] = node;
			cursor.setNumberOfChildren(cursor.getNumberOfChildren() + 1);
			cursor.getChildren()[index].setParent(parent);
		} else if (cursor.getNumberOfChildren() == index) { // can add at the end
			cursor.getChildren()[index] = node;
			cursor.setNumberOfChildren(cursor.getNumberOfChildren() + 1);
			cursor.getChildren()[index].setParent(parent);
		} else {
			throw new HoleInArrayException();
		}

	}

	/**
	 * Moves the cursor to its child node at the specified index
	 * @param index
	 * Integer index to go to the child node at that index
	 * @throws InvalidArgumentException
	 * If the index is less than 0 or gr
	 * @throws NoTreeException
	 * If the root of the tree is null, which means the tree is empty, which means that there are no children nodes to move to
	 */
	public void cursorToChild(int index) throws InvalidArgumentException, NoTreeException {
		// index = index - 1;
		if(root == null) {
			throw new NoTreeException();
		} else if (index < cursor.getNumberOfChildren() && index >= 0) {
			cursor = cursor.getChildren()[index];
		} else {
			throw new InvalidArgumentException();
		}
	}

	/**
	 * Moves the cursor to its parent node
	 * @throws InvalidCursorPositionException
	 * If the cursor is the root, cannot move because root does not have a parent. Or if the cursor is null, there is no parent node
	 */
	public void cursorToParent() throws InvalidCursorPositionException {
		if (cursor == root || cursor == null) {
			throw new InvalidCursorPositionException();
		} // root is the highest position on tree
		cursor = cursor.getParent();
	}

	/**
	 * Method that creates a NetworkTree by reading a file
	 * @param filename
	 * Name of the file to be read
	 * @return
	 * NetworkTree that is created
	 * @throws FileNotFoundException
	 * If the file with the file name is not found
	 * @throws InvalidArgumentException
	 * If the cursor to child or add child methods are not successful (when creating the tree)
	 * @throws HoleInArrayException
	 * If adding the node creates a hole in the array (when creating the tree)
	 * @throws FullTreeException
	 * If the tree is full, cannot add the child node (when creating the tree)
	 * @throws NoTreeException
	 * If the cursor to child method does not work because there is no tree (when creating the tree)
	 * @throws CannotAddChildException
	 * If a child is being added at a Nintendo node (when creating the tree)
	 */
	public static NetworkTree readFromFile(String filename)
			throws FileNotFoundException, InvalidArgumentException, HoleInArrayException, FullTreeException, NoTreeException, CannotAddChildException {
		NetworkTree tree = new NetworkTree();
		File inputFile = new File(filename);
		Scanner fs = new Scanner(inputFile);
		int letterIndex = 0;
		boolean hasDash = false;
		String number = "";
		String name;
		tree.cursorToRoot();
		// this.cursorToRoot();
		while (fs.hasNextLine()) { // reads each line
			String line = fs.nextLine();
			hasDash = false;
			if (Character.isDigit(line.charAt(0))) {
				for (int i = 0; i < line.length(); i++) {
					if (Character.isLetter(line.charAt(i))) {
						letterIndex = i;
						number = line.substring(0, letterIndex);
						break;
					} else if (line.charAt(i) == '-') {
						hasDash = true;
						letterIndex = i + 1;
						number = line.substring(0, i);
						break;
					}
				}
				// String number = line.substring(0, letterIndex);
				// now have the name and the number
				name = line.substring(letterIndex);
				// add the node to the tree!
				int j;
				while (number.length() > 1) {
					j = number.charAt(0) - '0' - 1;
					tree.cursorToChild(j);
					number = number.substring(1);
				}
				tree.addChild(number.charAt(0) - '0' - 1, new NetworkNode(name, hasDash));
				tree.cursorToRoot();

			} else { // doesn't start with a number (root)
				tree.root = new NetworkNode(line, false); // raspberry pi
				tree.cursor = tree.root;
			}
		}
		return tree;
	}

	/**
	 * Method that writes the tree to a String format and saves it to a file
	 * @param tree
	 * The NetworkTree to be saved to the file
	 * @param filename
	 * The filename of the file that the tree will be saved to
	 * @throws IOException
	 * If file has an input or output error
	 */
	public static void writeToFile(NetworkTree tree, String filename) throws IOException {
		// generates text file that reflects the structure of the network tree
		// the format of the tree should match the format of the input file
		File outputFile = new File(filename);
		FileWriter fw = new FileWriter(outputFile, false);
		PrintWriter pw = new PrintWriter(fw);
		// pw.println(root.getName());
		// NetworkNode nodePtr = root;
		// now print the kids
		// if kids are not null, going to recursively call print node on the kid
		// indent add
		NetworkNode nodePtr = root; // node we are at
		String pre = "";
		pw.print(saveTree(nodePtr, pre));
		pw.flush();
		pw.close();
	}

	/**
	 * Method that creates a description of the NetworkTree with a String (recursive)
	 * @param node
	 * Node to be represented with a String
	 * @param prefix
	 * The String of the number before the node's name
	 * @return
	 */
	private static String saveTree(NetworkNode node, String prefix) {
		String result = "";
		// File outputFile = new File(filename);
		// FileWriter fw = new FileWriter(outputFile, false);
		// PrintWriter pw = new PrintWriter(fw);
		result += prefix;
		if (node.getIsNintendo()) {
			result += "-";
		}
		result += node.getName() + "\r\n"; // print it's node, then go to the node's kids
		for (int i = 0; i < node.getNumberOfChildren(); i++) {
			if (node != null) { // node!=null
				result += saveTree(node.getChildren()[i], prefix + (i + 1));
			}
		}
		// System.out.println(result);
		return result;
	}

	/**
	 * Method that prints the tree recursively
	 * @param node
	 * Node that will be printed
	 * @param indent
	 * Integer that counts how many indents should be printed for the node
	 */
	private void printTree(NetworkNode node, int indent) {
		// depth is how much space (tab) you move the node when printing
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}
		if (cursor == node) {
			System.out.print("->");
		} else if (node.getIsNintendo()) {
			System.out.print("-");
		} else if (!node.getIsNintendo()) {
			System.out.print("+");
		}
		System.out.println(node.getName()); // print it's node, then go to the node's kids
		for (int i = 0; i < node.getNumberOfChildren(); i++) {
			if (node != null) { // node!=null
				printTree(node.getChildren()[i], indent + 1);
			}
		}
	}

	/**
	 * Method that prints the NetworkNode tree
	 */
	public void printTree() {
		NetworkNode nodePtr = root; // node we are at
		if (root == null) { // the tree is empty, root is null
			System.out.println("There is no tree to print.");
		} else {
			int indent = 0;
			printTree(nodePtr, indent);
		}
	}

	// getters and setters
	public NetworkNode getCursor() {
		return cursor;
	}

	public void setCursor(NetworkNode n) {
		cursor = n;
	}

	public NetworkNode getRoot() {
		return root;
	}

	public void setRoot(NetworkNode r) { root = r; }
}
