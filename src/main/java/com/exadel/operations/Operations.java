package com.exadel.operations;

import com.exadel.constants.LibraryItemType;
import com.exadel.entities.LibraryDescription;
import com.exadel.entities.LibraryItem;
import com.exadel.soap.BatchNode;
import com.exadel.soap.ListNode;
import com.exadel.soap.QueryOptionsNode;
import com.microsoft.schemas.sharepoint.soap.*;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * This is a proxy class that provides simple interface for interacting with SharePoint
 */
public class Operations {

    private ListsSoap listsSoap;
    private CopySoap copySoap;

    private String siteUrl;

    private static int SERVER_DOCLIB_TEMPLATE = 101;


    public Operations(String login, String password, String siteUrl, String wsdlLocation) throws Exception {
        listsSoapAuthentication(login, password, wsdlLocation);
        copySoapAuthentication(login, password, wsdlLocation);

        this.siteUrl = siteUrl;
    }


    public void addDocumentLibrary(String libraryName, String libraryDescription) {
        listsSoap.addList(libraryName, libraryDescription, SERVER_DOCLIB_TEMPLATE);
    }


    public void addLibraryToQuickLaunch(String libraryName) throws Exception {
        HashMap<String, String> libraryProperties = new HashMap<String, String>();
        libraryProperties.put("OnQuickLaunch", "TRUE");

        setLibraryProperties(libraryName, libraryProperties);
    }


    public void setLibraryProperties(String libraryName, HashMap<String, String> properties) throws Exception {
        ListNode listNode = new ListNode();
        listNode.fillListAttributes(properties);

        UpdateList.ListProperties listProperties = new UpdateList.ListProperties();
        listProperties.getContent().add(listNode.getRootDocument().getDocumentElement());

        listsSoap.updateList(libraryName, listProperties, null, null, null, null);
    }


    public void deleteLibrary(String libraryName) {
        listsSoap.deleteList(libraryName);
    }


    public List<LibraryDescription> getDescrForAvailableDocLibs() {
        List<LibraryDescription> descriptions = new LinkedList<LibraryDescription>();

        GetListCollectionResponse.GetListCollectionResult result = listsSoap.getListCollection();
        Object resultContent = result.getContent().get(0);

        if ((resultContent != null) && (resultContent instanceof ElementNSImpl)) {
            ElementNSImpl node = (ElementNSImpl) resultContent;

            /* Use this code to see what does result contains
            Document document = node.getOwnerDocument();
            System.out.println("Response for getExistingLists");
            System.out.println(xmlToString(document));
            */

            NodeList list = node.getElementsByTagName("List");

            for (int i = 0; i < list.getLength(); i++) {
                NamedNodeMap attributes = list.item(i).getAttributes();
                Node serverTemplate = attributes.getNamedItem("ServerTemplate");

                if ((serverTemplate != null) &&
                        (serverTemplate.getNodeValue().equals(String.valueOf(SERVER_DOCLIB_TEMPLATE)))) {
                    LibraryDescription libraryDescription = new LibraryDescription();
                    libraryDescription.setTitle(attributes.getNamedItem("Title").getNodeValue());
                    libraryDescription.setDescription(attributes.getNamedItem("Description").getNodeValue());
                    libraryDescription.setItemsCount(Integer.parseInt(attributes.getNamedItem("ItemCount").getNodeValue()));

                    descriptions.add(libraryDescription);
                }
            }
        }

        return descriptions;
    }


    public List<LibraryItem> getLibraryItems(String listName, String rowLimit) throws Exception {
        return getLibraryItems(listName, rowLimit, null);
    }


    public List<LibraryItem> getFolderItems(String listName, String folderUrl,  String rowLimit) throws Exception {
        HashMap<String, String> elements = new HashMap<String, String>();
        elements.put("Folder", folderUrl);

        QueryOptionsNode qoNode = new QueryOptionsNode();
        qoNode.fillQueryOptionsElements(elements);

        GetListItems.QueryOptions queryOptions = new GetListItems.QueryOptions();
        queryOptions.getContent().add(qoNode.getRootDocument().getDocumentElement());

        return getLibraryItems(listName, rowLimit, queryOptions);
    }


    public void downloadFile(String sourceURL, String destinationPath) throws IOException {
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
    }


    public void uploadFile(String filePath, String destinationURL) throws Exception {
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


    public void deleteLibraryFile(String libraryName, String fileName) {
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("ID", "1"); // It isn't used (We can put here any id)
        fields.put("FileRef", siteUrl + "/" + libraryName + "/" + fileName);

        deleteLibraryItem(libraryName, fields);
    }


    public void deleteLibraryFolder(String libraryName, String folderName) {
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("ID", "1"); // It isn't used (We can put here any id)
        fields.put("FileRef", siteUrl + "/" + libraryName + "/" + folderName);

        deleteLibraryItem(libraryName, fields);
    }


    public void createNewFolder(String listName, String folderName) {

        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("ID", "New");
        fields.put("FSObjType", "1");
        fields.put("BaseName", folderName);

        if (listName != null) {
            try {
                BatchNode deleteRequest = new BatchNode("New");
                deleteRequest.fillMethodFields(fields);

                UpdateListItems.Updates updates = new UpdateListItems.Updates();
                Object docObj = (Object) deleteRequest.getRootDocument().getDocumentElement();
                updates.getContent().add(docObj);

                UpdateListItemsResponse.UpdateListItemsResult result = listsSoap.updateListItems(listName, updates);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private List<LibraryItem> getLibraryItems(String listName, String rowLimit, GetListItems.QueryOptions queryOptions) throws Exception {
        List<LibraryItem> libraryItems = new LinkedList<LibraryItem>();

        if (listsSoap != null && listName != null && rowLimit != null) {
            try {
                String viewName = "";
                GetListItems.ViewFields viewFields = null;
                GetListItems.Query query = null;
                String webID = "";

                GetListItemsResponse.GetListItemsResult result = listsSoap.getListItems(listName, viewName, query,
                        viewFields, rowLimit, queryOptions, webID);
                Object resultContent = result.getContent().get(0);

                if ((resultContent != null) && (resultContent instanceof ElementNSImpl)) {
                    ElementNSImpl node = (ElementNSImpl) resultContent;

                    // Print the retrieved info in the console (just for debugging)
                    /*
                    Document document = node.getOwnerDocument();;
                    System.out.println(xmlToString(document));
                    */


                    NodeList list = node.getElementsByTagName("z:row");

                    for (int i = 0; i < list.getLength(); i++) {
                        NamedNodeMap attributes = list.item(i).getAttributes();

                        LibraryItem libraryItem = new LibraryItem();
                        libraryItem.setItemName(attributes.getNamedItem("ows_LinkFilename").getNodeValue());

                        String fsObjType = attributes.getNamedItem("ows_FSObjType").getNodeValue();
                        int objType = Integer.parseInt(fsObjType.substring(fsObjType.indexOf('#') + 1));

                        switch (objType) {
                            case 0:
                                libraryItem.setType(LibraryItemType.FILE);
                                break;
                            case 1:
                                libraryItem.setType(LibraryItemType.FOLDER);
                                break;
                        }

                        libraryItems.add(libraryItem);
                    }
                } else {
                    throw new Exception(listName + " list response from SharePoint is either null or corrupt\n");
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }

        return libraryItems;
    }


    private void deleteLibraryItem(String libraryName, HashMap<String, String> fields) {
        if ((listsSoap != null) && (libraryName != null) && (fields != null) && (!fields.isEmpty())) {
            try {
                BatchNode deleteRequest = new BatchNode("Delete");
                deleteRequest.fillMethodFields(fields);

                UpdateListItems.Updates updates = new UpdateListItems.Updates();
                Object docObj = (Object) deleteRequest.getRootDocument().getDocumentElement();
                updates.getContent().add(docObj);

                UpdateListItemsResponse.UpdateListItemsResult result = listsSoap.updateListItems(libraryName, updates);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void listsSoapAuthentication(String login, String password, String wsdlLocation) throws Exception {
        Lists service = new Lists(new URL(new File(wsdlLocation + "lists.wsdl").toURI().toURL().toExternalForm()),
                new QName("http://schemas.microsoft.com/sharepoint/soap/", "Lists"));
        listsSoap = service.getListsSoap();
        serviceAuthentication(listsSoap, login, password);
    }


    private void copySoapAuthentication(String login, String password, String wsdlLocation) throws Exception {
        Copy service = new Copy(new URL(new File(wsdlLocation + "copy.wsdl").toURI().toURL().toExternalForm()),
                new QName("http://schemas.microsoft.com/sharepoint/soap/", "Copy"));
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
}
