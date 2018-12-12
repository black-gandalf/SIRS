package pt.ulisboa.tecnico.securechildlocator.ws;

import java.util.Timer;

public class SecureChildLocatorApp {
	public static void main(String[] args) throws Exception {
		// Check arguments
		if (args.length < 1) {
			System.err.println("Argument(s) missing!");
			System.err.println("Usage: java " + SecureChildLocatorApp.class.getName() + " wsURL");
			return;
		}
		String wsURL = args[0];
		String wsI = args[1];

		// Create server implementation object
		SecureChildLocatorEndpointManager endpoint = new SecureChildLocatorEndpointManager(wsURL);
		endpoint.setWsi(wsI);

		// create timer object
		Timer timer = new Timer(true);
		LifeProof lifeproof = null;

		try {
			endpoint.start();
			if (wsI.equals("1")) {
				// Primary server
				System.out.println("Primary server");
				lifeproof = new LifeProof(endpoint, true);
			}
			else {
				// Secondary server
				System.out.println("Backup server");
				lifeproof = new LifeProof(endpoint, false);
			}
			//schedule timer for the life proof
			timer.schedule(lifeproof, 0 * 1000, LifeProof.IM_ALIVE_SECONDS * 1000);
			// await connections
			endpoint.awaitConnections();
		} finally {
			endpoint.stop();
		}

	}

}
