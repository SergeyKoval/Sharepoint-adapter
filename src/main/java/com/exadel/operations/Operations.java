package com.exadel.operations;

import com.exadel.soap.ListsRequest;
import com.microsoft.schemas.sharepoint.soap.*;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * This is a proxy class that provides simple interface for interacting with SharePoint
 */
public class Operations {

    private ListsSoap listsSoap;
    private CopySoap copySoap;

    private static Operations operations;


    private Operations(String login, String password) throws Exception {
        listsSoapAuthentication(login, password);
        copySoapAuthentication(login, password);
    }


    public void addDocumentLibrary(String libraryName, String libraryDescription) {
        listsSoap.addList(libraryName, libraryDescription, 101);
    }


    public void displaySPList(String listName, List<String> listColumnNames, String rowLimit) throws Exception {
        if (listsSoap != null && listName != null && listColumnNames != null && rowLimit != null) {
            try {
                String viewName = "";
                GetListItems.ViewFields viewFields = null;
                GetListItems.Query query = null;
                GetListItems.QueryOptions queryOptions = null;
                String webID = "";

                GetListItemsResponse.GetListItemsResult result = listsSoap.getListItems(listName, viewName, query,
                        viewFields, rowLimit, queryOptions, webID);
                Object resultContent = result.getContent().get(0);

                if ((resultContent != null) && (resultContent instanceof ElementNSImpl)) {
                    ElementNSImpl node = (ElementNSImpl) resultContent;

                    //Print the retrieved info in the console (just for debugging)
                    Document document = node.getOwnerDocument();
                    System.out.println("Response for getListItems");
                    System.out.println(xmlToString(document));

                    //selects a list of nodes which have z:row elements
                    NodeList list = node.getElementsByTagName("z:row");
                    System.out.println("There are " + list.getLength() + " files exist");

                    //Displaying meta-data for every file
                    for (int i = 0; i < list.getLength(); i++) {

                        //Gets the attributes of the current row/element
                        NamedNodeMap attributes = list.item(i).getAttributes();
                        System.out.println("Item ID: " + attributes.getNamedItem("ows_ID").getNodeValue());

                        //Displays all the attributes of the list item that correspond to the column names given
                        for (String columnName : listColumnNames) {
                            String internalColumnName = "ows_" + columnName;

                            if (attributes.getNamedItem(internalColumnName) != null) {
                                System.out.println(columnName + ": " + attributes.getNamedItem(internalColumnName).getNodeValue());
                            } else {
                                throw new Exception("Couldn't find the '" + columnName +
                                        "' column in the '" + listName + "' list in SharePoint.\n");
                            }
                        }
                    }
                } else {
                    throw new Exception(listName + " list response from SharePoint is either null or corrupt\n");
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public void downloadSPFile(String sourceURL, String destinationPath) throws IOException {
        Holder<Long> getItemResult = new Holder<Long>();
        getItemResult.value = new Long(20);

        Holder<FieldInformationCollection> fields = new Holder<FieldInformationCollection>();
        FieldInformationCollection fcol = new FieldInformationCollection();
        fcol.getFieldInformation().add(new FieldInformation());
        fields.value = fcol;

        Holder<byte[]> stream = new Holder<byte[]>();
        stream.value = new byte[]{};

        copySoap.getItem(sourceURL, getItemResult, fields, stream);

        FileOutputStream fos = new FileOutputStream(new File(destinationPath));
        fos.write(stream.value);
        fos.close();

        System.out.println(getItemResult.value + " " + stream.value.length);
    }

    public void uploadSPFile(String filePath, String destinationURL) throws Exception {
        try {
            String sourceURL = "file:///" + filePath;
            File file = new File(filePath);
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] stream = new byte[inputStream.available()];
            inputStream.read(stream);

            DestinationUrlCollection destinationUrls = new DestinationUrlCollection();
            destinationUrls.getString().add(destinationURL);

            FieldInformationCollection infCollection = new FieldInformationCollection();

            FieldInformation fInf = new FieldInformation();
            fInf.setDisplayName(file.getName());
            fInf.setInternalName(file.getName());

            infCollection.getFieldInformation().add(fInf);

            Holder<Long> copyIntoItemsResult = new Holder<Long>();
            copyIntoItemsResult.value = new Long(0);

            Holder<CopyResultCollection> copyResultCollectionHolder = new Holder<CopyResultCollection>();
            copyResultCollectionHolder.value = new CopyResultCollection();
            copyResultCollectionHolder.value.getCopyResult().add(new CopyResult());

            copySoap.copyIntoItems(sourceURL, destinationUrls, infCollection, stream, copyIntoItemsResult, copyResultCollectionHolder);

            String errorMessage = copyResultCollectionHolder.value.getCopyResult().get(0)
                    .getErrorMessage();

            System.out.println("Upload error message: " + errorMessage);
        } catch (Exception ex) {
            throw new Exception("Error: " + ex.toString());
        }
    }


    public void deleteListItem(String listName, HashMap<String, String> itemAttributes) {
        if ((listsSoap != null) && (listName != null) && (itemAttributes != null) && (!itemAttributes.isEmpty())) {
            try {
                ListsRequest deleteRequest = new ListsRequest("Delete");
                deleteRequest.fillMethodFields(itemAttributes);

                System.out.println("REQUEST:"
                        + xmlToString(deleteRequest.getRootDocument()));

                UpdateListItems.Updates updates = new UpdateListItems.Updates();
                Object docObj = (Object) deleteRequest.getRootDocument().getDocumentElement();
                updates.getContent().add(docObj);

                UpdateListItemsResponse.UpdateListItemsResult result = listsSoap.updateListItems(listName, updates);

                System.out.println("RESPONSE : "
                        + xmlToString((org.w3c.dom.Document) (((ElementNSImpl) result.getContent().get(0)).getOwnerDocument())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void listsSoapAuthentication(String login, String password) throws Exception {
        Lists service = new Lists();
        listsSoap = service.getListsSoap();
        serviceAuthentication(listsSoap, login, password);
    }


    private void copySoapAuthentication(String login, String password) throws Exception {
        Copy service = new Copy();
        copySoap = service.getCopySoap();
        serviceAuthentication(copySoap, login, password);
    }


    private void serviceAuthentication(Object soap, String login, String password) throws Exception {
        if ((login != null) && (password != null)) {
            ((BindingProvider) soap).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, login);
            ((BindingProvider) soap).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
        } else {
            throw new Exception("Invalid credentials was given");
        }
    }

    /**
     * This method is just for debugging
     *
     * @param docToString Document which will be transformed into string
     * @return String representation
     */
    public static String xmlToString(Document docToString) {
        StringBuilder result = new StringBuilder("@@@@@@ BEGIN @@@@@@\n");

        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter strWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(strWriter);
            DOMSource source = new DOMSource(docToString);
            transformer.transform(source, streamResult);
            String xmlString = strWriter.toString();

            result.append(xmlString);
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }

        result.append("@@@@@@ END @@@@@@\n");
        return result.toString();
    }


    public static Operations getInstance(String login, String password) throws Exception {
        if (operations == null) {
            operations = new Operations(login, password);
        }

        return operations;
    }
}
