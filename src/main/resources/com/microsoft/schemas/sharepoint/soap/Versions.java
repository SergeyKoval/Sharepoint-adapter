
package com.microsoft.schemas.sharepoint.soap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Versions", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", wsdlLocation = "file:/D:/Temp/versions.wsdl")
public class Versions
    extends Service
{

    private final static URL VERSIONS_WSDL_LOCATION;
    private final static WebServiceException VERSIONS_EXCEPTION;
    private final static QName VERSIONS_QNAME = new QName("http://schemas.microsoft.com/sharepoint/soap/", "Versions");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/D:/Temp/versions.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        VERSIONS_WSDL_LOCATION = url;
        VERSIONS_EXCEPTION = e;
    }

    public Versions() {
        super(__getWsdlLocation(), VERSIONS_QNAME);
    }

    public Versions(WebServiceFeature... features) {
        super(__getWsdlLocation(), VERSIONS_QNAME, features);
    }

    public Versions(URL wsdlLocation) {
        super(wsdlLocation, VERSIONS_QNAME);
    }

    public Versions(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, VERSIONS_QNAME, features);
    }

    public Versions(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Versions(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns VersionsSoap
     */
    @WebEndpoint(name = "VersionsSoap")
    public VersionsSoap getVersionsSoap() {
        return super.getPort(new QName("http://schemas.microsoft.com/sharepoint/soap/", "VersionsSoap"), VersionsSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns VersionsSoap
     */
    @WebEndpoint(name = "VersionsSoap")
    public VersionsSoap getVersionsSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.microsoft.com/sharepoint/soap/", "VersionsSoap"), VersionsSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns VersionsSoap
     */
    @WebEndpoint(name = "VersionsSoap12")
    public VersionsSoap getVersionsSoap12() {
        return super.getPort(new QName("http://schemas.microsoft.com/sharepoint/soap/", "VersionsSoap12"), VersionsSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns VersionsSoap
     */
    @WebEndpoint(name = "VersionsSoap12")
    public VersionsSoap getVersionsSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.microsoft.com/sharepoint/soap/", "VersionsSoap12"), VersionsSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (VERSIONS_EXCEPTION!= null) {
            throw VERSIONS_EXCEPTION;
        }
        return VERSIONS_WSDL_LOCATION;
    }

}
