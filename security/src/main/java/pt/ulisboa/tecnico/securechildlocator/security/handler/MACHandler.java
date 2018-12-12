package pt.ulisboa.tecnico.securechildlocator.security.handler;

import pt.ulisboa.tecnico.securechildlocator.security.CryptoUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.soap.*;
import java.util.Iterator;

/**
 * This SOAPHandler outputs the contents of inbound and outbound messages.
 */
public class MACHandler implements SOAPHandler<SOAPMessageContext> {
    private String stringToBeCiphered = "imsecure";
    //
    // Handler interface implementation
    //

    /**
     * Gets the header blocks that can be processed by this Handler instance. If
     * null, processes all.
     */
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    /**
     * The handleMessage method is invoked for normal processing of inbound and
     * outbound messages.
     */
    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outbound = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // print current timestamp
        System.out.print("[");
        System.out.print(dateFormatter.format(new Date()));
        System.out.print("] ");

        System.out.print("intercepted ");
        if (outbound) {
            System.out.print("OUTbound");
            try{
                SOAPMessage soapMessage = smc.getMessage();
                SOAPPart soapPart = soapMessage.getSOAPPart();
                SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
                SOAPHeader soapHeader = soapEnvelope.getHeader();
                if (soapHeader == null) {
                    soapHeader = soapEnvelope.addHeader();
                }
                Name name = soapEnvelope.createName("mac", "m", "mac");
                SOAPHeaderElement element = soapHeader.addHeaderElement(name);
                String checkString = CryptoUtil.cipherStringUsingMAC(stringToBeCiphered,2);
                element.addTextNode(checkString);

            }catch(Exception e){
                System.out.printf("Caught exception while doing HMAC: %s%n", e);
            }


        }
        else {
            System.out.print("INbound");
            try {
                SOAPMessage soapMessage = smc.getMessage();
                SOAPPart soapPart = soapMessage.getSOAPPart();
                SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
                SOAPHeader soapHeader = soapEnvelope.getHeader();
                Name name = soapEnvelope.createName("mac", "m", "mac");
                Iterator itr = soapHeader.getChildElements(name);
                if (!itr.hasNext()) {
                    System.out.println("MAC not found");
                    return true;
                }
                SOAPElement element = (SOAPElement) itr.next();
                String valueString = element.getValue();
                String checkString = CryptoUtil.cipherStringUsingMAC(stringToBeCiphered,2);
                if (!valueString.equals(checkString)){
                    System.out.println("MESSAGE MODIFIED");
                    System.exit(-1);
                }

            }catch(Exception e){
                System.out.printf("Caught exception while doing HMAC: %s%n", e);
            }
        }
        logToSystemOut(smc);
        return true;
    }

    /** The handleFault method is invoked for fault message processing. */
    @Override
    public boolean handleFault(SOAPMessageContext smc) {
        logToSystemOut(smc);
        return true;
    }

    /**
     * Called at the conclusion of a message exchange pattern just prior to the
     * JAX-WS runtime dispatching a message, fault or exception.
     */
    @Override
    public void close(MessageContext messageContext) {
        // nothing to clean up
    }

    /** Date formatter used for outputting timestamps in ISO 8601 format */
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    /**
     * Check the MESSAGE_OUTBOUND_PROPERTY in the context to see if this is an
     * outgoing or incoming message. Write a brief message to the print stream
     * and output the message. The writeTo() method can throw SOAPException or
     * IOException
     */
    private void logToSystemOut(SOAPMessageContext smc) {
        Boolean outbound = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // print current timestamp
        System.out.print("[");
        System.out.print(dateFormatter.format(new Date()));
        System.out.print("] ");

        System.out.print("intercepted ");
        if (outbound)
            System.out.print("OUTbound");
        else
            System.out.print(" INbound");
        System.out.println(" SOAP message:");

        SOAPMessage message = smc.getMessage();
        try {
            message.writeTo(System.out);
            System.out.println(); // add a newline after message

        } catch (SOAPException se) {
            System.out.print("Ignoring SOAPException in handler: ");
            System.out.println(se);
        } catch (IOException ioe) {
            System.out.print("Ignoring IOException in handler: ");
            System.out.println(ioe);
        }
    }

}
