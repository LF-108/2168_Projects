import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

// Your class. Notice how it has no generics.
// This is because we use generics when we have no idea what kind of data we are getting
// Here we know we are getting two pieces of data:  a string and a line number
public class IndexTree extends BinaryTree<String>{

	// This is your root
	// again, your root does not use generics because you know your nodes
	// hold strings, an int, and a list of integers
	private IndexNode root;

	// Make your constructor
	// It doesn't need to do anything
	public IndexTree() {
		super();
		root = null;		
	}
	// complete the methods below

	//As this class extends the BinaryTree class, please note that the required
	//methods are within that class
	public static void main(String[] args) {

		IndexTree index = new IndexTree();

		// add all the words to the tree
		String file_Name = "shakespeare.txt";

		// Scans in the file
		try {
			Scanner scanner = new Scanner(new File(file_Name));

			int line_Number = 0;

			// Reads in the file line by line
			while (scanner.hasNextLine()) {
				line_Number++;

				// We count by line rather than using scanner.next(), since there are multiple
				// words on the same line
				String next_Line = scanner.nextLine();

				// "\\s+" sets a space, multiple spaces, or other forms of space such as tab as
				// delimeters, so that we can split a line into individual words
				String[] words = next_Line.split("\\s+");

				// This is to trim away extraneous punctuation from the characters, so that the
				// same word
				// is not counted twice
				for (int i = 0; i < words.length; i++) {

					// We set words[i] to a version of itself that does not have any characters that
					// are not letters
					// We achieve this by using \\w which represents all letter characters, and ^,
					// to negate this and
					// look for non letter characters

					words[i] = words[i].replaceAll("[^\\w]", "");

					// Call add and add the word to the index				
					index.add(words[i], line_Number);
	
				}
			}

			scanner.close();
         
		//Throws an exception and prints an error message if the file is not found
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.out.println("Error: Unable to find file");
		}
		// print out the IndexTree
		index.printIndex();

		// test removing a word from the index
		if(index.contains("zed")){
		System.out.println("Removing zed from the index.");
		index.delete("zed");
		}
		else {
			System.out.println("This value is not in the index.");
		}
		
		if(index.contains("zodiac")){
			System.out.println("Removing zodiac from the index.");
			index.delete("zodiac");
		}
		else {
			System.out.println("This value is not in the index.");
		}
		
		if(index.contains("Money is Expensive.")) {
			System.out.println("Removing Money is Expensive. from the index.");
			index.delete("Money is Expensive.");
		}
		else {
			System.out.println("'Money is Expensive' is not in the index.");
		}
        
		//Prints IndexTree without removed indexes
		index.printIndex();

	}
}
