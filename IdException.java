
public class IdException extends Exception {
	@Override
	String toString() {
		return "Invalid id format or ID already exists\nTry again later!"
	}
}