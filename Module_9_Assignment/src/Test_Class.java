import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.PrintStream;
import java.util.Random;
public class Test_Class extends Algorithms {

	//The test cases extend the algorithms class to call the methods and return a map
	//of comparisons and exchanges
	public static <T extends Comparable <T>> Map<String, Integer> test_1(T []arr){
		return sort_1(arr);
	}
	
	public static <T extends Comparable <T>> Map<String, Integer> test_2(T []arr) {
		return sort_2(arr);
	}
	
    public static <T extends Comparable <T>> Map<String, Integer> test_3(T []arr) {
	   return sort_3(arr);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String run_Time_File = "run_time.csv";
		String comparison_File = "comparisons.csv";
		String exchanges_File = "exchanges.csv";
		try {
			//Creates the files and writes the initial table headings	
			FileWriter time_Writer = new FileWriter("run_time.csv", true);
			time_Writer.append(", Sort 1, Sort 2, Sort 3"+System.lineSeparator());
			time_Writer.close();
			
			FileWriter comparison_Writer = new FileWriter("comparisons.csv", true);
			comparison_Writer.append(", Sort 1, Sort 2, Sort 3"+ System.lineSeparator());
			comparison_Writer.close();
						
			FileWriter exchanges_Writer = new FileWriter("exchanges.csv", true);	
			exchanges_Writer.append(", Sort 1, Sort 2, Sort 3"+ System.lineSeparator());
			exchanges_Writer.close();
			
			//Writes the time, comparison count, and exchange count to the respective files
			for(int i = 10; i<10000; i *= 2) {
				Integer arr[] = new Integer[i];
				
				Random random = new Random();
				for(int j = 0; j<arr.length; j++) {
					arr[j] = random.nextInt(10000);
				}
				
				Integer arr_2[] = arr.clone();
				Integer arr_3[] = arr.clone();
				
				//Gets the run time for each test case
				long startTime = System.nanoTime();
				Map<String, Integer> sort_1 = test_1(arr);
				long sort_1_Time = System.nanoTime() - startTime;
				
				long startTime_2 = System.nanoTime();
				Map<String, Integer> sort_2 = test_2(arr_2);
				long sort_2_Time = System.nanoTime() - startTime_2;
				
				long startTime_3 = System.nanoTime();
				Map<String, Integer> sort_3 = test_3(arr_3);
				long sort_3_Time = System.nanoTime() - startTime_3;
				
				time_Writer = new FileWriter("run_time.csv", true);
				time_Writer.append(i + ", " + sort_1_Time + ", "+sort_2_Time+ ", "+ sort_3_Time + System.lineSeparator());
				time_Writer.close();
				
				comparison_Writer = new FileWriter("comparisons.csv", true);
				comparison_Writer.append(i + ", " + +sort_1.get("comparisons") + ", "+sort_2.get("comparisons") +", "+ sort_3.get("comparisons") +System.lineSeparator());
				comparison_Writer.close();
				
				exchanges_Writer = new FileWriter("exchanges.csv", true);
				exchanges_Writer.append(i + ", " + sort_1.get("exchanges") + ", "+sort_2.get("exchanges")+ ", "+ sort_3.get("exchanges") + System.lineSeparator());
				exchanges_Writer.close();
			}
			}catch(IOException e) {
				System.out.println("Error");
				e.printStackTrace();
			}
		
	    
	}
    
    
    }


