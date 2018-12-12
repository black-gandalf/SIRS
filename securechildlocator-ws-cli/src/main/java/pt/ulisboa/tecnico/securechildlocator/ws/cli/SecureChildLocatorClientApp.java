package pt.ulisboa.tecnico.securechildlocator.ws.cli;

import pt.ulisboa.tecnico.securechildlocator.ws.LocationView;

/** Main class that starts the Supplier Web Service client. */
public class SecureChildLocatorClientApp {

	private static final long LOCATION_UPDATE_INTERVAL = 10000;

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

		while (true) {
			double latitude = -180 + Math.random() * 360;
			double longitude = -180 + Math.random() * 360;

			LocationView locationView = new LocationView();
			locationView.setLatitude(Double.toString(latitude));
			locationView.setLongitude(Double.toString(longitude));
			Thread.sleep(LOCATION_UPDATE_INTERVAL);
			client.addLocation(locationView);


			System.out.println("[latitude: " + latitude + ", longitude: " + longitude + "]");
		}
	}

}
