package pt.ulisboa.tecnico.securechildlocator.ws.monitor;

/** Exception used to report problems in Web Service port wrapper. */
public class SecureChildLocatorMonitorException extends Exception {

	private static final long serialVersionUID = 1L;

	public SecureChildLocatorMonitorException() {
	}

	public SecureChildLocatorMonitorException(String message) {
		super(message);
	}

	public SecureChildLocatorMonitorException(Throwable cause) {
		super(cause);
	}

	public SecureChildLocatorMonitorException(String message, Throwable cause) {
		super(message, cause);
	}

}
