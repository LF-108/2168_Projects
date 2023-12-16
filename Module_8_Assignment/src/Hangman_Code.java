import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Random;

public class Hangman_Code {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		// As the game can end before the number of turns is used up, we will use a
		// boolean to keep track of whether the game has been completed. 
		// Since we are altering the map containing all the dictionary words directly, 
		// we create the map each time within the loop so that if the player wants to play 
		// another game, the map is repopulated with all the dictionary words
		boolean game_Running = true;

		// This integer will hold a random number between 1 and 10, which will pick the
		// strategy
		// we use for the game
		int game_method;
		while (game_Running == true) {

			// Random rand = new Random();
			game_method = (int) Math.floor(Math.random() * (10) + 1);

			String file_Name = "dictionary.txt";
			Scanner file_Scanner = new Scanner(new File(file_Name));

			// Creates a map with all the dictionary words and their lengths
			Map<String, Integer> word_lengths = new HashMap<String, Integer>();

			// Declares and instantiates word_list, which will contain all the words
			// of the size given by the user
			Set<String> word_list = new HashSet<String>();

			while (file_Scanner.hasNext()) {
				String to_Add = file_Scanner.next().toLowerCase();
				int length_to_Add = to_Add.length();
				word_lengths.put(to_Add, length_to_Add);
			}

			// A char that takes in user input for continuing to play games
			char play_again = ' ';

			// The length of the word to be guesses, inputted by the player
			int word_length = 0;

			// Gets the size of the word to be guessed from the player
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter the size of the hidden word");
			word_length = scanner.nextInt();

			// Checks that the dictionary contains words of the length the user requested,
			// or asks for a different length
			while (word_lengths.containsValue(word_length) == false) {
				System.out.println("There are no words of this length in the dictionary. Please enter another length.");
				word_length = scanner.nextInt();
			}

			// Gets the number of letter guesses the player has, making sure the number is
			// positive
			int guess_count = 0;
			System.out.println("Please enter the number of wrong guesses you have.");
			guess_count = scanner.nextInt();

			while (guess_count <= 0) {
				System.out.println("Please enter a positive number of guesses.");
				guess_count = scanner.nextInt();
			}

			// Creates a Collection from the word_length map of all words that are of the
			// size given by the user
			Set<Integer> words_of_size = new HashSet<Integer>();
			words_of_size = Collections.singleton(word_length);

			// Retains all the words that are of the given size in the map
			word_lengths.values().retainAll(words_of_size);

			// Sets word_list equal to the set created by the keys of word_lengths
			word_list = word_lengths.keySet();

			// A set that contains the letters guessed by the player so far
			Set<Character> guesses = new HashSet<Character>();

			// A variable that represents whether or not the word has been guessed yet
			boolean word_guessed = false;

			// Holds the character guesses of the user
			char guess = ' ';
			while (word_guessed == false && guess_count > 0) {

				// Converts the letter to lowercase, so that uppercase letters do not get
				// counted as unique characters
				System.out.println("Please enter a letter");
				guess = scanner.next().toLowerCase().charAt(0);

				// Adds the character to guesses if it has not been guessed already
				if (guesses.contains(guess) == false) {
					guesses.add(guess);
				}

				// Asks for a different character if the user already tried a letter
				else {
					System.out.println("That letter has already been entered. Please enter a different letter.");
					guess = scanner.next().toLowerCase().charAt(0);
					
					while (guesses.contains(guess)) {
						System.out.println("That letter has already been entered. Please enter a different letter.");
						guess = scanner.next().toLowerCase().charAt(0);
					}
				}

				// A map that has keys of string displays, with sets of string words that
				// fall into the same word family as display
				Map<String, Set<String>> word_families = new HashMap<String, Set<String>>();

				// A string that will contain the different word families as we loop through
				// to determine the largest one
				String letter_orders = "";

				// Contains the largest set of words from the different word families
				Set<String> largest_Set = new HashSet<String>();

				// Will use the strategy that eliminates all words with the letter a. Due to needing to
				// ensure that the list of words is properly maintained, much of the code is repeated
				// for both methods
				if (game_method >= 5) {

					// Since we cannot iterate through a set without an iterator, we use an enhanced
					// for loop to iterate
					// through the set in the arbitrary order it places elements in
					for (String word : word_list) {
						String display = "";
						// Displays the incomplete word created by the letter guesses
						for (int i = 0; i < word.length(); i++) {
							if (word.charAt(i) == guess) {
								display += "" + word.charAt(i);
							} else {
								display += "_";
							}
						}
						if (word_families.containsKey(display) == false) {

							// Creates a new word_family that meets the conditions of display
							word_families.put(display, new HashSet<String>());

							// Adds the word to the word family that falls under display
							word_families.get(display).add(word);
						}

					}

					Set<String> no_A_Set = new HashSet<String>();

					// Iterates through the word_families map, setting largest_Set to the largest
					// set value attached
					// to the word_families map keys
					for (String family : word_families.keySet()) {
						Iterator<String> itr = word_families.get(family).iterator();
						while (itr.hasNext()) {
							String word = itr.next();

							if (word.contains("a") == false)
								no_A_Set.add(word);
						}
					}

					// Sets our list of words to the largest set so we have the largest amount of
					// cheating options to work with
					word_list = no_A_Set;

					// Finds the largest set to cheat with based on the values of the word_families
					// map

					for (String family : word_families.keySet()) {
						for (String no_A : no_A_Set) {
							if (word_families.get(family).contains(no_A)) {
								if (word_families.get(family).size() > largest_Set.size())
									letter_orders = family;
								largest_Set = word_families.get(family);
							}
						}
					}
				}
				 // Will use the standard strategy of choosing words from the largest set
				 else {
					for (String word : word_list) {
						String display = "";
						// Displays the incomplete word created by the letter guesses
						for (int i = 0; i < word.length(); i++) {
							if (word.charAt(i) == guess) {
								display += "" + word.charAt(i);
							} else {
								display += "_";
							}
						}
						if (word_families.containsKey(display) == false) {

							// Creates a new word_family that meets the conditions of display
							word_families.put(display, new HashSet<String>());

							// Adds the word to the word family that falls under display
							word_families.get(display).add(word);
						}

					}

					// Iterates through the word_families map, setting largest_Set to the largest
					// set value attached
					// to the word_families map keys
					// String letter_orders = "";
					for (String family : word_families.keySet()) {
						if (word_families.get(family).size() > largest_Set.size()) {
							letter_orders = family;
							largest_Set = word_families.get(family);
						}
					}
				}
				// Sets our list of words to the largest set so we have the largest amount of
				// cheating options to work with
				word_list = largest_Set;

				// Prints out whether the character guessed is contained in the word, and
				// decrements
				// the number of guesses if this is not the case
				if (letter_orders.contains("" + guess)) {
					System.out.println("Correct guess");
				} else {
					System.out.println("Wrong guess");
					guess_count--;
				}

				// We set word_guessed to true, since its simpler to test for conditions that
				// make
				// victory false rather than true
				word_guessed = true;

				// As the size of word_list shrinks as we are given more information from the
				// player in the form of guesses
				// if the word_list is not narrowed down to one word by the end of the turn,
				// this means the player cannot
				// have won
				if (word_list.size() != 1) {
					word_guessed = false;
				}

				// Checks if we have guessed all the characters in word_list, otherwise we
				// cannot have won
				else {
					for (String word : word_list) {
						for (int i = 0; i < word.length(); i++) {
							if (!guesses.contains(word.charAt(i))) {
								word_guessed = false;
								break;
							}
						}
					}
				}

				// Checks if we have used up all our guesses or if we have guessed the word
				// based on the
				// conditional statements above
				if (guess_count == 0 || word_guessed) {
					if (guess_count == 0) {
						System.out.println("\nSorry but you are out of guesses");
					} else {
						System.out.println("\nYou win!");
					}
				}
			}

			// Converts the string set word_list to an array, and gets the toString of the
			// first (and only) element
			System.out.println("The word was " + word_list.toArray()[0].toString());

			// Asks the user if they would like to keep playing
			System.out.println("\nWould you like to play again? Enter Y for yes or N for no:");
			play_again = scanner.next().charAt(0);

			while (!(play_again == 'Y' || play_again == 'y' || play_again == 'N' || play_again == 'n')) {
				System.out.println("Would you like to play again? Enter Y for yes or N for no:");
				play_again = scanner.next().charAt(0);
			}

			// Ends the game if N entered
			if (play_again == 'N' || play_again == 'n') {
				game_Running = false;
				break;

			}
		}
	}
}
