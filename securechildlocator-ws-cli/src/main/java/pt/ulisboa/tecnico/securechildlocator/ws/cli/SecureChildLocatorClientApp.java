package pt.ulisboa.tecnico.securechildlocator.ws.cli;

import java.util.List;

import pt.ulisboa.tecnico.securechildlocator.ws.LocationView;

/** Main class that starts the Supplier Web Service client. */
public class SecureChildLocatorClientApp {
	public static void main(String[] args) throws Exception {
		// Check arguments
		if (args.length < 1) {
			System.err.println("Argument(s) missing!");
			System.err.println("Usage: java " + SecureChildLocatorClientApp.class.getName() + " wsURL");
			return;
		}
		String wsURL = args[0];

		// Create client
		System.out.printf("Creating client for server at %s%n", wsURL);
		SecureChildLocatorClient client = new SecureChildLocatorClient(wsURL);

		// the following remote invocations are just basic examples
		// the actual tests are made using JUnit

		// System.out.println("Invoke ping()...");
		// String result = client.ping("client");
		// System.out.print("Result: ");
		// System.out.println(result);

		System.out.println("Invoke addLocation()...");
		LocationView view = new LocationView();
		view.setLatitude("18");
		view.setLongitude("-9");
		client.addLocation(view);

		System.out.println("Invoke listLocations()...");
		List<LocationView> result = client.listLocations();
		for (LocationView location : result) {
			System.out.println("[" + location.getLatitude() + ", " + location.getLongitude() + "]");
		}
	}

}
