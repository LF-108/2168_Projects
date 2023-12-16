
//A class to represent each patient as an object. The fields are the columns provided by the CSV file
public class Patient implements Comparable<Patient> {

	protected String Patient_ID;
	protected String Age;
	protected String Gender;
	protected String Insurance_Type;
	protected String Chronic_Disease;
	protected String Mental_Health_Status;
	protected String Employment_Status;
	protected String Education_Level;
	protected String Transportation_Access;
	protected String Distance_from_Facility;
	protected String Area_Type;
	protected String Booking_Date;
	protected String Appointment_Date;
	protected String Appointment_Outcome;
	
	public Patient() {
		this.Patient_ID = "N/A";
		this.Age = "N/A";
		this.Gender = "N/A";
		this.Insurance_Type = "N/A";
		this.Chronic_Disease = "N/A";
		this.Mental_Health_Status = "N/A";
		this.Employment_Status = "N/A";
		this.Education_Level = "N/A";
		this.Transportation_Access = "N/A";
		this.Distance_from_Facility = "N/A";
		this.Area_Type = "N/A";
		this.Booking_Date = "N/A";
		this.Appointment_Date = "N/A";
		this.Appointment_Outcome = "N/A";
	}
	
	public Patient(String Patient_ID,String Age,String Gender,String Insurance_Type, String Chronic_Disease, 
	String Mental_Health_Status,String Employment_Status,String Education_Level,String Transportation_Access, 
	String Distance_from_Facility, String Area_Type,String Booking_Date,String Appointment_Date,String Appointment_Outcome) {
		this.Patient_ID = Patient_ID;
		this.Age = Age;
		this.Gender = Gender;
		this.Insurance_Type = Insurance_Type;
		this.Chronic_Disease = Chronic_Disease;
		this.Mental_Health_Status = Mental_Health_Status;
		this.Employment_Status = Employment_Status;
		this.Education_Level = Education_Level;
		this.Transportation_Access = Transportation_Access;
		this.Distance_from_Facility = Distance_from_Facility;
		this.Area_Type = Area_Type;
		this.Booking_Date = Booking_Date;
		this.Appointment_Date = Appointment_Date;
		this.Appointment_Outcome = Appointment_Outcome;
	}
	
	public String get_ID() {
		return this.Patient_ID;
	}
	
	@Override
	public int compareTo(Patient A) {
		return this.Patient_ID.compareTo(A.Patient_ID);
	}
}
