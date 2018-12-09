package pt.ulisboa.tecnico.securechildlocator.ws.monitor.io;

import java.util.List;

import pt.ulisboa.tecnico.securechildlocator.ws.LocationView;
import pt.ulisboa.tecnico.securechildlocator.ws.monitor.SecureChildLocatorMonitor;

public class ListLocationsCommand extends Command {

	public ListLocationsCommand(SecureChildLocatorMonitor monitor) {
		super(monitor);
	}

	@Override
	public final void execute() {
		List<LocationView> locationViews = monitor.listLocations();
		for (LocationView location : locationViews) {
			System.out.println("Location [latitude=" + location.getLatitude() + ", longitude=" + location.getLongitude());
		}
	}

}
