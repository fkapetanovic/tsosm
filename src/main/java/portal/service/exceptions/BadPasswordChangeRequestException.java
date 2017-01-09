package portal.service.exceptions;

public class BadPasswordChangeRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadPasswordChangeRequestException(String message) {
		super(message);
	}
}
