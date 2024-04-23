// COP3330 Final Project
// Group Members: Agnieszka Calkowska, Erick Washbourne, Tomas Baron

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

enum LectureType {
	GRAD, UNDERGRAD;
}

enum LectureMode {
	F2F, MIXED, ONLINE;
}

public class ProjectDriver {
	static Scanner scan = new Scanner(System.in);
	static ArrayList<UndergraduateStudent> undergradStudents = new ArrayList<UndergraduateStudent>();
	static ArrayList<PhdStudent> phdStudents = new ArrayList<PhdStudent>();
	static ArrayList<MsStudent> msStudents = new ArrayList<MsStudent>();
	static ArrayList<Lecture> classList;

	public static void getClasses(String[] args) throws FileNotFoundException {
		classList = new ArrayList<Lecture>();
		
		Scanner readClasses = new Scanner(new File("lec.txt"));
		
		String line = "";
		String[] lectureItems;
		Lecture lecture=null;
		
		boolean skipLine = false;
		boolean oneMorePass = false;
		
		while (readClasses.hasNextLine() || oneMorePass ) {
			if (skipLine == false) {
				line = readClasses.nextLine();
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

						while (readClasses.hasNextLine()) {
							line = readClasses.nextLine();
							if (line.length() > 15) {//True if this is not a lab!
								if ( readClasses.hasNextLine() == false) {//reading the last line if any...
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
			classList.add(lecture);
		}//end of while

		readClasses.close();
	}

	public static void main(String[] args) {

		int selection = -1;
		while (selection != 0) {
			System.out.println("---------------------------------------------------\n");
			System.out.println("Main Menu\n");

			System.out.println("1 : Student Management");
			System.out.println("2 : Course Management");
			System.out.println("0 : Exit\n");

			System.out.print("\tEnter your selection: ");

			selection = scan.nextInt();

			System.out.println("\n-----------------\n");

			if (selection == 1) manageStudent();
			else if (selection == 2) manageCourse();
		}

		System.out.println("Take Care!\n");
		scan.close();
	}

	public static void manageStudent() {
		char selection;

		System.out.println("Student Management Menu:\n");

		System.out.println("Choose one of:\n");

		System.out.println("  A - Search add a student");
		System.out.println("  B - Delete a Student");
		System.out.println("  C - Print Fee Invoice");
		System.out.println("  D - Print List of Students");
		System.out.println("  X - Back to Main Menu\n\n");

		System.out.print ("Enter your selection: ");

		selection = scan.next().charAt(0);

		System.out.println();

		if (selection >= 'a') selection += 'A' - 'a';

		if (selection == 'A') addStudent();
		else if (selection == 'B') delStudent();
		else if (selection == 'C') printInvoice();
		else if (selection == 'D') printStudents();
		else return;
		
		manageStudent();
	}

	public static void addStudent() {
		System.out.println("add student");
	}


	public static void delStudent() {
		System.out.println("del student");
	}


	public static void printInvoice() {
		System.out.println("print invoice");
	}


	// should be done
	public static void printStudents() {
		System.out.println("PhD Students");
		System.out.println("------------");

		for (Student student : phdStudents) {
			System.out.println("    - " + student.getName());
		}

		System.out.println();

		System.out.println("MS Students");
		System.out.println("------------");

		for (Student student : msStudents) {
			System.out.println("    - " + student.getName());
		}

		System.out.println();

		System.out.println("Underdraduate Students");
		System.out.println("------------");

		for (Student student : undergradStudents) {
			System.out.println("    - " + student.getName());
		}

		System.out.println();
	}

	public static void manageCourse() {

		char selection;

		System.out.println("Course Management Menu:\n");

		System.out.println("Choose one of:\n");

		System.out.println("  A - Search for a class or lab using the class/lab number");
		System.out.println("  B - delete a class");
		System.out.println("  C - Add a lab to a class");
		System.out.println("  X - Back to Main Menu\n\n");

		System.out.print ("Enter your selection: ");

		selection = scan.next().charAt(0);

		System.out.println();

		if (selection >= 'a') selection += 'A' - 'a';

		if (selection == 'A') searchCourse();
		else if (selection == 'B') delClass();
		else if (selection == 'C') addLab();
		else return;
		
		manageCourse();
	}

	public static void searchCourse() {
		System.out.println("search for a class");
	}

	public static void delClass() {
		System.out.println("del class");
	}

	public static void addLab() {
		System.out.println("add lab");
	}

}
