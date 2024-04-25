import java.util.ArrayList;

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

	public String getCrn() {
		return crn;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getLectureName() {
		return lectureName;
	}

	public LectureType getLectureType() {
		return lectureType;
	}

	public LectureMode getLectureMode() {
		return lectureMode;
	}

	public String getClassroom() {
		return classroom;
	}

	public boolean getHasLabs() {
		return hasLabs;
	}

	public int getCreditHours() {
		return creditHours;
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

