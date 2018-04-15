package com.example.cynth.nintendonetworkmanager;

/**
 * NetworkNode holds the type of component being represented, an array of children and a string of text.
 * Class creates NetworkNode objects.
 * 
 * CSE 214-R12 Recitation, CSE 214-02 Lecture
 * @author Cynthia Lee
 * e-mail: cynthia.lee.2@stonybrook.edu
 * Stony Brook ID: 111737790
 */
public class NetworkNode {
	// type of component being represented, an array of children (null if this will
	// be a Control), and string for the text (null if this is a Container). Be sure
	// to include all getters and setters.
	private String name; // null if container
	private boolean isNintendo;
	private boolean isBroken; // default is false
	private NetworkNode parent;
	final int maxChildren = 9; // may have full node exception
	private NetworkNode[] children = new NetworkNode[maxChildren]; // should be no holes in the array, null if Control
	private int numberOfChildren = 0;

	/**
	 * NetworkNode constructor that creates a NetworkNode object assigning it a name and if it is a Nintendo node.
	 * @param n
	 * String that will be the name
	 * @param isItNintendo
	 * Boolean value that will represents if the node is a Nintendo node or not. True if Nintendo node, otherwise false.
	 */
	public NetworkNode(String n, boolean isItNintendo) {
		name = n;
		isNintendo = isItNintendo;
	}

	// all getters and setters
	public String getName() {
		return name;
	}

	public boolean getIsNintendo() {
		return isNintendo;
	}

	public boolean getIsBroken() {
		return isBroken;
	}

	public NetworkNode getParent() {
		return parent;
	}

	public NetworkNode[] getChildren() {
		return children;
	}

	public int getMaxChildrenNumber() {
		return maxChildren;
	}

	public int getNumberOfChildren() {
		/*
		for (int i = 0; i < maxChildren; i++) {
			if (children[i] == null) {
				break;
			} else {
				numberOfChildren++;
			}
		}
		*/
		return numberOfChildren;
	}
	
	public void setNumberOfChildren(int n) {
		numberOfChildren = n;
	}
	
	public void setName(String n) {
		name = n;
	}

	public void setIsNintendo(boolean n) {
		isNintendo = n;
	}

	public void setIsBroken(boolean b) {
		isBroken = b;
	}

	public void setParent(NetworkNode p) {
		parent = p;
	}

	public void setChildren(NetworkNode[] c) {
		children = c;
	}

	/*
	public NetworkNode removeChild(NetworkNode target) {
		int index = 0;
		NetworkNode removed = null;
		for (int i = 0; i < children.length; i++) {
			if (children[i] == target) {
				removed = children[i];
				children[i] = null;
				index = i;
			}
		}
		// int shiftIndex = index+1;
		// while (children[shiftIndex]!=null) {
		// children[shiftIndex-1] = children[shiftIndex];
		// shiftIndex++;
		// }
		for (int i = index; i < numberOfChildren - 1; i++) {
			children[i] = children[i+1];
		}
		children[numberOfChildren-1] = null;
		return removed;
	}

	public void setChild(int index, NetworkNode n) {
		this.children[index] = n;
	}
	*/
}
