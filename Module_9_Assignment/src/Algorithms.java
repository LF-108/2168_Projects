import java.util.*;

public class Algorithms extends sort_3_class {

	// Insertion Sort
	public static <T extends Comparable<T>> Map<String, Integer> sort_1(T[] arr) {
		T temp;

		int exchanges = 0;
		int comparisons = 0;

		for (int i = 0; i < arr.length - 1; i++) {
			int j = i + 1;
			// While the element in front the target has an element behind it that is
			// greater than it, swap until it's in the correct position
			while (j > 0 && arr[j].compareTo(arr[j - 1]) < 0) {
				temp = arr[j];
				arr[j] = arr[j - 1];
				arr[j - 1] = temp;
				j--;
				exchanges += 2;
				comparisons++;

			}
		}

		int sort_1_results[] = { comparisons, exchanges };

		// Creates a map that holds the number of comparisons and exchanges
		// with these labels as keys
		Map<String, Integer> sort_1 = new HashMap<String, Integer>();
		sort_1.put("comparisons", sort_1_results[0]);
		sort_1.put("exchanges", sort_1_results[1]);
		return sort_1;
	}

	// Sorts the first, middle, and last values of the array to help choose a
	// partition
	// value - based off the textbook
	public static <T extends Comparable<T>> int[] pivot_chooser(T arr[], int first, int last) {
		T temp;
		int middle = (first + last) / 2;

		int comparisons = 0;
		int exchanges = 0;

		// Makes sure first, middle, and last are in order, starting by comparing the
		// middle value to the first and swapping if needed
		if (arr[middle].compareTo(arr[first]) < 0) {
			comparisons++;
			exchanges += 2;
			temp = arr[middle];
			arr[middle] = arr[first];
			arr[first] = temp;
		}

		if (arr[last].compareTo(arr[middle]) < 0) {
			comparisons++;
			exchanges += 2;
			temp = arr[last];
			arr[last] = arr[middle];
			arr[middle] = temp;
		}

		if (arr[middle].compareTo(arr[first]) < 0) {
			comparisons++;
			exchanges += 2;
			temp = arr[middle];
			arr[middle] = arr[first];
			arr[first] = temp;
		}
		int comps_and_exchanges[] = { comparisons, exchanges };
		return comps_and_exchanges;
	}

	// A helper method for quicksort that chooses the index of the pivot - based off
	// the textbook
	public static <T extends Comparable<T>> int[] partition(T arr[], int first, int last) {
		T temp;
		int up = first;
		int down = last;
		int middle = (first + last) / 2;

		// Puts the first, middle, and last element in ascending order
		int prelim_operations[] = pivot_chooser(arr, first, last);

		int comparisons = prelim_operations[0];
		int exchanges = prelim_operations[1];

		// Swaps first and middle element
		temp = arr[first];
		arr[first] = arr[middle];
		arr[middle] = temp;
		exchanges += 2;

		do {
			// Allocates elements to either the left or right of the first element
			while (up < last && arr[first].compareTo(arr[up]) >= 0) {
				up++;
				comparisons++;
			}

			while (arr[first].compareTo(arr[down]) < 0) {
				down--;
				comparisons++;
			}

			// swaps element at up and down if up is less than down before we terminate the
			// loop
			if (up < down) {
				temp = arr[up];
				arr[up] = arr[down];
				arr[down] = temp;
				comparisons++;
				exchanges += 2;
			}

		} while (up < down);
		comparisons++;

		// Swaps first element and the element and down, putting the pivot where it
		// belongs
		temp = arr[first];
		arr[first] = arr[down];
		arr[down] = temp;

		exchanges += 2;

		int quick_sort_info[] = { down, comparisons, exchanges };
		return quick_sort_info;
	}

	// Sorting algorithm for quicksort - based off the algorithm in the textbook
	public static <T extends Comparable<T>> int[] quick_sort(T arr[], int first, int last) {

		int pivot_index = 0;

		int comparisons = 0;
		int exchanges = 0;
		// Makes recursive calls to sort the left and right halves of the array
		// after the pivot until the array is sorted
		if (first < last) {
			int count_1[] = partition(arr, first, last);
			pivot_index = count_1[0];

			comparisons += count_1[1];
			exchanges += count_1[2];

			int[] count_2 = quick_sort(arr, first, pivot_index - 1);
			comparisons += count_2[0];
			exchanges += count_2[1];

			int[] count_3 = quick_sort(arr, pivot_index + 1, last);
			comparisons += count_3[0];
			exchanges += count_3[1];
		}

		int results[] = { comparisons, exchanges };
		return results;
	}

	// A helper method to run quicksort, so that the additional parameters
	// required to run the quicksort method do not give its identity away
	public static <T extends Comparable<T>> Map<String, Integer> sort_2(T arr[]) {
		int sort_2_results[] = quick_sort(arr, 0, arr.length - 1);
		Map<String, Integer> sort_2 = new HashMap<String, Integer>();
		sort_2.put("comparisons", sort_2_results[0]);
		sort_2.put("exchanges", sort_2_results[1]);
		return sort_2;
	}
	

	public static void main(String[] args) {

		// Basic Tests for sort_1 and sort_2
		Integer arr[] = { 4, -8, 9, 3, 2, 7, 6, 1, 13, 10, 5 };
		Integer arr_2[] = { 6, 7, 4, 3, 1, -90, 6, 4, -2 };
		Integer arr_3[] = {8,4,3};

		sort_1(arr_3);

		for (int i = 0; i < arr_3.length; i++) {
			System.out.println("" + arr_3[i]);
		}

		System.out.println("");

		sort_2(arr_2);

		for (int i = 0; i < arr_2.length; i++) {
			System.out.println("" + arr_2[i]);
		}
		
	}
}