package pt.ulisboa.tecnico.securechildlocator.ws.cli;

/** Exception used to report problems in Web Service port wrapper. */
public class SecureChildLocatorClientException extends Exception {

	private static final long serialVersionUID = 1L;

	public SecureChildLocatorClientException() {
	}

	public SecureChildLocatorClientException(String message) {
		super(message);
	}

	public SecureChildLocatorClientException(Throwable cause) {
		super(cause);
	}

	public SecureChildLocatorClientException(String message, Throwable cause) {
		super(message, cause);
	}

}
