package pt.ulisboa.tecnico.securechildlocator.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import pt.ulisboa.tecnico.securechildlocator.domain.Location;
import pt.ulisboa.tecnico.securechildlocator.domain.SecureChildLocator;

@WebService(
		endpointInterface = "pt.ulisboa.tecnico.securechildlocator.ws.SecureChildLocatorPortType",
		wsdlLocation = "securechildlocator.1_0.wsdl",
		name = "SecureChildLocatorWebService",
		portName = "SecureChildLocatorPort", 
		targetNamespace = "http://ws.securechildlocator.tecnico.ulisboa.pt/", 
		serviceName = "SecureChildLocatorService"
)
@HandlerChain(file = "/securechildlocator-ws_handler-chain.xml")
public class SecureChildLocatorPortImpl implements SecureChildLocatorPortType {

	// end point manager
	private SecureChildLocatorEndpointManager endpointManager;

	public SecureChildLocatorPortImpl(SecureChildLocatorEndpointManager endpointManager) {
		this.endpointManager = endpointManager;
	}

	// Main operations -------------------------------------------------------

	public void addLocation(LocationView locationToAdd) {
		String latitude = locationToAdd.getLatitude();
		String longitude = locationToAdd.getLongitude();

		// add a new location
		SecureChildLocator locator = SecureChildLocator.getInstance();
		locator.addLocation(latitude, longitude);
	}

	public List<LocationView> listLocations() {
		SecureChildLocator locator = SecureChildLocator.getInstance();
		List<LocationView> lvs = new ArrayList<LocationView>();
		for (Location location : locator.getLocations()) {
			LocationView lv = newLocationView(location);
			lvs.add(lv);
		}
		return lvs;
	}

	// Auxiliary operations --------------------------------------------------

	public String ping(String name) {
		if (name == null || name.trim().length() == 0)
			name = "friend";

		String wsName = "SecureChildLocator";

		StringBuilder builder = new StringBuilder();
		builder.append("Hello ").append(name);
		builder.append(" from ").append(wsName);
		return builder.toString();
	}

	public void clear() {
		SecureChildLocator.getInstance().reset();
	}

	// View helpers ----------------------------------------------------------

	private LocationView newLocationView(Location location) {
		LocationView view = new LocationView();
		view.setLatitude(location.getLatitude());
		view.setLongitude(location.getLongitude());
		return view;
	}

	// Exception helpers -----------------------------------------------------

	/** Helper method to throw new BadProductId exception */
	/* private void throwBadProductId(final String message) throws BadProductId_Exception {
		BadProductId faultInfo = new BadProductId();
		faultInfo.message = message;
		throw new BadProductId_Exception(message, faultInfo);
	} */

}
