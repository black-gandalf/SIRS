package pt.ulisboa.tecnico.securechildlocator.security.handler;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.security.Key;
import javax.crypto.Cipher;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

import org.w3c.dom.NodeList;
import pt.ulisboa.tecnico.securechildlocator.security.CryptoUtil;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;



public class AEScypherHandler implements SOAPHandler<SOAPMessageContext> {
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
        if (outbound) {
            System.out.println("OUTbound");

            //cipher = null;

            try {
                SOAPMessage soapMessage = smc.getMessage();
                SOAPPart soapPart = soapMessage.getSOAPPart();
                SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
                SOAPBody spBody = soapEnvelope.getBody();
                NodeList nodeList = spBody.getElementsByTagNameNS("http://ws.securechildlocator.tecnico.ulisboa.pt/","addLocation");
                if (nodeList.getLength() != 0) {
                    NodeList nodeList1 = nodeList.item(0).getChildNodes();
                    NodeList nodeList2 = nodeList1.item(0).getChildNodes();
                    String latitudeEncripted = CryptoUtil.cipherString(nodeList2.item(0).getTextContent(),1);
                    nodeList2.item(0).setTextContent(latitudeEncripted);
                    String longitudeEncripted = CryptoUtil.cipherString(nodeList2.item(1).getTextContent(),1);
                    nodeList2.item(1).setTextContent(longitudeEncripted);
                }


            }catch(Exception e){
                System.out.printf("Caught exception while LLLLLLLchypering the message: %s%n", e);
            }
        }
        else {
            System.out.println(" INbound");
            try{
                SOAPMessage soapMessage1 = smc.getMessage();
                SOAPPart soapPart = soapMessage1.getSOAPPart();
                SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
                SOAPBody spBody = soapEnvelope.getBody();
                NodeList nodeList = spBody.getElementsByTagNameNS("http://ws.securechildlocator.tecnico.ulisboa.pt/", "addLocation");
                if (nodeList.getLength()!= 0) {
                    NodeList nodeList1 = nodeList.item(0).getChildNodes();
                    NodeList nodeList2 = nodeList1.item(0).getChildNodes();
                    String latitudeDecripted = CryptoUtil.decipherString(nodeList2.item(0).getTextContent(),1);
                    nodeList2.item(0).setTextContent(latitudeDecripted);
                    String longitudeDecripted = CryptoUtil.decipherString(nodeList2.item(1).getTextContent(),1);
                    nodeList2.item(1).setTextContent(longitudeDecripted);
                }

                NodeList nodeList3 = spBody.getElementsByTagNameNS("http://ws.securechildlocator.tecnico.ulisboa.pt/","listLocationsResponse");
                if (nodeList3.getLength() != 0){
                    System.out.println("YAH ENTROU");
                    NodeList locations = nodeList3.item(0).getChildNodes();
                    int i = 0;
                    while(locations.item(i) != null){
                        NodeList latitudeAndLongitude = locations.item(i).getChildNodes();
                        String latitudeDecripted = CryptoUtil.decipherString(latitudeAndLongitude.item(0).getTextContent(),1);
                        latitudeAndLongitude.item(0).setTextContent(latitudeDecripted);
                        String longitudeDecripted = CryptoUtil.decipherString(latitudeAndLongitude.item(1).getTextContent(),1);
                        latitudeAndLongitude.item(1).setTextContent(longitudeDecripted);
                        i ++;
                        System.out.println("lalala");
                    }
                }


            }catch(Exception e){
                System.out.printf("Caught exception while dechypering the message: %s%n", e);
            }
        }

        return true;
    }

    /** The handleFault method is invoked for fault message processing. */
    @Override
    public boolean handleFault(SOAPMessageContext smc) {
        System.out.println("ERRO");
        logToSystemOut(smc);
        return true;
    }

    /**
     * Called at the conclusion of a message exchange pattern just prior to the
     * JAX-WS runtime dispatching a message, fault or exception.
     */
    @Override
    public void close(MessageContext messageContext) {
        System.out.println("fechou");
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
