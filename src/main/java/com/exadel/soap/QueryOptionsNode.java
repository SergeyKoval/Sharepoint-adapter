package com.exadel.soap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;


public class QueryOptionsNode {

    private Document rootDocument;


    public QueryOptionsNode() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        rootDocument = docBuilder.newDocument();

        Element rootElement = rootDocument.createElement("QueryOptions");
        rootDocument.appendChild(rootElement);
    }

    /**
     * @param fields Pairs (fieldName, fieldValue)
     * @return
     */
    public void fillMethodFields(HashMap<String, String> fields) {
        Node rootElement = rootDocument.getElementsByTagName("QueryOptions").item(0);
        Element createdElement = null;

        for (Map.Entry<String, String> fieldAttr : fields.entrySet()) {
            createdElement = getRootDocument().createElement(fieldAttr.getKey());
            Text attributeValue = getRootDocument().createTextNode(fieldAttr.getValue());
            createdElement.appendChild(attributeValue);

            rootElement.appendChild(createdElement);
        }
    }

    public Document getRootDocument() {
        return rootDocument;
    }
}