package com.exadel.soap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;


public class ListNode {

    private Document rootDocument;


    public ListNode() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        rootDocument = docBuilder.newDocument();

        Element rootElement = rootDocument.createElement("List");
        rootDocument.appendChild(rootElement);
    }

    /**
     * @param attributes Pairs (attributeName, attributeValue)
     * @return
     */
    public void fillListAttributes(HashMap<String, String> attributes) {
        Element rootElement = (Element) rootDocument.getElementsByTagName("List").item(0);

        for (Map.Entry<String, String> attribute : attributes.entrySet()) {
            rootElement.setAttribute(attribute.getKey(), attribute.getValue());
        }
    }

    public Document getRootDocument() {
        return rootDocument;
    }
}