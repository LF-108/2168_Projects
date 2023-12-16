import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Disaster_Planning {

	// The helper method, which allocates cities recursively
	public static boolean allocate(HashSet<String> to_visit, HashMap<String, HashSet<String>> roadNetwork,
			int numCities, HashSet<String> supplyLocations) {

		// We have visited every city, and we can return true
		if (to_visit.size() == 0) {
			return true;
		}

		// We have used up all our cities and failed to meet the above case, so we
		// return false
		if (numCities == 0) {
			return false;
		}

		// For every city, we check if we can solve the problem by allocating supplies
		// to the city itself or a neighbors
		for (String city : to_visit) {

			// Creats a set with the city and its neighbors to check
			Set<String> cities_and_neighbors = new HashSet<String>();
			cities_and_neighbors.add(city);
			if (roadNetwork.get(city) != null) {
				cities_and_neighbors.addAll(roadNetwork.get(city));
			}

			// For each city in the set, we add it to the set of supplied locations
			for (String adjacent_city : cities_and_neighbors) {
				supplyLocations.add(adjacent_city);

				// Creates a set that includes the remaining cities to cover (i.e every city
				// excluding the city we
				// are checking and its neighbors) to use a parameter for the recursive call
				HashSet<String> added_city = new HashSet<String>(to_visit);

				added_city.remove(adjacent_city);
				if (roadNetwork.get(adjacent_city) != null) {
					added_city.removeAll(roadNetwork.get(adjacent_city));
				}

				// Checks if by allocating to this city, we have solved the problem
				if (allocate(added_city, roadNetwork, numCities - 1, supplyLocations) == true) {
					return true;
				}

				// Removes the city from supplyLocations, taking back the move we made so we can
				// try a
				// different city
				supplyLocations.remove(adjacent_city);
			}
		}

		// If we cannot solve the problem with a city or any of its neighbors, that city
		// cannot be covered and we
		// return false
		return false;
	}

	// The main method, which calls on the helper method by providing it a string of
	// cities to be visited
	public static boolean allocateResources(HashMap<String, HashSet<String>> roadNetwork, int numCities,
			HashSet<String> supplyLocations) {

		HashSet<String> to_visit = new HashSet<String>();
		for (String city : roadNetwork.keySet()) {
			to_visit.add(city);
		}

		boolean can_allocate = allocate(to_visit, roadNetwork, numCities, supplyLocations);

		// If we can allocate supplies, we print out the list of cities with supplies
		if (can_allocate == true) {
			System.out.println("Is it true that we can allocate the resource to all the cities: " + can_allocate);
			System.out.println("Cities with supplies: " + supplyLocations);
			return can_allocate;
		}

		// If we can't allocate supplies, we print that this is not the case, and an
		// empty set of allocated cities
		else {
			System.out.println("Is it true that we can allocate the resource to all the cities: " + can_allocate);
			System.out.println("Cities with supplies: " + supplyLocations);
			return can_allocate;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		// Creates the scanner to scan in the filename and number of cities
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the name of the file containing the city data");
		String filename = scanner.nextLine();

		// Creates the file and scanner we will use to get the roadNetwork data
		File allocate_file = new File(filename);
		Scanner allocate_scanner = new Scanner(allocate_file);

		System.out.println("Please enter the maximum number of cities we can allocate supplies to");
		int num_Cities = scanner.nextInt();

		// The map and set that will hold a city and its neighbors and the cities that
		// have
		// supplies respectively
		HashMap<String, HashSet<String>> roadNetwork = new HashMap<String, HashSet<String>>();
		HashSet<String> supplyLocations = new HashSet<String>();

		while (allocate_scanner.hasNextLine()) {

			// Splits by : to get the city, and creates a new set to hold the neighbors of
			// that city
			String input = allocate_scanner.nextLine();
			String[] get_city = input.split(":");
			HashSet<String> neighbors = new HashSet<String>();

			// Gets the neighbors and adds them to the set
			String get_neighbors[] = get_city[1].split(",");
			for (int i = 0; i < get_neighbors.length; i++) {
				get_neighbors[i] = get_neighbors[i].trim();
			}
			neighbors.addAll(Arrays.asList(get_neighbors));

			// Adds the city and its neighbors to the map
			roadNetwork.put(get_city[0].trim(), neighbors);

		}

		// Checks if we can allocate to the cities, and prints out the supply locations
		boolean can_be_allocated = allocateResources(roadNetwork, num_Cities, supplyLocations);

	}
}
