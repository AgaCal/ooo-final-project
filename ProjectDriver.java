// COP3330 Final Project
// Group Members: Agnieszka Calkowska, Erick Washbourne, Tomas Baron

import java.util.Scanner;

public class ProjectDriver {
	static Scanner scan = new Scanner(System.in);

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


	public static void printStudents() {
		System.out.println("print students");
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
