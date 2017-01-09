package portal.service.exceptions;

public class DataEntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataEntityNotFoundException(String message) {
		super(message);
	}
}
