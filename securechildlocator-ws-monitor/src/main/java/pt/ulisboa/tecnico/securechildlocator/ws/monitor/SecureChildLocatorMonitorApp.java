package pt.ulisboa.tecnico.securechildlocator.ws.monitor;

import java.util.List;

import pt.ulisboa.tecnico.securechildlocator.ws.LocationView;

/** Main class that starts the Supplier Web Service client. */
public class SecureChildLocatorMonitorApp {
	public static void main(String[] args) throws Exception {
		// Check arguments
		if (args.length < 1) {
			System.err.println("Argument(s) missing!");
			System.err.println("Usage: java " + SecureChildLocatorMonitorApp.class.getName() + " wsURL");
			return;
		}
		String wsURL = args[0];

		// Create monitor
		System.out.printf("Creating monitor for server at %s%n", wsURL);
		SecureChildLocatorMonitor monitor = new SecureChildLocatorMonitor(wsURL);

		// the following remote invocations are just basic examples
		// the actual tests are made using JUnit

		System.out.println("Invoke ping()...");
		String result = monitor.ping("monitor");
		System.out.print("Result: ");
		System.out.println(result);
	}

}
