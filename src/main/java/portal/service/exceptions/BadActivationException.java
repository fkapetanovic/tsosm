package portal.service.exceptions;

public class BadActivationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BadActivationException(String message) {
		super(message);
	}
}
