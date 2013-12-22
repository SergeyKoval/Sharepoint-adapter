
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
@WebServiceClient(name = "Permissions", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/directory/", wsdlLocation = "file:/C:/Users/pmitrafanau/IdeaProjects/SPPermissions/src/main/resources/permissions.wsdl")
public class Permissions
    extends Service
{

    private final static URL PERMISSIONS_WSDL_LOCATION;
    private final static WebServiceException PERMISSIONS_EXCEPTION;
    private final static QName PERMISSIONS_QNAME = new QName("http://schemas.microsoft.com/sharepoint/soap/directory/", "Permissions");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Users/pmitrafanau/IdeaProjects/SPPermissions/src/main/resources/permissions.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PERMISSIONS_WSDL_LOCATION = url;
        PERMISSIONS_EXCEPTION = e;
    }

    public Permissions() {
        super(__getWsdlLocation(), PERMISSIONS_QNAME);
    }

    public Permissions(WebServiceFeature... features) {
        super(__getWsdlLocation(), PERMISSIONS_QNAME, features);
    }

    public Permissions(URL wsdlLocation) {
        super(wsdlLocation, PERMISSIONS_QNAME);
    }

    public Permissions(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PERMISSIONS_QNAME, features);
    }

    public Permissions(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Permissions(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PermissionsSoap
     */
    @WebEndpoint(name = "PermissionsSoap")
    public PermissionsSoap getPermissionsSoap() {
        return super.getPort(new QName("http://schemas.microsoft.com/sharepoint/soap/directory/", "PermissionsSoap"), PermissionsSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PermissionsSoap
     */
    @WebEndpoint(name = "PermissionsSoap")
    public PermissionsSoap getPermissionsSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.microsoft.com/sharepoint/soap/directory/", "PermissionsSoap"), PermissionsSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns PermissionsSoap
     */
    @WebEndpoint(name = "PermissionsSoap12")
    public PermissionsSoap getPermissionsSoap12() {
        return super.getPort(new QName("http://schemas.microsoft.com/sharepoint/soap/directory/", "PermissionsSoap12"), PermissionsSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PermissionsSoap
     */
    @WebEndpoint(name = "PermissionsSoap12")
    public PermissionsSoap getPermissionsSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.microsoft.com/sharepoint/soap/directory/", "PermissionsSoap12"), PermissionsSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PERMISSIONS_EXCEPTION!= null) {
            throw PERMISSIONS_EXCEPTION;
        }
        return PERMISSIONS_WSDL_LOCATION;
    }

}
