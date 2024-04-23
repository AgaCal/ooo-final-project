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