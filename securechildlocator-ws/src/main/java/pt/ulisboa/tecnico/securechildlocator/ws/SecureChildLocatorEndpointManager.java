package pt.ulisboa.tecnico.securechildlocator.ws;

import java.io.IOException;

import javax.xml.ws.Endpoint;


/** End point manager */
public class SecureChildLocatorEndpointManager {

	/** Web Service location to publish */
	private String wsURL = null;

	private String Wsi = null;

	/** Port implementation */
	private SecureChildLocatorPortImpl portImpl = new SecureChildLocatorPortImpl(this);

	/** Obtain Port implementation */
	public SecureChildLocatorPortType getPort() {
		return portImpl;
	}

	/** Web Service end point */
	private Endpoint endpoint = null;

	/** output option **/
	private boolean verbose = true;

	public void setWsi(String Wsi) { this.Wsi = Wsi;}

	public String getWsi() { return this.Wsi; }

	public void changeURL(String wsURL){ this.wsURL = wsURL;}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	private boolean primary = false;

	public boolean isPrimary() {
		return primary;
	}

	/** constructor with provided web service URL */
	public SecureChildLocatorEndpointManager(String wsURL) {
		if (wsURL == null)
			throw new NullPointerException("Web Service URL cannot be null!");
		this.wsURL = wsURL;
	}

	/* end point management */

	public void start() throws Exception {
		try {
			// publish end point
			endpoint = Endpoint.create(this.portImpl);
			if (verbose) {
				System.out.printf("Starting %s%n", wsURL);
			}
			endpoint.publish(wsURL);
		} catch (Exception e) {
			endpoint = null;
			if (verbose) {
				System.out.printf("Caught exception when starting: %s%n", e);
				e.printStackTrace();
			}
			throw e;
		}
	}

	public void awaitConnections() {
		if (verbose) {
			System.out.println("Awaiting connections");
			System.out.println("Press enter to shutdown");
		}
		try {
			System.in.read();
		} catch (IOException e) {
			if (verbose) {
				System.out.printf("Caught i/o exception when awaiting requests: %s%n", e);
			}
		}
	}

	public void stop() throws Exception {
		try {
			if (endpoint != null) {
				// stop end point
				endpoint.stop();
				if (verbose) {
					System.out.printf("Stopped %s%n", wsURL);
				}
			}
		} catch (Exception e) {
			if (verbose) {
				System.out.printf("Caught exception when stopping: %s%n", e);
			}
		}
		this.portImpl = null;
	}

}
