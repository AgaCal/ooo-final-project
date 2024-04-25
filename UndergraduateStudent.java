import java.util.ArrayList;

public class UndergraduateStudent extends Student {
	private ArrayList<Lecture> classes;
	private boolean resident;
	private double gpa;

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

		System.out.println("1 Credit Hour = $" + rate);
		System.out.println("\nCRN\tCRN_PREFIX\tCR_HOURS");

		for(int i = 0; i < classes.size(); i++) {

			String crn = classes.get(i).getCrn();
			String prefix = classes.get(i).getPrefix();
			int crHrs = classes.get(i).getCreditHours();

			System.out.printf("%s\t%s\t\t%d\t$%.2f\n", crn, prefix, crHrs, crHrs*rate);
			total += crHrs*rate;
		}

		System.out.println("\nHealth & id fees\t\t$35.00");

		if (!resident)total *= 2;

		if (gpa >= 3.5 && total >= 500) {
			System.out.printf("----------------------------------------\n\t\t\t\t$%.2f\n", total);
			System.out.printf("\t\t\t\t-$%.2f\n", total*.25);
			System.out.printf("\t\t\t\t-------\n\tTotal Payments\t\t$%.2f\n", total*.75);
		} else {
			System.out.printf("----------------------------------------\n\tTotal Payments\t\t$%.2f\n", total);
		}
	}
}
