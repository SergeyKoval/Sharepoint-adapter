package com.exadel.soap;

import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * This class provides SOAP request for SharePoint Lists web-services methods
 */
public class BatchNode {

    private Document rootDocument;
    private Element rootDocContent;

    /**
     * @param requestType Valid values: new, update, delete
     * @throws Exception
     */
    public BatchNode(String requestType) throws Exception {
        if (requestType != null) {
            if (requestType.equals("New") || requestType.equals("Update") || requestType.equals("Delete")) {
                try {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
                    rootDocument = docBuilder.newDocument();

                    Element rootElement = rootDocument.createElement("Batch");
                    rootDocument.appendChild(rootElement);

                    rootElement.setAttribute("ListVersion", "1");
                    rootElement.setAttribute("OnError", "Continue");

                    rootDocContent = rootDocument.createElement("Method");
                    rootDocContent.setAttribute("Cmd", requestType);
                    rootDocContent.setAttribute("ID", "1");

                    rootDocument.getElementsByTagName("Batch").item(0).appendChild(rootDocContent);
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                    throw (new Exception(ex.toString()));
                }
            } else {
                String err = "Unsupported request type";
                throw (new Exception(err));
            }
        } else {
            String err = "Null parameters";
            throw (new Exception(err));
        }
    }

    /**
     *
     * @param fields Pairs (fieldName, fieldValue)
     * @return
     */
    public boolean fillMethodFields(HashMap<String, String> fields) {
        if ((fields != null) && (getRootDocContent() != null) &&
                (getRootDocument() != null) && (!fields.isEmpty())) {

            Element createdElement = null;

            for (Map.Entry<String, String> fieldAttr : fields.entrySet()) {
                createdElement = getRootDocument().createElement("Field");
                createdElement.setAttribute("Name", fieldAttr.getKey());
                Text attributeValue = getRootDocument().createTextNode(fieldAttr.getValue());
                createdElement.appendChild(attributeValue);

                getRootDocContent().appendChild(createdElement);
            }

            return true;
        }

        return false;
    }


    public Document getRootDocument() {
        return rootDocument;
    }


    public Element getRootDocContent() {
        return rootDocContent;
    }
}
