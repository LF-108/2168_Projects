//The BinaryTree class. Implements a binary tree that was mean for searching and sorting purposes
import java.io.*;

public class Binary_Tree<E> implements Serializable {
	
	//The Node, which in this case will hold a patient object
	private static class Node<E>{
		Patient patient;
		Node<Patient>left;
		Node<Patient>right;
		String Patient_ID;
		
		public Node(Patient patient) {
			this.patient = patient;
			Patient_ID = patient.Patient_ID;
		}
		
	}
	
	protected Node<Patient> root;
    
	//Constructors for the Binary Tree
	public Binary_Tree() {
		this.root = null;
	}

	protected Binary_Tree(Node root) {
		this.root = root;
	}

	public Binary_Tree(Node root, Binary_Tree<E> right_Tree, Binary_Tree<E> left_Tree) {
		this.root = root;
		if (right_Tree != null) {
			right_Tree.root = this.root;
		} else {
			right_Tree.root = null;
		}
		if (left_Tree != null) {
			left_Tree.root = this.root;
		} else {
			left_Tree.root = null;
		}

	}

	// Gets the right subtree, in the form of a BinaryTree (not a string
	// representation)
	public Binary_Tree<Patient> get_Left_Subtree() {
		if (root != null && root.left != null) {
			Binary_Tree<Patient> ret_Left_Tree = new Binary_Tree<Patient>(root.left);
			return ret_Left_Tree;
		} else {
			return null;
		}
	}

	// Gets the right subtree, in the form of a BinaryTree (not a string
	// representation)
	public Binary_Tree<Patient> get_Right_Subtree() {
		if (root != null && root.right != null) {
			Binary_Tree<Patient> ret_Right_Tree = new Binary_Tree<Patient>(root.right);
			return ret_Right_Tree;
		} else {
			return null;
		}
	}

	// this is your wrapper method
	// it takes in two pieces of data rather than one
	// call your recursive add method
	public void add(Patient patient) {
		root = add(this.root, patient);

	}

	// your recursive method for add
	// Think about how this is slightly different the the regular add method
	// When you add the word to the index, if it already exists,
	// you want to add it to the IndexNode that already exists
	// otherwise make a new indexNode
	private Node<Patient> add(Node<Patient> root, Patient patient) {
		if (root == null) {
			root = new Node(patient);
			return root;
		}

		//Adds a new node to the left or right of our tree based on the patient's ID number
		else if (patient.Patient_ID.compareTo(root.Patient_ID) < 0) {
			root.left = add(root.left, patient);
			return root;
		}

		else if (patient.Patient_ID.compareTo(root.Patient_ID) > 0) {
			root.right = add(root.right,patient);
			return root;
		}
		
		return null;

	}

	// returns true if the word is in the index
	public boolean contains(String ID) {
		return contains(root, ID);
	}

	// A helper method for the contains method, following the pattern of add and
	// delete. Checks if the tree is empty or the root equals the word we
	// are looking for, and will search the left and right subtrees if this
	// is not the case
	private boolean contains(Node root, String ID) {
		if (root == null) {
			return false;
		} else if (ID.compareTo(root.Patient_ID) == 0) {
			return true;
		}

		// Searches the left or right subtree respectively depending on if the word
		// is before or after the root alphabetically
		else if (ID.compareTo(root.Patient_ID) < 0) {
			return contains(root.left, ID);
		} else {
			return contains(root.right, ID);
		}
	}

	// call your recursive method
	// use book as guide
	public void delete(Patient patient) {
		delete(this.root, patient);
	}

	// your recursive case
	// remove the word and all the entries for the word
	// This should be no different than the regular technique.
	private Node<Patient> delete(Node<Patient> root, Patient patient) {
		if (root == null) {
			return null;
		}

		// Checks whether to go down the left or right subtree to delete
		// the word based on compareTo
		else if (patient.Patient_ID.compareTo(root.Patient_ID) < 0) {
			root.left = delete(root.left, patient);
			return root;
		} else if (patient.Patient_ID.compareTo(root.Patient_ID) > 0) {
			root.right = delete(root.right, patient);
			return root;
		}

		// Item is at the node, so we need to shift the positions of the children
		else {

			// Shifts the right or left node respectively if the node with the
			// desired string has only one child
			if (root.left == null) {
				return root.right;
			} else if (root.right == null) {
				return root.left;
			}

			else {
				// If the node does not have a right grandchild from its left child, we shift
				// the left child
				if (root.left.right == null) {
					root.Patient_ID = root.left.Patient_ID;
					root.left = root.left.left;
					return root;
				} else {
					// If the above is not true, we search for the largest child of the present
					// nodes left child and shift the tree accordingly
					root.Patient_ID = findLargestChild(root.left);
					return root;
				}
			}
		}
	}

	// A helper method for the delete method, that finds the largest child to
	// replace the deleted IndexNode when we remove an IndexNode that is not a
	// leaf
	private String findLargestChild(Node root) {
		String largestVal;

		// Since the right side of the tree will have the largest values, we search
		// there first for the largest value
		// checking for which values on the right are leaves and therefore the largest
		// (in this case as the tree sorts the
		// values in order)
		if (root.right.right == null) {
			largestVal = root.right.Patient_ID;
			root.right = root.right.left;
			return largestVal;
		}
		// If we need to go further down the tree to find the largest value, we make a
		// recursive call to findLargestChild
		else {
			return findLargestChild(root.right);
		}
	}

	// Checks if a node on the tree is a leaf, i.e that it has no child nodes
	public boolean isLeaf() {
		// If the left and right nodes are null, then the node is a leaf
		if (root.left == null && root.right == null) {
			return true;
		} else {
			return false;
		}
	}

	// prints all the words in the index in an inorder traversal
	// To successfully print it out
	// this should print out each word followed by the number of occurrences and the
	// list of all occurrences
	// each word and its data gets its own line
	public void printIndex() {
		printIndex(root);
	}

	// A helper method for the public printIndex method
	private void printIndex(Node<Patient> root) {

		// If the tree is not empty, recursively prints the left subtree
		// prints the root, then prints the right subtree
		if (root != null) {
			printIndex(root.left);
			System.out.println(root.toString());
			printIndex(root.right);

		}
	}

	// The toString method, which makes a recursive call the the helper toString
	// below
	public String toString() {
		StringBuilder builder = new StringBuilder();
		toString(root, 1, builder);
		return builder.toString();
	}

	// A helper method for the toString() method, that builds a string
	// representation
	// of the tree recursively
	private void toString(Node<Patient> Inode, int depth, StringBuilder builder) {
		for (int i = 1; i < depth; i++) {
			builder.append(" ");
		}

		// Appends null or appends the value at the node, traversing
		// left and right as needed to further build the tree
		if (Inode == null) {
			builder.append("null\n");
		} else {
			builder.append(Inode.Patient_ID);
			builder.append("\n");
			toString(Inode.left, depth + 1, builder);
			toString(Inode.right, depth + 1, builder);
		}
	}
}