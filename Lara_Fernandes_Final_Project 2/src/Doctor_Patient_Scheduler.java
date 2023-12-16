import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Doctor_Patient_Scheduler {
	private static boolean can_schedule(Map<String, Integer> doctors, Map<String, Integer> patients,
			HashMap<String, HashSet<String>> schedule) {

		if (patients.isEmpty() == true) {
			return true;
		}

		// Gets the next patient. We will be matching the patients to a doctor
		String current_patient = patients.keySet().iterator().next();

		// Gets the time the patient needs with a doctor
		int time_needed = patients.get(current_patient);

		// Iterates through the doctors to match a patient to them
		for (String doctor : doctors.keySet()) {
			int doctor_hours = doctors.get(doctor);

			if (doctor_hours >= time_needed) {
				int new_time = doctor_hours - time_needed;
				doctors.put(doctor, new_time);

				// If we already have a set containing 1 or more patients, we add to that set
				// else we create a new set and add our first assigned patient
				HashSet<String> assigned_patients = schedule.getOrDefault(doctor, new HashSet<String>());
				assigned_patients.add(current_patient);

				schedule.put(doctor, assigned_patients);

				patients.remove(current_patient);

				if (can_schedule(doctors, patients, schedule) == true) {
					return true;
				}

				else {
					int old_time = new_time + time_needed;
					doctors.put(doctor, old_time);
					assigned_patients.remove(current_patient);
					schedule.put(doctor, assigned_patients);
					patients.put(current_patient, time_needed);

				}
			}
		}

		return false;
	}

	public static boolean schedulePatients(Map<String, Integer> doctors, Map<String, Integer> patients,
			HashMap<String, HashSet<String>> schedule) {

		// As we alter the maps during the recursive allocations process, we will put
		// them into
		// these Maps before alteration so that we have the original values for display
		Map<String, Integer> temp_doctors = new HashMap<String, Integer>(doctors);
		Map<String, Integer> temp_patients = new HashMap<String, Integer>(patients);

		// Checks if we can schedule the patients to the doctors
		if (can_schedule(doctors, patients, schedule) == true) {

			// We will print out the schedule in reverse alphabetical order by doctor
			ArrayList<String> sorted_keys = new ArrayList<String>(schedule.keySet());
			Collections.sort(sorted_keys, Collections.reverseOrder());
			// Prints out the schedule
			for (String doctor : sorted_keys) {

				// Keeps a list of patients and the total hours a doctor spends seeing them
				String patient_list = "";
				int total_hours = 0;
				int count = 0;

				// Checks if the value at key doctor in schedule is NULL before proceeding
				if (schedule.get(doctor) != null) {

					// Gets the size of the schedule. Using count and this value together we
					// make sure not to print a comma after the last patient
					int size = schedule.get(doctor).size();

					// Adds to the patient list and total hours of a doctor for display
					for (String patient : schedule.get(doctor)) {
						if (count == size - 1) {
							patient_list += " " + patient;
						}

						else {
							patient_list += " " + patient + ",";
						}

						if (temp_patients.get(patient) != null) {
							total_hours += temp_patients.get(patient);
						}
						count++;
					}
					System.out.println("Doctor " + doctor + "(" + temp_doctors.get(doctor)
							+ " hours free) sees patients" + patient_list + "(" + total_hours + " hours total)");
				}

			}
			return true;
		}

		else {
			System.out.println("Unable to schedule all patients to a doctor");
			return false;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the name of the file containing the patient data");
		String filename = scanner.nextLine();

		File schedule_file = new File(filename);
		Scanner schedule_scanner = new Scanner(schedule_file);

		// The maps that will hold the doctor and patient info respectively, as well as
		// the map that will display
		// the possible patient-doctor combos if this distribution is possible
		Map<String, Integer> doctors = new HashMap<String, Integer>();
		Map<String, Integer> patients = new HashMap<String, Integer>();
		HashMap<String, HashSet<String>> schedule = new HashMap<String, HashSet<String>>();

		while (schedule_scanner.hasNextLine()) {
			String to_process = schedule_scanner.nextLine();

			// We split each line into words. With the way the file is formatted, the first
			// word will always
			// tell us if the information on that line pertains to a doctor or a patient,
			// and the last how many hours
			// they have
			String tokens[] = to_process.split(" ");
			int hours;
			if (tokens[0].equals("Doctor")) {
				// Removes the colon after the doctor's last name so we can add it to the map
				tokens[2] = tokens[2].substring(0, tokens[2].length() - 1);

				// Converts the last token to an integer set as the value
				hours = Integer.parseInt(tokens[3]);
				doctors.put(tokens[2], hours);
			}

			else if (tokens[0].equals("Patient")) {
				if (tokens.length == 3) {
					// Removes the colon after the patients's last name so we can add it to the map
					tokens[1] = tokens[1].substring(0, tokens[1].length() - 1);

					// Converts the last token to an integer set as the value
					hours = Integer.parseInt(tokens[2]);
					patients.put(tokens[1], hours);
				}

				// Accounts for people with split names, such as St. Martin, Mary Anne, etc.
				// Given most last names are one word or hyphenated, the above accounts the the
				// majority of names
				else if (tokens.length == 4) {
					// Removes the colon after the patients's last name so we can add it to the map
					tokens[2] = tokens[2].substring(0, tokens[2].length() - 1);

					// Converts the last token to an integer set as the value
					hours = Integer.parseInt(tokens[3]);
					String name = tokens[1] + " " + tokens[2];
					patients.put(name, hours);
				}
			}
		}
		// Calls the method and prints out the schedule
		boolean can_schedule = schedulePatients(doctors, patients, schedule);
	}

}
