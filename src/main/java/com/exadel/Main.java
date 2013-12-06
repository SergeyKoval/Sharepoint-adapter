package com.exadel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.Authenticator;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import com.exadel.authenticator.NTLMAuthenticator;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.microsoft.schemas.sharepoint.soap.*;

public class Main {

    private static String username = "username";
    private static String password = "password";

    private static String spSiteURL = "http://192.168.43.167:42553";

    private static ListsSoap listsSoap;
    private static VersionsSoap versionsSoap;
    private static CopySoap copySoap;


    public static void main(String[] args) {
        try {
            Authenticator.setDefault(new NTLMAuthenticator(username, password));

            initSPListSoap(username, password, spSiteURL);
            initSPVersionsSoap(username, password, spSiteURL);
            initSPCopySoap(username, password, spSiteURL);

            displaySharePointList();

            // String checkoutURL = spSiteURL + "/Documents/48.jpg";
            // checkOutFile(listsSoap, checkoutURL);
            // undoCheckOutFile(listsSoap, checkoutURL);
            // checkOutFile(listsSoap, checkoutURL);

            // UploadFile("D:/46.jpg");

            // checkInFile(listsSoap, checkoutURL, "Test Checkin");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }
    }


    public static URL convertToURLEscapingIllegalCharacters(String string){
        try {
            String decodedURL = URLDecoder.decode(string, "UTF-8");

            URL url = new URL(decodedURL);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
                    url.getPath(), url.getQuery(), url.getRef());

            return uri.toURL();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static void initSPListSoap(String username, String password, String url) throws Exception {
        if (username != null && password != null) {
            try {
                URL wsdlURL = new URL(Main.class.getClassLoader().getResource("lists.wsdl").toExternalForm());
                Lists service = new Lists(wsdlURL);
                listsSoap = service.getListsSoap();

                ((BindingProvider) listsSoap).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
                ((BindingProvider) listsSoap).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);

                URL convertedURL = convertToURLEscapingIllegalCharacters(url + "/_vti_bin/Lists.asmx");
                ((BindingProvider) listsSoap).getRequestContext().put(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY, convertedURL.toString());
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            throw new Exception("invalid connection details given.");
        }
    }


    public static void initSPVersionsSoap(String userName, String password, String url) throws Exception {
        if (userName != null && password != null) {
            try {
                URL wsdlURL = new URL(Main.class.getClassLoader().getResource("versions.wsdl").toExternalForm());
                Versions service = new Versions(wsdlURL);
                versionsSoap = service.getVersionsSoap();

                ((BindingProvider) versionsSoap).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, userName);
                ((BindingProvider) versionsSoap).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);

                URL convertedURL = convertToURLEscapingIllegalCharacters(url + "/_vti_bin/Versions.asmx");
                ((BindingProvider) versionsSoap).getRequestContext().put(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY, convertedURL.toString());
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new Exception("invalid connection details given.");
        }
    }


    public static void initSPCopySoap(String userName, String password, String url) throws Exception {
        if (userName != null && password != null) {
            try {
                URL wsdlURL = new URL(Main.class.getClassLoader().getResource("copy.wsdl").toExternalForm());
                Copy service = new Copy(wsdlURL);
                copySoap = service.getCopySoap();

                ((BindingProvider) copySoap).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, userName);
                ((BindingProvider) copySoap).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);

                URL convertedURL = convertToURLEscapingIllegalCharacters(url + "/_vti_bin/Copy.asmx");
                ((BindingProvider) copySoap).getRequestContext().put(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY, convertedURL.toString());
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new Exception("invalid connection details given.");
        }
    }


    public static void displaySharePointList() throws Exception {
        try {
            String listName = "Documents";
            String rowLimit = "150";

            ArrayList<String> listColumnNames = new ArrayList<String>();
            listColumnNames.add("LinkFilename");
            listColumnNames.add("FileRef");

            String viewName = "";
            GetListItems.ViewFields viewFields = null;
            GetListItems.Query query = null;
            GetListItems.QueryOptions queryOptions = null;
            String webID = "";

            GetListItemsResponse.GetListItemsResult result = listsSoap.getListItems(listName, viewName, query,
                    viewFields, rowLimit, queryOptions, webID);
            Object listResult = result.getContent().get(0);

            if ((listResult != null) && (listResult instanceof Element)) {
                Element node = (Element) listResult;

                NodeList list = node.getElementsByTagName("z:row");
                System.out.println("=> " + list.getLength() + " results from SharePoint Online");

                for (int i = 0; i < list.getLength(); i++) {

                    //Gets the attributes of the current row/element
                    NamedNodeMap attributes = list.item(i).getAttributes();
                    System.out.println("******** Item ID: " + attributes.getNamedItem("ows_ID").getNodeValue()+" ********");

                    //Displays all the attributes of the list item that correspond to the column names given
                    for (String columnName : listColumnNames) {
                        String internalColumnName = "ows_" + columnName;
                        if (attributes.getNamedItem(internalColumnName) != null) {
                            System.out.println(columnName + ": " + attributes.getNamedItem(internalColumnName).getNodeValue());
                        } else {
                            throw new Exception("Couldn't find the '" + columnName + "' column in the '" + listName + "' list in SharePoint.\n");
                        }
                    }
                }
            } else {
                throw new Exception(listName + " list response from SharePoint is either null or corrupt\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Exception. See stacktrace.\n" + ex.toString() + "\n");
        }
    }


    public static boolean checkOutFile(ListsSoap port, String pageUrl) {
        System.out.println("Checking-out pageUrl=" + pageUrl);

        String checkOutToLocal = "true";
        String lastModified    = "";

        boolean result = port.checkOutFile(pageUrl, checkOutToLocal, "6kfgkhkjt");
        System.out.println("Check-out result = " + result);

        return result;
    }                                     //2013-12-06 10:19:46


    public static boolean undoCheckOutFile(ListsSoap port, String pageUrl) {
        System.out.println("Undo checkout pageUrl=" + pageUrl);

        boolean result = port.undoCheckOut(pageUrl);
        System.out.println("Undo checkout result = " + result);

        return result;
    }


    public static boolean checkInFile(ListsSoap port, String pageUrl, String comment) {
        System.out.println("Checking-in pageUrl=" + pageUrl + " comment=" + comment);

        // checkinType = values 0, 1 or 2, where 0 = MinorCheckIn, 1 = MajorCheckIn, and 2 = OverwriteCheckIn.
        String checkinType = "0";
        boolean result = port.checkInFile(pageUrl, comment, checkinType);

        System.out.println("Check-in result = " + result);

        return result;
    }


    public static void DownloadFile(String sourceUrl, String destination, String versionNumber) throws Exception {
        try {
            String fileName = sourceUrl.substring(sourceUrl.lastIndexOf("/") + 1);

            destination = destination + "\\" + fileName;

            //Prepare the Parameters required for the method
            javax.xml.ws.Holder<FieldInformationCollection> fieldInfoArray = new javax.xml.ws.Holder<FieldInformationCollection>();
            FieldInformationCollection fic = new FieldInformationCollection();
            fic.getFieldInformation().add(new FieldInformation());
            fieldInfoArray.value = fic;

            javax.xml.ws.Holder<Long> cResultArray = new javax.xml.ws.Holder<Long>();
            javax.xml.ws.Holder<byte[]> fileContents = new javax.xml.ws.Holder<byte[]>(); // no need to initialize the GetItem takes care of that.

            //Cal Web Service Method
            copySoap.getItem(sourceUrl, cResultArray, fieldInfoArray, fileContents);

            //Write the byte[] to the output file
            //Integer val = fileContents.value;
            FileOutputStream fos = new FileOutputStream(destination);
            fos.write((byte[])fileContents.value);
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException : " + ex);
        } catch (IOException ioe) {
            System.out.println("IOException : " + ioe);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error: " + ex.toString());
        }
    }


    public static void UploadFile(String filepath) throws Exception {
        try {
            String sourceURL = "file:///D:/46.jpg";
            File file = new File("D:/46.jpg");
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] stream = new byte[inputStream.available()];
            inputStream.read(stream);

            DestinationUrlCollection destinationUrls = new DestinationUrlCollection();
            destinationUrls.getString().add("http://192.168.43.167:42553/Documents/46.jpg"); // попробовать без имени файла

            FieldInformationCollection infCollection = new FieldInformationCollection();

            FieldInformation fInf = new FieldInformation();
            fInf.setDisplayName(file.getName());
            fInf.setInternalName(file.getName());

            infCollection.getFieldInformation().add(fInf);

            copySoap.copyIntoItems(sourceURL, destinationUrls, infCollection, stream, new Holder<Long>(), new Holder<CopyResultCollection>());
            // error message always exists
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException : " + ex);
        } catch (IOException ioe) {
            System.out.println("IOException : " + ioe);
        } catch (Exception ex) {
            throw new Exception("Error: " + ex.toString());
        }
    }
}
