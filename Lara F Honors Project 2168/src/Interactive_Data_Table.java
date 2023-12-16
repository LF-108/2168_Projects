import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JTable;
import java.util.ArrayList;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Interactive_Data_Table {

	// A method that performs a binary search on an arraylist containing the patient
	// ids in order
	// to find if a given patient ID already exists in our system
	public static int binarySearch(ArrayList<String> Patient_IDs, int low, int high, String ID) {

		if (low <= high) {
			int mid = (low + high) / 2;
			if (Patient_IDs.get(mid).equals(ID)) {
				int return_val = mid;
				return return_val;
			} else if ((ID.compareTo(Patient_IDs.get(mid))) < 0) {
				int return_val = binarySearch(Patient_IDs, low, mid - 1, ID);
				return return_val;
			} else {
				int return_val = binarySearch(Patient_IDs, mid + 1, high, ID);
				return return_val;
			}
		} else {
			return -1;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		String table_name = "healthcare_dataset.csv";

		File health_care_data = new File(table_name);
		Scanner health_scanner = new Scanner(health_care_data);

		// Since we need an array for the columns of the JTable and a 2D Array for the
		// data
		// we will use this array for the columns
		String[] columns = { "Patient_ID", "Age", "Gender", "Insurance_Type", "Chronic_Disease", "Mental_Health_Status",
				"Employment_Status", "Education_Level", "Transportation_Access", "Distance_from_Facility", "Area_Type",
				"Booking_Date", "Appointment_Date", "Appointment_Outcome" };

		// Arraylists containing the patient objects and their IDs
		ArrayList<Patient> patients = new ArrayList<Patient>();
		ArrayList<String> IDs = new ArrayList<String>();

		Binary_Tree bt = new Binary_Tree();

		// We use this to skip the first row, which contains the headers that we have
		// already stored in
		// an array
		String skip_headers = health_scanner.nextLine();

		// Creates patient objects that will be the basis of our table from the given
		// csv file
		while (health_scanner.hasNextLine()) {
			Patient next_Patient = new Patient();
			String next_row = health_scanner.nextLine();
			String row[] = next_row.split(",");

			// Converts the first element of the split array into an int for patient ID
			next_Patient.Patient_ID = row[0];
			IDs.add(row[0]);

			// Converts the second element of the split array into a double for age
			if (row[1] == "") {
				next_Patient.Age = "N/A";
			} else {
				next_Patient.Age = row[1];
			}

			if (row[2] == "") {
				next_Patient.Gender = "N/A";
			} else {
				next_Patient.Gender = row[2];
			}

			if (row[3] == "") {
				next_Patient.Insurance_Type = "N/A";
			} else {
				next_Patient.Insurance_Type = row[3];
			}

			if (row[4] == "") {
				next_Patient.Chronic_Disease = "N/A";
			} else {
				next_Patient.Chronic_Disease = row[4];
			}

			if (row[5] == "") {
				next_Patient.Mental_Health_Status = "N/A";
			} else {
				next_Patient.Mental_Health_Status = row[5];
			}

			if (row[6] == "") {
				next_Patient.Employment_Status = "N/A";
			} else {
				next_Patient.Employment_Status = row[6];
			}

			if (row[7] == "") {
				next_Patient.Education_Level = "N/A";
			} else {
				next_Patient.Education_Level = row[7];
			}

			if (row[8] == "") {
				next_Patient.Transportation_Access = "N/A";
			} else {
				next_Patient.Transportation_Access = row[8];
			}

			if (row[9] == "") {
				next_Patient.Distance_from_Facility = "N/A";
			} else {
				next_Patient.Distance_from_Facility = row[9];
			}

			if (row[10] == "") {
				next_Patient.Area_Type = "N/A";
			} else {
				next_Patient.Area_Type = row[10];
			}

			if (row[11] == "") {
				next_Patient.Booking_Date = "N/A";
			} else {
				next_Patient.Booking_Date = row[11];
			}

			if (row[12] == "") {
				next_Patient.Appointment_Date = "N/A";
			} else {
				next_Patient.Appointment_Date = row[12];
			}

			if (row.length == 13) {
				next_Patient.Appointment_Outcome = "N/A";
			} else {
				next_Patient.Appointment_Outcome = row[13];
			}

			patients.add(next_Patient);
			bt.add(next_Patient);
		}

		// As the table is meant to hold all the attributes of each object, we make the
		// 2D array type Object
		// so it can hold the various data types of the different fields of the patient
		// object
		String[][] table_data = new String[patients.size()][14];

		for (int i = 0; i < patients.size(); i++) {
			table_data[i][0] = patients.get(i).Patient_ID;
			table_data[i][1] = patients.get(i).Age;
			table_data[i][2] = patients.get(i).Gender;
			table_data[i][3] = patients.get(i).Insurance_Type;
			table_data[i][4] = patients.get(i).Chronic_Disease;
			table_data[i][5] = patients.get(i).Mental_Health_Status;
			table_data[i][6] = patients.get(i).Employment_Status;
			table_data[i][7] = patients.get(i).Education_Level;
			table_data[i][8] = patients.get(i).Transportation_Access;
			table_data[i][9] = patients.get(i).Distance_from_Facility;
			table_data[i][10] = patients.get(i).Area_Type;
			table_data[i][11] = patients.get(i).Booking_Date;
			table_data[i][12] = patients.get(i).Appointment_Date;
			table_data[i][13] = patients.get(i).Appointment_Outcome;

		}

		// Creates the jtable and the model the table is built upong
		DefaultTableModel health_data = new DefaultTableModel(table_data, columns);
		JTable health_table = new JTable(health_data);

		try {
			health_table.print();
		} catch (PrinterException pe) {
			System.out.println("The table failed to print");
		}

		// Sets up the JFrame and other graphics components
		JFrame jf = new JFrame("Health Care Data");
		jf.setBounds(0, 0, 1500, 1500);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);

		JScrollPane js = new JScrollPane(health_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		js.setBounds(0, 0, 850, 850);
		health_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jf.getContentPane().add(js);

		// Allows us to sort each column in ascending and descending order
		TableRowSorter sorter = new TableRowSorter(health_data);
		health_table.setRowSorter(sorter);

		// Graphics Elements of the Search Bar
		JLabel search_button = new JLabel("Search (ID):");
		search_button.setBounds(880, 50, 131, 23);
		jf.getContentPane().add(search_button);

		JTextField search_Box = new JTextField();
		search_Box.setBounds(960, 50, 131, 23);
		jf.getContentPane().add(search_Box);
		search_Box.setColumns(14);

		// Searches for any entries that contain the text provided in the search bar,
		// based on patient ID
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String target = search_Box.getText().toString(); // get the search keyword
				TableRowSorter<DefaultTableModel> search = new TableRowSorter<>(health_data);
				health_table.setRowSorter(search);
				search.setRowFilter(RowFilter.regexFilter(target, 0));

			}
		});
		search.setBounds(960, 90, 131, 23);
		jf.getContentPane().add(search);

		// Graphics elements for the add button, which takes in attributes and an ID to
		// create a new patient
		// object and table row, given that the ID is not already present in our system
		JLabel Add = new JLabel("Add:");
		Add.setBounds(870, 120, 131, 23);
		jf.getContentPane().add(Add);

		JLabel ID_Label = new JLabel("ID:");
		ID_Label.setBounds(870, 140, 131, 23);
		jf.getContentPane().add(ID_Label);

		JTextField Add_ID = new JTextField();
		Add_ID.setBounds(1050, 140, 131, 23);
		jf.getContentPane().add(Add_ID);
		Add_ID.setColumns(14);

		JLabel Age_Label = new JLabel("Age:");
		Age_Label.setBounds(870, 160, 131, 23);
		jf.getContentPane().add(Age_Label);

		JTextField Add_Age = new JTextField();
		Add_Age.setBounds(1050, 160, 131, 23);
		jf.getContentPane().add(Add_Age);
		Add_Age.setColumns(14);

		JLabel Gender_Label = new JLabel("Gender:");
		Gender_Label.setBounds(870, 180, 131, 23);
		jf.getContentPane().add(Gender_Label);

		String[] gender_ops = { "Male", "Female", "N/A" };
		JComboBox<String> gender_drop_down = new JComboBox<String>(gender_ops);
		gender_drop_down.setBounds(1050, 180, 131, 23);
		jf.getContentPane().add(gender_drop_down);

		JLabel Insurance_Label = new JLabel("Insurance:");
		Insurance_Label.setBounds(870, 200, 131, 23);
		jf.getContentPane().add(Insurance_Label);

		String insurance_ops[] = { "Medicaid", "Medicare", "Private", "Uninsured", "N/A" };
		JComboBox<String> insurance_drop_down = new JComboBox<String>(insurance_ops);
		insurance_drop_down.setBounds(1050, 200, 131, 23);
		jf.getContentPane().add(insurance_drop_down);

		JLabel Chronic_Label = new JLabel("Chronic Disease:");
		Chronic_Label.setBounds(870, 220, 131, 23);
		jf.getContentPane().add(Chronic_Label);

		String chronic_ops[] = { "False", "True", "N/A" };
		JComboBox<String> chronic_drop_down = new JComboBox<String>(chronic_ops);
		chronic_drop_down.setBounds(1050, 220, 131, 23);
		jf.getContentPane().add(chronic_drop_down);

		JLabel Mental_Label = new JLabel("Mental Health:");
		Mental_Label.setBounds(870, 240, 131, 23);
		jf.getContentPane().add(Mental_Label);

		String mental_ops[] = { "False", "True", "N/A" };
		JComboBox<String> mental_drop_down = new JComboBox<String>(mental_ops);
		mental_drop_down.setBounds(1050, 240, 131, 23);
		jf.getContentPane().add(mental_drop_down);

		JLabel Employment_Label = new JLabel("Employment:");
		Employment_Label.setBounds(870, 260, 131, 23);
		jf.getContentPane().add(Employment_Label);

		String employed_ops[] = { "Employed", "Retired", "Student", "Unemployed", "N/A" };
		JComboBox<String> employed_drop_down = new JComboBox<String>(employed_ops);
		employed_drop_down.setBounds(1050, 260, 131, 23);
		jf.getContentPane().add(employed_drop_down);

		JLabel Education_Label = new JLabel("Education:");
		Education_Label.setBounds(870, 280, 131, 23);
		jf.getContentPane().add(Education_Label);

		String education_ops[] = { "Advanced Degree", "College", "High School", "N/A" };
		JComboBox<String> education_drop_down = new JComboBox<String>(education_ops);
		education_drop_down.setBounds(1050, 280, 131, 23);
		jf.getContentPane().add(education_drop_down);

		JLabel Transportation_Label = new JLabel("Transportation:");
		Transportation_Label.setBounds(870, 300, 131, 23);
		jf.getContentPane().add(Transportation_Label);

		String transportation_ops[] = { "False", "True", "N/A" };
		JComboBox<String> transportation_drop_down = new JComboBox<String>(transportation_ops);
		transportation_drop_down.setBounds(1050, 300, 131, 23);
		jf.getContentPane().add(transportation_drop_down);

		JLabel Distance_Label = new JLabel("Distance:");
		Distance_Label.setBounds(870, 320, 131, 23);
		jf.getContentPane().add(Distance_Label);

		JTextField Distance_From_Facility = new JTextField();
		Distance_From_Facility.setBounds(1050, 320, 131, 23);
		jf.getContentPane().add(Distance_From_Facility);
		Distance_From_Facility.setColumns(14);

		JLabel Area_Label = new JLabel("Area:");
		Area_Label.setBounds(870, 340, 131, 23);
		jf.getContentPane().add(Area_Label);

		String area_ops[] = { "Rural", "Suburban", "Urban", "N/A" };

		JComboBox<String> area_drop_down = new JComboBox<String>(area_ops);
		area_drop_down.setBounds(1050, 340, 131, 23);
		jf.getContentPane().add(area_drop_down);

		JLabel Booking_Label = new JLabel("Booking Date:");
		Booking_Label.setBounds(870, 360, 131, 23);
		jf.getContentPane().add(Booking_Label);

		JTextField Booking_Date = new JTextField();
		Booking_Date.setBounds(1050, 360, 131, 23);
		jf.getContentPane().add(Booking_Date);
		Booking_Date.setColumns(14);

		JLabel Appointment_Label = new JLabel("Appointment Date:");
		Appointment_Label.setBounds(870, 380, 131, 23);
		jf.getContentPane().add(Appointment_Label);

		JTextField Appointment_Date = new JTextField();
		Appointment_Date.setBounds(1050, 380, 131, 23);
		jf.getContentPane().add(Appointment_Date);
		Appointment_Date.setColumns(14);

		JLabel Outcome_Label = new JLabel("Appointment Outcome:");
		Outcome_Label.setBounds(870, 400, 160, 23);
		jf.getContentPane().add(Outcome_Label);

		String outcome_ops[] = { "No-Show", "Show", "N/A" };

		JComboBox<String> outcome_drop_down = new JComboBox<String>(outcome_ops);
		outcome_drop_down.setBounds(1050, 400, 131, 23);
		jf.getContentPane().add(outcome_drop_down);

		// Sorts the arrays so we add, delete, and update at the correct indexes
		Collections.sort(IDs);
		Collections.sort(patients);

		// Adds a new row to the jtable
		JButton Add_Button = new JButton("Add");
		Add_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ID = Add_ID.getText();
				String Age = Add_Age.getText();
				String gender = gender_drop_down.getSelectedItem().toString();
				String insurance = insurance_drop_down.getSelectedItem().toString();
				String chronic = chronic_drop_down.getSelectedItem().toString();
				String mental = mental_drop_down.getSelectedItem().toString();
				String employment = employed_drop_down.getSelectedItem().toString();
				String education = education_drop_down.getSelectedItem().toString();
				String transportation = transportation_drop_down.getSelectedItem().toString();
				String Distance = Distance_From_Facility.getText();
				String Area = area_drop_down.getSelectedItem().toString();
				String Booked_Date = Booking_Date.getText();
				String Appointment_Day = Appointment_Date.getText();
				String outcome = outcome_drop_down.getSelectedItem().toString();

				Patient to_add = new Patient(ID, Age, gender, insurance, chronic, mental, employment, education,
						transportation, Distance, Area, Booked_Date, Appointment_Day, outcome);

				// If the ID is already present, we should update rather than add a duplicate
				// row, so we inform the user
				if (IDs.contains(ID)) {
					JOptionPane.showMessageDialog(null, "ID already present in system. Please update instead.");
				}

				// If the ID is not present, we create a new object and add a corresponding row
				// to the table. We
				// convert the IDs to integers for proper comparison, as strings comparison does
				// not always put the
				// IDs in numerical order
				else {
					Integer ID_rep = Integer.valueOf(ID);
					Integer end_ID = Integer.valueOf(IDs.get(IDs.size() - 1));
					Integer beg_ID = Integer.valueOf(IDs.get(0));
					int index = 0;
					if (ID_rep.compareTo(end_ID) > 0) {
						patients.add(to_add);
						IDs.add(ID);
						index = IDs.size() - 1;
					}

					else if (ID_rep.compareTo(beg_ID) < 0) {
						patients.add(0, to_add);
						IDs.add(0, ID);
						index = 0;

					}

					else {
						for (int i = 0; i < IDs.size(); i++) {
							Integer current_ID = Integer.valueOf(IDs.get(i));
							if (ID_rep.compareTo(current_ID) < 0) {
								patients.add(i, to_add);
								IDs.add(i, ID);
								index = i;
								break;
							}
						}
					}

					Object row[] = { ID, Age, gender, insurance, chronic, mental, employment, education, transportation,
							Distance, Area, Booked_Date, Appointment_Day, outcome };

					// Adds the new row
					DefaultTableModel newModel = (DefaultTableModel) health_table.getModel();

					newModel.insertRow(index, row);

				}
			}
		});
		Add_Button.setBounds(960, 430, 131, 23);
		jf.getContentPane().add(Add_Button);

		// The graphical components of the update button
		JLabel Update = new JLabel("Update: (Highlight Entry)");
		Update.setBounds(870, 460, 160, 23);
		jf.getContentPane().add(Update);

		JLabel Update_Age_Label = new JLabel("Age:");
		Update_Age_Label.setBounds(870, 490, 131, 23);
		jf.getContentPane().add(Update_Age_Label);

		JTextField Update_Add_Age = new JTextField();
		Update_Add_Age.setBounds(1050, 490, 131, 23);
		jf.getContentPane().add(Update_Add_Age);
		Update_Add_Age.setColumns(14);

		JLabel Update_Gender_Label = new JLabel("Gender:");
		Update_Gender_Label.setBounds(870, 510, 131, 23);
		jf.getContentPane().add(Update_Gender_Label);

		JComboBox<String> update_gender_drop_down = new JComboBox<String>(gender_ops);
		update_gender_drop_down.setBounds(1050, 510, 131, 23);
		jf.getContentPane().add(update_gender_drop_down);

		JLabel Update_Insurance_Label = new JLabel("Insurance:");
		Update_Insurance_Label.setBounds(870, 530, 131, 23);
		jf.getContentPane().add(Update_Insurance_Label);

		JComboBox<String> update_insurance_drop_down = new JComboBox<String>(insurance_ops);
		update_insurance_drop_down.setBounds(1050, 530, 131, 23);
		jf.getContentPane().add(update_insurance_drop_down);

		JLabel Update_Chronic_Label = new JLabel("Chronic Disease:");
		Update_Chronic_Label.setBounds(870, 550, 131, 23);
		jf.getContentPane().add(Update_Chronic_Label);

		JComboBox<String> update_chronic_drop_down = new JComboBox<String>(chronic_ops);
		update_chronic_drop_down.setBounds(1050, 550, 131, 23);
		jf.getContentPane().add(update_chronic_drop_down);

		JLabel Update_Mental_Label = new JLabel("Mental Health:");
		Update_Mental_Label.setBounds(870, 570, 131, 23);
		jf.getContentPane().add(Update_Mental_Label);

		JComboBox<String> update_mental_drop_down = new JComboBox<String>(mental_ops);
		update_mental_drop_down.setBounds(1050, 570, 131, 23);
		jf.getContentPane().add(update_mental_drop_down);

		JLabel Update_Employment_Label = new JLabel("Employment:");
		Update_Employment_Label.setBounds(870, 590, 131, 23);
		jf.getContentPane().add(Update_Employment_Label);

		JComboBox<String> update_employed_drop_down = new JComboBox<String>(employed_ops);
		update_employed_drop_down.setBounds(1050, 590, 131, 23);
		jf.getContentPane().add(update_employed_drop_down);

		JLabel Update_Education_Label = new JLabel("Education:");
		Update_Education_Label.setBounds(870, 610, 131, 23);
		jf.getContentPane().add(Update_Education_Label);

		JComboBox<String> update_education_drop_down = new JComboBox<String>(education_ops);
		update_education_drop_down.setBounds(1050, 610, 131, 23);
		jf.getContentPane().add(update_education_drop_down);

		JLabel Update_Transportation_Label = new JLabel("Transportation:");
		Update_Transportation_Label.setBounds(870, 630, 131, 23);
		jf.getContentPane().add(Update_Transportation_Label);

		JComboBox<String> update_transportation_drop_down = new JComboBox<String>(transportation_ops);
		update_transportation_drop_down.setBounds(1050, 630, 131, 23);
		jf.getContentPane().add(update_transportation_drop_down);

		JLabel Update_Distance_Label = new JLabel("Distance:");
		Update_Distance_Label.setBounds(870, 650, 131, 23);
		jf.getContentPane().add(Update_Distance_Label);

		JTextField Update_Distance_From_Facility = new JTextField();
		Update_Distance_From_Facility.setBounds(1050, 650, 131, 23);
		jf.getContentPane().add(Update_Distance_From_Facility);
		Update_Distance_From_Facility.setColumns(14);

		JLabel Update_Area_Label = new JLabel("Area:");
		Update_Area_Label.setBounds(870, 670, 131, 23);
		jf.getContentPane().add(Update_Area_Label);

		JComboBox<String> update_area_drop_down = new JComboBox<String>(area_ops);
		update_area_drop_down.setBounds(1050, 670, 131, 23);
		jf.getContentPane().add(update_area_drop_down);

		JLabel Update_Booking_Label = new JLabel("Booking Date:");
		Update_Booking_Label.setBounds(870, 690, 131, 23);
		jf.getContentPane().add(Update_Booking_Label);

		JTextField Update_Booking_Date = new JTextField();
		Update_Booking_Date.setBounds(1050, 690, 131, 23);
		jf.getContentPane().add(Update_Booking_Date);
		Booking_Date.setColumns(14);

		JLabel Update_Appointment_Label = new JLabel("Appointment Date:");
		Update_Appointment_Label.setBounds(870, 710, 131, 23);
		jf.getContentPane().add(Update_Appointment_Label);

		JTextField Update_Appointment_Date = new JTextField();
		Update_Appointment_Date.setBounds(1050, 710, 131, 23);
		jf.getContentPane().add(Update_Appointment_Date);
		Update_Appointment_Date.setColumns(14);

		JLabel Update_Outcome_Label = new JLabel("Appointment Outcome:");
		Update_Outcome_Label.setBounds(870, 730, 160, 23);
		jf.getContentPane().add(Update_Outcome_Label);

		JComboBox<String> update_outcome_drop_down = new JComboBox<String>(outcome_ops);
		update_outcome_drop_down.setBounds(1050, 730, 131, 23);
		jf.getContentPane().add(update_outcome_drop_down);

		// The update button.
		JButton Update_Button = new JButton("Update");
		Update_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Gets the updated information from the text and dropdown buttons
				int row = health_table.convertRowIndexToModel(health_table.getSelectedRow());
				String ID = health_table.getValueAt(row, 0).toString();
				if (IDs.contains(ID)) {
					String Age = Update_Add_Age.getText().toString();
					String gender = update_gender_drop_down.getSelectedItem().toString();
					String insurance = update_insurance_drop_down.getSelectedItem().toString();
					String chronic = update_chronic_drop_down.getSelectedItem().toString();
					String mental = update_mental_drop_down.getSelectedItem().toString();
					String employment = update_employed_drop_down.getSelectedItem().toString();
					String education = update_education_drop_down.getSelectedItem().toString();
					String transportation = update_transportation_drop_down.getSelectedItem().toString();
					String Distance = Update_Distance_From_Facility.getText().toString();
					String Area = update_area_drop_down.getSelectedItem().toString();
					String Booked_Date = Update_Booking_Date.getText().toString();
					String Appointment_Day = Update_Appointment_Date.getText().toString();
					String outcome = update_outcome_drop_down.getSelectedItem().toString();

					// Updates the patient information in the patient arraylist
					int index = IDs.indexOf(ID);
					Patient updated_patient = new Patient(ID, Age, gender, insurance, chronic, mental, employment,
							education, transportation, Distance, Area, Booked_Date, Appointment_Day, outcome);
					patients.set(index, updated_patient);

					// Resets the values for each column at the selected row
					health_data.setValueAt(Age, row, 1);
					health_data.setValueAt(gender_drop_down.getSelectedItem(), row, 2);
					health_data.setValueAt(insurance, row, 3);
					health_data.setValueAt(chronic, row, 4);
					health_data.setValueAt(mental, row, 5);
					health_data.setValueAt(employment, row, 6);
					health_data.setValueAt(education, row, 7);
					health_data.setValueAt(transportation, row, 8);
					health_data.setValueAt(Distance, row, 9);
					health_data.setValueAt(Area, row, 10);
					health_data.setValueAt(Booked_Date, row, 11);
					health_data.setValueAt(Appointment_Day, row, 12);
					health_data.setValueAt(outcome, row, 13);
				}

			}

		});
		Update_Button.setBounds(960, 770, 131, 23);
		jf.getContentPane().add(Update_Button);

		JLabel Delete_Label = new JLabel("Delete (Highlight Entry):");
		Delete_Label.setBounds(870, 800, 160, 23);
		jf.getContentPane().add(Delete_Label);
		// Deletes a row from the jtable
		JButton Delete_Button = new JButton("Delete");
		Delete_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Finds the index of the row, removes it, and removes the corresponding array
				// elements
				int table_index = health_table.convertRowIndexToModel(health_table.getSelectedRow());
				health_data.removeRow(table_index);
				IDs.remove(table_index);
				patients.remove(table_index);

			}
		});
		Delete_Button.setBounds(960, 830, 131, 23);
		jf.getContentPane().add(Delete_Button);
	}

}
