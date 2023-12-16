
import java.util.List;
import java.util.ArrayList;

public class IndexNode  {

	String word;
	int occurences;
	List<Integer> list;
	
	IndexNode left;
	IndexNode right;
	
	
	// Constructors
	// Constructor should take in a word and a line number
	// it should initialize the list and set occurrences to 1
	public IndexNode(String word, int line_number) {
		this.word = word;
		occurences = 1;
		list = new ArrayList<Integer>();
		list.add(line_number);
	}
	
	//Adds an occurence of a word at a line to the list of lines that word occurs at
	public void occurence_Add(int line_number) {
		occurences++;
		list.add(line_number);
	}
	
	// Complete This
	// return the word, the number of occurrences, and the lines it appears on.
	// string must be one line	
	public String toString(){
		String line_nums = "";
		
		//Creates a string with all the lines that a word occurs at to add to the final toString()
		for(int i = 0; i<list.size()-1; i++) {
			line_nums += list.get(i)+ ", ";
		}
		line_nums += list.get(list.size()-1);
		return "Word: "+ word+ ", Number of occurences: "+ occurences + ", Lines appeared on: "+ line_nums + "\n";
	}
	
	
	
}
