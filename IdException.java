
public class IdException extends Exception {
	@Override
	public String toString() {
		return "Invalid id format or ID already exists\nTry again later!";
	}
}