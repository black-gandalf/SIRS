package pt.ulisboa.tecnico.securechildlocator.ws;


import pt.ulisboa.tecnico.securechildlocator.domain.SecureChildLocator;
import pt.ulisboa.tecnico.securechildlocator.ws.cli.SecureChildLocatorClient;
import pt.ulisboa.tecnico.securechildlocator.ws.cli.SecureChildLocatorClientException;

import java.util.TimerTask;

public class LifeProof extends TimerTask {
    /** period that the function imAlive is called */
    public static final int IM_ALIVE_SECONDS = 1;
    /** tell if it is the primary mediator */
    private boolean primary;
    /** tell if the secondary is now the primary mediator */
    public static boolean secondaryAsPrimary;
    /** end point manager */
    private SecureChildLocatorEndpointManager endpoint;

    public LifeProof(SecureChildLocatorEndpointManager endpoint, boolean primary) {
        this.endpoint = endpoint;
        this.primary = primary;
        this.secondaryAsPrimary = false;
    }

    @Override
    public void run() {
        if (primary == true) {
            // Primary mediator
            try {

                SecureChildLocatorClient client = new SecureChildLocatorClient("http://localhost:8082/securechildlocator-ws/endpoint");
                client.imAlive();
            } catch (SecureChildLocatorClientException sce) {
                System.out.println("Backup server not found!");
            }
        } else {
            // Secondary mediator
            long timeStamp = SecureChildLocator.getInstance().getTimestamp();
            long dt = System.currentTimeMillis() - timeStamp;

            if (secondaryAsPrimary == true)
                return;
            if (dt > IM_ALIVE_SECONDS * 1000) {
                // Primary mediator is down
                try {
                    System.out.println("BackUp Server allocated to ");
                    endpoint.changeURL("http://localhost:8081/securechildlocator-ws/endpoint");
                    endpoint.start(); // allocate backup server in 8081 URL
                    secondaryAsPrimary = true;
                } catch (Exception e) {
                    System.out.println("Could not allocate backup server to url uddi http://localhost:8081/securechildlocator-ws/endpoint");
                }
            }
        }

    }

}

