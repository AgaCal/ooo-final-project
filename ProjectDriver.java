// COP3330 Final Project
// Group Members: Aga Calkowska, Erick Washbourne, Tomas Baron

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    // done, used his code
    public static void getClasses() throws FileNotFoundException {
        classList = new ArrayList<Lecture>();

        Scanner readClasses = new Scanner(new File("lec.txt"));

        String line = "";
        String[] lectureItems;
        Lecture lecture = null;

        boolean skipLine = false;
        boolean oneMorePass = false;

        while (readClasses.hasNextLine() || oneMorePass) {
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
                                if (readClasses.hasNextLine() == false) {//reading the last line if any...
                                    oneMorePass = true;
                                }
                                break;
                            }

                            labItems = line.split(",");
                            Lab lab = new Lab(labItems[0],
                                    labItems[1]);
                            labList.add(lab);
                        }//end of while

                        lecture = new Lecture(lectureItems[0], lectureItems[1], lectureItems[2], type, mode, lectureItems[5], hasLabs, Integer.parseInt(lectureItems[7]), labList);
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

    // Reads input until it matches a given predicate (and doesn't throw an exception)
    public static <T> T getValidInput(String prompt, String err, Supplier<T> reader, Predicate<T> validator) {
        // Declare the input result
        T result;

        // Loop until we get valid input
        while (true) {
            // Print the prompt and try to read input
            System.out.print(prompt);
            try {
                // Read the input and check for validity
                result = reader.get();
                if (validator.test(result)) break;
            } catch (InputMismatchException e) {
                // If input mismatched, the buffer won't skip the invalid input
                scan.next();
            } catch (Exception e) { /* noop */ }

            // Print an error message if the input was invalid (either bad input or failed validation)
            System.out.println(err);
        }

        // Return the (necessarily valid) result
        return result;
    }

    // Checks if a given string is a valid student ID
    public static boolean isValidId(String id) {
        return id.matches("[A-Za-z]{2}\\d{4}");
    }

    // Retrieves a character (only one) from the user; strings of length != 1 will throw an exception
    public static char getSingleCharacter(Scanner scan) throws StringIndexOutOfBoundsException {
        String input = scan.next();
        if (input.length() != 1) throw new StringIndexOutOfBoundsException();
        return input.charAt(0);
    }

    // done (?)
    public static void main(String[] args) {
        // Load the class list from the file
        try {
            getClasses();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not load class list from file!\nExiting...");
            return;
        }

        // Main menu loop
        int selection = -1;
        while (selection != 0) {
            // Print the main menu header
            System.out.println("=========");
            System.out.println("Main Menu");
            System.out.println("=========\n");

            // Print the menu options
            System.out.println("Choose one of:\n");
            System.out.println("  1 - Student Management");
            System.out.println("  2 - Course Management");
            System.out.println("  0 - Exit\n");

            // Get a valid selection from the user
            selection = getValidInput("Enter your selection (0-2):\n> ", "Invalid input!",
                    scan::nextInt, (s) -> s >= 0 && s <= 2);

            // Print an empty line for spacing
            System.out.println();

            // Go to a sub-menu if one was selected
            if (selection == 1) manageStudent();
            else if (selection == 2) manageCourse();
        }

        // Print a goodbye message and close the scanner
        System.out.println("Take Care!\n");
        scan.close();
    }

    // done
    public static void manageStudent() {
        // Print the menu header
        System.out.println("=======================");
        System.out.println("Student Management Menu");
        System.out.println("=======================\n");

        // Print the menu options
        System.out.println("Choose one of:");
        System.out.println("  A - Add a student");
        System.out.println("  B - Delete a student");
        System.out.println("  C - Print a student's fee invoice");
        System.out.println("  D - Print all students");
        System.out.println("  X - Exit to main menu\n");

        // Read input until we get a valid selection
        char selection = getValidInput("Enter your selection (A/B/C/D/X):\n> ", "Invalid input!",
                () -> Character.toUpperCase(getSingleCharacter(scan)),
                (ch) -> "ABCDX".contains(ch + ""));
        System.out.println(); // extra spacing

        // Perform the action based on the selection
        switch (selection) {
            case 'A':
                try {
                    // Try to a student
                    addStudent();
                } catch (IdException e) {
                    // If we caught an ID exception, print the error message
                    System.out.println(e + "\n");
                }
                break;

            case 'B':
                // Delete a student
                delStudent();
                break;

            case 'C':
                // Print a student's fee invoice
                printInvoice();
                break;

            case 'D':
                // Print all students
                printStudents();
                break;

            case 'X':
                // Exit to main menu
                return;
        }

        // If we didn't exit, recurse again
        manageStudent();
    }

    //done
    public static void addStudent() throws IdException {
        // Get the student ID
        String id = getValidInput("Enter the student's ID:\n> ", "Invalid input!",
                scan::next, ProjectDriver::isValidId);

        // Check if the student already exists
        if (Stream.of(undergradStudents.stream(), msStudents.stream(), phdStudents.stream())
                .flatMap(s -> s)
                .anyMatch(s -> s.getId().equalsIgnoreCase(id))) {
            // If so, throw an ID exception
            throw new IdException();
        }

        // Get the student type
        String type = getValidInput("Enter the student's type (PhD/MS/U/Undergrad):\n> ", "Invalid input!",
                scan::next,
                (s) -> {
                    // Check if the input is a valid student type
                    String typeUpper = s.toUpperCase();
                    return typeUpper.equals("PHD") || typeUpper.equals("MS")
                            || typeUpper.equals("U") || typeUpper.equals("UNDERGRAD");
                }).toUpperCase();

        // Print instructions for inputing student information
        System.out.println("\nStudent Info Format:");
        switch (type) {
        	case "PHD": {
        		System.out.println("<name>|<advisor>|<subject>|<list of lab numbers>");
        		System.out.println("Labs should be entered by lab number, separated by commas (no spaces)\n");
        		break;
        	}

        	case "MS": {
        		System.out.println("<name>|<list of lectures>");
        		System.out.println("Lectures should be entered by CRN or prefix, separated by commas (no spaces)\n");
        		break;
        	}
        	case "U":
        	case "UNDERGRAD": {
        		System.out.println("<name>|<gpa>|<resident status>|<list of lectures>");
        		System.out.println("Lectures should be entered by CRN or prefix, separated by commas (no spaces)\n");
        		break;
        	}

        }


        // Get remaining info without any checks (assumed to be valid)
        scan.nextLine(); // consume the newline
        System.out.print("Enter the student info (separated by '|'):\n> ");
        String[] info = scan.nextLine().trim().split("\\|");

        // Add the right type of student to the appropriate list
        String name = info[0];
        switch (type) {
            case "PHD": {
                // Get the student info
                String advisor = info[1], subject = info[2];

                // Retrieve the labs this student is in
                List<Lab> labs = Stream.of(info[3].split(","))
                        // map each CRN to the corresponding lab
                        .map(crn -> classList.stream()
                                // filter for classes with labs
                                .filter(Lecture::getHasLabs)
                                // find the lab with the matching CRN
                                .flatMap(l -> l.labs.stream())
                                .filter(l -> l.getCrn().equals(crn))
                                .findFirst())
                        // filter out any empty optionals (non-existent labs)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

                // Add the student to the list
                phdStudents.add(new PhdStudent(
                        name, advisor, subject, id, new ArrayList<>(labs)));
                break;
            }

            case "MS": {
                // Get the list of lectures from either CRNs or prefixes
                List<Lecture> lecs = Stream.of(info[1].split(","))
                        // map each CRN or prefix to the corresponding lecture
                        .map(str -> classList.stream()
                                // find the lecture with the matching CRN or prefix
                                .filter(l -> l.getCrn().equalsIgnoreCase(str)
                                        || l.getPrefix().equalsIgnoreCase(str))
                                .findFirst())
                        // filter out any empty optionals (non-existent lectures)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

                // Add the student to the list
                msStudents.add(new MsStudent(
                        name, id, new ArrayList<>(lecs)));
                break;
            }

            case "U":
            case "UNDERGRAD": {
                // Get the student info
                String gpaStr = info[1], residentStr = info[2];
                double gpa = Double.parseDouble(gpaStr); // parse the GPA (assumed to be valid)
                boolean resident = List.of("yes", "y", "true", "t")
                        .contains(residentStr.toLowerCase()); // parse the resident status in any boolean format

                // Get the list of lectures from either CRNs or prefixes
                List<Lecture> lecs = Stream.of(info[3].split(","))
                        // map each CRN or prefix to the corresponding lecture
                        .map(str -> classList.stream()
                                // find the lecture with the matching CRN or prefix
                                .filter(lec -> lec.getCrn().equalsIgnoreCase(str)
                                        || lec.getPrefix().equalsIgnoreCase(str))
                                .findFirst())
                        // filter out any empty optionals (non-existent lectures)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

                // Construct the student and add them to the list
                undergradStudents.add(new UndergraduateStudent(
                        name, id, gpa, resident, new ArrayList<>(lecs)));
                break;
            }
        }

        // Print a success message
        System.out.printf("[ %s ] added successfully!\n\n", name);
    }

    //done
    public static void delStudent() {
        // Get a valid student ID from the user
        String id = getValidInput("Enter the student's ID:\n> ", "Invalid input!",
                scan::next, ProjectDriver::isValidId);

        // Find a student in any of the lists matching the ID
        var student = Stream.of(
                        undergradStudents.stream(),
                        msStudents.stream(),
                        phdStudents.stream())
                .flatMap(s -> s)
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        // If we didn't find a student, print an error message and return
        if (student == null) {
            System.out.printf("No student found with ID %s!\n\n", id);
            return;
        }

        // Otherwise remove the student from the appropriate list
        String name = student.getName(); // save the name for the success message
        if (student instanceof UndergraduateStudent) undergradStudents.remove(student);
        else if (student instanceof MsStudent) msStudents.remove(student);
        else if (student instanceof PhdStudent) phdStudents.remove(student);

        // Print a success message
        System.out.printf("[ %s : %s ] removed successfully!\n\n", id, name);
    }

    // done
    public static void printInvoice() {
        // Get a valid student ID from the user
        String id = getValidInput("Enter the student's ID:\n> ", "Invalid input!",
                scan::next, ProjectDriver::isValidId);

        // Find a student in any of the lists matching the ID
        Student student = Stream.of(
                        undergradStudents.stream(),
                        msStudents.stream(),
                        phdStudents.stream())
                .flatMap(s -> s)
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        // If we didn't find a student, print an error message and return
        if (student == null) {
            System.out.printf("No student found with ID %s!\n\n", id);
            return;
        }

        // Otherwise, print the student's invoice
        student.printInvoice();
        System.out.println();
    }


    // done
    public static void printStudents() {
        System.out.println("PhD Students");
        System.out.println("------------");
        phdStudents.forEach(s -> System.out.println("  - " + s.getName()));
        if (phdStudents.size() == 0) System.out.println("  - (no Phd students enrolled)");
        System.out.println();

        System.out.println("MS Students");
        System.out.println("-----------");
        msStudents.forEach(s -> System.out.println("  - " + s.getName()));
        if (msStudents.size() == 0) System.out.println("  - (no Ms students enrolled)");
        System.out.println();

        System.out.println("Undergraduate Students");
        System.out.println("----------------------");
        undergradStudents.forEach(s -> System.out.println("  - " + s.getName()));
        if (undergradStudents.size() == 0) System.out.println("  - (no Undergraduate students enrolled)");
        System.out.println();
    }

    // done
    public static void manageCourse() {
        System.out.println("======================");
        System.out.println("Course Management Menu");
        System.out.println("======================\n");

        System.out.println("Choose one of:\n");
        System.out.println("  A - Search by class/lab number");
        System.out.println("  B - Delete a class");
        System.out.println("  C - Add a lab to a class");
        System.out.println("  X - Exit to Main Menu\n");

        // Read input until we get a valid selection
        char selection = getValidInput("Enter your selection (A/B/C/X):\n> ", "Invalid input!",
                () -> Character.toUpperCase(getSingleCharacter(scan)),
                (s) -> "ABCX".contains(s + ""));
        System.out.println();

        // Perform the action based on the selection
        switch (selection) {
            case 'A':
                // Search for a class/lab
                searchCourse();
                break;

            case 'B':
                // Delete a class
                delClass();
                break;

            case 'C':
                // Add a lab to a class
                addLab();
                break;

            case 'X':
                // Exit to main menu
                return;
        }

        // If we didn't exit, recurse again
        manageCourse();
    }

    //done
    public static void searchCourse() {
        // Get a valid class/lab number from the user
        String classNum = getValidInput("Enter the Class/Lab Number:\n> ", "Invalid input!",
                scan::next, (s) -> s.matches("\\d{5}"));

        System.out.println();

        // Iterate through the list of lectures
        for (Lecture currentLec : classList) {
            // Check the current lecture
            if (currentLec.getCrn().equals(classNum)) {
                // If this lecture matches, print the info and return
                System.out.printf("[ %s,%s,%s ]\n\n", currentLec.getCrn(), currentLec.getPrefix(), currentLec.getLectureName());
                return;
            }

            // Check any labs associated with the lecture
            if (currentLec.getHasLabs()) {
                for (int j = 0; j < currentLec.labs.size(); j++) {
                    Lab currentLab = currentLec.labs.get(j);
                    if (currentLab.getCrn().equals(classNum)) {
                        // If we found the lab, print the info and return
                        System.out.printf("Lab for [ %s,%s,%s ]\n", currentLec.getCrn(), currentLec.getPrefix(), currentLec.getLectureName());
                        System.out.println("Lab room " + currentLab.getClassroom() + "\n");
                        return;
                    }
                }
            }
        }

        // If we didn't find the class/lab, print empty course info
        System.out.println("[ No such lecture/lab ]\n");
    }

    // done
    public static void delClass() {
    	// Get a valid Lecture number from user
        String classNum = getValidInput("Enter the Class Number:\n> ", "Invalid input!",
       	scan::next, (s) -> s.matches("\\d{5}"));

        System.out.println();

        // Find associated lecture in class List or null
        Lecture toDel = classList.stream()
        		.filter(s -> s.getCrn().equalsIgnoreCase(classNum))
                .findFirst()
                .orElse(null);

        // If no class found then return
        if (toDel == null) {
            System.out.printf("No class with number %s\n\n", classNum);
            return;
        }

        //If class has labs, cannot be deleted (per announcement on April 22)
        if(toDel.getHasLabs()){
        	System.out.printf("Class number %s has labs and cannot be deleted!\n\n", classNum);
        	return;
        }

        // Checks to see if students are enrolled in the class. If any student is taking the class, the class cannot be deleted (per announcement on April 13)
        if (toDel.getLectureType() == LectureType.GRAD) {
            for (int i = 0; i < msStudents.size(); i++) {

            	Lecture studentLec = msStudents.get(i).classes.stream()
            		.filter(s -> s.getCrn().equalsIgnoreCase(classNum))
            		.findFirst()
            		.orElse(null);

            	if (studentLec != null) {
             		System.out.printf("Students are enrolled in class with number %s. Class cannot be deleted!\n\n", classNum);
            		return;           		
            	}

            }
        } else {
            for (int i = 0; i < undergradStudents.size(); i++) {

            	Lecture studentLec = undergradStudents.get(i).classes.stream()
            		.filter(s -> s.getCrn().equalsIgnoreCase(classNum))
            		.findFirst()
            		.orElse(null);

            	if (studentLec != null) {
             		System.out.printf("Students are enrolled in class with number %s. Class cannot be deleted!\n\n", classNum);
            		return;           		
            	}

	        }
	    }

        // Remove the class from the classList
        classList.remove(toDel);

        // Try to open lec.txt and scan it
        Scanner readClasses;
        File lec;
        try {
            lec = new File("lec.txt");
            readClasses = new Scanner(lec);
        } catch (FileNotFoundException e) {
            System.out.println("\"lec.txt\" file not found!");
            return;
        }

        // Try to make a tmp txt file to write everything to
        try {
            File tmpFile = new File("tmp.txt");
            FileWriter writer = new FileWriter(tmpFile, true);

            // Read everything in from lec.txt
            while (readClasses.hasNextLine()) {
                String read = readClasses.nextLine();
                String[] readInfo = read.split(",");

                // Skip the lecture that is to be removed
                if (!readInfo[0].equals(classNum)) {
                    writer.write(read);
                    writer.write("\n");
                }
            }

            // Rename the tmp file to lec to overwrite it
            tmpFile.renameTo(lec);
            // Close the writer
            writer.close();

        } catch (IOException e) {
            System.out.println("Something went wrong.\nPlease Try Again Later!");
        } finally {
        	// Close scanner and output successful message
            readClasses.close();
            System.out.println("[ " + classNum + "," + toDel.getPrefix() + "," + toDel.getLectureName() + " ] deleted!\n");
            return;
        }

    }

    // done
    public static void addLab() {
    	// Get a valid Lecture number from user
        String classNum = getValidInput("Enter the Lecture Number to Add Lab To:\n> ", "Invalid input!",
                scan::next, (s) -> s.matches("\\d{5}"));

        System.out.println();

        // Find associated lecture in class List or null
        Lecture addTo = classList.stream()
                .filter(s -> s.getCrn().equalsIgnoreCase(classNum))
                .findFirst()
                .orElse(null);

        // If class not found or it is an online class that can't have labs return
        if (addTo == null) {
            System.out.println(classNum + " is invalid.\nPlease Try Again Later!\n");
            return;
        } else if (addTo.getLectureMode() == LectureMode.ONLINE) {
            System.out.println(classNum + " is an online class, no labs can be added!\n");
            return;
        }

        // Otherwise it's valid and take the rest of info in
        System.out.println(classNum + " is valid. Enter the rest of the information (CRN,Location): \n");
        scan.nextLine();
        String other = scan.nextLine();
        String[] info = other.split(",");

        // If the class number already exists then return
        for (Lecture l : classList) {
            if (l.getCrn().equals(info[0])) {
                System.out.println("Sorry that Class Number already exists.\nPlease try again later!");
                return;
            }
            if (l.getHasLabs()) {
                for (Lab lab : l.labs) {
                    if (lab.getCrn().equals(info[0])) {
                        System.out.println("Sorry that Class Number already exists.\nPlease try again later!");
                        return;
                    }
                }
            }
        }

        // Otherwise make a new lab with given info
        Lab toAdd = new Lab(info[0], info[1]);

        // If given lecture has no labs make a lab list for it and set hasLabs to true
        if (!addTo.getHasLabs()) {
            addTo.setHasLabs(true);
            addTo.labs = new ArrayList<Lab>();
        }

        // Add the lab to the lecture's lablist
        addTo.labs.add(toAdd);

        // Try to open lec.txt and scan it
        Scanner readClasses;
        File lec;
        try {
            lec = new File("lec.txt");
            readClasses = new Scanner(lec);
        } catch (FileNotFoundException e) {
            System.out.println("\"lec.txt\" file not found!");
            return;
        }

        // Try to make a tmp txt file to write everything to
        try {
            File tmpFile = new File("tmp.txt");
            FileWriter writer = new FileWriter(tmpFile, true);

            // Read everything in from lec.txt
            while (readClasses.hasNextLine()) {
                String read = readClasses.nextLine();
                String[] readInfo = read.split(",");

                // If at the lecture to add lab to
                // Then print the new updated lecture with added lab
                if (readInfo[0].equals(classNum)) {
                    writer.write(addTo.toString());
                } else { // Otherwise just write straight from lec.txt
                    writer.write(read);
                    writer.write("\n");
                }
            }

            // Rename the tmp file to lec to overwrite it
            tmpFile.renameTo(lec);
            // Close the writer
            writer.close();

        } catch (IOException e) {
            System.out.println("Something went wrong.\nPlease Try Again Later!");
        } finally {
            // Close scanner and output successful message
            readClasses.close();
            System.out.println("[ " + info[0] + "," + info[1] + 
            	" ] added as lab to [ " + classNum + "," + addTo.getPrefix() + "," + addTo.getLectureName() + " ] !\n");
            return;
        }
    }

}


class IdException extends Exception {
    @Override
    public String toString() {
        return "Invalid ID format or ID already exists\nTry again later!";
    }
}

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

    public void setHasLabs(boolean hasLabs){
        this.hasLabs = hasLabs;
    }

    //________
    @Override
    public String toString() {
        String yesNo = hasLabs ? "YES" : "NO";
        String lectureAndLabs = crn + "," + prefix + "," + lectureName + "," + lectureType + "," + lectureMode;
        if(lectureMode != LectureMode.ONLINE){
            lectureAndLabs += "," + classroom;
        }
        lectureAndLabs += "," + yesNo + ","+ creditHours+"\n";
        
        if ( labs != null ) {//printing corresponding labs
            //lectureAndLabs+="\n";
            for (Lab lab: labs)
                lectureAndLabs+= lab +"\n";
        }

        return lectureAndLabs;
    }
}

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


abstract class Student {
    private String name, id;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
    }

    abstract public void printInvoice();

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

class UndergraduateStudent extends Student {
    private boolean resident;
    private double gpa;
    ArrayList<Lecture> classes;

    public UndergraduateStudent(String name, String id, double gpa, boolean resident, ArrayList<Lecture> classes) {
        super(name, id);
        this.classes = classes;
        this.resident = resident;
        this.gpa = gpa;
    }

    public void printInvoice() {
        System.out.println("VALENCE COLLEGE\nORLANDO FL 10101\n---------------------\n");
        System.out.println("Fee Invoice Prepared for Student: \n" + getId() + "-" + getName() + "\n");

        double rate = 120.25;
        double total = 0;

        if (!resident) rate *= 2;

        System.out.printf("1 Credit Hour = $%.2f\n", rate);
        System.out.println("\nCRN\tCRN_PREFIX\tCR_HOURS");

        for(int i = 0; i < classes.size(); i++) {

            String crn = classes.get(i).getCrn();
            String prefix = classes.get(i).getPrefix();
            int crHrs = classes.get(i).getCreditHours();

            System.out.printf("%s\t%s\t\t%d\t$%.2f\n", crn, prefix, crHrs, crHrs*rate);
            total += crHrs*rate;
        }

        System.out.println("\nHealth & id fees\t\t$35.00");
        total += 35;

        if (gpa >= 3.5 && total >= 500) {
            System.out.printf("----------------------------------------\n\t\t\t\t$%.2f\n", total);
            System.out.printf("\t\t\t\t-$%.2f\n", total*.25);
            System.out.printf("\t\t\t\t-------\n\tTotal Payments\t\t$%.2f\n", total*.75);
        } else {
            System.out.printf("----------------------------------------\n\tTotal Payments\t\t$%.2f\n", total);
        }
    }
}

abstract class GraduateStudent extends Student {

    public GraduateStudent(String name, String id) {
        super(name,id);
    }
}

class MsStudent extends GraduateStudent {
    ArrayList<Lecture> classes;

    public MsStudent(String name, String id, ArrayList<Lecture> classes) {
        super(name, id);
        this.classes = classes;
    }

    public void printInvoice() {
        System.out.println("VALENCE COLLEGE\nORLANDO FL 10101\n---------------------\n");
        System.out.println("Fee Invoice Prepared for Student: \n" + getId() + "-" + getName() + "\n");

        double rate = 300;
        double total = 0;

        System.out.printf("1 Credit Hour = $%.2f\n", rate);
        System.out.println("\nCRN\tCRN_PREFIX\tCR_HOURS");

        for(int i = 0; i < classes.size(); i++) {

            String crn = classes.get(i).getCrn();
            String prefix = classes.get(i).getPrefix();
            int crHrs = classes.get(i).getCreditHours();

            System.out.printf("%s\t%s\t\t%d\t$%.2f\n", crn, prefix, crHrs, crHrs*rate);
            total += crHrs*rate;
        }

        System.out.println("\nHealth & id fees\t\t$35.00");
        total += 35;

        System.out.printf("----------------------------------------\n\tTotal Payments\t\t$%.2f\n", total);
    }
}

class PhdStudent extends GraduateStudent {
    private String advisor, subject;
    ArrayList<Lab> labs;

    public PhdStudent(String name, String advisor, String subject, String id, ArrayList<Lab> labs) {
        super(name, id);
        this.labs = labs;
        this.advisor = advisor;
        this.subject = subject;
    }

    public void printInvoice() {
        System.out.println("VALENCE COLLEGE\nORLANDO FL 10101\n---------------------\n");
        System.out.println("Fee Invoice Prepared for Student: \n" + getId() + "-" + getName() + "\n");
        
        System.out.println("RESEARCH\n" + subject + "\t\t\t\t$700.00");

        System.out.println("\nHealth & id fees\t\t$35.00");
        
        double total = 735;
        if (labs.size() == 2) {
            System.out.printf("----------------------------------------\n\t\t\t\t$%.2f\n", total);
            System.out.printf("\t\t\t\t-$%.2f\n", total*.50);
            System.out.printf("\t\t\t\t-------\n\tTotal Payments\t\t$%.2f\n", total*.50);
        } else if (labs.size() >= 3) {
            System.out.printf("----------------------------------------\n\t\t\t\t$%.2f\n", total);
            System.out.printf("\t\t\t\t-$%.2f\n", 700.00);
            total -= 700;
            System.out.printf("\t\t\t\t-------\n\tTotal Payments\t\t$%.2f\n", total);
        } else {
            System.out.printf("----------------------------------------\n\tTotal Payments\t\t$%.2f\n", total);
        }

    }
}
