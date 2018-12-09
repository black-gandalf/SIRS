package pt.ulisboa.tecnico.securechildlocator.ws.monitor;

import pt.ulisboa.tecnico.securechildlocator.ws.monitor.io.Command;
import pt.ulisboa.tecnico.securechildlocator.ws.monitor.io.Input;
import pt.ulisboa.tecnico.securechildlocator.ws.monitor.io.ListLocationsCommand;

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
		
		// TODO: receive and process inputs
		Command commands[] = new Command[] {
			new ListLocationsCommand(monitor)
		};
		
		Input input = new Input(commands);
		input.open();
	}

}
