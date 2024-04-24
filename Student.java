
public abstract class Student {
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
