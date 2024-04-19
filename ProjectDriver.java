
import java.util.Scanner;

public class ProjectDriver {
	public static void main(String[] args) {

		int selection = -1;
		Scanner scan = new Scanner(System.in);

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
	}

	public static void manageStudent() {

		char selection;

		System.out.println("...");
	}

	public static void manageCourse() {
		System.out.println("nope");
	}
}
