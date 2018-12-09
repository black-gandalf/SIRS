package pt.ulisboa.tecnico.securechildlocator.ws;

public class SecureChildLocatorApp {
	public static void main(String[] args) throws Exception {
		// Check arguments
		if (args.length < 1) {
			System.err.println("Argument(s) missing!");
			System.err.println("Usage: java " + SecureChildLocatorApp.class.getName() + " wsURL");
			return;
		}
		String wsURL = args[0];

		// Create server implementation object
		SecureChildLocatorEndpointManager endpoint = new SecureChildLocatorEndpointManager(wsURL);
		try {
			endpoint.start();
			endpoint.awaitConnections();
		} finally {
			endpoint.stop();
		}

	}

}
