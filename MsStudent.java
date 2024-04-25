import java.util.ArrayList;

public class MsStudent extends GraduateStudent {
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
		System.out.printf("----------------------------------------\n\tTotal Payments\t\t$%.2f\n", total);
		System.out.println();
	}
}

