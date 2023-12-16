import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sort_3_class {

	// The test method that will be used in the test class, returns a map
	// that contains the number of comparisons and number of exchanges as values
	public static <T extends Comparable<T>> Map<String, Integer> sort_3(T[] arr) {

		// Creates a map, and has the comparisons and exchanges as keys
		// with their respective values as values
		Map<String, Integer> sort_3 = new HashMap<String, Integer>();
		TS tim_sort = new TS(arr);
		int comp_data[] = tim_sort.sort();

		sort_3.put("comparisons", comp_data[0]);
		sort_3.put("exchanges", comp_data[1]);
		return sort_3;
	}

	// Please note that due to the need to return the number of comparisons and
	// exchanges
	// almost all of the methods below return an int[] that holds these value.
	// Almost all
	// methods have their own comparison and exchange counters, which will add
	// int[0] and
	// int[1] respectively of the returned int[] for each method executed within the
	// method
	private static class TS<T extends Comparable<T>> {

		// Class for the run object, which is simply a subsection
		// of the array where the elements are in ascending order
		private static class Run {
			int startIndex;
			int length;

			Run(int startIndex, int length) {
				this.startIndex = startIndex;
				this.length = length;
			}
		}

		// Contains a list of runs, which will be merged as we
		// complete the Timsort process
		List<Run> runStack;
		T[] arr;

		public TS(T[] arr) {
			this.arr = arr;
			runStack = new ArrayList<>();
		}

		// The main sort method
		public int[] sort() {
			int comparisons = 0;
			int exchanges = 0;
			int remaining_elements = arr.length;

			// If the array has one or no elements, it's already sorted
			if (remaining_elements < 2) {
				comparisons++;
				int prelim_sort_count[] = { comparisons, exchanges };

				return prelim_sort_count;
			}

			int lo = 0;
			do {
				// Gets the number of comparisons and exchanges made in the process
				// of finding the run
				int next_Run_results[] = nextRun(lo);

				// The actual length of the run, starting from index 0 the first time
				// and subsequent values of lo and we loop through the do-while loop
				int runLength = next_Run_results[2];

				// Adds the number of comparisons and exchanges made from
				// finding the run to the respective variable
				comparisons += next_Run_results[0];
				exchanges += next_Run_results[1];
				// Adds the found run to the stack
				runStack.add(new Run(lo, runLength));

				// Adds the number of comparisons and exchanges made during
				// mergeCollapse to the respective variables
				int merge_Results[] = mergeCollapse();
				comparisons += merge_Results[0];
				exchanges += merge_Results[1];
				// Adjusts the
				lo += runLength;
				remaining_elements -= runLength;

				// Accounts for the comparisons made to check if the do-while is over
				comparisons++;
			} while (remaining_elements != 0);

			// Adds to the respective variable the number of comparisons and exchanges made
			// during mergeForce()
			int merge_Force_results[] = mergeForce();
			comparisons += merge_Force_results[0];
			exchanges += merge_Force_results[1];
			int sort_count[] = { comparisons, exchanges };
			return sort_count;
		}

		public int[] nextRun(int lo) {
			int comparisons = 0;
			int exchanges = 0;

			if (lo == arr.length - 1) {
				comparisons++;
				int next_Run_results[] = { comparisons, exchanges, 1 };
				return next_Run_results;
			}

			int hi = lo + 1;

			// If descending sequences found, reverses them to make them
			// ascending. These conditional statements find the indexes
			// we need to perform the swaps at
			if (arr[hi - 1].compareTo(arr[hi]) <= 0) {
				comparisons++;
				while (hi < arr.length && arr[hi - 1].compareTo(arr[hi]) <= 0) {
					comparisons++;
					hi++;
				}
			}

			else {
				while (hi < arr.length && arr[hi - 1].compareTo(arr[hi]) > 0) {
					comparisons++;
					hi++;
				}

				// Reverses a run found in descending order into ascending order
				int swap_Results[] = swapRun(lo, hi - 1);
				comparisons += swap_Results[0];
				exchanges += swap_Results[1];
			}
			int next_Run[] = { comparisons, exchanges, hi - lo };

			return next_Run;
		}

		// Reverses a run found in descending order into ascending order
		public int[] swapRun(int lo, int hi) {
			int exchanges = 0;
			int comparisons = 0;
			while (lo < hi) {
				comparisons++;
				swap(lo++, hi--);
				exchanges += 2;
			}
			int swap_Results[] = { comparisons, exchanges };
			return swap_Results;
		}

		// Swaps elements
		public void swap(int x, int y) {
			T temp = arr[x];
			arr[x] = arr[y];
			arr[y] = temp;
		}

		public int[] mergeCollapse() {
			int comparisons = 0;
			int exchanges = 0;
			int merge_At_results[];

			while (runStack.size() > 1) {
				comparisons++;
				int top = runStack.size() - 1;

				// In order to maintain the balance merges needed for Timsort, checks the
				// lengths of the runs
				// and merges accordingly based on the size of the runs in relation to each
				// other
				if (top > 1
						&& runStack.get(top - 2).length <= (runStack.get(top - 1).length + runStack.get(top).length)) {
					comparisons++;
					if (runStack.get(top - 2).length < runStack.get(top).length) {
						comparisons++;
						merge_At_results = mergeAt(top - 2);
						comparisons += merge_At_results[0];
						exchanges += merge_At_results[1];
					} else {
						merge_At_results = mergeAt(top - 1);
						comparisons += merge_At_results[0];
						exchanges += merge_At_results[1];
					}
				}

				// If the run at top-1 is shorter than the run at top, merge them at top-1
				else if (runStack.get(top - 1).length <= runStack.get(top).length) {
					comparisons++;
					merge_At_results = mergeAt(top - 1);
					comparisons += merge_At_results[0];
					exchanges += merge_At_results[1];
				} else {
					break;
				}

			}
			int merge_Collapse_results[] = { comparisons, exchanges };
			return merge_Collapse_results;
		}

		// Merges all remaining runs after merge collapse has executed
		public int[] mergeForce() {
			int comparisons = 0;
			int exchanges = 0;
			int merge_At_data[];
			while (runStack.size() > 1) {
				comparisons++;
				int top = runStack.size() - 1;

				// Merges the elements at top and top-2.
				if (top > 1 && runStack.get(top - 2).length < runStack.get(top).length) {
					comparisons++;
					merge_At_data = mergeAt(top - 2);
					comparisons += merge_At_data[0];
					exchanges += merge_At_data[1];

				}

				// If top-2 is not available to merge, this means we only have two elements
				// in the stack and we merge them
				else {
					merge_At_data = mergeAt(top - 1);
					comparisons += merge_At_data[0];
					exchanges += merge_At_data[1];
				}

			}
			int merge_Force_results[] = { comparisons, exchanges };
			return merge_Force_results;
		}

		// Merges two runs that are adjacent to each other on the stack
		public int[] mergeAt(int i) {

			// Gets the start point and length of the runs to be merged
			int base1 = runStack.get(i).startIndex;
			int len1 = runStack.get(i).length;
			int base2 = runStack.get(i + 1).startIndex;
			int len2 = runStack.get(i + 1).length;

			int comparisons = 0;
			int exchanges = 0;

			// prepares space for the merged runs in the stack
			runStack.set(i, new Run(base1, len1 + len2));

			if (i == runStack.size() - 3) {
				comparisons++;
				runStack.set(i + 1, runStack.get(i + 2));
			}

			// Removes the last element of the stack, since we are merging two
			// stack elements
			runStack.remove(runStack.size() - 1);

			// Makes sure first element of run 1 greater than or
			// equal to run 2's first element
			int reduce_Left_data[] = reduceLeft(base1, base2);
			comparisons += reduce_Left_data[0];

			int newBase1 = reduce_Left_data[1];
			len1 = len1 - (newBase1 - base1);

			if (len1 > 0) {
				comparisons++;

				// Makes sure last element of run 2 less than or
				// equal to run 1's last element
				int reduce_Right_data[] = reduceRight(newBase1, len1, base2, len2);
				comparisons += reduce_Right_data[0];
				len2 = reduce_Right_data[1];

				// If both subarray to be merged have more than one elements, we need to
				// merge with the merge method
				if (len2 > 0) {
					comparisons++;

					T[] run1 = (T[]) (new Comparable[len1]);
					T[] run2 = (T[]) (new Comparable[len2]);

					// Copies modified run1 and run2 into arrays to be merged
					System.arraycopy(arr, newBase1, run1, 0, len1);
					System.arraycopy(arr, base2, run2, 0, len2);

					// Merges the runs
					int merge_data[] = merge(newBase1, run1, run2);

					comparisons += merge_data[0];
					exchanges += merge_data[1];
				}
			}

			int merge_At_results[] = { comparisons, exchanges };
			return merge_At_results;
		}

		// Makes sure first element of run 1 is greater than or
		// equal to run 2's first element, so that we can compare
		// elements reasonably evenly when merging
		int[] reduceLeft(int base1, int base2) {
			int comparisons = 0;
			T base2Start = arr[base2];
			while (arr[base1].compareTo(base2Start) < 0) {
				comparisons++;
				base1++;
			}

			int comp_data[] = { comparisons, base1 };
			return comp_data;
		}

		// Makes sure last element of run 2 less than or
		// equal to run 1's last element, so that we can compare
		// elements reasonably evenly when merging
		int[] reduceRight(int base1, int len1, int base2, int len2) {
			int comparisons = 0;
			T run1End = arr[base1 + len1 - 1];
			while (arr[base2 + len2 - 1].compareTo(run1End) > 0) {
				comparisons++;
				len2--;
			}
			int comp_data[] = { comparisons, len2 };
			return comp_data;
		}

		// Merges two runs to be inserted at destIndex on the runStack
		private int[] merge(int destIndex, T[] run1, T[] run2) {
			int i = 0;
			int j = 0;

			int comparisons = 0;
			int exchanges = 0;

			// Merges while we still have unmerged elements
			while (i < run1.length && j < run2.length) {
				comparisons++;
				if (run1[i].compareTo(run2[j]) < 0) {
					comparisons++;
					exchanges++;
					arr[destIndex++] = run1[i++];

				} else {
					exchanges++;
					arr[destIndex++] = run2[j++];
				}
			}

			// If we finish merging all elements of one list and the other still has some
			// left add those remaining elements
			while (i < run1.length) {
				comparisons++;
				exchanges++;
				arr[destIndex++] = run1[i++];
			}
			while (j < run2.length) {
				comparisons++;
				exchanges++;
				arr[destIndex++] = run2[j++];
			}

			int op_results[] = { comparisons, exchanges };
			return op_results;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Basic test for sort_3
		Integer arr[] = { 4386, 8848, 2894, 7961, 8575, 8325, 5373, 4418, 8450, 9477 };

		sort_3(arr);

		for (int i = 0; i < arr.length; i++) {
			System.out.println("" + arr[i]);
		}
	}

}
