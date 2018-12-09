package pt.ulisboa.tecnico.securechildlocator.ws.monitor.io;

import pt.ulisboa.tecnico.securechildlocator.ws.monitor.SecureChildLocatorMonitor;

public abstract class Command {
	
	protected SecureChildLocatorMonitor monitor;
	
	public Command(SecureChildLocatorMonitor monitor) {
		this.monitor = monitor;
	}

	public abstract void execute();
}
