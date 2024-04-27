import java.util.ArrayList;

public class PhdStudent extends GraduateStudent {
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
			System.out.printf("\t\t\t\t-$%.2f\n", 700);
			total -= 700;
			System.out.printf("\t\t\t\t-------\n\tTotal Payments\t\t$%.2f\n", total);
		} else {
			System.out.printf("----------------------------------------\n\tTotal Payments\t\t$%.2f\n", total);
		}

	}
}

