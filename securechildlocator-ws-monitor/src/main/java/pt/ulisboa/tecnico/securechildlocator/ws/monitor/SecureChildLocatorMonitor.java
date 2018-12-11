package pt.ulisboa.tecnico.securechildlocator.ws.monitor;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import pt.ulisboa.tecnico.securechildlocator.ws.LocationView;
import pt.ulisboa.tecnico.securechildlocator.ws.SecureChildLocatorPortType;
import pt.ulisboa.tecnico.securechildlocator.ws.SecureChildLocatorService;

/**
 * Monitor port wrapper.
 *
 * Adds easier end point address configuration to the Port generated by
 * wsimport.
 */
public class SecureChildLocatorMonitor implements SecureChildLocatorPortType {

	/** WS service */
	SecureChildLocatorService service = null;

	/** WS port (port type is the interface, port is the implementation) */
	SecureChildLocatorPortType port = null;

	/** WS end point address */
	private String wsURL = null; // default value is defined inside WSDL

	public String getWsURL() {
		return wsURL;
	}

	/** output option **/
	private boolean verbose = false;

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	/** constructor with provided web service URL */
	public SecureChildLocatorMonitor(String wsURL) throws SecureChildLocatorMonitorException {
		this.wsURL = wsURL;
		createStub();
	}

	/** Stub creation and configuration */
	private void createStub() {
		if (verbose)
			System.out.println("Creating stub ...");
		service = new SecureChildLocatorService();
		port = service.getSecureChildLocatorPort();

		if (wsURL != null) {
			if (verbose)
				System.out.println("Setting endpoint address ...");
			BindingProvider bindingProvider = (BindingProvider) port;
			Map<String, Object> requestContext = bindingProvider.getRequestContext();
			requestContext.put(ENDPOINT_ADDRESS_PROPERTY, wsURL);
		}
	}

	// remote invocation methods ----------------------------------------------

	@Override
	public void addLocation(LocationView locationToAdd) {
		port.addLocation(locationToAdd);
	}

	@Override
	public List<LocationView> listLocations() {
		return port.listLocations();
	}

	@Override
	public String ping(String name) {
		return port.ping(name);
	}

	@Override
	public void clear() {
		port.clear();
	}

	@Override
	public void imAlive() { port.imAlive(); }
}
