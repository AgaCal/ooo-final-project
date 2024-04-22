import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	//This method is written to check that all lectures/labs are correctly transferred to an
	//ArrayList of type Lecture
	private static void printClasses ( ArrayList<Lecture> listOfLectures ) {
		System.out.println("\n_________________________________\n");
		System.out.print("Printing everything from the ArrayList....");
		System.out.println("\n_________________________________\n");
	
		for (Lecture lec: listOfLectures) {
			System.out.print(lec);
		}
	}

	//____________________________
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Lecture> listOfLectures = new ArrayList<>();
		
		Scanner scanner = new Scanner(new File("lec.txt"));
		
		String line = "";
		String[] lectureItems;
		Lecture lecture=null;
		
		boolean skipLine = false;
		boolean oneMorePass = false;
		
		while (scanner.hasNextLine() || oneMorePass ) {
			if (skipLine == false) {
				line = scanner.nextLine();
			}
			oneMorePass = false;
		
			lectureItems = line.split(",");
			//--------------------------------------------------------------------
		
			if (lectureItems.length > 2) {// It must be F2F, Mixed or Online lecture
				LectureType type; // Grad or UnderGrad
				LectureMode mode; // Online, F2F or Mixed
				ArrayList<Lab> labList = new ArrayList<>();
				type = LectureType.GRAD;
				if (lectureItems[3].compareToIgnoreCase("Graduate") != 0)
				type = LectureType.UNDERGRAD;
				// ________________________________________
				if (lectureItems[4].compareToIgnoreCase("ONLINE") == 0) {
					skipLine = false;
					lecture = new Lecture(lectureItems[0],
					lectureItems[1], lectureItems[2], type, LectureMode.ONLINE,
					Integer.parseInt(lectureItems[5]));
				} else {
					mode = LectureMode.F2F;
					if (lectureItems[4].compareToIgnoreCase("F2F") != 0)
						mode = LectureMode.MIXED;
					
					boolean hasLabs = true;
					if (lectureItems[6].compareToIgnoreCase("yes") != 0)
						hasLabs = false;

					if (hasLabs) {//Lecture has a lab
						skipLine = true;
						String[] labItems;

						while (scanner.hasNextLine()) {
							line = scanner.nextLine();
							if (line.length() > 15) {//True if this is not a lab!
								if ( scanner.hasNextLine() == false) {//reading the last line if any...
									oneMorePass = true;
								}
								break;
							}

							labItems = line.split(",");
							Lab lab = new Lab(labItems[0],
							labItems[1]);
							labList.add(lab);
						}//end of while
						
						lecture = new Lecture(lectureItems[0], lectureItems[1], lectureItems[2], type, mode, lectureItems[5], hasLabs,Integer.parseInt(lectureItems[7]), labList);
					} else {//Lecture doesn't have a lab
						skipLine = false;
						lecture = new Lecture(lectureItems[0],
						lectureItems[1], lectureItems[2], type, mode, lectureItems[5], hasLabs, Integer.parseInt(lectureItems[7]));
					}
				}
			}
			listOfLectures.add(lecture);
		}//end of while
		//Calling printClasses to check that loading to the arrayList is correct
		printClasses (listOfLectures);
	}//end of main
}

enum LectureType {
	GRAD, UNDERGRAD;
}

enum LectureMode {
	F2F, MIXED, ONLINE;
}

//_______________________________________________________________________________
class Lab {
	private String crn;
	private String classroom;

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	@Override
	public String toString() {
		return crn + "," + classroom;
	}

	public Lab(String crn, String classroom) {
		this.crn = crn;
		this.classroom = classroom;
	}
}//end of class Lab

//_______________________________________________________________________________
class Lecture {
	private String crn;
	private String prefix;
	private String lectureName;
	private LectureType lectureType; //Grad or UnderGrad
	private LectureMode lectureMode; //F2F, Mixed or Online
	private String classroom;
	private boolean hasLabs;
	private int creditHours;
	ArrayList<Lab> labs;
	// _________________
	//Helper method-used in constructors to set up the common fields
	private void LectureCommonInfoSetUp (String crn, String prefix, String lectureName, LectureType lectureType, LectureMode lectureMode) {
		this.crn = crn;
		this.prefix = prefix;
		this.lectureName = lectureName;
		this.lectureType = lectureType;
		this.lectureMode = lectureMode;
	}
	
	// Non-online with Labs
	public Lecture(String crn, String prefix, String lectureName, LectureType lectureType, LectureMode lectureMode, String classroom, boolean hasLabs, int creditHours, ArrayList<Lab> labs) {
		LectureCommonInfoSetUp(crn,prefix,lectureName,lectureType,lectureMode);
		this.classroom = classroom;
		this.hasLabs = hasLabs;
		this.creditHours = creditHours;
		this.labs = labs;
	}

	// Constructor for Non-online without Labs
	public Lecture( String crn, String prefix, String lectureName, LectureType lectureType, LectureMode lectureMode, String classroom, boolean hasLabs, int creditHours) {
		LectureCommonInfoSetUp(crn,prefix,lectureName,lectureType,lectureMode);
		this.classroom = classroom;
		this.hasLabs = hasLabs;
		this.creditHours = creditHours;
	}

	// Constructor for Online Lectures
	public Lecture(String crn, String prefix, String lectureName, LectureType lectureType, LectureMode lectureMode, int creditHours) {
		LectureCommonInfoSetUp(crn,prefix,lectureName,lectureType,lectureMode);
		this.classroom = classroom;
		this.hasLabs = hasLabs;
		this.creditHours = creditHours;
	}

	//________
	@Override
	public String toString() {
		String lectureAndLabs = crn + "," + prefix + "," + lectureName + "," + lectureType + "," + lectureMode + "," + hasLabs + ","+ creditHours+"\n";
		
		if ( labs != null ) {//printing corresponding labs
			//lectureAndLabs+="\n";
			for (Lab lab: labs)
				lectureAndLabs+= lab +"\n";
		}

		return lectureAndLabs;
	}
}

