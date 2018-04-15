package com.example.cynth.nintendonetworkmanager; /**
 * Driver class for the NetworkTree. Provides a user interface to manipulate the tree
 * 
 * CSE 214-R12 Recitation, CSE 214-02 Lecture
 * @author Cynthia Lee
 * e-mail: cynthia.lee.2@stonybrook.edu
 * Stony Brook ID: 111737790
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class NintendoNetwork {
	// takes in a text file
	// generates a NetworkTree and provides an interface for a user to manipulate
	// the tree

	/**
	 * Prompts the user with a menu to manipulate the tree
	 */
	public static void main(String[] args) {
		NetworkTree tree = new NetworkTree();
		Scanner input = new Scanner(System.in);
		String option;
		NetworkNode cut = null;
		System.out.println("Welcome to the Nintendo Network Manager.\n");
		/*
		 * System.out.
		 * println("Menu:\nL) Load from file\nC) Move cursor to a child node\nR) Move cursor to root\n"
		 * +
		 * "U) Move cursor up to parent\nA) Add a child\nX) Remove/Cut Cursor and its subtree\nV) Paste Cursor and its subtree"
		 * +
		 * "\nS) Save to file\nM) Cursor to root of minimal subtree containing all faults\nB) Mark cursor as broken/fixed\nQ) Quit"
		 * );
		 */
		System.out.println(
				"Menu:\nL) Load from file\nP) Print Tree\nC) Move cursor to a child node\nR) Move cursor to root\n"
						+ "U) Move cursor up to parent\nA) Add a child\nX) Remove/Cut Cursor and its subtree\nV) Paste Cursor and its subtree"
						+ "\nS) Save to file\nQ) Quit\n");
		do {
			System.out.print("Please enter an option: ");
			option = input.nextLine();
			if (option.equalsIgnoreCase("L")) {
				System.out.print("Please enter filename: ");
				String fileName = input.nextLine();
				try {
					tree = tree.readFromFile(fileName);
					System.out.println(fileName + "loaded");
				} catch (FileNotFoundException e) {
					System.out.println(fileName + " not found.");
				} catch (CannotAddChildException e) {
					System.out.println("Cannot add a child at a Nintendo node.");
				} catch (InvalidArgumentException e) {
					System.out.println("Could not reach child.");
				} catch (HoleInArrayException e) {
					System.out.println("Could not add child correctly. Adding would create a hole in the array.");
				} catch (FullTreeException e) {
					System.out.println("The tree is full, could not add child correctly.");
				} catch (NoTreeException e) {
					System.out.println("There is no tree.");
				}
				System.out.println();
			} else if (option.equalsIgnoreCase("P")) {
				System.out.println();
				tree.printTree();
				System.out.println();
			} else if (option.equalsIgnoreCase("C")) {
				System.out.print("Please enter an index: ");
				int i = Integer.parseInt(input.nextLine());
				try {
					tree.cursorToChild(i - 1);
					System.out.println("Cursor moved to " + tree.getCursor().getName());
				} catch (InvalidArgumentException e) {
					System.out.println("Invalid index was entered.");
				} catch (NoTreeException e) {
					System.out.println("No tree, no children to move cursor to.");
				}
				System.out.println();
			} else if (option.equalsIgnoreCase("A")) {
				// boolean emptyTree = false;
				int i = 0;
				if (tree.getCursor() == null) { // if the tree is empty, does not prompt for child index number
					// tree is empty so
					// emptyTree = true;
					i = 1;

				} else {
					System.out.print("Please enter an index: ");
					i = Integer.parseInt(input.nextLine());
				}
				System.out.print("Please enter device name: ");
				String name = input.nextLine();
				System.out.print("Is this Nintendo (y/n): ");
				String isNintendo = input.nextLine();
				try {
					if (isNintendo.equalsIgnoreCase("Y")) {
						NetworkNode created = new NetworkNode(name, true);
						// if (!emptyTree) {
						tree.addChild(i - 1, created);
						// } else {

						// }
						System.out.println("Nintendo added.");
						// move cursor to the new child added
						tree.setCursor(created);
					} else if (isNintendo.equalsIgnoreCase("N")) {
						NetworkNode created = new NetworkNode(name, false);
						tree.addChild(i - 1, created);
						System.out.println("Non-Nintendo added.");
						// move cursor to the new child added
						tree.setCursor(created);
					} else {
						System.out.println("Invalid choice to make device Nintendo or not.");
					}
				} catch (CannotAddChildException e) {
					System.out.println("Cannot add a child at a Nintendo node.");
				} catch (HoleInArrayException e) {
					System.out.println("The index entered is invalid as it would make a hole in the child array.");
				} catch (InvalidArgumentException e) {
					System.out.println("Cannot add a child with an invalid index.");
				} catch (FullTreeException e) {
					System.out.println("Cannot add a child if the parent's child array is full.");
				}
				System.out.println();
			} else if (option.equalsIgnoreCase("X")) {
				try {
					cut = tree.cutCursor();
					if (tree.getRoot() == null) {
						System.out.println(cut.getName() + " cut, the tree is cleared.\n");
					} else {
						System.out.println(cut.getName() + " cut, cursor is at " + tree.getCursor().getName() + "\n");
					}
				} catch (NoTreeException e) {
					System.out.println("No tree, no nodes to cut.\n");
				}
			} else if (option.equalsIgnoreCase("V")) {
				if (cut == null) {
					System.out.println("Cannot paste, nothing to paste.\n");
				} else {
					System.out.print("Please enter an index: ");
					int i = Integer.parseInt(input.nextLine());
					try {
						// tree.addChild(i - 1, cut);
						if (tree.getRoot() != null) { // tree is not empty, do this
							tree.addChild(i - 1, cut);
							System.out.println(cut.getName() + " pasted as child as of " + cut.getParent().getName());
							cut = null;
						} else { // tree empty cause root is null
							if (i==1) { // make sure the index is valid when the tree is empty
								// index should be 1, it is the only choice?
								tree.addChild(i - 1, cut);
								System.out.println(cut.getName() + " is pasted.");
								tree.setCursor(cut);
								cut = null;
							} else {
								System.out.println("Cannot paste at that index. Since the tree is empty, should paste at index 1.");
							}
						}
					} catch (CannotAddChildException e) {
						System.out.println("Cannot add a child at a Nintendo node.");
					} catch (HoleInArrayException e) {
						System.out.println("The index entered is invalid as it would make a hole in the child array.");
					} catch (InvalidArgumentException e) {
						System.out.println("Cannot add a child with an invalid index.");
					} catch (FullTreeException e) {
						System.out.println("Cannot add a child if the parent's child array is full.");
					}
					System.out.println();
				}
			} else if (option.equalsIgnoreCase("S")) {
				System.out.print("Please enter a filename: ");
				String fileName = input.nextLine();
				try {
					tree.writeToFile(tree, fileName);
					System.out.println("File saved.\n");
				} catch (IOException e) {
					System.out.println("File has IOException.\n");
				}
			} else if (option.equalsIgnoreCase("U")) {
				try {
					tree.cursorToParent();
					System.out.println("Cursor moved to " + tree.getCursor().getName());
				} catch (InvalidCursorPositionException e) {
					System.out.println("Can't move the cursor up, will be at an invalid position.");
				}
				System.out.println();
			} else if (option.equalsIgnoreCase("R")) {
				tree.cursorToRoot();
				if (tree.getRoot() == null) {
					System.out.println("There is no tree.\n");
				} else {
					System.out.println("Cursor moved to " + tree.getCursor().getName() + "\n");
				}
			} else if (!option.equalsIgnoreCase("Q")) {
				System.out.println("Invalid choice! Please enter again.");
				System.out.println();
			}

		} while (!option.equalsIgnoreCase("Q"));
		System.out.println("Make like a tree and leave!");
	}
}
