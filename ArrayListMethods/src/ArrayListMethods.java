//Lara Fernandes
//9/21/23
//Contains several methods that process Lists, and create new ArrayLists as needed.
//Includes testing as well.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class ArrayListMethods{
	
	//Checks to see if all the elements in a list are unique
	public static <E> boolean unique(List<E> values) {
		boolean isUnique = true;
		
		//Compares using .equals and ==, to account for all types that the list might be
		for(int i = 0; i< values.size(); i++) {
			for(int j = i+1; j<values.size(); j++) {
				if(values.get(i).equals(values.get(j))||values.get(i)==values.get(j)) {
				isUnique = false;
				}
			}
		}
		
		return isUnique;
	}
	
	//Finds all elements in a list that are multiples of factor
	public static List<Integer> allMultiples(List<Integer> values , int factor){
		
		//Creates a new ArrayList to hold all the elements that are a multiple of factor
		ArrayList<Integer> allMultiples = new ArrayList<Integer>();
		
		//Checks each element of values to see if it's divisible by factor
		for(int i = 0; i<values.size(); i++) {
			if(values.get(i)%factor == 0) {
				allMultiples.add(values.get(i));
			}
		}
		return allMultiples;
	}
	
	//Finds all strings of a certain length
	public static List<String> allStringsOfSize(List<String> contents, int numOfChars){
		
		//Declares a new ArrayList that will contain all the strings of the given length
		ArrayList<String> allStringsOfSize = new ArrayList<String>();
		
		//Searches for the strings of a certain length and adds them to the ArrayList
		for(int i = 0; i<contents.size(); i++) {
			if(contents.get(i).length() == numOfChars) {
				allStringsOfSize.add(contents.get(i));
			}
		}
		return allStringsOfSize;
	}
	
	//Extends the comparable interface in order to make the lists comparable, so that they can be sorted
	//and then compared in their sorted forms to see if they are permutations
	public static <E extends Comparable<E>> boolean permutations(List<E> firstList, List<E>secondList){
		boolean isPermutation = true;
		
		//Automatically says that the lists are not permutations if the lengths are not the same
		if(firstList.size()!=secondList.size()) {
			isPermutation = false;
			return isPermutation;
		}
		
		//Sorts the list in ascending order, and compares to see if each element matches at each index.
		else {
			
		//Sorts the lists
		Collections.sort(firstList);
		Collections.sort(secondList);
		
		//Compares the lists and if it finds that they are not the same (since when sorted if two lists are
		//permutations they should be the exact same) sets isPermutation to false
		for(int i = 0; i<firstList.size(); i++) {
			if(firstList.get(i)!=secondList.get(i)||!(firstList.get(i).equals(secondList.get(i)))){
				isPermutation = false;
			}
		}
		}		
				
		return isPermutation;
	}
	
	//Splits a string into an ArrayList of sanitized tokens
	public static List<String> tokenize(String sentence){
		
		/*Sanitizes string with replace all, using the below to replace anything that is not
		a digit or number with "", then creates an array out of the sentence by splitting by 
		whitespace works with Unicode and ASCII characters, unlike other possible parameters
		for replaceAll()*/
		String new_Sentence = sentence.replaceAll("[^\\s\\p{L}0-9]", "");	
		String [] word_split = new_Sentence.split(" ", -1);
		
		//Converts the array into an ArrayList
		ArrayList<String>words = new ArrayList<String>(Arrays.asList(word_split));
		
		return words;
	}
	
	//Removes all of a certain element from ArrayList values
	public static <E> List<E> removeAll(List<E> values, E toRemove){
		
		//Removes toRemove from the List
		for(int i = values.size()-1; i>=0; i--) {
			if(values.get(i)== toRemove) {
				values.remove(values.get(i));
			}
		}
		return values;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
    //Checks allMultiples
    ArrayList<Integer>nums = new ArrayList<Integer>(Arrays.asList(3,4,5,6,9,7,8,-9, 9,33));
    System.out.println("Before allMultiples:" + nums);
    List<Integer>nums2 =allMultiples(nums, 3);
    System.out.println("After allMultiples: " + nums2 + "\n");
    
    
    //Checks allStringsOfSize
    ArrayList<String>words = new ArrayList<String>(Arrays.asList("dog", "cat", "fox", "elephant", "kitten", "pet", "puppy", "rat"));
    List<String>words_of_length = allStringsOfSize(words, 3);
    System.out.println("List before allStringsOfSize: " +words);
    System.out.println("List after allStringsOfSize: "+ words_of_length + "\n");
    
    //Checks tokenize
    String sentence = "This! @is# a ***sentence with( words() ,in ..it!";
    System.out.println("Sentence to be tokenized:" + sentence);
    List<String>tokens = tokenize(sentence);
    System.out.println("Tokens after tokenize was applied: " + tokens+ "\n");
    
    
    //Checks unique
    ArrayList<String>newList = new ArrayList<String>();
    newList.add("M");
    newList.add("O");
    newList.add("L");
    newList.add("J");
    
    
    boolean is_Nums_Unique = unique(nums);
    System.out.println(nums);
    System.out.println("Is nums unique?: " + is_Nums_Unique + "\n");
    
    boolean is_NewList_Unique = unique(newList);
    System.out.println(newList);
    System.out.println("Is newList unique?: " + is_NewList_Unique + "\n");
    
    
    //Checks permutations
    ArrayList<Integer>perms1 = new ArrayList<Integer>(Arrays.asList(1,1,2,3,4,5));
    ArrayList<Integer>perms2 = new ArrayList<Integer>(Arrays.asList(5,4,1,3,1,2));
    
    ArrayList<Integer>perms3 = new ArrayList<Integer>(Arrays.asList(1,1,1,1));
    ArrayList<Integer>perms4 = new ArrayList<Integer>(Arrays.asList(1,4,1,1));
    
    System.out.println("perms1: "+ perms1);
    System.out.println("perms2: "+ perms2);
    boolean is_perm_1_2 = permutations(perms1, perms2);
    System.out.println("Are these ArrayLists permutations?: "+ is_perm_1_2 + "\n");
    
    
    System.out.println("perms3: "+ perms3);
    System.out.println("perms4: "+ perms4);
    boolean is_perm_3_4 = permutations(perms3, perms4);
    System.out.println("Are these ArrayLists permutations?: "+ is_perm_3_4 + "\n");
    
    //Checks removeAll()
    List<Integer> int_test = new ArrayList<>(Arrays.asList(1,1,1,4,5,6,8,9,8,1,1,3));
    List<String>string_test = new ArrayList<>(Arrays.asList("A", "B", "H", "B", "O", "B" , "L", "B", "W", "W"));
    
    System.out.println("int_test before removeAll():" + int_test);
    removeAll(int_test,1);
    System.out.println("int_test after removeAll():" + int_test + "\n");
    System.out.println("string_test before removeAll():" + string_test);
    removeAll(string_test,"B");
    System.out.println("string_test after removeAll():" + string_test);
	}

}
